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
import es.aleph_tea.teabuddy.database.entity.Usuario_Actividad;
import es.aleph_tea.teabuddy.database.repository.ActividadRepository;
import es.aleph_tea.teabuddy.database.repository.UsuarioRepository;
import es.aleph_tea.teabuddy.database.repository.Usuario_ActividadRepository;

public class FBRTDBControllerVoluntario extends FBRTDatabaseController {

    public FBRTDBControllerVoluntario(Context activitiesListContext,
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

                                String actividadId = activity.getKey();

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

        mDatabase.child("UsuariosAsociacion")
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

                                // Obtenemos los valores en crudo de cada actividad
                                String nombre = snapshot.child("nombre").getValue().toString();
                                String apellidos = snapshot.child("apellidos").getValue().toString();
                                String email = snapshot.child("email").getValue().toString();
                                String fecha_nacimiento = snapshot.child("fecha_nacimiento").getValue().toString();
                                String numero_telefono = snapshot.child("numero_telefono").getValue().toString();
                                String rol = snapshot.child("rol").getValue().toString();

                                // Creamos el usuario obtenido para guardarlo en la lista
                                Usuario usuario = new Usuario();

                                // Ajustamos sus valores
                                usuario.setUid(uidActual);
                                usuario.setNombre(nombre);
                                usuario.setApellidos(apellidos);
                                usuario.setEmail(email);
                                usuario.setFecha_nacimiento(fecha_nacimiento);
                                usuario.setNumero_telefono(numero_telefono);
                                usuario.setRol(rol);

                                // Añadimos el usuario modificado a la base de datos ROOM
                                repoU.insertUsuario(usuario);
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
