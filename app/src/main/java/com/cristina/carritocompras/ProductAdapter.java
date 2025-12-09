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

/**
 * Adaptador para el RecyclerView que muestra el catálogo principal de productos.
 * Vincula la lista de productos con la interfaz de usuario, mostrando cada producto en un elemento de la lista
 * Gestiona los eventos de clic tanto para añadir un producto al carrito como para ver sus detalles
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    /** La lista de productos a mostrar */
    private List<Product> productList;
    /** Listener para el evento de clic en el botón "Añadir al carrito" */
    private OnAddToCartClickListener cartListener;
    /** Listener para el evento de clic en un elemento completo de la lista */
    private OnProductClickListener productListener;

    /**
     * Interfaz para comunicar el evento de "Añadir al carrito" a la actividad
     */
    public interface OnAddToCartClickListener {
        /**
         * Se invoca cuando el usuario pulsa el botón de añadir al carrito.
         * @param product El producto que se va a añadir
         */
        void onAddToCartClick(Product product);
    }

    /**
     * Interfaz para comunicar el evento de clic en un producto a la actividad
     */
    public interface OnProductClickListener {
        /**
         * Se invoca cuando el usuario pulsa sobre cualquier parte de la vista de un producto.
         * @param product El producto seleccionado
         */
        void onProductClick(Product product);
    }

    /**
     * Constructor
     * @param productList La lista de productos a mostrar
     * @param cartListener El listener para la acción de añadir al carrito
     * @param productListener El listener para la acción de ver el detalle del producto
     */
    public ProductAdapter(List<Product> productList, OnAddToCartClickListener cartListener, OnProductClickListener productListener) {
        this.productList = productList;
        this.cartListener = cartListener;
        this.productListener = productListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product, cartListener, productListener);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    /**
     * Clase interna que representa la vista de un solo producto en la lista.
     * Mantiene las referencias a los componentes de la interfaz para un elemento
     */
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productTitle;
        TextView productPrice;
        Button addToCartButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productTitle = itemView.findViewById(R.id.productTitle);
            productPrice = itemView.findViewById(R.id.productPrice);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }

        /**
         * Vincula los datos de un objeto Product a las vistas del layout.
         * También configura los listeners para los eventos de clic.
         * @param product El producto a mostrar
         * @param cartListener El listener para la acción de añadir al carrito
         * @param productListener El listener para la acción de ver el detalle
         */
        public void bind(final Product product, final OnAddToCartClickListener cartListener, final OnProductClickListener productListener) {
            productTitle.setText(product.getName());
            productPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
            Glide.with(itemView.getContext())
                    .load(product.getImage())
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_image_placeholder)
                    .into(productImage);

            addToCartButton.setOnClickListener(v -> {
                if (cartListener != null) {
                    cartListener.onAddToCartClick(product);
                }
            });

            itemView.setOnClickListener(v -> {
                if (productListener != null) {
                    productListener.onProductClick(product);
                }
            });
        }
    }
}