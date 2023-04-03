package es.aleph_tea.teabuddy;

public class Usuario {
    private String email;
    private String password;
    private String n_telefono;
    private String fecha_nacimiento;
    private String apellido;
    private String nombre;

    public Usuario(String email, String password, String n_telefono, String fecha_nacimiento, String apellido, String nombre) {
        this.email = email;
        this.password = password;
        this.n_telefono = n_telefono;
        this.fecha_nacimiento = fecha_nacimiento;
        this.apellido = apellido;
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getN_telefono() {
        return n_telefono;
    }

    public void setN_telefono(String n_telefono) {
        this.n_telefono = n_telefono;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
