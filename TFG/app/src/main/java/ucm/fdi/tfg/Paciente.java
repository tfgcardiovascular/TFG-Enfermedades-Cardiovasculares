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

    //IMC
    private String height = "";
    private String weight = "";
    private String imc = "";

    //HTA
    private String sistolica = "";
    private String diastolica = "";


    //TABAQUISMO
    private String cantidad = "";
    private String adiccion = "";
    private String ipa = "";

    //DIABETES
    private String tipo = "";
    private String tratamiento = "";
    private String hbac = "";
    private String monitorizacion = "";
    private String yearEnfermo = "";
    private String lastEyes = "";
    private String creatinina = "";
    private String microalbuminuria = "";
    private String raza = "";
    private String urea ="";

    //COLESTEROL
    private String hdl = "";
    private String total = "";
    private String trigliceridos = "";
    private String ldl = "";

    // CARDIOVASCULAR
    private String cardiovascular ="";
    private String finalTratamiento = "";


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

    //IMC
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getImc() {
        return imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
    }

    //HTA
    public String getSistolica() {
        return sistolica;
    }

    public void setSistolica(String sistolica) {
        this.sistolica = sistolica;
    }

    public String getDiastolica() {
        return diastolica;
    }

    public void setDiastolica(String diastolica) {
        this.diastolica = diastolica;
    }

    //TABAQUISMO
    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getAdiccion() {
        return adiccion;
    }

    public void setAdiccion(String adiccion) {
        this.adiccion = adiccion;
    }

    public String getIpa() {
        return ipa;
    }

    public void setIpa(String ipa) {
        this.ipa = ipa;
    }

    //DIABETES
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getHbac() {
        return hbac;
    }

    public void setHbac(String hbac) {
        this.hbac = hbac;
    }

    public String getMonitorizacion() {
        return monitorizacion;
    }

    public void setMonitorizacion(String monitorizacion) {
        this.monitorizacion = monitorizacion;
    }

    public String getYearEnfermo() {
        return yearEnfermo;
    }

    public void setYearEnfermo(String yearEnfermo) {
        this.yearEnfermo = yearEnfermo;
    }

    public String getLastEyes() {
        return lastEyes;
    }

    public void setLastEyes(String lastEyes) {
        this.lastEyes = lastEyes;
    }

    public String getCreatinina() {
        return creatinina;
    }

    public void setCreatinina(String creatinina) {
        this.creatinina = creatinina;
    }

    public String getMicroalbuminuria() {
        return microalbuminuria;
    }

    public void setMicroalbuminuria(String microalbuminuria) {
        this.microalbuminuria = microalbuminuria;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getUrea() {
        return urea;
    }

    public void setUrea(String urea) {
        this.urea = urea;
    }

    public String getHdl() {
        return hdl;
    }

    public void setHdl(String hdl) {
        this.hdl = hdl;
    }

    public String getColesterolTotal() {
        return total;
    }

    public void setColesterolTotal(String total) {
        this.total = total;
    }

    public String getTrigliceridos() {
        return trigliceridos;
    }

    public void setTrigliceridos(String trigliceridos) {
        this.trigliceridos = trigliceridos;
    }

    public String getLdl() {
        return ldl;
    }

    public void setLdl(String ldl) {
        this.ldl = ldl;
    }

    // CARDIOVASCULAR
    public String getCardiovascular() {
        return cardiovascular;
    }

    public void setCardiovascular(String cardiovascular) {
        this.cardiovascular = cardiovascular;
    }

    public String getFinalTratamiento() {
        return finalTratamiento;
    }

    public void setFinalTratamiento(String finalTratamiento) {
        this.finalTratamiento = finalTratamiento;
    }

    @Override
    public String toString() {
        return "paciente{" +
                "ID='" + id + '\'' +
                ", sexo='" + sexo + '\'' +
                ", edad='" + edad + '\'' +

                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", imc='" + imc + '\'' +

                ", sistolica='" + sistolica + '\'' +
                ", diastolica='" + diastolica + '\'' +

                ", cantidad='" + cantidad + '\'' +
                ", adiccion='" + adiccion + '\'' +
                ", ipa='" + ipa + '\'' +

                ", hdl='" + hdl + '\'' +
                ", colesterol total='" + total + '\'' +
                ", trigliceridos='" + trigliceridos + '\'' +
                ", ldl='" + ldl + '\'' +


                ", cardiovascular='" + cardiovascular + '\'' +
                ", finalTratamiento='" + finalTratamiento + '\'' +
                '}';
    }
}
