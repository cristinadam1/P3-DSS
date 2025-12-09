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
 * Gestiona la creación de un producto nuevo
 * <p>
 * Presenta un formulario para que el administrador introduzca los detalles de un producto
 * y envía los datos al servidor para que se creen
 */
public class AddProductActivity extends AppCompatActivity {

    /** Campo de texto para el título del producto */
    private TextInputEditText titleEditText;
    /** Campo de texto para el precio del producto */
    private TextInputEditText priceEditText;
    /** Campo de texto para la descripción del producto */
    private TextInputEditText descriptionEditText;
    /** Menú desplegable para seleccionar la categoría del producto */
    private AutoCompleteTextView categoryAutoComplete;
    /** Botón para enviar el formulario y guardar el producto */
    private Button addProductButton;

    /**
     * Se ejecuta cuando la actividad se creada. Inicializa las vistas, configura
     * el menú desplegable de categorías y establece los listeners de los botones.
     * @param savedInstanceState Si la actividad se reinicia, contiene los datos guardados previamente.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        titleEditText = findViewById(R.id.titleEditText);
        priceEditText = findViewById(R.id.priceEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        categoryAutoComplete = findViewById(R.id.categoryAutoComplete);
        addProductButton = findViewById(R.id.addProductButton);

        String[] categories = getResources().getStringArray(R.array.product_categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categories);
        categoryAutoComplete.setAdapter(adapter);

        addProductButton.setOnClickListener(v -> addProduct());
    }

    /**
     * Recoge los datos del formulario, los valida y los envía al servidor para crear el producto.
     * <p>
     * Muestra mensajes de feedback al usuario durante el proceso y, si tiene éxito, cierra la actividad.
     */
    private void addProduct() {
        addProductButton.setEnabled(false);
        Toast.makeText(this, "Guardando producto...", Toast.LENGTH_SHORT).show();

        String name = titleEditText.getText().toString().trim();
        String priceStr = priceEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String category = categoryAutoComplete.getText().toString().trim();

        if (name.isEmpty() || priceStr.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Nombre, precio y categoría son obligatorios", Toast.LENGTH_SHORT).show();
            addProductButton.setEnabled(true);
            return;
        }

        Double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "El formato del precio no es válido", Toast.LENGTH_SHORT).show();
            addProductButton.setEnabled(true);
            return;
        }

        Product newProduct = new Product(name, price, description, "", category);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.addProduct(newProduct).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                addProductButton.setEnabled(true);
                if (response.isSuccessful()) {
                    Toast.makeText(AddProductActivity.this, "Producto añadido con éxito", Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(AddProductActivity.this, "Error del servidor: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                addProductButton.setEnabled(true);
                Toast.makeText(AddProductActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Gestiona el clic en el botón de "Atrás" de la barra de acción.
     * @return Siempre devuelve true para indicar que el evento ha sido gestionado.
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}