package es.aleph_tea.teabuddy;

import androidx.appcompat.app.AppCompatActivity;

public class Sesion extends AppCompatActivity {
    public static boolean mantenerIniciado;
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
