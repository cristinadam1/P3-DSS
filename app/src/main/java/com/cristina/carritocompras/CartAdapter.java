package com.cristina.carritocompras;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> cartItems;
    private OnRemoveFromCartClickListener listener;

    public interface OnRemoveFromCartClickListener {
        void onRemoveFromCartClick(Product product);
    }

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

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productTitle;
        TextView productPrice;
        Button removeFromCartButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productTitle = itemView.findViewById(R.id.productTitle);
            productPrice = itemView.findViewById(R.id.productPrice);
            removeFromCartButton = itemView.findViewById(R.id.removeFromCartButton);
        }

        public void bind(final Product product, final OnRemoveFromCartClickListener listener) {
            productTitle.setText(product.getTitle());
            productPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
            Glide.with(itemView.getContext())
                    .load(product.getImage())
                    .into(productImage);

            removeFromCartButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onRemoveFromCartClick(product);
                }
            });
        }
    }
}