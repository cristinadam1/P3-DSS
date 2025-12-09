package com.cristina.carritocompras;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.util.Locale;

/**
 * Actividad que muestra los detalles de un solo producto
 * Esta pantalla se abre cuando el usuario selecciona un producto del catálogo.
 * Recibe los datos del producto a través de un Intent y los muestra en una vista con la imagen más grande y toda la información relevante
 */
public class ProductDetailActivity extends AppCompatActivity {

    /**
     * Se ejecuta al crear la actividad
     * Inicializa las vistas, recupera los datos del producto pasados en el Intent
     * y los asigna a los componentes de la interfaz de usuario correspondientes
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detalles del producto");
        }

        // Vinculo las vistas del layout con las variables
        ImageView detailProductImage = findViewById(R.id.detailProductImage);
        TextView detailProductTitle = findViewById(R.id.detailProductTitle);
        TextView detailProductPrice = findViewById(R.id.detailProductPrice);
        TextView detailProductCategory = findViewById(R.id.detailProductCategory);
        TextView detailProductDescription = findViewById(R.id.detailProductDescription);

        // Recupero los datos del producto desde los extras del Intent
        String name = getIntent().getStringExtra("name");
        double price = getIntent().getDoubleExtra("price", 0.0);
        String category = getIntent().getStringExtra("category");
        String description = getIntent().getStringExtra("description");
        String image = getIntent().getStringExtra("image");

        // Asigno los datos a las vistas
        detailProductTitle.setText(name);
        detailProductPrice.setText(String.format(Locale.getDefault(), "$%.2f", price));
        detailProductCategory.setText(category != null ? category : "Sin categoría");
        detailProductDescription.setText(description != null && !description.isEmpty() ? description : "No hay descripción disponible");

        // Cargo la imagen
        Glide.with(this)
                .load(image)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .into(detailProductImage);
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