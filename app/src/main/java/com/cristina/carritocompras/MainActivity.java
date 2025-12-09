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

/**
 * Actividad principal de la aplicación que muestra el catálogo de productos.
 * Presenta una lista de productos obtenidos desde un servidor, permite
 * filtrarlos por categoría y proporciona navegación hacia otras secciones como
 * el carrito de compras, el mapa y el panel de administración.
 */
public class MainActivity extends AppCompatActivity {

    /** Lista visual para mostrar los productos */
    private RecyclerView recyclerView;
    /** Adaptador que conecta la lista de productos con el RecyclerView */
    private ProductAdapter productAdapter;
    /** Botón flotante para acceder al carrito de compras */
    private FloatingActionButton fabCart;
    /** Menú desplegable para filtrar los productos por categoría */
    private AutoCompleteTextView filterCategoryAutoComplete;

    /**
     * Se ejecuta al crear la actividad.
     * Inicializa los componentes de la interfaz, configura el menú de filtro y el listener para el botón del carrito
     */
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

    /**
     * Se ejecuta cada vez que la actividad vuelve a ser visible para el usuario.
     * Llama a "loadProducts" para asegurar que la lista de productos esté siempre actualizada,
     * (como por ejemplo después de añadir/editar un producto en el panel de administración)
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Carga todos los productos por defecto al volver a la pantalla.
        loadProducts(null);
    }

    /**
     * Configura el menú desplegable para el filtro de categorías.
     * Las categorías están definidas en "arrays.xml" y añade un listener
     * para detectar la selección de una categoría y refrescar la lista de productos.
     */
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

    /**
     * Hace una llamada a la API para obtener la lista de productos y la enseña
     * 
     * @param category La categoría por la que filtrar. Si es nulo, se obtienen todos los productos
     */
    private void loadProducts(String category) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
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

    /**
     * Carga el menú de opciones en la barra de acción
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Gestiona los clics en los elementos del menú de opciones
     */
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