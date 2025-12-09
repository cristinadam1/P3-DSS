package com.cristina.carritocompras;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Actividad que gestiona la edición de un producto existente.
 * Presenta un formulario pre-cargado con los datos del producto seleccionado.
 * Permite al administrador modificar los detalles y enviar la actualización al servidor.
 */
public class EditProductActivity extends AppCompatActivity {

    /** Campo de texto para el título del producto */
    private TextInputEditText titleEditText;
    /** Campo de texto para el precio del producto */
    private TextInputEditText priceEditText;
    /** Campo de texto para la descripción del producto */
    private TextInputEditText descriptionEditText;
    /** Menú desplegable para seleccionar la categoría del producto */
    private AutoCompleteTextView categoryAutoComplete;
    /** Botón para enviar el formulario y actualizar el producto */
    private Button updateProductButton;
    /** El ID del producto que se está editando */
    private Integer productId;

    /**
     * Se ejecuta al crear la actividad.
     * Inicializa las vistas, recibe los datos del producto a editar desde el Intent,
     * rellena el formulario con esos datos y configura los listeners
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Editar Producto");
        }

        titleEditText = findViewById(R.id.editTitleEditText);
        priceEditText = findViewById(R.id.editPriceEditText);
        descriptionEditText = findViewById(R.id.editDescriptionEditText);
        categoryAutoComplete = findViewById(R.id.editCategoryAutoComplete);
        updateProductButton = findViewById(R.id.updateProductButton);

        // recibe los datos del producto desde el Intent
        productId = getIntent().getIntExtra("productId", -1);
        String name = getIntent().getStringExtra("name");
        double price = getIntent().getDoubleExtra("price", 0.0);
        String description = getIntent().getStringExtra("description");
        String category = getIntent().getStringExtra("category");

        // rellena los campos del formulario
        titleEditText.setText(name);
        priceEditText.setText(String.valueOf(price));
        descriptionEditText.setText(description);
        categoryAutoComplete.setText(category, false);

        // configura el menú desplegable de categorias
        String[] categories = getResources().getStringArray(R.array.product_categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categories);
        categoryAutoComplete.setAdapter(adapter);

        updateProductButton.setOnClickListener(v -> updateProduct());
    }

    /**
     * Recoge los datos modificados del formulario, los valida y los envía al servidor.
     * Construye un objeto Product con los nuevos datos y usa la API para actualizarlo.
     * Si tiene éxito, cierra la actividad y vuelve al panel de administración.
     */
    private void updateProduct() {
        String name = titleEditText.getText().toString().trim();
        String priceStr = priceEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String category = categoryAutoComplete.getText().toString().trim();

        if (name.isEmpty() || priceStr.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Precio no válido", Toast.LENGTH_SHORT).show();
            return;
        }

        Product updatedProduct = new Product(name, price, description, "", category);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        apiService.editProduct(productId, updatedProduct).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditProductActivity.this, "Producto actualizado", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(EditProductActivity.this, "Error al actualizar: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(EditProductActivity.this, "Fallo de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Gestiona la navegación hacia atrás desde el botón en la barra de acción
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}