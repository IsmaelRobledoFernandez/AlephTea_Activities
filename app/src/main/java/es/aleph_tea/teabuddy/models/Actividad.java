package es.aleph_tea.teabuddy.models;

public class Actividad {
    String nombre_actividad_str;
    String descripcion_actividad_str;
    String fecha_actividad_str;
    String hora_actividad_str;
    String localizacion_str;
//    int numero_voluntarios;
//    int numero_monitores;

    public Actividad(){

    }
    public Actividad(String nombre_actividad_str, String descripcion_actividad_str, String fecha_actividad_str, String hora_actividad_str, String localizacion_str) {
        this.nombre_actividad_str = nombre_actividad_str;
        this.descripcion_actividad_str = descripcion_actividad_str;
        this.fecha_actividad_str = fecha_actividad_str;
        this.hora_actividad_str = hora_actividad_str;
        this.localizacion_str = localizacion_str;
    }

    public String getNombre_actividad_str() {
        return nombre_actividad_str;
    }

    public void setNombre_actividad_str(String nombre_actividad_str) {
        this.nombre_actividad_str = nombre_actividad_str;
    }

    public String getDescripcion_actividad_str() {
        return descripcion_actividad_str;
    }

    public void setDescripcion_actividad_str(String descripcion_actividad_str) {
        this.descripcion_actividad_str = descripcion_actividad_str;
    }

    public String getFecha_actividad_str() {
        return fecha_actividad_str;
    }

    public void setFecha_actividad_str(String fecha_actividad_str) {
        this.fecha_actividad_str = fecha_actividad_str;
    }

    public String getHora_actividad_str() {
        return hora_actividad_str;
    }

    public void setHora_actividad_str(String hora_actividad_str) {
        this.hora_actividad_str = hora_actividad_str;
    }

    public String getLocalizacion_str() {
        return localizacion_str;
    }

    public void setLocalizacion_str(String localizacion_str) {
        this.localizacion_str = localizacion_str;
    }
}
