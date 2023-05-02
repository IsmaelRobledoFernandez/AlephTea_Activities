package es.aleph_tea.teabuddy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import es.aleph_tea.teabuddy.database.AppDatabase;
import es.aleph_tea.teabuddy.database.dao.ActividadDAO;
import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.database.repository.ActividadRepository;
import es.aleph_tea.teabuddy.database.repository.ActividadRepositoryImpl;
import es.aleph_tea.teabuddy.databinding.ActivityActivityDetailsBinding;
import es.aleph_tea.teabuddy.interfaces.SecondaryActivity;

public class ActivityDetailsActivity extends AppCompatActivity implements SecondaryActivity {

    private AppDatabase db;
    private ActividadDAO dao;
    private ActividadRepository repo;
    private ActivityActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Recepcion del id de la actividad a detallar
        Bundle parametro = this.getIntent().getExtras();

        // Inicializacion Toolbar
        inicializarToolbar();

        if(parametro != null) {

            int actividadId = parametro.getInt("actividadId");

            // Inicialización BD Room
            db = AppDatabase.getInstance(this.getApplicationContext());
            dao = db.actividadDAO();
            repo = new ActividadRepositoryImpl(dao);

            // Obtenemos la actividad que acabamos de clickar
            Actividad actividadActual = repo.findByIdActividad(actividadId);

            // Mostramos los detalles por pantalla
            if (actividadActual != null)
            putActividadDetails(actividadActual);
            else Log.d("ErrorDetallesActividad",
                    "No se pudieron recuperar los detalles de la actividad");

        }
    }

    @Override
    public void inicializarToolbar() {
        TextView tituloActividad;

        tituloActividad = (TextView) findViewById(R.id.toolbarTitle);

        tituloActividad.setText("Detalles de la actividad");

        accesoBotonAtras();
    }

    @Override
    public void accesoBotonAtras() {

        ImageView botonAtras;

        botonAtras = (ImageView) findViewById(R.id.btn_back);
        botonAtras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("ActivityDetailsActivity",
                        "Volviendo a la lista de actividades...");
                finish();
            }
        });
    }

    private void putActividadDetails(Actividad actividad) {

        binding.nombreActividad.setText(actividad.getNombre());
        binding.detallesActividad.setText(actividad.getDescripcion());
        binding.fechaHora.setText(actividad.getFechaHora().toString());
        binding.lugar.setText(actividad.getLocalizacion());

    }
}