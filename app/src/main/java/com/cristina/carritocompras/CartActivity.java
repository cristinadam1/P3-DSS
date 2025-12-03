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

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private TextView totalPriceTextView;
    private TextView emptyCartMessage;
    private Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

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

    private void loadCart() {
        List<Product> cartItems = CartManager.getInstance(this).getCartItems();
        
        if (cartItems.isEmpty()) {
            cartRecyclerView.setVisibility(View.GONE);
            totalPriceTextView.setVisibility(View.GONE);
            emptyCartMessage.setVisibility(View.VISIBLE);
            checkoutButton.setVisibility(View.GONE); // Ocultar checkout si está vacío
        } else {
            cartRecyclerView.setVisibility(View.VISIBLE);
            totalPriceTextView.setVisibility(View.VISIBLE);
            emptyCartMessage.setVisibility(View.GONE);
            checkoutButton.setVisibility(View.VISIBLE); // Mostrar checkout si hay items
            
            cartAdapter = new CartAdapter(cartItems, new CartAdapter.OnRemoveFromCartClickListener() {
                @Override
                public void onRemoveFromCartClick(Product product) {
                    CartManager.getInstance(CartActivity.this).removeFromCart(product);
                    loadCart(); // Recargar la lista y volver a comprobar si está vacía
                }
            });
            cartRecyclerView.setAdapter(cartAdapter);
            
            double total = CartManager.getInstance(this).getTotalPrice();
            totalPriceTextView.setText(String.format(Locale.getDefault(), "Total: $%.2f", total));
        }
    }
}