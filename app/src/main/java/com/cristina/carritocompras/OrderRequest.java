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
}