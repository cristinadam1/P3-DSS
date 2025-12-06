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

public class AdminProductAdapter extends RecyclerView.Adapter<AdminProductAdapter.ViewHolder> {

    private List<Product> productList;
    private OnDeleteProductClickListener listener;

    public interface OnDeleteProductClickListener {
        void onDeleteProductClick(Product product);
    }

    public AdminProductAdapter(List<Product> productList, OnDeleteProductClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product, listener);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productTitle;
        TextView productPrice;
        ImageButton deleteProductButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productTitle = itemView.findViewById(R.id.productTitle);
            productPrice = itemView.findViewById(R.id.productPrice);
            deleteProductButton = itemView.findViewById(R.id.deleteProductButton);
        }

        public void bind(final Product product, final OnDeleteProductClickListener listener) {
            productTitle.setText(product.getName());
            productPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
            Glide.with(itemView.getContext())
                    .load(product.getImage())
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_image_placeholder)
                    .into(productImage);

            deleteProductButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteProductClick(product);
                }
            });
        }
    }
}