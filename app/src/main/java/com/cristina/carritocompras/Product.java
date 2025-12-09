package com.cristina.carritocompras;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("productoId")
    private Integer id;

    @SerializedName("productoNombre")
    private String name;

    private Double price;
    private String description;
    private String category;
    private String image;

    public Product(String name, double price, String description, String image, String category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Double getPrice() {
        return price;
    }
    public String getImage() {
        return image;
    }
    public String getCategory() {
        return category;
    }
    public String getDescription() {
        return description;
    }
}