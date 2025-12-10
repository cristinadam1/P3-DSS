package com.cristina.carritocompras;

import com.google.gson.annotations.SerializedName;

/**
 * Representa el modelo de un producto en la aplicación.
 * Contiene todos los atributos de un producto, como su ID, nombre, precio, etc.
 * Se usa tanto para mostrar los productos en la interfaz como para enviar y recibir datos del servidor.
 * Las anotaciones "@SerializedName" las uso para mapear los nombres de los campos del JSON a las variables de esta clase
 */
public class Product {

    /** El ID único del producto. Mapeado desde el campo "productoId" del JSON */
    @SerializedName("productoId")
    private Integer id;

    /** El nombre del producto. Mapeado desde el campo "productoNombre" del JSON */
    @SerializedName("productoNombre")
    private String name;

    /** El precio del producto */
    private Double price;
    /** La descripción detallada del producto */
    private String description;
    /** La categoría a la que pertenece el producto */
    private String category;
    /** La URL de la imagen del producto */
    private String image;

    /**
     * Constructor de la clase Product.
     * @param name El nombre del producto
     * @param price El precio del producto
     * @param description La descripción del producto
     * @param image La URL de la imagen del producto
     * @param category La categoría del producto
     */
    public Product(String name, double price, String description, String image, String category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
    }

    // Getters y setters

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

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id){
        this.id = id;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImage(String image) {
        this.image = image;
    }
}