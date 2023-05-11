package es.aleph_tea.teabuddy.models.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.aleph_tea.teabuddy.database.AppDatabase;
import es.aleph_tea.teabuddy.database.dao.UsuarioDAO;
import es.aleph_tea.teabuddy.database.entity.Usuario;
import es.aleph_tea.teabuddy.database.repository.UsuarioRepository;
import es.aleph_tea.teabuddy.database.repository.UsuarioRepositoryImpl;

public class UsuarioViewModel extends AndroidViewModel {

    AppDatabase bdActividades;

    UsuarioDAO dao;

    UsuarioRepository repo;

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        // Inicialización BD ROOM
        bdActividades = AppDatabase.getInstance(application.getApplicationContext());
        dao = bdActividades.usuarioDAO();
        repo = new UsuarioRepositoryImpl(dao);
    }

    public LiveData<List<Usuario>> getAll() { return repo.findAllUsuarios(); }

    public LiveData<List<Usuario>> getActual(String uid) { return repo.findUsuarioActual(uid); }

}
