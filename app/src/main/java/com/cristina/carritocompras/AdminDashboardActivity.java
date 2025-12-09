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

/**
 * Actividad principal para la gestión de productos por parte del administrador.
 * Esta pantalla muestra una lista de todos los productos y proporciona
 * las opciones para añadir, editar y eliminar productos del catálogo
 */
public class AdminDashboardActivity extends AppCompatActivity {

    /** Lista visual para mostrar los productos */
    private RecyclerView recyclerView;
    /** Adaptador que conecta los datos de los productos con el RecyclerView */
    private AdminProductAdapter adapter;
    /** Interfaz para realizar las llamadas a la API REST */
    private ApiService apiService;
    /**
     * Launcher para gestionar el resultado de la pantalla de añadir producto.
     * Se utiliza para refrescar la lista cuando se añade un nuevo producto.
     */
    private ActivityResultLauncher<Intent> addProductLauncher;
    /**
     * Launcher para gestionar el resultado de la pantalla de editar producto.
     * Se utiliza para refrescar la lista cuando se actualiza un producto.
     */
    private ActivityResultLauncher<Intent> editProductLauncher;

    /**
     * Método que se ejecuta al crear la actividad.
     * Inicializa los componentes de la interfaz, los listeners y los launchers.
     */
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

        apiService = ApiClient.getClient().create(ApiService.class);

        addProductLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    loadProducts();
                }
            });

        editProductLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    loadProducts();
                }
            });

        loadProducts();
    }

    /**
     * Obtiene la lista completa de productos desde la API y la muestra en el RecyclerView.
     * Se pasa "null" como categoría para asegurar que se obtienen todos los productos sin filtrar.
     */
    private void loadProducts() {
        apiService.getAllProducts(null).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new AdminProductAdapter(response.body(), 
                        product -> deleteProduct(product),
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

    /**
     * Prepara y lanza la actividad para editar un producto.
     * Crea un Intent, le añade los datos del producto seleccionado como extras y abre la pantalla "EditProductActivity".
     * @param product El objeto del producto sobre el que se ha pulsado "editar"
     */
    private void editProduct(Product product) {
        Intent intent = new Intent(this, EditProductActivity.class);
        intent.putExtra("productId", product.getId());
        intent.putExtra("name", product.getName());
        intent.putExtra("price", product.getPrice());
        intent.putExtra("description", product.getDescription());
        intent.putExtra("category", product.getCategory());
        editProductLauncher.launch(intent);
    }

    /**
     * Hace una llamada a la API para eliminar un producto por su ID.
     * Si la operación tiene éxito, refresca la lista de productos.
     * @param product El producto que se va a eliminar.
     */
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

    /**
     * Gestiona la navegación hacia atrás desde el botón en la barra de acción.
     * Cierra la actividad actual y vuelve a la pantalla anterior.
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}