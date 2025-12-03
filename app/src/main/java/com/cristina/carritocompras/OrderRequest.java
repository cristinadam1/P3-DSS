package com.cristina.carritocompras;

import java.util.List;

public class OrderRequest {
    private int userId;
    private String date;
    private List<OrderProduct> products;

    public OrderRequest(int userId, String date, List<OrderProduct> products) {
        this.userId = userId;
        this.date = date;
        this.products = products;
    }

    // Getters no son estrictamente necesarios para la serialización de Gson si los campos son privados,
    // pero son buena práctica.
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