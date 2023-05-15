package es.aleph_tea.teabuddy.controllers;

import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.aleph_tea.teabuddy.database.AppDatabase;
import es.aleph_tea.teabuddy.database.dao.ActividadDAO;
import es.aleph_tea.teabuddy.database.dao.UsuarioDAO;
import es.aleph_tea.teabuddy.database.dao.Usuario_ActividadDAO;
import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.database.entity.Usuario;
import es.aleph_tea.teabuddy.database.entity.Usuario_Actividad;
import es.aleph_tea.teabuddy.database.repository.ActividadRepository;
import es.aleph_tea.teabuddy.database.repository.ActividadRepositoryImpl;
import es.aleph_tea.teabuddy.database.repository.UsuarioRepository;
import es.aleph_tea.teabuddy.database.repository.UsuarioRepositoryImpl;
import es.aleph_tea.teabuddy.database.repository.Usuario_ActividadRepository;
import es.aleph_tea.teabuddy.database.repository.Usuario_ActividadRepositoryImpl;

public class FBRTDatabaseController {

    protected View activitiesView;
    protected Context activitiesListContext; // this.getApplicationContext()

    protected String uidActual;

    // Firebase
    DatabaseReference mDatabase;

    AppDatabase db;

    ActividadDAO daoA;
    ActividadRepository repoA;

    Usuario_ActividadDAO daoUA;
    Usuario_ActividadRepository repoUA;

    UsuarioDAO daoU;
    UsuarioRepository repoU;

    public FBRTDatabaseController(Context activitiesListContext, View activitiesView, String uidActual) {
        this.activitiesListContext = activitiesListContext;
        this.activitiesView = activitiesView;
        this.uidActual = uidActual;
    }

    public void startService() {

        // Inicialización FirebaseRTDB
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Inicialización BD ROOM
        db = AppDatabase.getInstance(activitiesListContext);

        //  Inicialización repo Actividades
        daoA = db.actividadDAO();
        repoA = new ActividadRepositoryImpl(daoA);

        // Inicialización repo Usuario_Actividad
        daoUA = db.usuario_actividadDAO();
        repoUA = new Usuario_ActividadRepositoryImpl(daoUA);

        // Inicialización repo Usuario_Actividad
        daoU = db.usuarioDAO();
        repoU = new UsuarioRepositoryImpl(daoU);
    }

    protected void roomRTUpdate(DatabaseReference mDatabase, ActividadRepository repoA,
                              Usuario_ActividadRepository repoUA, UsuarioRepository repoU) {}
}

