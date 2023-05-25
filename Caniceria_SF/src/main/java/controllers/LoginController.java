package controllers;

import com.mycompany.caniceria_sf.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import models.ConnectionBD;
import models.LoginModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;



public class LoginController implements Initializable {

    @FXML
    private TextField txt_username;
    @FXML
    private PasswordField txt_clave;

    @FXML
    private Button btn_login;

    Connection conBD;
    PreparedStatement consulta;
    ResultSet rta;
    ConnectionBD cx = new ConnectionBD();

    public LoginModel login(String user, String clv){
        LoginModel lg = new LoginModel();
        String sql = "SELECT * FROM usuarios WHERE nombre = ? AND clave = ?";
        try{
            conBD = cx.getConnection();
            consulta = conBD.prepareStatement(sql);
            consulta.setString(1,user);
            consulta.setString(2,clv);
            rta= consulta.executeQuery();
            if(rta.next()){
                lg.setId(rta.getInt("id"));
                lg.setUserName(rta.getString("nombre"));
                lg.setClave(rta.getString("clave"));
                lg.setTelefono(rta.getString("telefono"));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return lg;
    }
    @FXML
    private void btnEvent(javafx.event.ActionEvent event) throws IOException {
        Object evt = event.getSource();
        if (evt.equals(btn_login)) {
            if (!txt_username.getText().isEmpty() && !txt_clave.getText().isEmpty()) {
                String user = txt_username.getText();
                String clave = txt_clave.getText();
                LoginModel l = new LoginModel();
                l = login(user, clave);
                if(l.getUserName()!=null && l.getClave()!=null){
                    FXMLLoader loader = new FXMLLoader(App.class.getResource("index" + ".fxml"));
                    Parent indexRoot = loader.load();
                    IndexController indexController = loader.getController();
                    indexController.setUserName(user);
                    Scene indexScene = new Scene(indexRoot);
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    primaryStage.setScene(indexScene);
                    primaryStage.show();
                }else{
                    JOptionPane.showMessageDialog(null, "Usuario o Contraseña incorrectos", "Error", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Los campos están vacíos", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }



}
