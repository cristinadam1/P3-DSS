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

public interface ApiService {
    @GET("api/products")
    Call<List<Product>> getAllProducts(@Query("category") String category);

    @POST("api/products")
    Call<Void> addProduct(@Body Product product);

    @DELETE("api/products/{id}")
    Call<Void> deleteProduct(@Path("id") int id);

    @PUT("api/products/{id}")
    Call<Product> editProduct(@Path("id") int id, @Body Product product);

    @POST("api/carts")
    Call<OrderResponse> createOrder(@Body OrderRequest orderRequest);
}