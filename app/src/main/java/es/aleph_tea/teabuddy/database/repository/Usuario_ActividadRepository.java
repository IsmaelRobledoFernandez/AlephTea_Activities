package es.aleph_tea.teabuddy.database.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import es.aleph_tea.teabuddy.database.entity.Usuario_Actividad;

public interface Usuario_ActividadRepository {

    List<Usuario_Actividad> getInscripcionesUsuario_Actividad(String uid);

    Usuario_Actividad getUsuario_ActividadUsuariosInscritos(int actividadId);

    void deleteAllUsuarios_Actividades();

    void deleteActividadesUsuario_Actividad(String uid);

    Usuario_Actividad findByIdUsuario_Actividad(String uid,int actividadId);

    void insertUsuario_Actividad(Usuario_Actividad usuario_actividad);

    void updateUsuario_Actividad (Usuario_Actividad usuario_actividad);

    void deleteUsuario_Actividad(Usuario_Actividad usuario_actividad);

    LiveData<List<Usuario_Actividad>> findUsuarios_ActividadesInscritasAUsuario(String uid);
    LiveData<List<Usuario_Actividad>> findUsuarios_ActividadesInscritosAActividades(int actividadId);
}
