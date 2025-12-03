package com.cristina.carritocompras;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private TextView totalPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        loadCart();
    }

    private void loadCart() {
        List<Product> cartItems = CartManager.getInstance(this).getCartItems();
        cartAdapter = new CartAdapter(cartItems, new CartAdapter.OnRemoveFromCartClickListener() {
            @Override
            public void onRemoveFromCartClick(Product product) {
                CartManager.getInstance(CartActivity.this).removeFromCart(product);
                loadCart(); // Recargar la lista
            }
        });
        cartRecyclerView.setAdapter(cartAdapter);
        
        double total = CartManager.getInstance(this).getTotalPrice();
        totalPriceTextView.setText(String.format(Locale.getDefault(), "Total: $%.2f", total));
    }
}