package es.aleph_tea.teabuddy.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "actividad")

public class Actividad {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    String actividadId;
    String nombre;
    String descripcion;
    Long fechaHora;
    String localizacion;
    int numero_voluntarios;
    int numero_monitores;

    int numero_voluntarios_max;

    int numero_monitores_max;
    @Override
    public String toString() {
        return "Id: " + getActividadId() + " Nombre: " + getNombre();
    }

    public String getActividadId() {
        return actividadId;
    }

    public void setActividadId(String actividadId) {
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

    public Long getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Long fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public int getNumero_voluntarios() {
        return numero_voluntarios;
    }

    public void setNumero_voluntarios(int numero_voluntarios) {
        this.numero_voluntarios = numero_voluntarios;
    }

    public int getNumero_monitores() {
        return numero_monitores;
    }

    public void setNumero_monitores(int numero_monitores) {
        this.numero_monitores = numero_monitores;
    }

    public int getNumero_voluntarios_max() {
        return numero_voluntarios_max;
    }

    public void setNumero_voluntarios_max(int numero_voluntarios_max) {
        this.numero_voluntarios_max = numero_voluntarios_max;
    }

    public int getNumero_monitores_max() {
        return numero_monitores_max;
    }

    public void setNumero_monitores_max(int numero_monitores_max) {
        this.numero_monitores_max = numero_monitores_max;
    }
}
