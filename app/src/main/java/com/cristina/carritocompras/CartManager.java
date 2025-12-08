package com.cristina.carritocompras;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final String PREF_NAME = "CartPrefs";
    private static final String KEY_CART_ITEMS = "cart_items";
    private static CartManager instance;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private CartManager(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public static synchronized CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context);
        }
        return instance;
    }

    public List<Product> getCartItems() {
        String json = sharedPreferences.getString(KEY_CART_ITEMS, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<Product>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void addToCart(Product product) {
        List<Product> items = getCartItems();
        items.add(product);
        saveCartItems(items);
    }

    public void removeFromCart(Product product) {
        List<Product> items = getCartItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(product.getId())) { // Usar .equals para comparar Integer
                items.remove(i);
                break;
            }
        }
        saveCartItems(items);
    }

    public void clearCart() {
        saveCartItems(new ArrayList<>());
    }

    private void saveCartItems(List<Product> items) {
        String json = gson.toJson(items);
        sharedPreferences.edit().putString(KEY_CART_ITEMS, json).apply();
    }
    
    public double getTotalPrice() {
        List<Product> items = getCartItems();
        double total = 0;
        for (Product p : items) {
            total += p.getPrice();
        }
        return total;
    }
}