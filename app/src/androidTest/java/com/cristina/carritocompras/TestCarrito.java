package com.cristina.carritocompras;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 * Prueba para la lógica del carrito
 * 
 * Esta clase es para verificar el funcionamiento del CartManager para añadir, eliminar
 * y calcular el total de los productos en el carrito.
 */
@RunWith(AndroidJUnit4.class)
public class TestCarrito {

    private CartManager cartManager;

    /**
     * Prepara el entorno de prueba antes de cada test.
     * Obtiene el contexto de la aplicación y una instancia del CartManager.
     */
    @Before
    public void setup() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        cartManager = CartManager.getInstance(appContext);
        cartManager.clearCart();
    }

    /**
     * Prueba que se puede añadir un producto al carrito correctamente.
     */
    @Test
    public void testAddProductToCart() {
        Product product = new Product("Test product", 10.0, "", "", "Test");
        product.setId(1);

        cartManager.addToCart(product);

        assertEquals(1, cartManager.getCartItems().size());
        assertEquals("Test product", cartManager.getCartItems().get(0).getName());
    }

    /**
     * Prueba que el precio total del carrito se calcula correctamente.
     */
    @Test
    public void testTotalPriceCalculation() {
        Product product1 = new Product("Producto A", 15.50, "", "", "Test");
        product1.setId(1);
        Product product2 = new Product("Producto B", 5.0, "", "", "Test");
        product2.setId(2);
        cartManager.addToCart(product1);
        cartManager.addToCart(product2);

        double total = cartManager.getTotalPrice();

        assertEquals(20.50, total, 0.0);
    }

    /**
     * Prueba que se puede eliminar un producto del carrito
     */
    @Test
    public void testRemoveProductFromCart() {
        Product product = new Product("Producto a eliminar", 99.9, "", "", "Test");
        product.setId(123); // Asignamos un ID de prueba para evitar el NullPointerException
        cartManager.addToCart(product);
        assertEquals(1, cartManager.getCartItems().size());

        cartManager.removeFromCart(product);

        assertTrue(cartManager.getCartItems().isEmpty());
    }

    /**
     * Prueba que el método clearCart vacía el carrito por completo (se usa cuando se hace un pedido)
     */
    @Test
    public void testClearCart() {
        Product product1 = new Product("Producto 1", 10.0, "", "", "Test");
        product1.setId(1);
        cartManager.addToCart(product1);
        
        assertEquals(1, cartManager.getCartItems().size());

        cartManager.clearCart();

        assertTrue(cartManager.getCartItems().isEmpty());
        assertEquals(0.0, cartManager.getTotalPrice(), 0.0);
    }
}