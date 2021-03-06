package ucm.fdi.tfg;

public class Category {

    private String nombre;
    private String colegiado;
    private String mail;
    private String categoryId;

    public Category() {
        super();
    }

    public Category(String categoryId, String nombre, String colegiado, String mail) {
        super();
        this.nombre = nombre;
        this.colegiado = colegiado;
        this.mail = mail;
        this.categoryId = categoryId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColegiado() {
        return colegiado;
    }

    public void setColegiado(String colegiado) {
        this.colegiado = colegiado;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

     public String getCategoryId(){
        return categoryId;
    }

    public void setCategoryId(String categoryId){
        this.categoryId = categoryId;
    }

}