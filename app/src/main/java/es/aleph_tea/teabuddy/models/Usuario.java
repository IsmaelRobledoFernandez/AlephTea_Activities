package es.aleph_tea.teabuddy.models;

public class Usuario {
    public String email;
    public String password;
    public String n_telefono;
    public String fecha_nacimiento;
    public String apellido;
    public String nombre;

    public String rol;

    public Usuario(String email, String n_telefono, String fecha_nacimiento, String apellido, String nombre, String rol) {
        this.email = email;
        this.n_telefono = n_telefono;
        this.fecha_nacimiento = fecha_nacimiento;
        this.apellido = apellido;
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getRol(){return rol;}


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

    public void setRol(String rol) {
        this.rol = rol;
    }
}
