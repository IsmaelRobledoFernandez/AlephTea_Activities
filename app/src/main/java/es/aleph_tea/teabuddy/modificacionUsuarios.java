package es.aleph_tea.teabuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Space;
import android.widget.Spinner;

public class modificacionUsuarios extends AppCompatActivity {

    EditText nombre, apellido, n_telefono, email, fecha_nac;
    String nombre_str, apellido_str, n_telefono_str, email_str, fecha_nac_str, tipo_usuario_str;
    Spinner rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificacion_usuarios);

        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        n_telefono = findViewById(R.id.n_telefono);
        email = findViewById(R.id.email);
        fecha_nac = findViewById(R.id.fecha_nac);
        rol = findViewById(R.id.tipo_usuario);

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
    }
}