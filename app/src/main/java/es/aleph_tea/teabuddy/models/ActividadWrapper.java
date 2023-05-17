package es.aleph_tea.teabuddy.models;

public class ActividadWrapper{
    private ActividadAPI event;

    public ActividadWrapper(ActividadAPI event) {
        this.event = event;
    }

    public ActividadAPI getEvent() {
        return event;
    }

    public void setEvent(ActividadAPI event) {
        this.event = event;
    }
}