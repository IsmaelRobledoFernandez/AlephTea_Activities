package es.aleph_tea.teabuddy.database.repository;

import java.util.List;

import es.aleph_tea.teabuddy.database.dao.ActividadDAO;
import es.aleph_tea.teabuddy.database.entity.Actividad;

public class ActividadRepositoryImpl implements ActividadRepository{

    ActividadDAO dao;

    public ActividadRepositoryImpl(ActividadDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Actividad> getAllActividades() {
        return dao.getAll();
    }

    @Override
    public List<Actividad> getActividadesInscritas() {
        return dao.getInscritas();
    }

    @Override
    public Actividad findById(int actividadId) { return dao.findById(actividadId); }

    @Override
    public void insertActividad(Actividad actividad) {
        dao.insert(actividad);
    }

    @Override
    public void updateActividad(Actividad actividad) {
        dao.update(actividad);
    }

    @Override
    public void deleteActividad(Actividad actividad) {
        dao.delete(actividad);
    }
}
