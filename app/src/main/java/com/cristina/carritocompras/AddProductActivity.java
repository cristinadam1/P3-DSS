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

public class AddProductActivity extends AppCompatActivity {
    private TextInputEditText titleEditText, priceEditText, descriptionEditText;
    private AutoCompleteTextView categoryAutoComplete;
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
        categoryAutoComplete = findViewById(R.id.categoryAutoComplete);
        addProductButton = findViewById(R.id.addProductButton);

        String[] categories = getResources().getStringArray(R.array.product_categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categories);
        categoryAutoComplete.setAdapter(adapter);

        addProductButton.setOnClickListener(v -> addProduct());
    }
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}