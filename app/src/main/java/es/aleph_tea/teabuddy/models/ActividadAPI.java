package es.aleph_tea.teabuddy.models;

public class ActividadAPI {

    private String dat_nombre;
    private String actividad_extraexcolar_descrip;

    private String centro_nombre;

    public String getActividad_extraexcolar_descrip() {
        return actividad_extraexcolar_descrip;
    }

    public void setActividad_extraexcolar_descrip(String actividad_extraexcolar_descrip) {
        this.actividad_extraexcolar_descrip = actividad_extraexcolar_descrip;
    }

    public String getDat_nombre() {
        return dat_nombre;
    }

    public void setDat_nombre(String dat_nombre) {
        this.dat_nombre = dat_nombre;
    }

    public String getCentro_nombre() {
        return centro_nombre;
    }

    public void setCentro_nombre(String centro_nombre) {
        this.centro_nombre = centro_nombre;
    }

}
