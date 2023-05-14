package es.aleph_tea.teabuddy.models.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.aleph_tea.teabuddy.database.AppDatabase;
import es.aleph_tea.teabuddy.database.dao.Usuario_ActividadDAO;
import es.aleph_tea.teabuddy.database.entity.Usuario_Actividad;
import es.aleph_tea.teabuddy.database.repository.Usuario_ActividadRepository;
import es.aleph_tea.teabuddy.database.repository.Usuario_ActividadRepositoryImpl;

public class Usuario_ActividadViewModel extends AndroidViewModel {

    AppDatabase bdActividades;

    Usuario_ActividadDAO dao;

    Usuario_ActividadRepository repo;

    public Usuario_ActividadViewModel(@NonNull Application application) {
        super(application);
        // Inicialización BD ROOM
        bdActividades = AppDatabase.getInstance(application.getApplicationContext());
        dao = bdActividades.usuario_actividadDAO();
        repo = new Usuario_ActividadRepositoryImpl(dao);
    }

    public LiveData<List<Usuario_Actividad>> getInscritas(String uid) {
        return repo.findUsuarios_ActividadesInscritasAUsuario(uid); }

    public LiveData<List<Usuario_Actividad>> getUsuariosInscritos(String actividadId) {
        return repo.findUsuarios_ActividadesInscritosAActividades(actividadId); }
}
