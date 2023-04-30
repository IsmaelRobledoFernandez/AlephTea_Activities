package es.aleph_tea.teabuddy.ui.main.usuarios.admin;

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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.models.Actividad;
import es.aleph_tea.teabuddy.models.Usuario;
import es.aleph_tea.teabuddy.ui.main.usuarios.LoginMainActivity;

public class AddNewActivity extends AppCompatActivity {
    DatabaseReference dbRef;
    String nombre_actividad_str, descripcion_actividad_str, fecha_actividad_str, hora_actividad_str, localizacion_str;
    Button añadir_actividad;
    EditText nombre_actividad, descripcion_actividad, fecha_actividad, hora_actividad, localizacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbRef = FirebaseDatabase.getInstance().getReference("ActividadesAsociacion");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_actividades);
        gestion();
    }

    private void gestion(){
        nombre_actividad = (EditText) findViewById(R.id.nombre_actividad);
        descripcion_actividad = (EditText) findViewById(R.id.descripcion_actividad);
        fecha_actividad = (EditText) findViewById(R.id.fecha_actividad);
        hora_actividad = (EditText) findViewById(R.id.hora_actividad);
        localizacion = (EditText) findViewById(R.id.localizacion);
        añadir_actividad = findViewById(R.id.añadir_actividad);

        fecha_actividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(fecha_actividad.getWindowToken(), 0);

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        fecha_actividad.setText(day+"/"+month+"/"+year);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });
        añadir_actividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre_actividad_str = nombre_actividad.getText().toString().trim();
                descripcion_actividad_str = descripcion_actividad.getText().toString().trim();
                hora_actividad_str = hora_actividad.getText().toString().trim();
                fecha_actividad_str = fecha_actividad.getText().toString().trim();
                localizacion_str = localizacion.getText().toString().trim();

                nombre_actividad.setHint("Nombre Actividad");
                descripcion_actividad.setHint("Descripcion Actividad");
                hora_actividad.setHint("Hora Actividad");
                fecha_actividad.setHint("Fecha Actividad");
                localizacion.setHint("Localizacion");

                Map<String, Object> actividad = new HashMap<>();
                actividad.put("nombre_actividad_str", nombre_actividad_str);
                actividad.put("descripcion_actividad_str", descripcion_actividad_str);
                actividad.put("hora_actividad_str", hora_actividad_str);
                actividad.put("fecha_actividad_str", fecha_actividad_str);
                actividad.put("localizacion_str", localizacion_str);

                dbRef.push().setValue(actividad);

                Toast.makeText(AddNewActivity.this, "Actividad Añadida", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), AdminListaActividadesFragment.class));
            }
        });
    }
}