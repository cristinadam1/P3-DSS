package com.cristina.carritocompras;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interfaz que define los endpoints de la API del servidor
 */
public interface ApiService {

    /**
     * Obtiene una lista de productos. Permite filtrar por categoría.
     * @param category La categoría por la que se quiere filtrar. Puede ser nulo para obtener todos los productos
     * @return Una llamada (Call) que va a devolver una lista de productos
     */
    @GET("api/products")
    Call<List<Product>> getAllProducts(@Query("category") String category);

    /**
     * Envía un producto nuevo al servidor para que se cree.
     * @param product El objeto Product con los datos del producto nuevo
     * @return Una llamada (Call) que no devuelve contenido en el cuerpo de la respuesta
     */
    @POST("api/products")
    Call<Void> addProduct(@Body Product product);

    /**
     * Solicita al servidor que elimine un producto por su ID.
     * @param id El ID del producto que se va a eliminar
     * @return Una llamada (Call) que no devuelve contenido en el cuerpo de la respuesta
     */
    @DELETE("api/products/{id}")
    Call<Void> deleteProduct(@Path("id") int id);

    /**
     * Envía los datos actualizados de un producto al servidor.
     * @param id El ID del producto que se va a editar
     * @param product El objeto Product con los datos actualizados
     * @return Una llamada (Call) que va a devolver el producto actualizado
     */
    @PUT("api/products/{id}")
    Call<Product> editProduct(@Path("id") int id, @Body Product product);

    /**
     * Envía un nuevo pedido (carrito de compras) al servidor.
     * @param orderRequest El objeto que contiene los detalles del pedido
     * @return Una llamada (Call) que va a devolver una respuesta con el ID del pedido creado
     */
    @POST("api/carts")
    Call<OrderResponse> createOrder(@Body OrderRequest orderRequest);
}