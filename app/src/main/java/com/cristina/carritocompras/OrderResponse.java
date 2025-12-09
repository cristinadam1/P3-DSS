package com.cristina.carritocompras;

/**
 * Representa la respuesta del servidor después de crear un nuevo pedido.
 */
public class OrderResponse {

    /** El ID del pedido creado en el servidor */
    private int id;

    /**
     * Obtiene el ID del pedido
     * @return El ID del pedido
     */
    public int getId() {
        return id;
    }
}