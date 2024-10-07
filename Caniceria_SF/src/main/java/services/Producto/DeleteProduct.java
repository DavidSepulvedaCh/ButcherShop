package services.Producto;

import controllers.Productos.ProductController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import models.ProductModel;

import javax.swing.*;

public class DeleteProduct {

    private final ProductController productController;

    public DeleteProduct(ProductController productController) {
        this.productController = productController;
    }

    public void deleteProduct(String idProducto, String nombreProducto, TableView<ProductModel> tableProduct) {
        if (!idProducto.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar producto");
            alert.setHeaderText("¿Estás seguro de que deseas eliminar el producto " + nombreProducto + " ?");

            ButtonType buttonTypeYes = new ButtonType("Eliminar");
            ButtonType buttonTypeNo = new ButtonType("Cancelar");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeYes) {
                    if (productController.deleteProduct(idProducto)) {
                        JOptionPane.showMessageDialog(null, "El producto se eliminó exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        productController.updateProducts(tableProduct);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar el producto", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    System.out.println("Acción cancelada por el usuario.");
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "No has seleccionado un producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
