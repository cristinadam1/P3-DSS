package com.cristina.carritocompras;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestor para el carrito de compras. Se encarga de la persistencia de los productos del carrito.
 * Se usa Singleton para asegurar una única instancia en toda la aplicación y gestiona el almacenamiento y recuperación de la lista de productos del carrito
 * usando SharedPreferences y Gson para la serialización.
 */
public class CartManager {
    private static final String PREF_NAME = "CartPrefs";
    private static final String KEY_CART_ITEMS = "cart_items";
    private static CartManager instance;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    /**
     * Constructor privado para forzar el uso del patrón Singleton.
     * @param context Contexto de la aplicación para acceder a SharedPreferences.
     */
    private CartManager(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    /**
     * Obtiene la instancia única y sincronizada del CartManager.
     * 
     * @param context El contexto de la aplicación
     * @return La instancia única de CartManager
     */
    public static synchronized CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context);
        }
        return instance;
    }

    /**
     * Recupera la lista de productos del carrito desde SharedPreferences.
     * @return Una lista de productos. Si no hay productos, devuelve una lista vacía
     */
    public List<Product> getCartItems() {
        String json = sharedPreferences.getString(KEY_CART_ITEMS, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<Product>>() {}.getType();
        return gson.fromJson(json, type);
    }

    /**
     * Añade un producto al carrito y guarda la lista actualizada.
     * @param product El producto a añadir
     */
    public void addToCart(Product product) {
        List<Product> items = getCartItems();
        items.add(product);
        saveCartItems(items);
    }

    /**
     * Elimina la primera ocurrencia de un producto del carrito basado en su ID.
     * @param product El producto a eliminar
     */
    public void removeFromCart(Product product) {
        List<Product> items = getCartItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(product.getId())) {
                items.remove(i);
                break;
            }
        }
        saveCartItems(items);
    }

    /**
     * Vacía el carrito de compras, eliminando todos los productos
     */
    public void clearCart() {
        saveCartItems(new ArrayList<>());
    }

    /**
     * Guarda la lista de productos del carrito en SharedPreferences.
     * Convierte la lista a formato JSON antes de guardarla.
     * @param items La lista de productos a guardar
     */
    private void saveCartItems(List<Product> items) {
        String json = gson.toJson(items);
        sharedPreferences.edit().putString(KEY_CART_ITEMS, json).apply();
    }
    
    /**
     * Calcula el precio total de todos los productos en el carrito.
     * @return El precio total como un valor double
     */
    public double getTotalPrice() {
        List<Product> items = getCartItems();
        double total = 0;
        for (Product p : items) {
            total += p.getPrice();
        }
        return total;
    }
}