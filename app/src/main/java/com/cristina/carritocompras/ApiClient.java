package com.cristina.carritocompras;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Cliente centralizado para la configuración y creación de la instancia de Retrofit.
 * Usa Singleton para asegurar que solo exista una instancia de Retrofit en toda la aplicación.
 * Centraliza la configuración de la URL base y los tiempos de espera (timeouts) de la conexión.
 */
public class ApiClient {

    /** La instancia única de Retrofit. Se inicializa una sola vez */
    private static Retrofit retrofit = null;

    /**
     * La URL base del servidor. 
     * Se utiliza la IP 10.0.2.2 para conectar desde el emulador de Android al localhost de mi ordenador
     */
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    /**
     * Obtiene la instancia Singleton del cliente Retrofit.
     * Si la instancia no ha sido creada, la configura con un cliente OkHttpClient
     * personalizado que incluye timeouts extendidos para evitar errores en conexiones lentas.
     * 
     * @return La instancia única de Retrofit, lista para que se use
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
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