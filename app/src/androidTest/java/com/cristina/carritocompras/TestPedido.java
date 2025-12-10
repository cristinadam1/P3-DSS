package com.cristina.carritocompras;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Pruebas para la estructura del pedido (checkout)
 * Verifica que los objetos que se envían al servidor (OrderRequest y OrderProduct)
 * se construyen con los datos correctos.
 */
@RunWith(AndroidJUnit4.class)
public class TestPedido {

    /**
     * Prueba que un producto individual del pedido guarda bien el ID y cantidad
     */
    @Test
    public void testOrderProductCreation() {
        // Creo un producto
        OrderProduct item = new OrderProduct(5, 2);

        assertEquals(5, item.getProductId());
        assertEquals(2, item.getQuantity());
    }

    /**
     * Prueba que la solicitud completa de pedido (OrderRequest) se crea correctamente
     */
    @Test
    public void testOrderRequestCreation() {
        // Lista de productos
        List<OrderProduct> items = new ArrayList<>();
        items.add(new OrderProduct(101, 1));
        items.add(new OrderProduct(102, 3));

        // Solicitud de pedido
        String fecha = "04-12-2025";
        OrderRequest request = new OrderRequest(1, fecha, items);

        assertEquals(1, request.getUserId());
        assertEquals("04-12-2025", request.getDate());
        assertEquals(2, request.getProducts().size());
        
        // Verificar un dato dentro de la lista
        assertEquals(102, request.getProducts().get(1).getProductId());
    }
}