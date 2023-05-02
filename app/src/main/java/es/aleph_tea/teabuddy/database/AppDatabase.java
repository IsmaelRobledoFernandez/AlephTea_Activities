package es.aleph_tea.teabuddy.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.aleph_tea.teabuddy.database.dao.ActividadDAO;
import es.aleph_tea.teabuddy.database.dao.UsuarioDAO;
import es.aleph_tea.teabuddy.database.dao.Usuario_ActividadDAO;
import es.aleph_tea.teabuddy.database.entity.Actividad;
import es.aleph_tea.teabuddy.database.entity.Usuario;
import es.aleph_tea.teabuddy.database.entity.Usuario_Actividad;

@Database(entities = {
        Actividad.class, Usuario_Actividad.class,
        Usuario.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase INSTANCE;

    public abstract ActividadDAO actividadDAO();

    public abstract Usuario_ActividadDAO usuario_actividadDAO();

    public abstract UsuarioDAO usuarioDAO();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context,AppDatabase.class,"actividades.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        return INSTANCE;
    }
}
