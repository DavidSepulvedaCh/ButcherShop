package services.Producto;

import controllers.Productos.ProductController;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class RegProductoVerifier {
    private ProductController productController;

    public RegProductoVerifier() {
        this.productController = new ProductController();
    }

    public void verificarRegProduct(TableView tableProduct, TextField txtCodigoProductoReg, TextField txtProductoReg, TextField txtPrecioReg) throws SQLException {
        if (!txtCodigoProductoReg.getText().isEmpty() && !txtProductoReg.getText().isEmpty() && !txtPrecioReg.getText().isEmpty()) {
            String codeProduct = txtCodigoProductoReg.getText();
            String nameProduct = txtProductoReg.getText();
            String priceProduct = txtPrecioReg.getText();

            // Llamada al método que interactúa con la base de datos
            productController.verifyRegProducts(codeProduct, nameProduct, priceProduct);

            if (productController.registroExitoso) {
                showAlert("Éxito", "Registro de producto exitoso", Alert.AlertType.INFORMATION);
                txtCodigoProductoReg.setText("");
                txtProductoReg.setText("");
                txtPrecioReg.setText("");
                productController.updateProducts(tableProduct);
            } else {
                showAlert("Error", "Error al registrar el producto", Alert.AlertType.ERROR);
                txtCodigoProductoReg.setText("");
                txtProductoReg.setText("");
                txtPrecioReg.setText("");
            }
        } else {
            showAlert("Error", "Faltan campos por completar", Alert.AlertType.WARNING);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
