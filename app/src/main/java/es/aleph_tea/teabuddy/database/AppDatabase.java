package es.aleph_tea.teabuddy.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.aleph_tea.teabuddy.database.dao.ActividadDAO;
import es.aleph_tea.teabuddy.database.entity.Actividad;

@Database(entities = {
        Actividad.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase INSTANCE;

    public abstract ActividadDAO actividadDAO();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context,AppDatabase.class,"actividades.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        return INSTANCE;
    }
}
