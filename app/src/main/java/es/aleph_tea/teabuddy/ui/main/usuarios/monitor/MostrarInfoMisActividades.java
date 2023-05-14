package es.aleph_tea.teabuddy.ui.main.usuarios.monitor;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import es.aleph_tea.teabuddy.R;

public class MostrarInfoMisActividades extends AppCompatActivity {

    TextView nombre_actividad, descripcion_actividad, fecha_actividad, hora_actividad, localizacion;

    String nombre_actividad_str, descripcion_actividad_str, localizacion_actividad_str;
    Long fecha_actividad_str, hora_actividad_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_info_mis_actividades);

        nombre_actividad = findViewById(R.id.nombre);
        descripcion_actividad = findViewById(R.id.descripcion);
        fecha_actividad = findViewById(R.id.fecha);
        hora_actividad = findViewById(R.id.hora);
        localizacion = findViewById(R.id.localizacion);

        nombre_actividad_str = getIntent().getStringExtra("nombre_actividad");
        descripcion_actividad_str = getIntent().getStringExtra("descripcion_actividad");
        fecha_actividad_str = getIntent().getLongExtra("fecha_actividad",0l);
        hora_actividad_str = getIntent().getLongExtra("hora_actividad",0l);
        localizacion_actividad_str = getIntent().getStringExtra("localizacion_actividad");


        nombre_actividad.setText(nombre_actividad_str);
        descripcion_actividad.setText(descripcion_actividad_str);
        //Generamos un formato para la fecha
        DateFormat fecha = new SimpleDateFormat("dd/mm/yyyy");
        fecha_actividad.setText(fecha.format(new Date(new Timestamp(fecha_actividad_str).getTime())));
        //Generamos un formato para la hora
        DateFormat hora = new SimpleDateFormat("hh:mm");
        hora_actividad.setText(hora.format(new Date(new Timestamp(hora_actividad_str).getTime())));
        localizacion.setText(localizacion_actividad_str);
    }
}