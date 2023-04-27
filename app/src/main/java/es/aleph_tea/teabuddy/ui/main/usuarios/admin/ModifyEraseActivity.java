package es.aleph_tea.teabuddy.ui.main.usuarios.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.models.Usuario;

public class ModifyEraseActivity extends AppCompatActivity {
    String nombre, descripcion, fecha, hora, loc;
    EditText nombre_actividad, descripcion_actividad, fecha_actividad, hora_actividad, localizacion;
    Button eliminar_actividad, alta_actividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_actividades);
        gestionSignUp();
    }

    private void gestionSignUp(){
        // nombre_actividad, descripcion_actividad, fecha_actividad, hora_actividad, localizacion
        nombre_actividad = (EditText) findViewById(R.id.nombre_actividad);
        descripcion_actividad = (EditText) findViewById(R.id.descripcion_actividad);
        fecha_actividad = (EditText) findViewById(R.id.fecha_actividad);
        hora_actividad = (EditText) findViewById(R.id.hora_actividad);
        localizacion = (EditText) findViewById(R.id.localizacion);
        // eliminar_actividad, alta_actividad
        alta_actividad = findViewById(R.id.alta_actividad);
        alta_actividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // nombre, descripcion, fecha, hora, loc;
                nombre = nombre_actividad.getText().toString().trim();
                descripcion = descripcion_actividad.getText().toString().trim();
                fecha = fecha_actividad.getText().toString().trim();
                hora = hora_actividad.getText().toString().trim();
                loc = localizacion.getText().toString().trim();
            }
        });
    }
}