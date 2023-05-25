package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class IndexController {
    @FXML
    private Label lbl_username;

    public void setUserName(String userName) {
        lbl_username.setText(userName);
    }

}

