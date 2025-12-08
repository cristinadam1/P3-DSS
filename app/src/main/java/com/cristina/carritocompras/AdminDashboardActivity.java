package com.cristina.carritocompras;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminProductAdapter adapter;
    private ApiService apiService;
    private ActivityResultLauncher<Intent> addProductLauncher;
    private ActivityResultLauncher<Intent> editProductLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.adminRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fabAddProduct);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddProductActivity.class);
            addProductLauncher.launch(intent);
        });

        apiService = RetrofitClient.getClient().create(ApiService.class);

        addProductLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    loadProducts();
                }
            });

        // Launcher para refrescar después de editar
        editProductLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    loadProducts();
                }
            });

        loadProducts();
    }

    private void loadProducts() {
        apiService.getAllProducts(null).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new AdminProductAdapter(response.body(), 
                        // Listener para borrar
                        product -> deleteProduct(product),
                        // Listener para editar
                        product -> editProduct(product)
                    );
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(AdminDashboardActivity.this, "Error al cargar productos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(AdminDashboardActivity.this, "Fallo de red", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editProduct(Product product) {
        Intent intent = new Intent(this, EditProductActivity.class);
        intent.putExtra("productId", product.getId());
        intent.putExtra("name", product.getName());
        intent.putExtra("price", product.getPrice());
        intent.putExtra("description", product.getDescription());
        intent.putExtra("category", product.getCategory());
        editProductLauncher.launch(intent);
    }

    private void deleteProduct(Product product) {
        apiService.deleteProduct(product.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AdminDashboardActivity.this, "Producto eliminado", Toast.LENGTH_SHORT).show();
                    loadProducts();
                } else {
                    Toast.makeText(AdminDashboardActivity.this, "Error al eliminar: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AdminDashboardActivity.this, "Fallo de red al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}