package com.cristina.carritocompras;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para la clase Product
 * 
 * Verifica que el modelo de datos funciona correctamente, permitiendo
 * crear instancias, modificar sus atributos y gestionarlos en listas.
 */
@RunWith(AndroidJUnit4.class)
public class TestProducto {

    /**
     * Prueba la creación de un producto y la correcta asignación de sus valores iniciales
     */
    @Test
    public void testProductCreation() {
        Product product = new Product("Camiseta", 20.0, "Camiseta de algodón", "url_imagen", "Ropa");
        
        assertEquals("Camiseta", product.getName());
        assertEquals(20.0, product.getPrice(), 0.0);
        assertEquals("Camiseta de algodón", product.getDescription());
        assertEquals("Ropa", product.getCategory());
    }

    /**
     * Prueba la modificación de los atributos de un producto (simulando una edición)
     */
    @Test
    public void testProductModification() {
        // Crear producto original
        Product product = new Product("Original", 10.0, "Desc", "img", "Cat");
        
        // Modificar
        product.setName("Modificado");
        product.setPrice(15.0);
        product.setDescription("Nueva descripción");
        product.setCategory("Nueva categoría");
        
        // Verificar cambios
        assertEquals("Modificado", product.getName());
        assertEquals(15.0, product.getPrice(), 0.0);
        assertEquals("Nueva descripción", product.getDescription());
        assertEquals("Nueva categoría", product.getCategory());
    }

    /**
     * Prueba la eliminación de un producto (simulada en una lista)
     */
    @Test
    public void testProductDeletion() {
        // Lista para simular el catálogo o carrito
        List<Product> productList = new ArrayList<>();
        Product product = new Product("Producto a eliminar", 10.0, "Desc", "img", "Cat");
        
        // Añado el producto
        productList.add(product);
        assertEquals(1, productList.size());
        
        // Elimino el producto
        productList.remove(product);
        
        // Verifico que se ha eliminado correctamente
        assertTrue(productList.isEmpty());
    }
}