package com.cristina.carritocompras;

/**
 * Representa un producto dentro de una solicitud de pedido (OrderRequest).
 * Contiene el ID de un producto y la cantidad que se quiere pedir
 */
public class OrderProduct {

    /** El ID único del producto */
    private int productId;
    /** La cantidad de unidades del producto a pedir */
    private int quantity;

    /**
     * Constructor de la clase
     * @param productId El ID del producto
     * @param quantity La cantidad que se quiere
     */
    public OrderProduct(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}