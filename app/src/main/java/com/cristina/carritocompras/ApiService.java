package com.cristina.carritocompras;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/products")
    Call<List<Product>> getProducts();

    @POST("api/carts")
    Call<OrderResponse> createOrder(@Body OrderRequest orderRequest);

    @POST("api/products")
    Call<Void> addProduct(@Body Product product);

    @DELETE("api/products/{id}")
    Call<Void> deleteProduct(@Path("id") int id);
}