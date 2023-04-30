package es.aleph_tea.teabuddy.models;

public class Usuario {
    public String email;
    public String password;
    public String numero_telefono;
    public String fecha_nacimiento;
    public String apellidos;
    public String nombre;

    public String rol;

    public Usuario(){

    }
    public Usuario(String email, String numero_telefono, String fecha_nacimiento, String apellidos, String nombre, String rol) {
        this.email = email;
        this.numero_telefono = numero_telefono;
        this.fecha_nacimiento = fecha_nacimiento;
        this.apellidos = apellidos;
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
        return numero_telefono;
    }

    public void setN_telefono(String n_telefono) {
        this.numero_telefono = numero_telefono;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getApellido() {
        return apellidos;
    }

    public void setApellido(String apellidos) {
        this.apellidos = apellidos;
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
