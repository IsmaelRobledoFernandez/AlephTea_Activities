package es.aleph_tea.teabuddy.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import es.aleph_tea.teabuddy.database.entity.Actividad;

@Dao
public interface ActividadDAO {

    @Query("select * from Actividad")
    List<Actividad> getAll();

    @Query("delete from Actividad")
    void deleteAll();

    // Consulta alternativa: update sqlite_sequence set seq=0 where name='Actividad'
    @Query("delete from sqlite_sequence")
    void resetTable();

    @Query("select * from Actividad where actividadId = :actividadId")
    Actividad findById(int actividadId);

    @Insert
    void insert(Actividad actividad);
    @Update
    void update (Actividad actividad);
    @Delete
    void delete(Actividad actividad);

    @Query("select * from Actividad")
    LiveData<List<Actividad>> findAll();

}
