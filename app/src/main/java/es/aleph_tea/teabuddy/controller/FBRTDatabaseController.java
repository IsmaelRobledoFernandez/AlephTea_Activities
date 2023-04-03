package es.aleph_tea.teabuddy.controller;

import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.aleph_tea.teabuddy.database.AppDatabase;
import es.aleph_tea.teabuddy.database.dao.ActividadDAO;
import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.database.repository.ActividadRepository;
import es.aleph_tea.teabuddy.database.repository.ActividadRepositoryImpl;

public class FBRTDatabaseController {

    private View activitiesView;
    private Context activitiesListContext; // this.getApplicationContext()

    public FBRTDatabaseController(Context activitiesListContext, View activitiesView) {
        this.activitiesListContext = activitiesListContext;
        this.activitiesView = activitiesView;
    }

    public void startService() {

        DatabaseReference mDatabase;

        AppDatabase db;
        ActividadDAO dao;
        ActividadRepository repo;

        // Inicialización FirebaseRTDB
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Inicialización BD ROOM
        db = AppDatabase.getInstance(activitiesListContext);
        dao = db.actividadDAO();
        repo = new ActividadRepositoryImpl(dao);

        // Obtención de datos en tiempo real guardados en ROOM dinámicamente
        roomRTUpdate(mDatabase,repo);
    }

    private void roomRTUpdate(DatabaseReference mDatabase, ActividadRepository repo) {
        mDatabase.child("Actividades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    Snackbar.make(activitiesView,"Actualizando actividades...",Snackbar.LENGTH_SHORT)
                            .show();

                    Log.d("ActualizacionFB", "Hubo cambios en FBRTDB, actualizando las " +
                            "listas de actividades");

                    // Como hay cambios en firebase, reconstruimos la tabla de actividades en ROOM

                    repo.deleteAllActividades();

                    for (DataSnapshot ds : snapshot.getChildren()) {

                        // Obtenemos los valores en crudo de cada actividad

                        String nombre = ds.child("nombre").getValue().toString();

                        String descripcion = ds.child("descripcion").getValue().toString();

                        Long fechaHora = Long.parseLong(ds.child("fechaHora")
                                .getValue().toString());

                        String localizacion = ds.child("localizacion").getValue().toString();

                        boolean estaInscrito = Boolean.parseBoolean(
                                ds.child("estaInscrito")
                                        .getValue().toString());

                        // Creamos la actividad obtenida para guardarla en la lista
                        Actividad actividad = new Actividad();

                        // Ajustamos sus valores
                        actividad.setNombre(nombre);
                        actividad.setDescripcion(descripcion);
                        actividad.setFechaHora(fechaHora);
                        actividad.setLocalizacion(localizacion);
                        actividad.setEstaInscrito(estaInscrito);

                        // Añadimos la actividad a la base de datos ROOM
                        repo.insertActividad(actividad);
                    }
                    // Activamos los trigger para la actualizacion de la UI
                    repo.findAllActividades();
                    repo.findActividadesInscritas();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
