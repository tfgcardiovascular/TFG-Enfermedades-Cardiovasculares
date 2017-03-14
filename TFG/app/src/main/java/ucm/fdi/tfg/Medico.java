package ucm.fdi.tfg;

import java.io.Serializable;

public class Medico implements Serializable {

    private final static Medico medico=new Medico();
    private String id;
    private String colegiado;
    private String password;
    private String telefono;
    private String nombre;
    private String apellidos;
    private String mail;
    private String rol;
    //private byte[] imagen;

    public static final Medico getIntsance(){

        return medico;
    }

    // Constructor sin parámetros
    public Medico() {

    }

    // Constructor con parámetros
    public Medico(String id, String colegiado, String password, String telefono, String nombre, String apellidos, String mail, String rol) {

        this.id = id;
        this.colegiado = colegiado;
        this.password = password;
        this.telefono = telefono;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.mail = mail;
        this.rol = rol;
    }

    // Getters and setters
   /* public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColegiado() {
        return colegiado;
    }

    public void setColegiado(String colegiado) {
        this.colegiado = colegiado;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {this.mail = mail;}

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "ID='" + id + '\'' +
                ", colegiado='" + colegiado + '\'' +
                ", Nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", password='" + password + '\'' +
                ", telefono='" + telefono + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }



}
