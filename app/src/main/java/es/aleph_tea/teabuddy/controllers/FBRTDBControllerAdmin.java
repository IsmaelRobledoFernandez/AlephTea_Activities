package es.aleph_tea.teabuddy.controllers;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.database.entity.Usuario;
import es.aleph_tea.teabuddy.database.repository.ActividadRepository;
import es.aleph_tea.teabuddy.database.repository.UsuarioRepository;
import es.aleph_tea.teabuddy.database.repository.Usuario_ActividadRepository;

public class FBRTDBControllerAdmin extends FBRTDatabaseController {

    public FBRTDBControllerAdmin(Context activitiesListContext,
                                      View activitiesView, String uidActual) {
        super(activitiesListContext,activitiesView,uidActual);
    }

    @Override
    public void startService() {
        super.startService();
        // Obtención de datos en tiempo real guardados en ROOM dinámicamente
        roomRTUpdate(mDatabase,repoA,repoUA,repoU);
    }

    @Override
    protected void roomRTUpdate(DatabaseReference mDatabase, ActividadRepository repoA,
                                Usuario_ActividadRepository repoUA, UsuarioRepository repoU) {

        mDatabase.child("ActividadesAsociacion").addValueEventListener(new ValueEventListener() {
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

                        String actividadId = ds.getKey();

                        String nombre = ds.child("nombre").getValue().toString();

                        String descripcion = ds.child("descripcion").getValue().toString();

                        Long fechaHora = Long.parseLong(ds.child("fechaHora")
                                .getValue().toString());

                        String localizacion = ds.child("localizacion").getValue().toString();

                        int numero_voluntarios = Integer.parseInt(
                                ds.child("numero_voluntarios").getValue().toString());
                        int numero_monitores = Integer.parseInt(
                                ds.child("numero_monitores").getValue().toString());

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

        mDatabase.child("UsuariosAsociacion")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            Snackbar.make(activitiesView, "Actualizando info. Usuario...", Snackbar.LENGTH_SHORT)
                                    .show();

                            Log.d("ActualizacionFB", "Hubo cambios en FBRTDB, actualizando la " +
                                    "Información de todos los usuarios");

                            // Como hay cambios en firebase, reconstruimos la tabla de inscripciones en ROOM
                            repoU.deleteAllUsuarios();

                            for (DataSnapshot ds : snapshot.getChildren()) {

                                // Obtenemos los valores en crudo de cada Usuario
                                String uid = ds.getKey();
                                String nombre = ds.child("nombre").getValue().toString();
                                String apellidos = ds.child("apellidos").getValue().toString();
                                String email = ds.child("email").getValue().toString();
                                String fecha_nacimiento = ds.child("fecha_nacimiento").getValue().toString();
                                String numero_telefono = ds.child("numero_telefono").getValue().toString();
                                String rol = ds.child("rol").getValue().toString();

                                // Creamos el usuario obtenido para guardarlo en la lista
                                Usuario usuario = new Usuario();

                                // Ajustamos sus valores
                                usuario.setUid(uid);
                                usuario.setNombre(nombre);
                                usuario.setApellidos(apellidos);
                                usuario.setEmail(email);
                                usuario.setFecha_nacimiento(fecha_nacimiento);
                                usuario.setNumero_telefono(numero_telefono);
                                usuario.setRol(rol);

                                // Añadimos el usuario modificado a la base de datos ROOM
                                repoU.insertUsuario(usuario);
                            }
                            // Activamos los trigger para la actualizacion de la UI
                            repoU.findAllUsuarios();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


}
