package com.cristina.carritocompras;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detalle del Producto");
        }

        ImageView detailProductImage = findViewById(R.id.detailProductImage);
        TextView detailProductTitle = findViewById(R.id.detailProductTitle);
        TextView detailProductPrice = findViewById(R.id.detailProductPrice);
        TextView detailProductCategory = findViewById(R.id.detailProductCategory);
        TextView detailProductDescription = findViewById(R.id.detailProductDescription);

        // Recibir datos del Intent
        String name = getIntent().getStringExtra("name");
        double price = getIntent().getDoubleExtra("price", 0.0);
        String category = getIntent().getStringExtra("category");
        String description = getIntent().getStringExtra("description");
        String image = getIntent().getStringExtra("image");

        // Asignar datos a las vistas
        detailProductTitle.setText(name);
        detailProductPrice.setText(String.format(Locale.getDefault(), "$%.2f", price));
        detailProductCategory.setText(category != null ? category : "Sin categoría");
        detailProductDescription.setText(description != null && !description.isEmpty() ? description : "No hay descripción disponible.");

        // Cargar imagen
        Glide.with(this)
                .load(image)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .into(detailProductImage);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}