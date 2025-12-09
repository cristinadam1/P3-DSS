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
    private OnDeleteProductClickListener deleteListener;
    private OnEditProductClickListener editListener;

    public interface OnDeleteProductClickListener {
        void onDeleteProductClick(Product product);
    }

    public interface OnEditProductClickListener {
        void onEditProductClick(Product product);
    }

    public AdminProductAdapter(List<Product> productList, OnDeleteProductClickListener deleteListener, OnEditProductClickListener editListener) {
        this.productList = productList;
        this.deleteListener = deleteListener;
        this.editListener = editListener;
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
        holder.bind(product, deleteListener, editListener);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productTitle;
        TextView productPrice;
        ImageButton editProductButton;
        ImageButton deleteProductButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productTitle = itemView.findViewById(R.id.productTitle);
            productPrice = itemView.findViewById(R.id.productPrice);
            editProductButton = itemView.findViewById(R.id.editProductButton);
            deleteProductButton = itemView.findViewById(R.id.deleteProductButton);
        }

        public void bind(final Product product, final OnDeleteProductClickListener deleteListener, final OnEditProductClickListener editListener) {
            productTitle.setText(product.getName());
            productPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
            Glide.with(itemView.getContext())
                    .load(product.getImage())
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_image_placeholder)
                    .into(productImage);

            editProductButton.setOnClickListener(v -> {
                if (editListener != null) {
                    editListener.onEditProductClick(product);
                }
            });

            deleteProductButton.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onDeleteProductClick(product);
                }
            });
        }
    }
}