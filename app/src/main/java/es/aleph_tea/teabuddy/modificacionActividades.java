package es.aleph_tea.teabuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class modificacionActividades extends AppCompatActivity {

    EditText nombre_actividad, descripcion_actividad, fecha_actividad, hora_actividad, localizacion;
    String nombre_actividad_str, descripcion_actividad_str, fecha_actividad_str, localizacion_actividad_str, hora_actividad_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificacion_actividades);

        nombre_actividad = findViewById(R.id.nombre_actividad);
        descripcion_actividad = findViewById(R.id.descripcion_actividad);
        fecha_actividad = findViewById(R.id.fecha_actividad);
        hora_actividad = findViewById(R.id.hora_actividad);
        localizacion = findViewById(R.id.localizacion);

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
    }
}