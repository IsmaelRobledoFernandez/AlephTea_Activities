package es.aleph_tea.teabuddy.controllers;

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
import es.aleph_tea.teabuddy.database.dao.UsuarioDAO;
import es.aleph_tea.teabuddy.database.dao.Usuario_ActividadDAO;
import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.database.entity.Usuario;
import es.aleph_tea.teabuddy.database.entity.Usuario_Actividad;
import es.aleph_tea.teabuddy.database.repository.ActividadRepository;
import es.aleph_tea.teabuddy.database.repository.ActividadRepositoryImpl;
import es.aleph_tea.teabuddy.database.repository.UsuarioRepository;
import es.aleph_tea.teabuddy.database.repository.UsuarioRepositoryImpl;
import es.aleph_tea.teabuddy.database.repository.Usuario_ActividadRepository;
import es.aleph_tea.teabuddy.database.repository.Usuario_ActividadRepositoryImpl;

public class FBRTDatabaseController {

    private View activitiesView;
    private Context activitiesListContext; // this.getApplicationContext()

    String uidActual;

    public FBRTDatabaseController(Context activitiesListContext, View activitiesView, String uidActual) {
        this.activitiesListContext = activitiesListContext;
        this.activitiesView = activitiesView;
        this.uidActual = uidActual;
    }

    public void startService() {

        DatabaseReference mDatabase;

        AppDatabase db;

        ActividadDAO daoA;
        ActividadRepository repoA;

        Usuario_ActividadDAO daoUA;
        Usuario_ActividadRepository repoUA;

        UsuarioDAO daoU;
        UsuarioRepository repoU;

        // Inicialización FirebaseRTDB
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Inicialización BD ROOM
        db = AppDatabase.getInstance(activitiesListContext);

        //  Inicialización repo Actividades
        daoA = db.actividadDAO();
        repoA = new ActividadRepositoryImpl(daoA);

        // Inicialización repo Usuario_Actividad
        daoUA = db.usuario_actividadDAO();
        repoUA = new Usuario_ActividadRepositoryImpl(daoUA);

        // Inicialización repo Usuario_Actividad
        daoU = db.usuarioDAO();
        repoU = new UsuarioRepositoryImpl(daoU);

        // Obtención de datos en tiempo real guardados en ROOM dinámicamente
        roomRTUpdate(mDatabase,repoA,repoUA,repoU);
    }

    private void roomRTUpdate(DatabaseReference mDatabase, ActividadRepository repoA,
                              Usuario_ActividadRepository repoUA, UsuarioRepository repoU) {

        mDatabase.child("Actividades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    Snackbar.make(activitiesView,"Actualizando actividades...",Snackbar.LENGTH_SHORT)
                            .show();

                    Log.d("ActualizacionFB", "Hubo cambios en FBRTDB, actualizando las " +
                            "listas de actividades");

                    // Como hay cambios en firebase, reconstruimos la tabla de actividades en ROOM

                    repoA.deleteAllActividades();

                    for (DataSnapshot ds : snapshot.getChildren()) {

                        // Obtenemos los valores en crudo de cada actividad

                        int actividadId = Integer.parseInt(ds.getKey());

                        String nombre = ds.child("nombre").getValue().toString();

                        String descripcion = ds.child("descripcion").getValue().toString();

                        Long fechaHora = Long.parseLong(ds.child("fechaHora")
                                .getValue().toString());

                        String localizacion = ds.child("localizacion").getValue().toString();

                        // Creamos la actividad obtenida para guardarla en la lista
                        Actividad actividad = new Actividad();

                        // Ajustamos sus valores
                        actividad.setActividadId(actividadId);
                        actividad.setNombre(nombre);
                        actividad.setDescripcion(descripcion);
                        actividad.setFechaHora(fechaHora);
                        actividad.setLocalizacion(localizacion);

                        // Añadimos la actividad a la base de datos ROOM
                        repoA.insertActividad(actividad);
                    }
                    // Activamos los trigger para la actualizacion de la UI
                    repoA.findAllActividades();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //
        // A continuación se encuentran los listeners de las modificaciones
        // que se le realicen a las actividades que contenga el usuario actual
        // y las modificaciones que se le realice al perfil del usuario actual
        // (Se considera usuario actual a aquel que tiene iniciada la sesión)
        //

        mDatabase.child("Usuario_Actividad")
                .child(uidActual).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    Snackbar.make(activitiesView, "Actualizando inscripciones...", Snackbar.LENGTH_SHORT)
                            .show();

                    Log.d("ActualizacionFB", "Hubo cambios en FBRTDB, actualizando las " +
                            "Inscripciones del usuario actual");

                    // Como hay cambios en firebase, reconstruimos la tabla de inscripciones en ROOM
                    repoUA.deleteActividadesUsuario_Actividad(uidActual);

                    for (DataSnapshot activity : snapshot.getChildren()) {

                        // Obtenemos los valores en crudo de cada actividad

                        int actividadId = Integer.parseInt(activity.getKey());

                        Boolean inscrito = (Boolean) activity.getValue();

                        // Creamos la relacion obtenida para guardarla en la lista
                        Usuario_Actividad usuario_actividad = new Usuario_Actividad();

                        // Ajustamos sus valores
                        usuario_actividad.setUid(uidActual);
                        usuario_actividad.setActividadId(actividadId);
                        usuario_actividad.setInscrito(inscrito);

                        // Añadimos la actividad a la base de datos ROOM
                        repoUA.insertUsuario_Actividad(usuario_actividad);
                    }
                    // Activamos los trigger para la actualizacion de la UI
                    repoUA.findUsuarios_ActividadesInscritasAUsuario(uidActual);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("Usuario")
                .child(uidActual).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            Snackbar.make(activitiesView, "Actualizando info. Usuario...", Snackbar.LENGTH_SHORT)
                                    .show();

                            Log.d("ActualizacionFB", "Hubo cambios en FBRTDB, actualizando la " +
                                    "Información del usuario actual");

                            // Como hay cambios en firebase, reconstruimos la tabla de inscripciones en ROOM
                            repoU.deleteUsuarioActual(uidActual);

                            for (DataSnapshot ds : snapshot.getChildren()) {

                                    // Obtenemos los valores en crudo de cada actividad
                                    String nombre = ds.child("nombre").getValue().toString();
                                    String apellidos = ds.child("apellidos").getValue().toString();
                                    String fecha_nacimiento = ds.child("fecha_nacimiento").getValue().toString();
                                    String numero_telefono = ds.child("numero_telefono").getValue().toString();
                                    String rol = ds.child("rol").getValue().toString();

                                    // Creamos la relacion obtenida para guardarla en la lista
                                    Usuario usuario = new Usuario();

                                    // Ajustamos sus valores
                                    usuario.setNombre(nombre);
                                    usuario.setApellidos(apellidos);
                                    usuario.setFecha_nacimiento(fecha_nacimiento);
                                    usuario.setNumero_telefono(numero_telefono);
                                    usuario.setRol(rol);

                                    // Añadimosel usuario modificado a la base de datos ROOM
                                    repoU.insertUsuario(usuario);
                                }
                            // Activamos los trigger para la actualizacion de la UI
                            repoU.findUsuarioActual(uidActual);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
     }
}

