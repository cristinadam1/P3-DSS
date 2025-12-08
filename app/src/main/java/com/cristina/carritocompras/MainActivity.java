package com.cristina.carritocompras;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private FloatingActionButton fabCart;
    private AutoCompleteTextView filterCategoryAutoComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fabCart = findViewById(R.id.fabCart);
        filterCategoryAutoComplete = findViewById(R.id.filterCategoryAutoComplete);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setupFilterMenu();

        fabCart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts(null);
    }

    private void setupFilterMenu() {
        List<String> categories = new ArrayList<>();
        categories.add("Todas");
        categories.addAll(List.of(getResources().getStringArray(R.array.product_categories)));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categories);
        filterCategoryAutoComplete.setAdapter(adapter);

        filterCategoryAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCategory = (String) parent.getItemAtPosition(position);
            if (selectedCategory.equals("Todas")) {
                loadProducts(null);
            } else {
                loadProducts(selectedCategory);
            }
        });
    }

    private void loadProducts(String category) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class); // CAMBIO AQUÍ
        Call<List<Product>> call = apiService.getAllProducts(category);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productAdapter = new ProductAdapter(response.body(), 
                        product -> {
                            CartManager.getInstance(MainActivity.this).addToCart(product);
                            Toast.makeText(MainActivity.this, "Añadido: " + product.getName(), Toast.LENGTH_SHORT).show();
                        },
                        product -> {
                            Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                            intent.putExtra("name", product.getName());
                            intent.putExtra("price", product.getPrice());
                            intent.putExtra("category", product.getCategory());
                            intent.putExtra("description", product.getDescription());
                            intent.putExtra("image", product.getImage());
                            startActivity(intent);
                        }
                    );
                    recyclerView.setAdapter(productAdapter);
                } else {
                    Log.e("MainActivity", "Error al cargar productos: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("MainActivity", "Error de conexión", t);
                Toast.makeText(MainActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_map) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.action_admin) {
            Intent intent = new Intent(this, AdminLoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}