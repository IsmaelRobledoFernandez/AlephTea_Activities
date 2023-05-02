package es.aleph_tea.teabuddy.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.database.entity.Usuario;

@Dao
public interface UsuarioDAO {

    @Query("select * from Usuario")
    List<Usuario> getAll();

    @Query("select * from Usuario where uid = :uid")
    List<Usuario> getActual(String uid);

    @Query("delete from Usuario")
    void deleteAll();

    @Query("delete from Usuario where uid = :uid")
    void deleteActual(String uid);

    // Consulta alternativa: update sqlite_sequence set seq=0 where name='Actividad'
    @Query("delete from sqlite_sequence")
    void resetTable();

    @Query("select * from Usuario where uid = :uid")
    Actividad findById(int uid);

    @Insert
    void insert(Usuario usuario);
    @Update
    void update (Usuario usuario);
    @Delete
    void delete(Usuario usuario);

    @Query("select * from Usuario")
    LiveData<List<Usuario>> findAll();

    @Query("select * from Usuario where uid = :uid")
    LiveData<List<Usuario>> findActual(String uid);
}
