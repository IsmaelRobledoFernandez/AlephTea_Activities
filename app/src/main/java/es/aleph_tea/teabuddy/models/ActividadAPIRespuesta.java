package es.aleph_tea.teabuddy.models;
import java.util.ArrayList;

public class ActividadAPIRespuesta {

    private ArrayList<ActividadWrapper> events;

    public ArrayList<ActividadWrapper> getData() {
        return events;
    }

    public void setData(ArrayList<ActividadWrapper> events) {
        this.events = events;
    }
}
