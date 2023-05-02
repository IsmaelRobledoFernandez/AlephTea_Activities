package es.aleph_tea.teabuddy.database.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import es.aleph_tea.teabuddy.database.dao.UsuarioDAO;
import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.database.entity.Usuario;

public class UsuarioRepositoryImpl implements UsuarioRepository {

    UsuarioDAO dao;

    public UsuarioRepositoryImpl(UsuarioDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return dao.getAll();
    }

    @Override
    public List<Usuario> getUsuarioActual(String uid) {
        return dao.getActual(uid);
    }

    @Override
    public void deleteAllUsuarios() {
        dao.deleteAll();
        dao.resetTable();
    }

    @Override
    public void deleteUsuarioActual(String uid) {
        dao.deleteActual(uid);
    }

    @Override
    public Actividad findByIdUsuario(int uid) {
        return dao.findById(uid);
    }

    @Override
    public void insertUsuario(Usuario usuario) {
        dao.insert(usuario);
    }

    @Override
    public void updateUsuario(Usuario usuario) {
        dao.update(usuario);
    }

    @Override
    public void deleteUsuario(Usuario usuario) {
        dao.delete(usuario);
    }

    @Override
    public LiveData<List<Usuario>> findAllUsuarios() {
        return dao.findAll();
    }

    @Override
    public LiveData<List<Usuario>> findUsuarioActual(String uid) {
        return dao.findActual(uid);
    }
}
