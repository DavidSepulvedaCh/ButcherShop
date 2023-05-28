package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.BuyResModel;
import models.ProductModel;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import controllers.BuyResController;




import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private BuyResController buyResController;

    @FXML
    private TableView<ProductModel> tableProduct;
    @FXML
    private TableColumn<ProductModel, String> codigoColumn;
    @FXML
    private TableColumn<ProductModel, Double> precioColumn;
    @FXML
    private TableColumn<ProductModel, String> productoColumn;
    @FXML
    private TextField txtPesoArroba;
    @FXML
    private TextField txtPrecioArroba;
    @FXML
    private TextField txtProveedor;
    @FXML
    private DatePicker lblFechaCompra;
    @FXML
    private ComboBox cbxTipoAnimal;
    @FXML
    private TableView<BuyResModel> tableBuyRes;
    @FXML
    private TableColumn<BuyResModel, String> tipoColumn;
    @FXML
    private TableColumn<BuyResModel, String> pesoColumn;
    @FXML
    private TableColumn<BuyResModel, String> precioCompraColumn;
    @FXML
    private TableColumn<BuyResModel, String> fechaColumn;
    @FXML
    private TableColumn<BuyResModel, String> proveedorColumn;


    public IndexController() {

        productController = new ProductController();
        buyResController = new BuyResController();
    }

    public void initialize() {
        ObservableList<String> opciones = FXCollections.observableArrayList("Res", "Cerdo");
        cbxTipoAnimal.setItems(opciones);
        ObservableList<ProductModel> productList = productController.getAllProducts();
        ObservableList<BuyResModel> purchasesList = buyResController.getAllPurchases();

        // Configura las celdas de las columnas
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        productoColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // ==== compra

        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipoAnimal"));
        pesoColumn.setCellValueFactory(new PropertyValueFactory<>("pesoArrobas"));
        precioCompraColumn.setCellValueFactory(new PropertyValueFactory<>("precioArroba"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaCompra"));
        proveedorColumn.setCellValueFactory(new PropertyValueFactory<>("proveedor"));


        // Agrega la lista de productos a la tabla
        tableProduct.setItems(productList);
        tableBuyRes.setItems(purchasesList);
        for (BuyResModel res : purchasesList) {
            System.out.println("Id: " + res.getId());
            System.out.println("Tipo: " + res.getTipoAnimal());
            System.out.println("Peso: " + res.getPesoArrobas());
            System.out.println("Precio: " + res.getPrecioArroba());
            System.out.println("Proveedor: " + res.getProveedor());
            System.out.println("------------------------");
        }

    }

    public void updateProducts() {
        ObservableList<ProductModel> productList = productController.getAllProducts();
        tableProduct.setItems(productList);
    }
    public void updatePurchases() {
        ObservableList<BuyResModel> purchasesList = buyResController.getAllPurchases();
        tableBuyRes.setItems(purchasesList);
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
                updateProducts();

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

    public void vefiryBuyRes() throws SQLException {
        if (!txtPesoArroba.getText().isEmpty() || !txtPrecioArroba.getText().isEmpty() || !txtProveedor.getText().isEmpty() || cbxTipoAnimal.getValue() != null || lblFechaCompra.getValue() != null) {
            String pesoArroba = txtPesoArroba.getText();
            String precioArroba = txtPrecioArroba.getText();
            String proveedor = txtProveedor.getText();
            LocalDate fecha = lblFechaCompra.getValue();
            String tipo = cbxTipoAnimal.getValue().toString();

            BuyResModel buyMdl = new BuyResModel();
            buyMdl.setPesoArrobas(pesoArroba);
            buyMdl.setPrecioArroba(precioArroba);
            buyMdl.setProveedor(proveedor);
            buyMdl.setFechaCompra(String.valueOf(fecha));
            buyMdl.setTipoAnimal(tipo);

            boolean buySuccess = buyResController.registerBuyRes(buyMdl);
            if(buySuccess){
                JOptionPane.showMessageDialog(null, "Registro de compra exitoso", "Exito", JOptionPane.INFORMATION_MESSAGE);
                txtProveedor.setText("");
                txtPrecioArroba.setText("");
                txtPesoArroba.setText("");
                updatePurchases();
            }else{
                JOptionPane.showMessageDialog(null, "Error al registrar la compra", "Error", JOptionPane.ERROR_MESSAGE);
                txtProveedor.setText("");
                txtPrecioArroba.setText("");
                txtPesoArroba.setText("");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Faltan campos por completar", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

}
