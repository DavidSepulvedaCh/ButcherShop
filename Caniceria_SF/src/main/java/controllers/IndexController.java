package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ProductModel;




import javax.swing.*;
import java.sql.SQLException;
import java.util.Map;

public class IndexController {
    @FXML
    private Label lbl_username;

    @FXML
    private TextField txtCodigoProductoReg;
    @FXML
    private TextField txtProductoReg;
    @FXML
    private TextField txtPrecioReg;
    private ProductController productController;

    @FXML
    private TableView<ProductModel> tableProduct;
    @FXML
    private TableColumn<ProductModel, String> codigoColumn;
    @FXML
    private TableColumn<ProductModel, Double> precioColumn;
    @FXML
    private TableColumn<ProductModel, String> productoColumn;




    public IndexController() {
        productController = new ProductController();
    }

    public void initialize() {
        ObservableList<ProductModel> productList = productController.getAllProducts();

        // Configura las celdas de las columnas
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        productoColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // Agrega la lista de productos a la tabla
        tableProduct.setItems(productList);
        //System.out.println(productList);
        for (ProductModel product : productList) {
            System.out.println("Id: " + product.getId());
            System.out.println("Nombre: " + product.getNombre());
            System.out.println("Precio: " + product.getPrecio());
            System.out.println("Código: " + product.getCodigo());
            System.out.println("------------------------");
        }

    }

    public void updateTable() {
        ObservableList<ProductModel> productList = productController.getAllProducts();
        tableProduct.setItems(productList);
    }


    public void setUserName(String userName) {
        lbl_username.setText(userName);
    }


    public void verificarRegProduct() throws SQLException {
        if (!txtCodigoProductoReg.getText().isEmpty() && !txtProductoReg.getText().isEmpty() && !txtPrecioReg.getText().isEmpty()) {
            String codeProduct = txtCodigoProductoReg.getText();
            String nameProduct = txtProductoReg.getText();
            String priceProduct = txtPrecioReg.getText();

            ProductModel prModel = new ProductModel();
            prModel.setCodigo(codeProduct);
            prModel.setNombre(nameProduct);
            prModel.setPrecio(priceProduct);

            boolean registroExitoso = productController.registerProduct(prModel);

            if (registroExitoso) {
                JOptionPane.showMessageDialog(null, "Registro de producto exitoso", "Exito", JOptionPane.INFORMATION_MESSAGE);
                txtCodigoProductoReg.setText("");
                txtProductoReg.setText("");
                txtPrecioReg.setText("");
                updateTable();

            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar el producto", "Error", JOptionPane.ERROR_MESSAGE);
                txtCodigoProductoReg.setText("");
                txtProductoReg.setText("");
                txtPrecioReg.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Faltan campos por completar", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}
