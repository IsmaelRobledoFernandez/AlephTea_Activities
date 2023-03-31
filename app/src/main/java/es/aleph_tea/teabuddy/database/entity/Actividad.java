package es.aleph_tea.teabuddy.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "actividades")
public class Actividades {

    @PrimaryKey(autoGenerate = true)
    int actividadId;
    String nombre;
    String descripcion;
    String fechaHora;
    String localizacion;
    boolean estaInscrito;

    public int getActividadId() {
        return actividadId;
    }

    public void setActividadId(int actividadId) {
        this.actividadId = actividadId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public boolean isEstaInscrito() {
        return estaInscrito;
    }

    public void setEstaInscrito(boolean estaInscrito) {
        this.estaInscrito = estaInscrito;
    }
}
