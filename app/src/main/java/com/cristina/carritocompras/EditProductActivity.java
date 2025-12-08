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

public class EditProductActivity extends AppCompatActivity {

    private TextInputEditText titleEditText, priceEditText, descriptionEditText;
    private AutoCompleteTextView categoryAutoComplete;
    private Button updateProductButton;
    private Integer productId;

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

        // Recibir datos del producto
        productId = getIntent().getIntExtra("productId", -1);
        String name = getIntent().getStringExtra("name");
        double price = getIntent().getDoubleExtra("price", 0.0);
        String description = getIntent().getStringExtra("description");
        String category = getIntent().getStringExtra("category");

        // Rellenar formulario
        titleEditText.setText(name);
        priceEditText.setText(String.valueOf(price));
        descriptionEditText.setText(description);
        categoryAutoComplete.setText(category, false); // El 'false' evita que se dispare el filtro

        // Configurar desplegable
        String[] categories = getResources().getStringArray(R.array.product_categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categories);
        categoryAutoComplete.setAdapter(adapter);

        updateProductButton.setOnClickListener(v -> updateProduct());
    }

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

        // Crear objeto y enviar a la API
        Product updatedProduct = new Product(name, price, description, "", category);
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}