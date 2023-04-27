package es.aleph_tea.teabuddy.models;

public class Actividad {
    String nombre;
    String descripcion;
    String fecha;
    String hora;

    String localizacion;
    int numero_voluntarios;
    int numero_monitores;

    public Actividad(String nombre, String descripcion, String fecha, String hora, String localizacion, int numero_voluntarios, int numero_monitores) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.numero_voluntarios = numero_voluntarios;
        this.numero_monitores = numero_monitores;
        this.localizacion = localizacion;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }
    public String getLocalizacion(){
        return localizacion;
    }
}
