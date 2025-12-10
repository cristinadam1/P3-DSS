package com.cristina.carritocompras;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Prueba instrumentada para el flujo de administrador.
 * 
 * Esta prueba simula el comportamiento de un usuario al iniciar sesión como administrador
 * y navegar a la pantalla de añadir producto.
 */
@RunWith(AndroidJUnit4.class)
public class AdminFlowTest {

    /**
     * Regla para lanzar la AdminLoginActivity antes de cada prueba.
     * Esto asegura que cada test comience desde la pantalla de login.
     */
    @Rule
    public ActivityScenarioRule<AdminLoginActivity> activityRule = new ActivityScenarioRule<>(AdminLoginActivity.class);

    /**
     * Prueba el flujo de login exitoso.
     * Escribe las credenciales correctas y verifica que se navega al dashboard de admin.
     */
    @Test
    public void testSuccessfulLogin() {
        // 1. Escribir "admin" en el campo de usuario
        onView(withId(R.id.usernameEditText)).perform(typeText("admin"));
        
        // 2. Escribir "admin" en el campo de contraseña
        onView(withId(R.id.passwordEditText)).perform(typeText("admin"), closeSoftKeyboard());
        
        // 3. Pulsar el botón de login
        onView(withId(R.id.loginButton)).perform(click());
        
        // 4. Verificar que se muestra el RecyclerView del dashboard de administrador
        onView(withId(R.id.adminRecyclerView)).check(matches(isDisplayed()));
    }

    /**
     * Prueba que al pulsar el botón flotante de añadir, se abre la pantalla correcta.
     */
    @Test
    public void testNavigateToAddProductScreen() {
        // Primero, hacemos login para llegar al dashboard
        onView(withId(R.id.usernameEditText)).perform(typeText("admin"));
        onView(withId(R.id.passwordEditText)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        // Ahora que estamos en el dashboard, pulsamos el botón flotante (+)
        onView(withId(R.id.fabAddProduct)).perform(click());

        // Verificamos que estamos en la pantalla de "Añadir Producto"
        // buscando un elemento que solo existe en esa pantalla, como el botón de guardar.
        onView(withId(R.id.addProductButton)).check(matches(isDisplayed()));
    }
}