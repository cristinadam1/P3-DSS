# Carrito de Compras

Este proyecto es una aplicación Android nativa que implementa un sistema de carrito de compras completo. La aplicación se conecta a un backend propio desarrollado en Spring Boot, permitiendo la gestión de productos en tiempo real, la simulación de pedidos y la visualización de puntos de venta en un mapa.

## Características

#### Catálogo de Productos
- **Visualización de productos**: La lista de productos se obtiene del servidor y se muestra en un `RecyclerView`.
- **Actualización en tiempo real**: La lista de productos se refresca automáticamente al volver a la pantalla principal.

#### Carrito de Compras
- **Añadir y eliminar**: Funcionalidad para añadir productos al carrito y eliminarlos.
- **Persistencia**: El contenido del carrito se almacena localmente, persistiendo entre sesiones.
- **Estado vacío**: Muestra un mensaje informativo cuando el carrito no contiene productos.

#### Proceso de Checkout
- **Pantalla de resumen**: Muestra el total del pedido antes de la confirmación.
- **Envío de pedido**: La confirmación de la compra envía el pedido al servidor.

#### Panel de Administración
- **Acceso restringido**: Formulario de login para verificar las credenciales del administrador.
- **Gestión de productos**: Permite al administrador visualizar la lista completa de productos.
- **Añadir productos**: Formulario para crear y guardar nuevos productos en la base de datos del servidor.
- **Eliminar productos**: Funcionalidad para eliminar productos directamente desde la lista del panel de administración.

#### Geolocalización con Google Maps
- **Mapa interactivo**: Muestra la ubicación del usuario y la de almacenes o puntos de venta.
- **Ubicación del usuario**: Solicita permisos de ubicación y marca la posición actual del dispositivo en el mapa.
- **Indicaciones de ruta**: Permite obtener indicaciones para llegar a un almacén pulsando sobre su marcador.

## Tecnologías y Arquitectura

#### Frontend (Android)
- **Lenguaje**: Java
- **Arquitectura**: Actividades separadas para cada funcionalidad principal.
- **UI**: `RecyclerView`, `Material Design Components`, `Fragments` (para el mapa).
- **Red**: `Retrofit` y `OkHttp` para la comunicación con el API REST.
- **Serialización**: `Gson` para la conversión de objetos Java a JSON.
- **Imágenes**: `Glide` para la carga y caché de imágenes desde URL.
- **Geolocalización**: `Google Maps SDK` y `LocationManager`.

#### Backend (Spring Boot)
- **Lenguaje**: Java
- **Framework**: Spring Boot
- **Seguridad**: Spring Security para proteger las rutas de administración.
- **API**: API REST para las operaciones CRUD de productos.
- **Base de Datos**: JPA/Hibernate (compatible con H2, MySQL, PostgreSQL, etc.).

## Instalación y Puesta en Marcha

Para ejecutar este proyecto, es necesario configurar tanto el backend como la aplicación Android.

#### 1. Backend (Spring Boot)
- Clonar y ejecutar el servidor Spring Boot.
- Asegurar que la configuración de seguridad (`SecurityConfig.java`) permite el acceso a las rutas `/api/**`.
- Verificar que el servidor se ejecuta en el puerto `8080`.

#### 2. Aplicación Android
1.  **Clonar el repositorio** y abrir el proyecto en Android Studio.
2.  **Configurar la API Key de Google Maps**:
    - Obtener una clave de API válida desde la Google Cloud Console.
    - Sustituir el valor de `com.google.android.geo.API_KEY` en el archivo `app/src/main/AndroidManifest.xml` por la clave obtenida.
3.  **Verificar la URL del Backend**:
    - Confirmar que la `BASE_URL` en `RetrofitClient.java` apunta a la dirección correcta.
    - Para un emulador Android, la dirección para acceder al `localhost` del ordenador es `http://10.0.2.2:8080/`.
    - Si se usa un dispositivo físico, se debe reemplazar por la IP local del ordenador en la red (ej: `http://192.168.1.100:8080/`).
4.  **Ejecutar la aplicación** en un emulador o dispositivo físico.

## Credenciales de Administrador

Para acceder al panel de administración de productos, utilizar las siguientes credenciales:
- **Usuario**: `admin`
- **Contraseña**: `1234`
