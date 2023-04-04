package es.aleph_tea.teabuddy.login;

import static es.aleph_tea.teabuddy.LoginMainActivity.PREFERENCE_ESTADO_BUTTON_SESION;
import static es.aleph_tea.teabuddy.LoginMainActivity.STRING_PREFERENCES;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.aleph_tea.teabuddy.LoginMainActivity;
import es.aleph_tea.teabuddy.R;

public class AccountDetailsActivity extends AppCompatActivity {
    Button btn_borrar_cuenta, btn_cambiar_pass, btn_cerrar_sesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_actividades);
        btn_cerrar_sesion = findViewById(R.id.btn_cerrar_sesion);
        btn_borrar_cuenta = findViewById(R.id.btn_borrar_cuenta);
        btn_cambiar_pass = findViewById(R.id.btn_cambiar_pass);
        btn_cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), LoginMainActivity.class));
                ponerFalse();
            }
        });
        btn_borrar_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrarCuenta();
            }
        });
        btn_cambiar_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarPass();
            }
        });
    }
    protected void borrarCuenta(){
        // Abre una actividad en la que el usuario tiene que introducir su correo y contraseña y pulsar el boton
        deleteUser();
        finish();
        startActivity(new Intent(getApplicationContext(), LoginMainActivity.class));
    }
    protected void cambiarPass(){
        // Abre una actividad en la que el usuario tiene que introducir su correo
        finish();
        startActivity(new Intent(getApplicationContext(), ModificarPassActivity.class));
    }

    public void deleteUser() {
        // [START delete_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Delete", "User account deleted.");
                        }
                    }
                });
        // [END delete_user]
    }
    protected void ponerFalse(){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        preferences.edit().putBoolean(PREFERENCE_ESTADO_BUTTON_SESION, false).apply();
    }
}