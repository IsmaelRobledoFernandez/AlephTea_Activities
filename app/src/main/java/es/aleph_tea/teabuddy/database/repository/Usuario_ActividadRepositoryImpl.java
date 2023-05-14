package es.aleph_tea.teabuddy.database.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import es.aleph_tea.teabuddy.database.dao.Usuario_ActividadDAO;
import es.aleph_tea.teabuddy.database.entity.Usuario_Actividad;

public class Usuario_ActividadRepositoryImpl implements Usuario_ActividadRepository{

    Usuario_ActividadDAO dao;

    public Usuario_ActividadRepositoryImpl(Usuario_ActividadDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Usuario_Actividad> getInscripcionesUsuario_Actividad(String uid) {
        return dao.getInscripciones(uid);
    }

    @Override
    public Usuario_Actividad getUsuario_ActividadUsuariosInscritos(String actividadId) {
        return dao.getUsuariosInscritos(actividadId);
    }

    @Override
    public void deleteAllUsuarios_Actividades() {
        dao.deleteAll();
       //dao.resetTable();
    }

    public void deleteActividadesUsuario_Actividad(String uid) {
        dao.deleteActividades(uid);
    }

    @Override
    public Usuario_Actividad findByIdUsuario_Actividad(String uid, String actividadId) {
        return dao.findById(uid,actividadId);
    }

    @Override
    public void insertUsuario_Actividad(Usuario_Actividad usuario_actividad) {
        dao.insert(usuario_actividad);
    }

    @Override
    public void updateUsuario_Actividad(Usuario_Actividad usuario_actividad) {
        dao.update(usuario_actividad);
    }

    @Override
    public void deleteUsuario_Actividad(Usuario_Actividad usuario_actividad) {
        dao.delete(usuario_actividad);
    }

    @Override
    public LiveData<List<Usuario_Actividad>> findUsuarios_ActividadesInscritasAUsuario(String uid) {
        return dao.findInscritas(uid);
    }

    @Override
    public LiveData<List<Usuario_Actividad>> findUsuarios_ActividadesInscritosAActividades(String actividadId) {
        return dao.findUsuariosInscritos(actividadId);
    }
}
