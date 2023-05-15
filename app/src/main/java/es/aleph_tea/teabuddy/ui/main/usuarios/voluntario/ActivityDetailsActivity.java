package es.aleph_tea.teabuddy.ui.main.usuarios.voluntario;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.aleph_tea.teabuddy.R;
import es.aleph_tea.teabuddy.database.AppDatabase;
import es.aleph_tea.teabuddy.database.dao.ActividadDAO;
import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.database.repository.ActividadRepository;
import es.aleph_tea.teabuddy.database.repository.ActividadRepositoryImpl;
import es.aleph_tea.teabuddy.databinding.ActivityActivityDetailsBinding;
import es.aleph_tea.teabuddy.interfaces.SecondaryActivity;
import es.aleph_tea.teabuddy.models.Sesion;

public class ActivityDetailsActivity extends AppCompatActivity implements SecondaryActivity {

    private AppDatabase db;
    private ActividadDAO dao;
    private ActividadRepository repo;
    private ActivityActivityDetailsBinding binding;

    // ID de la actividad actual
    String idActividadActual;

    // Menu FAB
    FloatingActionMenu actionMenu;

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

            idActividadActual = parametro.getString("actividadId");

            // Inicialización BD Room
            db = AppDatabase.getInstance(this.getApplicationContext());
            dao = db.actividadDAO();
            repo = new ActividadRepositoryImpl(dao);

            // Obtenemos la actividad que acabamos de clickar
            Actividad actividadActual = repo.findByIdActividad(idActividadActual);

            if (actividadActual != null) {
                // Mostramos los detalles por pantalla
                putActividadDetails(actividadActual);
                // Configuramos menu FAB
                actionMenu = binding.actionMenu;
                actionMenu.setClosedOnTouchOutside(true);

            }
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

    public void clickApuntarse(View view) {
        if(idActividadActual != null)
        mostrarDialogoApuntarse(idActividadActual);
    }

    public void clickDesapuntarse(View view) {
        if(idActividadActual != null)
        mostrarDialogoDesapuntarse(idActividadActual);
    }

    private void mostrarDialogoApuntarse(String actividadId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDetailsActivity.this);
        builder.setTitle("Atención!");
        builder.setMessage("¿Quiere apuntarse a esta actividad?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"Inscripción en proceso...",Toast.LENGTH_SHORT).show();
                        inscribirUsuario(actividadId);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"Cancelando acción...",Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    private void mostrarDialogoDesapuntarse(String actividadId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDetailsActivity.this);
        builder.setTitle("Atención!");
        builder.setMessage("¿Quiere desapuntarse de esta actividad?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"Borrado en proceso...",Toast.LENGTH_SHORT).show();
                        DesinscribirUsuario(actividadId);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"Cancelando acción...",Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    private boolean inscribirUsuario(String actividadId) {

        boolean success;
        // Inicialización FirebaseRTDB
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Usuario_Actividad").child(Sesion.SesionUid)
                .child(actividadId)
                .setValue(true);

        success = true;

        return success;
    }

    private boolean DesinscribirUsuario(String actividadId) {

        boolean success;
        // Inicialización FirebaseRTDB
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Usuario_Actividad").child(Sesion.SesionUid)
                .child(actividadId)
                .setValue(false);

        success = true;

        return success;
    }
}