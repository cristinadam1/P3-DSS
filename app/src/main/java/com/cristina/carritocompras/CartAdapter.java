package com.cristina.carritocompras;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import java.util.Locale;

/**
 * Adaptador para el RecyclerView que muestra los productos en el carrito de compras.
 * Se encarga de vincular la lista de productos del carrito con la interfaz de usuario,
 * mostrando cada producto como un elemento individual. También gestiona el evento de clic para el botón de eliminar producto.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    /** La lista de productos que se muestra en el carrito */
    private List<Product> cartItems;
    /** Listener para gestionar el evento de clic en el botón de eliminar */
    private OnRemoveFromCartClickListener listener;

    /**
     * Interfaz para comunicar el evento de clic de eliminación a la actividad contenedora
     */
    public interface OnRemoveFromCartClickListener {
        /**
         * Se invoca cuando el usuario pulsa el botón de eliminar en un producto del carrito.
         * @param product El producto que se va a eliminar
         */
        void onRemoveFromCartClick(Product product);
    }

    /**
     * Constructor del adaptador.
     * @param cartItems La lista de productos del carrito a mostrar
     * @param listener El listener para la acción de eliminar
     */
    public CartAdapter(List<Product> cartItems, OnRemoveFromCartClickListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_product, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartItems.get(position);
        holder.bind(product, listener);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    /**
     * Clase interna que representa la vista de un solo producto en la lista del carrito.
     */
    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productTitle;
        TextView productPrice;
        ImageButton removeFromCartButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productTitle = itemView.findViewById(R.id.productTitle);
            productPrice = itemView.findViewById(R.id.productPrice);
            removeFromCartButton = itemView.findViewById(R.id.removeFromCartButton);
        }

        /**
         * Vincula los datos de un objeto Product a las vistas correspondientes del layout.
         * También configura el listener para el botón de eliminar.
         * @param product El producto a mostrar
         * @param listener El listener para la acción de eliminar
         */
        public void bind(final Product product, final OnRemoveFromCartClickListener listener) {
            productTitle.setText(product.getName());
            productPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
            Glide.with(itemView.getContext())
                    .load(product.getImage())
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_image_placeholder)
                    .into(productImage);

            removeFromCartButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onRemoveFromCartClick(product);
                }
            });
        }
    }
}