package es.aleph_tea.teabuddy.database.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import es.aleph_tea.teabuddy.database.entity.Actividad;

public interface ActividadRepository {

    List<Actividad> getAllActividades();

    void deleteAllActividades();

    Actividad findByIdActividad(int actividadId);

    void insertActividad(Actividad actividad);

    void updateActividad(Actividad actividad);

    void deleteActividad(Actividad actividad);

    LiveData<List<Actividad>> findAllActividades();

}
