package com.cristina.carritocompras;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Actividad que gestiona la pantalla final del proceso de compra (checkout).
 * 
 * Muestra un resumen del precio total y un botón para confirmar el pedido
 * Al confirmar, envía los datos del carrito al servidor para crear la orden
 */
public class CheckoutActivity extends AppCompatActivity {

    /** Campo de texto para mostrar el precio total del pedido */
    private TextView checkoutTotalTextView;
    /** Botón para confirmar y enviar el pedido al servidor */
    private Button confirmOrderButton;

    /**
     * Se ejecuta al crear la actividad.
     * 
     * Inicializa las vistas, calcula y muestra el precio total, y configura el listener del botón de confirmación.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        checkoutTotalTextView = findViewById(R.id.checkoutTotalTextView);
        confirmOrderButton = findViewById(R.id.confirmOrderButton);

        double total = CartManager.getInstance(this).getTotalPrice();
        checkoutTotalTextView.setText(String.format(Locale.getDefault(), "$%.2f", total));

        confirmOrderButton.setOnClickListener(v -> placeOrder());
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
     * Prepara y envía la solicitud del pedido al servidor.
     * 
     * Recoge los productos del carrito, construye el objeto de la solicitud (OrderRequest)
     * y lo envía a la API. Si la operación tiene éxito, vacía el carrito y vuelve a la pantalla principal
     */
    private void placeOrder() {
        List<Product> cartItems = CartManager.getInstance(this).getCartItems();
        if (cartItems.isEmpty()) {
            Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
            return;
        }

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (Product product : cartItems) {
            orderProducts.add(new OrderProduct(product.getId(), 1)); // Asumimos cantidad 1 por simplicidad
        }

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        OrderRequest orderRequest = new OrderRequest(1, date, orderProducts); // Asumimos UserId 1

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<OrderResponse> call = apiService.createOrder(orderRequest);

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(CheckoutActivity.this, "Pedido realizado con éxito! ID: " + response.body().getId(), Toast.LENGTH_LONG).show();
                    CartManager.getInstance(CheckoutActivity.this).clearCart();

                    Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CheckoutActivity.this, "Error al realizar el pedido: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Toast.makeText(CheckoutActivity.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}