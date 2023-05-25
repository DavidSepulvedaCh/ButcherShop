package models;

public class LoginModel {
    private String userName;
    private String clave;

    public LoginModel(String userName, String clave){
        this.userName = userName;
        this.clave = clave;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getClave() { return clave; }

    public void setClave(String clave) { this.clave = clave; }
}
