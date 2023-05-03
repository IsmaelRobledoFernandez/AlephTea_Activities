package es.aleph_tea.teabuddy.ui.main.usuarios.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.aleph_tea.teabuddy.R;

public class modificacionActividades extends AppCompatActivity {

    DatabaseReference dbRef;
    EditText nombre_actividad, descripcion_actividad, fecha_actividad, hora_actividad, localizacion;
    String uid, nombre_actividad_str, descripcion_actividad_str, fecha_actividad_str, localizacion_actividad_str, hora_actividad_str;

    Button eliminar_actividad, alta_actividad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificacion_actividades);

        dbRef = FirebaseDatabase.getInstance().getReference("ActividadesAsociacion");

        nombre_actividad = findViewById(R.id.nombre_actividad);
        descripcion_actividad = findViewById(R.id.descripcion_actividad);
        fecha_actividad = findViewById(R.id.fecha_actividad);
        hora_actividad = findViewById(R.id.hora_actividad);
        localizacion = findViewById(R.id.localizacion);
        eliminar_actividad = findViewById(R.id.eliminar_actividad);
        alta_actividad = findViewById(R.id.alta_actividad);

        uid = getIntent().getStringExtra("uid");
        nombre_actividad_str = getIntent().getStringExtra("nombre_actividad");
        descripcion_actividad_str = getIntent().getStringExtra("descripcion_actividad");
        fecha_actividad_str = getIntent().getStringExtra("fecha_actividad");
        hora_actividad_str = getIntent().getStringExtra("hora_actividad");
        localizacion_actividad_str = getIntent().getStringExtra("localizacion_actividad");


        nombre_actividad.setHint(nombre_actividad_str);
        descripcion_actividad.setHint(descripcion_actividad_str);
        fecha_actividad.setHint(fecha_actividad_str);
        hora_actividad.setHint(hora_actividad_str);
        localizacion.setHint(localizacion_actividad_str);

        eliminar_actividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef.child(uid).removeValue(null);
                startActivity(new Intent(getApplicationContext(), MainActivityAdmin.class));
            }
        });
        alta_actividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}