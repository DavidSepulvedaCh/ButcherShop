package services;

import controllers.BuyRes.BuyResController;
import javafx.scene.control.*;
import models.BuyResModel;

import javax.swing.*;
import java.time.LocalDate;

public class BuyResVerifier {

    private BuyResController buyResController;

    public BuyResVerifier() {
        this.buyResController = new BuyResController();
    }

    public void vefiryBuyRes(TextField txtPesoArroba, TextField txtPrecioArroba, TextField txtProveedor, DatePicker lblFechaCompra, ComboBox cbxTipoAnimal, TableView<BuyResModel> tableBuyRes) {
        if (!txtPesoArroba.getText().isEmpty() && !txtPrecioArroba.getText().isEmpty() && !txtProveedor.getText().isEmpty() && cbxTipoAnimal.getValue() != null && lblFechaCompra.getValue() != null) {
            String pesoArroba = txtPesoArroba.getText();
            String precioArroba = txtPrecioArroba.getText();
            String proveedor = txtProveedor.getText();
            LocalDate fecha = lblFechaCompra.getValue();
            String tipo = cbxTipoAnimal.getValue().toString();

            buyResController.verifyBuyRes(pesoArroba, precioArroba, proveedor, fecha, tipo);
            if (buyResController.buySuccess) {
                txtProveedor.setText("");
                txtPrecioArroba.setText("");
                txtPesoArroba.setText("");
                lblFechaCompra.setValue(null);
                cbxTipoAnimal.setValue(0);
                buyResController.updatePurchases(tableBuyRes);
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar la compra", "Error", JOptionPane.ERROR_MESSAGE);
                txtProveedor.setText("");
                txtPrecioArroba.setText("");
                txtPesoArroba.setText("");
                lblFechaCompra.setValue(null);
                cbxTipoAnimal.setValue(0);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Faltan campos por completar", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}