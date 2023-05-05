package es.aleph_tea.teabuddy.ui.main.usuarios.admin;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import es.aleph_tea.teabuddy.R;

public class modificacionUsuarios extends AppCompatActivity {

    DatabaseReference dbRef;
    EditText nombre, apellido, n_telefono, email, fecha_nac;
    String uid, nombre_str, apellido_str, n_telefono_str, email_str, fecha_nac_str, tipo_usuario_str;
    Spinner rol;

    Button eliminar_usuario, modificar_usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificacion_usuarios);

        dbRef = FirebaseDatabase.getInstance().getReference("UsuariosAsociacion");

        eliminar_usuario = findViewById(R.id.eliminar_usuario);
        modificar_usuario = findViewById(R.id.alta_usuario);

        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        n_telefono = findViewById(R.id.n_telefono);
        email = findViewById(R.id.email);
        rol = findViewById(R.id.tipo_usuario);

        fecha_nac = (EditText) findViewById(R.id.fecha_nac);

        fecha_nac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(fecha_nac.getWindowToken(), 0);

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(modificacionUsuarios.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        fecha_nac.setText(day+"/"+month+"/"+year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        uid = getIntent().getStringExtra("uid");
        nombre_str = getIntent().getStringExtra("nombre_usuario");
        apellido_str = getIntent().getStringExtra("apellido_usuario");
        n_telefono_str = getIntent().getStringExtra("n_telefono_usuario");
        email_str = getIntent().getStringExtra("email_usuario");
        fecha_nac_str = getIntent().getStringExtra("fecha_nacimiento_usuario");
        tipo_usuario_str = getIntent().getStringExtra("rol_usuario");

        nombre.setText(nombre_str);
        apellido.setText(apellido_str);
        n_telefono.setText(n_telefono_str);
        email.setText(email_str);
        fecha_nac.setText(fecha_nac_str);

        switch (tipo_usuario_str){
            case "Administrador": rol.setSelection(2); break;
            case "Monitor": rol.setSelection(1); break;
            case "Voluntario": rol.setSelection(0); break;
        }

        eliminar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dbRef.child(uid).removeValue();
                    // FirebaseAuth.deleteUser(uid);
                    // Todo: permitir que el administrador borre un usuario de auth por uid
                    startActivity(new Intent(getApplicationContext(), MainActivityAdmin.class));
                } catch (Exception e) {
                    Log.d("Borrar Usuario "+uid+ ": ", "No se ha podido borrar el usuario");
                    Toast.makeText(getApplicationContext(), "No se ha podido borrar el usuario", Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);
                }
            }
        });
        modificar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre_str = nombre.getText().toString();
                apellido_str = apellido.getText().toString();
                n_telefono_str = n_telefono.getText().toString();
                email_str = email.getText().toString();
                fecha_nac_str = fecha_nac.getText().toString();
                tipo_usuario_str = rol.getTransitionName();

                Map<String, Object> usuario = new HashMap<>();
                usuario.put("email", email_str);
                usuario.put("nombre", nombre_str);
                usuario.put("apellidos", apellido_str);
                usuario.put("fecha_nacimiento", fecha_nac_str);
                usuario.put("numero_telefono", n_telefono_str);
                usuario.put("rol", tipo_usuario_str);

                startActivity(new Intent(getApplicationContext(), MainActivityAdmin.class));

            }
        });
    }
}