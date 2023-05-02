package es.aleph_tea.teabuddy.database.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.database.entity.Usuario;

public interface UsuarioRepository {

    List<Usuario> getAllUsuarios();

    List<Usuario> getUsuarioActual(String uid);

    void deleteAllUsuarios();

    void deleteUsuarioActual(String uid);

    Actividad findByIdUsuario(int uid);

    @Insert
    void insertUsuario(Usuario usuario);
    @Update
    void updateUsuario(Usuario usuario);
    @Delete
    void deleteUsuario(Usuario usuario);

    LiveData<List<Usuario>> findAllUsuarios();

    LiveData<List<Usuario>> findUsuarioActual(String uid);

}
