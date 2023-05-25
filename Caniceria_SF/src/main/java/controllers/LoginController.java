package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.lang.Character;
import com.mycompany.caniceria_sf.App;
import javafx.scene.Parent;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private TextField txt_username;
    @FXML
    private PasswordField txt_clave;

    @FXML
    private Button btn_login;

    @FXML
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();
        if (evt.equals(txt_username)) {
            if (Character.isWhitespace(event.getCharacter().charAt(0))) {
                event.consume();
            }
        } else if (evt.equals(txt_clave)) {
            if (Character.isWhitespace(event.getCharacter().charAt(0))) {
                event.consume();
            }
        }
    }



    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        Object evt = event.getSource();
        if (evt.equals(btn_login)) {
            if (!txt_username.getText().isEmpty() && !txt_clave.getText().isEmpty()) {
                String user = txt_username.getText();
                String clave = txt_clave.getText();
                if (user.equals("david") && clave.equals("123")) {
                    FXMLLoader loader = new FXMLLoader(App.class.getResource("index" + ".fxml"));
                    Parent indexRoot = loader.load();
                    IndexController indexController = loader.getController();
                    indexController.setUserName(user);
                    Scene indexScene = new Scene(indexRoot);
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    primaryStage.setScene(indexScene);
                    primaryStage.show();


                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo ingresar", "Error", JOptionPane.WARNING_MESSAGE);
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
