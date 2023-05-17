package es.aleph_tea.teabuddy.ui.main.usuarios.voluntario;

import static es.aleph_tea.teabuddy.ui.main.usuarios.LoginMainActivity.PREFERENCE_ESTADO_BUTTON_SESION;
import static es.aleph_tea.teabuddy.ui.main.usuarios.LoginMainActivity.STRING_PREFERENCES;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.ui.main.usuarios.LoginMainActivity;
import es.aleph_tea.teabuddy.ui.main.usuarios.ModificarPassActivity;

public class AccountDetailsActivity extends AppCompatActivity {
    TextView email, nombre, apellido, fecha_nac, n_telefono;
    Button btn_borrar_cuenta, btn_cambiar_pass, btn_cerrar_sesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        email = findViewById(R.id.email);
        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        fecha_nac = findViewById(R.id.fecha_nac);
        n_telefono = findViewById(R.id.n_telefono);
        btn_cerrar_sesion = findViewById(R.id.btn_cerrar_sesion);
        btn_borrar_cuenta = findViewById(R.id.btn_borrar_cuenta);
        btn_cambiar_pass = findViewById(R.id.btn_cambiar_pass);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UsuariosAsociacion");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nombre.setText(snapshot.child("nombre").getValue().toString());
                apellido.setText(snapshot.child("apellidos").getValue().toString());
                fecha_nac.setText(snapshot.child("fecha_nacimiento").getValue().toString());
                n_telefono.setText(snapshot.child("numero_telefono").getValue().toString());
                email.setText(snapshot.child("email").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn_cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ponerFalse();
                restartLogin();
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
        // Abre una actividad en la que el usuario tiene que introducir
        // su correo y contraseña y pulsar el boton
        deleteUser();
        restartLogin();
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

    private void restartLogin() {
        // Con este codigo, cerramos el acceso a las pantallas anteriores
        Intent intent = new Intent(getApplicationContext(), LoginMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}