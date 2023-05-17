package es.aleph_tea.teabuddy.ui.main.usuarios.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import es.aleph_tea.teabuddy.R;

public class ModificacionActividades extends AppCompatActivity {

    DatabaseReference dbRef;
    EditText nombre_actividad, descripcion_actividad, fecha_actividad, hora_actividad, localizacion;
    String uid, nombre_actividad_str, descripcion_actividad_str, fecha_actividad_str, localizacion_actividad_str, hora_actividad_str;

    Button eliminar_actividad, modificar_actividad;
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
        modificar_actividad = findViewById(R.id.alta_actividad);


        fecha_actividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(fecha_actividad.getWindowToken(), 0);

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ModificacionActividades.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        fecha_actividad.setText(day+"/"+month+"/"+year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        uid = getIntent().getStringExtra("uid");
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

        eliminar_actividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef.child(uid).removeValue(null);
                startActivity(new Intent(getApplicationContext(), MainActivityAdmin.class));
            }
        });
        modificar_actividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre_actividad_str = nombre_actividad.getText().toString();
                descripcion_actividad_str = descripcion_actividad.getText().toString();
                fecha_actividad_str = fecha_actividad.getText().toString();
                hora_actividad_str = hora_actividad.getText().toString();
                localizacion_actividad_str = localizacion.getText().toString();

                Map<String, Object> actividad = new HashMap<>();
                actividad.put("nombre_actividad_str", nombre_actividad_str);
                actividad.put("descripcion_actividad_str", descripcion_actividad_str);
                actividad.put("hora_actividad_str", hora_actividad_str);
                actividad.put("fecha_actividad_str", fecha_actividad_str);
                actividad.put("localizacion_str", localizacion_actividad_str);

                dbRef.child(uid).setValue(actividad);
                startActivity(new Intent(getApplicationContext(), MainActivityAdmin.class));
            }
        });
    }
}