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
 * Adaptador para el RecyclerView que muestra la lista de productos en el panel de administración.
 * Se encarga de vincular la lista de productos con la interfaz de usuario,
 * mostrando cada producto como un elemento en la lista. También gestiona los eventos de clic
 * para los botones de editar y eliminar.
 */
public class AdminProductAdapter extends RecyclerView.Adapter<AdminProductAdapter.ViewHolder> {

    /** La lista de productos que se muestra en el RecyclerView */
    private List<Product> productList;
    /** Listener para gestionar el evento de clic en el botón de eliminar */
    private OnDeleteProductClickListener deleteListener;
    /** Listener para gestionar el evento de clic en el botón de editar */
    private OnEditProductClickListener editListener;

    /**
     * Interfaz para comunicar el evento de clic de eliminación a la actividad contenedora
     */
    public interface OnDeleteProductClickListener {
        /**
         * Se invoca cuando el usuario pulsa el botón de eliminar en un producto.
         * @param product El producto que se va a eliminar.
         */
        void onDeleteProductClick(Product product);
    }

    /**
     * Interfaz para comunicar el evento de clic de edición a la actividad contenedora
     */
    public interface OnEditProductClickListener {
        /**
         * Se invoca cuando el usuario pulsa el botón de editar en un producto.
         * @param product El producto que se va a editar
         */
        void onEditProductClick(Product product);
    }

    /**
     * Constructor del adaptador.
     * @param productList La lista de productos a mostrar
     * @param deleteListener El listener para la acción de eliminar
     * @param editListener El listener para la acción de editar
     */
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

    /**
     * Clase interna que representa la vista de un solo producto en la lista.
     * Mantiene las referencias a los componentes de la interfaz para un elemento.
     */
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

        /**
         * Vincula los datos de un objeto Product a las vistas correspondientes del layout.
         * También configura los listeners para los botones de editar y eliminar.
         * @param product El producto a mostrar
         * @param deleteListener El listener para la acción de eliminar
         * @param editListener El listener para la acción de editar
         */
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