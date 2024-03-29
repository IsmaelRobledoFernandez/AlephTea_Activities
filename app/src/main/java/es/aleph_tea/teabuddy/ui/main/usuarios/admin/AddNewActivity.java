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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.interfaces.LocalizacionActividadIn;
import es.aleph_tea.teabuddy.models.LocalizacionActividad;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddNewActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private LocalizacionActividad localizacionActividad;
    DatabaseReference dbRef;
    String nombre_actividad_str, ciudad_str, descripcion_actividad_str, localizacion_str;

    // Creamos un atributo para colocar la fecha con formato
    String fechaF;
    int numero_monitores, numero_voluntarios;
    Long fecha_actividad_str, hora_actividad_str;
    Button añadir_actividad;
    EditText nombre_actividad, ciudad, descripcion_actividad, fecha_actividad, hora_actividad, localizacion;
    EditText n_monitores, n_voluntarios;
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
        ciudad = findViewById(R.id.Ciudad);
        n_monitores = findViewById(R.id.n_monitores);
        n_voluntarios = findViewById(R.id.n_voluntarios);

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
                        fechaF = year+"-"+month+"-"+day;
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
                fecha_actividad_str = Timestamp.valueOf(fechaF
                        + " " + hora_actividad.getText().toString().trim()
                        + ":00").getTime();
                Log.d("TimestampNuevaActividad", fechaF
                        + " " + hora_actividad.getText().toString().trim()
                        + ":00");
                localizacion_str = localizacion.getText().toString().trim();
                ciudad_str = ciudad.getText().toString();

                nombre_actividad.setHint("Nombre Actividad");
                descripcion_actividad.setHint("Descripcion Actividad");
                hora_actividad.setHint("Hora Actividad");
                fecha_actividad.setHint("Fecha Actividad");
                localizacion.setHint("Localizacion");

                Map<String, Object> actividad = new HashMap<>();
                actividad.put("nombre", nombre_actividad_str);
                actividad.put("descripcion", descripcion_actividad_str);
                actividad.put("fechaHora", "47384784478");
                actividad.put("localizacion", "https://www.google.com/maps/search/?api=1&query="+localizacion_str.replace(" ", "+")+","+ciudad_str);
                actividad.put("numero_monitores", 0);
                actividad.put("numero_voluntarios", 0);
                actividad.put("numero_monitores_max", numero_monitores);
                actividad.put("numero_voluntarios_max", numero_voluntarios);

                dbRef.push().setValue(actividad);

                Toast.makeText(AddNewActivity.this, "Actividad Añadida", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivityAdmin.class));
            }
        });
    }
}