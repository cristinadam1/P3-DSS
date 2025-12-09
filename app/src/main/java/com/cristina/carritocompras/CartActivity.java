package com.cristina.carritocompras;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

/**
 * Actividad que muestra el contenido del carrito de compras del usuario.
 * Esta pantalla muestra una lista de los productos añadidos, calcula y enseña el precio total,
 * y permite al usuario proceder al checkout o eliminar productos del carrito
 */
public class CartActivity extends AppCompatActivity {

    /** Lista visual (RecyclerView) para mostrar los productos del carrito */
    private RecyclerView cartRecyclerView;
    /** Adaptador que conecta los datos de los productos del carrito con el RecyclerView */
    private CartAdapter cartAdapter;
    /** Campo de texto para mostrar el precio total de la compra */
    private TextView totalPriceTextView;
    /** Mensaje de texto que se muestra cuando el carrito está vacío */
    private TextView emptyCartMessage;
    /** Botón para iniciar el checkout */
    private Button checkoutButton;

    /**
     * Método que se ejecuta al crear la actividad.
     * Inicializa los componentes de la interfaz de usuario, configura el RecyclerView
     * y los listeners de los botones, y carga los productos del carrito
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        emptyCartMessage = findViewById(R.id.emptyCartMessage);
        checkoutButton = findViewById(R.id.checkoutButton);
        
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        checkoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            startActivity(intent);
        });

        loadCart();
    }

    /**
     * Gestiona la navegación hacia atrás desde el botón en la barra de acción
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Carga los productos guardados en el CartManager y actualiza la interfaz.
     * Si el carrito está vacío, oculta la lista y el total, y muestra un mensaje informativo.
     * Si contiene productos, configura el adaptador para mostrarlos, gestiona la eliminación de productos y calcula el precio total.
     */
    private void loadCart() {
        List<Product> cartItems = CartManager.getInstance(this).getCartItems();
        
        if (cartItems.isEmpty()) {
            cartRecyclerView.setVisibility(View.GONE);
            totalPriceTextView.setVisibility(View.GONE);
            emptyCartMessage.setVisibility(View.VISIBLE);
            checkoutButton.setVisibility(View.GONE);
        } else {
            cartRecyclerView.setVisibility(View.VISIBLE);
            totalPriceTextView.setVisibility(View.VISIBLE);
            emptyCartMessage.setVisibility(View.GONE);
            checkoutButton.setVisibility(View.VISIBLE);
            
            cartAdapter = new CartAdapter(cartItems, product -> {
                CartManager.getInstance(CartActivity.this).removeFromCart(product);
                loadCart();
            });
            cartRecyclerView.setAdapter(cartAdapter);
            
            double total = CartManager.getInstance(this).getTotalPrice();
            totalPriceTextView.setText(String.format(Locale.getDefault(), "$%.2f", total));
        }
    }
}