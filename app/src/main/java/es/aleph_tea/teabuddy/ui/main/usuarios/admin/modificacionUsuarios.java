package es.aleph_tea.teabuddy.ui.main.usuarios.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.Spinner;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Map;

import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.ui.main.usuarios.CambiarPassActivity;

public class modificacionUsuarios extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference dbRef;
    EditText nombre, apellido, n_telefono, email, fecha_nac;
    String uid, nombre_str, apellido_str, n_telefono_str, email_str, fecha_nac_str, tipo_usuario_str;
    Spinner rol;

    Button eliminar_usuario, alta_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificacion_usuarios);

        auth = FirebaseAuth.getInstance();

        dbRef = FirebaseDatabase.getInstance().getReference("UsuariosAsociacion");

        eliminar_usuario = findViewById(R.id.eliminar_usuario);
        alta_usuario = findViewById(R.id.alta_usuario);

        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        n_telefono = findViewById(R.id.n_telefono);
        email = findViewById(R.id.email);
        fecha_nac = findViewById(R.id.fecha_nac);
        rol = findViewById(R.id.tipo_usuario);

        uid = getIntent().getStringExtra("uid");
        nombre_str = getIntent().getStringExtra("nombre_usuario");
        apellido_str = getIntent().getStringExtra("apellido_usuario");
        n_telefono_str = getIntent().getStringExtra("n_telefono_usuario");
        email_str = getIntent().getStringExtra("email_usuario");
        fecha_nac_str = getIntent().getStringExtra("fecha_nacimiento_usuario");
        tipo_usuario_str = getIntent().getStringExtra("rol_usuario");

        nombre.setHint(nombre_str);
        apellido.setHint(apellido_str);
        n_telefono.setHint(n_telefono_str);
        email.setHint(email_str);
        fecha_nac.setHint(fecha_nac_str);

        switch (tipo_usuario_str){
            case "Administrador": rol.setSelection(2); break;
            case "Monitor": rol.setSelection(1); break;
            case "Voluntario": rol.setSelection(0); break;
        }

        eliminar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef.child(uid).removeValue();
                startActivity(new Intent(getApplicationContext(), MainActivityAdmin.class));
            }
        });
        alta_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}