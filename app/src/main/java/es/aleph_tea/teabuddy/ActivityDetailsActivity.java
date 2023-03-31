package es.aleph_tea.teabuddy;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import es.aleph_tea.teabuddy.database.AppDatabase;
import es.aleph_tea.teabuddy.database.dao.ActividadDAO;
import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.database.repository.ActividadRepository;
import es.aleph_tea.teabuddy.database.repository.ActividadRepositoryImpl;
import es.aleph_tea.teabuddy.databinding.ActivityActivitiesListBinding;
import es.aleph_tea.teabuddy.databinding.ActivityDetailsBinding;

public class ActivityDetailsActivity extends AppCompatActivity {

    private AppDatabase db;
    private ActividadDAO dao;
    private ActividadRepository repo;
    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Recepcion del id de la actividad a detallar
        Bundle parametro = this.getIntent().getExtras();

        if(parametro !=null) {

            int actividadId = parametro.getInt("actividadId");

            // Inicialización BD Room
            db = AppDatabase.getInstance(this.getApplicationContext());
            dao = db.actividadDAO();
            repo = new ActividadRepositoryImpl(dao);

            // Obtenemos la actividad que acabamos de clickar
            Actividad actividadActual = repo.findById(actividadId);

            // Mostramos los detalles por pantalla
            if (actividadActual != null)
            putActividadDetails(actividadActual);
            else Log.d("ErrorDetallesActividad",
                    "No se pudieron recuperar los detalles de la actividad");

        }
    }

    private void putActividadDetails(Actividad actividad) {

        binding.nombreActividad.setText(actividad.getNombre());
        binding.detallesActividad.setText(actividad.getDescripcion());
        binding.fechaHora.setText(actividad.getFechaHora().toString());
        binding.lugar.setText(actividad.getLocalizacion());

    }

}