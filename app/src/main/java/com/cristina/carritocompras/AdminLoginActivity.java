package com.cristina.carritocompras;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Actividad que gestiona la pantalla de inicio de sesión para el administrador.
 * Esta pantalla tiene un formulario de autenticación. Si las credenciales son correctas,
 * redirige al panel de administración (AdminDashboardActivity). En caso contrario, muestra un mensaje de error
 */
public class AdminLoginActivity extends AppCompatActivity {

    /** Campo de texto para el nombre de usuario */
    private TextInputEditText usernameEditText;
    /** Campo de texto para la contraseña */
    private TextInputEditText passwordEditText;
    /** Botón para iniciar el proceso de login */
    private Button loginButton;

    /**
     * Método que se ejecuta al crear la actividad.
     * Inicializa los componentes de la interfaz, establece un listener para el botón de login y define la lógica de autenticación.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (username.equals("admin") && password.equals("admin")) {
                Intent intent = new Intent(AdminLoginActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(AdminLoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Gestiona la navegación hacia atrás desde el botón en la barra de acción.
     * Cierra la actividad actual y vuelve a la pantalla anterior
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}