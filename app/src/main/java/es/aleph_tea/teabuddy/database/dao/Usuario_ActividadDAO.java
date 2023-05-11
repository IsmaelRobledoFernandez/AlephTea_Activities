package es.aleph_tea.teabuddy.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import es.aleph_tea.teabuddy.database.entity.Usuario_Actividad;

@Dao
public interface Usuario_ActividadDAO {

    @Query("select * from Usuario_Actividad where uid = :uid")
    List<Usuario_Actividad> getInscripciones(String uid);

    @Query("select * from Usuario_Actividad where actividadId = :actividadId")
    Usuario_Actividad getUsuariosInscritos(int actividadId);

    @Query("delete from Usuario_Actividad")
    void deleteAll();

    @Query("delete from Usuario_Actividad where uid = :uid")
    void deleteActividades(String uid);

    /*
    // Consulta alternativa: update sqlite_sequence set seq=0 where name='usuario_actividad'
    @Query("update sqlite_sequence set seq=0 where name='usuario_actividad'")
    void resetTable();
     */

    @Query("select * from Usuario_Actividad where uid = :uid and actividadId = :actividadId")
    Usuario_Actividad findById(String uid,int actividadId);

    @Insert
    void insert(Usuario_Actividad usuario_actividad);
    @Update
    void update (Usuario_Actividad usuario_actividad);
    @Delete
    void delete(Usuario_Actividad usuario_actividad);

    @Query("select * from Usuario_Actividad where uid = :uid")
    LiveData<List<Usuario_Actividad>> findInscritas(String uid);

    @Query("select * from Usuario_Actividad where actividadId = :actividadId")
    LiveData<List<Usuario_Actividad>> findUsuariosInscritos(int actividadId);

}
