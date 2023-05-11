package es.aleph_tea.teabuddy.models;

public class Sesion {
    public static boolean mantenerIniciado;

    public static String SesionUid = "PES2KERrBQSj0few7T5rPKMBngI3";
    public Sesion(boolean mantenerIniciado){
        this.mantenerIniciado = mantenerIniciado;
    }

    public boolean isMantenerIniciado() {
        return mantenerIniciado;
    }

    public void setMantenerIniciado(boolean mantenerIniciado) {
        this.mantenerIniciado = mantenerIniciado;
    }

}
