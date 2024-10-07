package services;

import controllers.Insumos.InsumoController;
import javafx.scene.control.*;
import models.InsumoModel;

import javax.swing.*;
import java.time.LocalDate;

public class InsumoVerifier {

    private InsumoController insumoController;

    public InsumoVerifier() {
        this.insumoController = new InsumoController();
    }

    public void verifyInsumo(TextField txtPrecioInsumo, TextField txtCantidadInsumo, TextField txtProveedorInsumo, DatePicker lblFechaInsumo, ComboBox cbxTipoInsumo, TextArea txtDescripcionInsumo, TableView<InsumoModel> tableBuyInsumo) {
        if (!txtPrecioInsumo.getText().isEmpty() && !txtCantidadInsumo.getText().isEmpty() && !txtProveedorInsumo.getText().isEmpty() && lblFechaInsumo.getValue() != null && cbxTipoInsumo.getValue() != null) {
            String precioInsumo = txtPrecioInsumo.getText();
            String cantidadInsumo = txtCantidadInsumo.getText();
            String proveedorInsumo = txtProveedorInsumo.getText();
            String insumo = cbxTipoInsumo.getValue().toString();
            String descripcionInsumo = txtDescripcionInsumo.getText();
            LocalDate fechaInsumo = lblFechaInsumo.getValue();

            insumoController.verifyInsumo(precioInsumo, cantidadInsumo, proveedorInsumo, fechaInsumo, insumo, descripcionInsumo);
            if (insumoController.insumoSuccess) {
                txtProveedorInsumo.setText("");
                txtCantidadInsumo.setText("");
                txtPrecioInsumo.setText("");
                cbxTipoInsumo.setValue(0);
                lblFechaInsumo.setValue(null);
                txtDescripcionInsumo.setText("");
                insumoController.updateInsumos(tableBuyInsumo);
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar la compra", "Error", JOptionPane.ERROR_MESSAGE);
                txtProveedorInsumo.setText("");
                txtCantidadInsumo.setText("");
                txtCantidadInsumo.setText("");
                cbxTipoInsumo.setValue(0);
                txtDescripcionInsumo.setText("");
                lblFechaInsumo.setValue(null);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Faltan campos por completar", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}