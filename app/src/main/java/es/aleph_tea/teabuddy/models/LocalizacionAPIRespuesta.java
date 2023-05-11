package es.aleph_tea.teabuddy.models;
import java.util.ArrayList;

public class LocalizacionAPIRespuesta {

    private ArrayList<LocalizacionActividad> data;

    public ArrayList<LocalizacionActividad> getData() {
        return data;
    }

    public void setData(ArrayList<LocalizacionActividad> data) {
        this.data = data;
    }
}
