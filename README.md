# CarritoComprasApp

Aplicación nativa Android de comercio electrónico desarrollada en Java para Práctica 3 de la asignatura Desarrollo de Sistemas de Software basados en Componentes y Servicios. 
Permite la visualización de un catálogo de productos previamente añadidos, gestión de carrito de compras, realización de pedidos, geolocalización de tiendas y administración de productos mediante una API RESTful.

## 1. Instrucciones de instalación y ejecución de la app

Para poner en marcha el proyecto, hay que hacer lo siguiente:

1.  **Requisitos previos:**
    *   Tener Android Studio instalado
    *   Tener el servidor Backend (Spring Boot) ejecutándose en el puerto `8080`, en concreto el proyecto que se ha subido en la entrega de la Práctica 1 con nombre "carrito-compras-P3"

2.  **Abrir el proyecto:**
    *   Descomprir el archivo `DelAguilaMartinCristina_AppSource.zip`.
    *   En Android Studio, seleccionar **File > Open** e ir hasta la carpeta del proyecto

3.  **Configuración de la API:**
    *   La aplicación está configurada para conectar al emulador mediante `http://10.0.2.2:8080/`.
    *   Para abrirla en un móvil, hay que editar la variable `BASE_URL` en `src/main/java/com/cristina/carritocompras/ApiClient.java` con la IP local

4.  **Ejecución:**
    *   Sincronizar el proyecto con Gradle (`Sync Project`).
    *   Seleccionar un emulador y darle al botón **Run**.

5.  **Acceso al panel de administración:**
    *   Desde el menú de la pantalla principal, seleccionar el emoticono de persona (admin). Las credenciales son las siguientes:
    *   **Usuario:** `admin`
    *   **Contraseña:** `admin`

## 2. Dependencias usadas

El proyecto usa las siguientes librerías externas para su funcionamiento:

*   **Retrofit 2 (`com.squareup.retrofit2`):** 
*   **Gson (`com.google.code.gson`):** Librería para serializar y deserializar objetos Java a formato JSON y viceversa
*   **Glide (`com.github.bumptech.glide`):** Gestión de carga y caché de imágenes desde URLs
*   **Google Play Services (`com.google.android.gms`):**
    *   `play-services-maps`: Para el mapa interactivo de Google Maps
    *   `play-services-location`: Para la geolocalización del usuario
*   **Espresso y JUnit:** Para la ejecución de pruebas unitarias e instrumentadas de interfaz

## 3. Estructura de carpetas y organización del código

El código fuente está en `src/main/java/com/cristina/carritocompras/` y se organiza de la siguiente manera:

*   **`api/`:**
    *   `ApiClient.java`: para la instancia de Retrofit.
    *   `ApiService.java`: se definen los endpoints HTTP de la API.
*   **`models/`:** se representan los datos (`Product`, `OrderRequest`, `OrderProduct`, `OrderResponse`).
*   **`adapters/`:**
    *   `ProductAdapter.java`: Gestiona la lista del catálogo principal
    *   `CartAdapter.java`: Gestiona la lista visual del carrito
    *   `AdminProductAdapter.java`: Gestiona la lista del panel de administración
*   **Actividades (Views/Controllers):**
    *   `MainActivity.java`: Pantalla principal y lógica de filtrado
    *   `CartActivity.java`: Pantalla del carrito y cálculo de totales
    *   `CheckoutActivity.java`: Pantalla de finalización de compra
    *   `MapsActivity.java`: Pantalla de mapa con marcadores y ubicación
    *   `Admin...Activity.java`: Pantallas de Login y gestión CRUD de productos
*   **Gestores:**
    *   `CartManager.java`: Clase Singleton encargada de la lógica de negocio del carrito y la persistencia de datos (SharedPreferences)

## 4. Lista de endpoints API utilizados y su descripción

La aplicación interactúa con los siguientes recursos del servidor:

| Método HTTP | Endpoint | Descripción |
| :--- | :--- | :--- |
| **GET** | `/api/products` | Descarga la lista completa de productos. Admite el parámetro `category` para filtrar. |
| **POST** | `/api/products` | Crea un nuevo producto en la base de datos (Usado en el panel Admin). |
| **PUT** | `/api/products/{id}` | Actualiza los datos de un producto existente identificado por su ID. |
| **DELETE** | `/api/products/{id}` | Elimina un producto del catálogo. |
| **POST** | `/api/carts` | Envía la confirmación del pedido (Checkout). El cuerpo de la petición incluye el ID de usuario, fecha y lista de productos. |
