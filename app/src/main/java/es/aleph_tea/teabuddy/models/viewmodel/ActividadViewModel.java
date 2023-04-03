package es.aleph_tea.teabuddy.models.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.aleph_tea.teabuddy.database.AppDatabase;
import es.aleph_tea.teabuddy.database.dao.ActividadDAO;
import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.database.repository.ActividadRepository;
import es.aleph_tea.teabuddy.database.repository.ActividadRepositoryImpl;

public class ActividadViewModel extends AndroidViewModel {

    AppDatabase bdActividades;
    private ActividadDAO dao;
    private ActividadRepository repo;

    public ActividadViewModel(@NonNull Application application) {
        super(application);
        // Inicialización BD ROOM
        bdActividades = AppDatabase.getInstance(application.getApplicationContext());
        dao = bdActividades.actividadDAO();
        repo = new ActividadRepositoryImpl(dao);
    }

    public LiveData<List<Actividad>> getAll() {
        return repo.findAllActividades();
    }

    public LiveData<List<Actividad>> getInscritas() {
        return repo.findActividadesInscritas();
    }
}
