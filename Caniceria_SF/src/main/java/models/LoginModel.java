package models;

public class LoginModel {
    private int id;
    private String userName;
    private String clave;
    private String telefono;

    public LoginModel() {
    }

    public LoginModel(int id, String userName, String clave, String telefono) {
        this.id = id;
        this.userName = userName;
        this.clave = clave;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
