package es.aleph_tea.teabuddy.database.dao;

import java.util.List;

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

    @Query("select * from Actividad where estaInscrito = 1")
    List<Actividad> getInscritas();

    @Query("select * from Actividad where actividadId = :actividadId")
    Actividad findById(int actividadId);

    @Insert
    void insert(Actividad actividad);
    @Update
    void update (Actividad actividad);
    @Delete
    void delete(Actividad actividad);

}
