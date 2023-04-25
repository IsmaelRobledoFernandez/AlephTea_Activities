package es.aleph_tea.teabuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MostrarInfoActividades extends AppCompatActivity {

    TextView nombre_actividad, descripcion_actividad, fecha_actividad, hora_actividad, localizacion;

    String nombre_actividad_str, descripcion_actividad_str, fecha_actividad_str, localizacion_actividad_str, hora_actividad_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_info_actividades);

        nombre_actividad = findViewById(R.id.nombre);
        descripcion_actividad = findViewById(R.id.descripcion);
        fecha_actividad = findViewById(R.id.fecha);
        hora_actividad = findViewById(R.id.hora);
        localizacion = findViewById(R.id.localizacion);

        nombre_actividad_str = getIntent().getStringExtra("nombre_actividad");
        descripcion_actividad_str = getIntent().getStringExtra("descripcion_actividad");
        fecha_actividad_str = getIntent().getStringExtra("fecha_actividad");
        hora_actividad_str = getIntent().getStringExtra("hora_actividad");
        localizacion_actividad_str = getIntent().getStringExtra("localizacion_actividad");


        nombre_actividad.setText(nombre_actividad_str);
        descripcion_actividad.setText(descripcion_actividad_str);
        fecha_actividad.setText(fecha_actividad_str);
        hora_actividad.setText(hora_actividad_str);
        localizacion.setText(localizacion_actividad_str);
    }
}