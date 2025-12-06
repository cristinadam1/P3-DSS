package com.cristina.carritocompras;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    // 10.0.2.2 es la dirección especial para acceder al localhost de mi PC desde el emulador.
    // Si uso un móvil físico, tengo que poner la IP local de mi PC
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            // Evitar errores de red en operaciones lentas
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}