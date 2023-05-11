package es.aleph_tea.teabuddy.ui.main.usuarios;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import es.aleph_tea.teabuddy.R;

public class MostrarInfoActividadesAPI extends AppCompatActivity {

    TextView nombre_actividad, descripcion_actividad, localizacion;

    String nombre_actividad_str, descripcion_actividad_str, localizacion_actividad_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_info_actividades_api);

        nombre_actividad = findViewById(R.id.nombre);
        descripcion_actividad = findViewById(R.id.descripcion);
        localizacion = findViewById(R.id.localizacion);

        nombre_actividad_str = getIntent().getStringExtra("nombre_actividad");
        descripcion_actividad_str = getIntent().getStringExtra("datos_actividad");
        localizacion_actividad_str = getIntent().getStringExtra("nombre_centro");

        nombre_actividad.setText(nombre_actividad_str);
        descripcion_actividad.setText(descripcion_actividad_str);
        localizacion.setText(localizacion_actividad_str);
    }
}