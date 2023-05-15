package es.aleph_tea.teabuddy.ui.main.usuarios.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import es.aleph_tea.teabuddy.R;

public class ModifyEraseActivity extends AppCompatActivity {
    String nombre, descripcion, fecha, hora, loc;
    EditText nombre_actividad, descripcion_actividad, fecha_actividad, hora_actividad, localizacion;
    Button eliminar_actividad, alta_actividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_actividades);
        modificarUsuario();
    }

    private void modificarUsuario(){
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