package com.cristina.carritocompras;

import java.util.List;

/**
 * Representa el cuerpo de una solicitud para crear un nuevo pedido
 */
public class OrderRequest {

    /** El ID del usuario que realiza el pedido */
    private int userId;
    /** La fecha en que se realiza el pedido, en formato de texto */
    private String date;
    /** La lista de productos y cantidades que componen el pedido */
    private List<OrderProduct> products;

    /**
     * Constructor de la clase
     * @param userId El ID del usuario
     * @param date La fecha del pedido
     * @param products La lista de productos del pedido
     */
    public OrderRequest(int userId, String date, List<OrderProduct> products) {
        this.userId = userId;
        this.date = date;
        this.products = products;
    }

    public int getUserId() {
        return userId;
    }
    public String getDate() {
        return date;
    }
    public List<OrderProduct> getProducts() {
        return products;
    }
}