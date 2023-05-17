package es.aleph_tea.teabuddy.models;

public class ActividadAPI {

    private String name;
    private String description;

    private String url;

    public ActividadAPI() {}

    public ActividadAPI(String name, String description, String url) {
        this.name = name;
        this.description = description;
        this.url = url;
    }
    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String geturl() {
        return url;
    }

    public void seturl(String url) {
        this.url = url;
    }

}
