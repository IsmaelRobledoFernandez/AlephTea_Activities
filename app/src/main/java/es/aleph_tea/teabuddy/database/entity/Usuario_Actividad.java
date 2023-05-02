package es.aleph_tea.teabuddy.database.entity;

import androidx.room.Entity;

@Entity(tableName = "actividad",primaryKeys = {"uid","actividadId"})

public class Usuario_Actividad {

    String uid;
    int actividadId;
    boolean inscrito;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getActividadId() {
        return actividadId;
    }

    public void setActividadId(int actividadId) {
        this.actividadId = actividadId;
    }

    public boolean isInscrito() {
        return inscrito;
    }

    public void setInscrito(boolean inscrito) {
        this.inscrito = inscrito;
    }
}
