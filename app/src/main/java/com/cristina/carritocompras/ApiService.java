package com.cristina.carritocompras;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/products")
    Call<List<Product>> getAllProducts(@Query("category") String category);

    @POST("api/products")
    Call<Void> addProduct(@Body Product product);

    @POST("api/products/delete/{id}")
    Call<Void> deleteProduct(@Path("id") int id);

    @POST("api/carts")
    Call<OrderResponse> createOrder(@Body OrderRequest orderRequest);
}