package ucm.fdi.tfg;

import java.io.Serializable;

public class Paciente implements Serializable {

    private final static Paciente paciente=new Paciente();
    private String id;
    private String sexo;
    private String edad;
    private String telefono;
    private String nombre;
    private String apellidos;
    private String mail;
    private String rol;
    //private byte[] imagen;

    public static final Paciente getIntsance(){

        return paciente;
    }

    // Constructor sin parámetros
    public Paciente() {

    }

    // Constructor con parámetros
    public Paciente(String id, String sexo, String edad) {

        this.id = id;
        this.sexo = sexo;
        this.edad = edad;

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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }


    @Override
    public String toString() {
        return "paciente{" +
                "ID='" + id + '\'' +
                ", sexo='" + sexo + '\'' +
                ", edad='" + edad + '\'' +
                '}';
    }



}
