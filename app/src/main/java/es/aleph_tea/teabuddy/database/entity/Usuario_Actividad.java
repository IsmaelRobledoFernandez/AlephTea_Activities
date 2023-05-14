package es.aleph_tea.teabuddy.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "usuario_actividad",primaryKeys = {"uid","actividadId"})

public class Usuario_Actividad {

    @NonNull
    String uid;
    @NonNull
    String actividadId;
    boolean inscrito;

    @Override
    public String toString() {
        return "Id: " + getUid() + " Actividad: " + getActividadId()
                + " EstaInscrito: " + isInscrito();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getActividadId() {
        return actividadId;
    }

    public void setActividadId(String actividadId) {
        this.actividadId = actividadId;
    }

    public boolean isInscrito() {
        return inscrito;
    }

    public void setInscrito(boolean inscrito) {
        this.inscrito = inscrito;
    }
}
