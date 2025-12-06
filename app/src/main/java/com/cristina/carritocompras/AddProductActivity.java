package com.cristina.carritocompras;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {

    private TextInputEditText titleEditText, priceEditText, descriptionEditText, categoryEditText;
    private Button addProductButton;

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
        categoryEditText = findViewById(R.id.categoryEditText);
        addProductButton = findViewById(R.id.addProductButton);

        addProductButton.setOnClickListener(v -> addProduct());
    }

    private void addProduct() {
        addProductButton.setEnabled(false);
        Toast.makeText(this, "Guardando producto...", Toast.LENGTH_SHORT).show();

        String title = titleEditText.getText().toString().trim();
        String priceStr = priceEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String category = categoryEditText.getText().toString().trim();
        String image = ""; 

        if (title.isEmpty() || priceStr.isEmpty() || description.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            addProductButton.setEnabled(true);
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "El formato del precio no es válido (ej: 10.99)", Toast.LENGTH_SHORT).show();
            addProductButton.setEnabled(true);
            return;
        }

        Product newProduct = new Product(title, price, description, image, category);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}