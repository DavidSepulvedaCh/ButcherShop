package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.BuyResModel;
import models.InsumoModel;
import models.ProductModel;
import controllers.InsumoController;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.time.LocalDate;

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
    private InsumoController insumoController;
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
    @FXML
    private TableView<InsumoModel> tableBuyInsumo;
    @FXML
    private TableColumn<InsumoModel, String> tipoInsumoColumn;
    @FXML
    private TableColumn<InsumoModel, String> precioInsumoColumn;
    @FXML
    private TableColumn<InsumoModel, String> cantidadInsumoColumn;
    @FXML
    private TableColumn<InsumoModel, String> proveedorInsumoColumn;
    @FXML
    private TableColumn<InsumoModel, String> fechaInsumoColumn;
    @FXML
    private TableColumn<InsumoModel, String> descripcionInsumoColumn;
    @FXML
    private TextField txtPrecioInsumo;
    @FXML
    private TextField txtCantidadInsumo;
    @FXML
    private DatePicker lblFechaInsumo;
    @FXML
    private TextField txtProveedorInsumo;
    @FXML
    private ComboBox cbxTipoInsumo;
    @FXML
    private TextArea txtDescripcionInsumo;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tbProductos;


    public IndexController() {

        productController = new ProductController();
        buyResController = new BuyResController();
        insumoController = new InsumoController();
    }

    public void initialize() {
        ObservableList<String> opcionesTipAnimal = FXCollections.observableArrayList("Res", "Cerdo");
        cbxTipoAnimal.setItems(opcionesTipAnimal);
        ObservableList<String> opcionesInsumos = FXCollections.observableArrayList("Bolsas plasticas", "Bolsas al vacio", "Limpido", "Vinagre", "Carbon");
        cbxTipoInsumo.setItems(opcionesInsumos);
        ObservableList<ProductModel> productList = productController.getAllProducts();
        ObservableList<BuyResModel> purchasesList = buyResController.getAllPurchases();
        ObservableList<InsumoModel> insumosList = insumoController.getAllInsumos();

        // Celdas tablas PRODUCTOS
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        productoColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // Celdas tabla COMPRA ANIMAL
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipoAnimal"));
        pesoColumn.setCellValueFactory(new PropertyValueFactory<>("pesoArrobas"));
        precioCompraColumn.setCellValueFactory(new PropertyValueFactory<>("precioArroba"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaCompra"));
        proveedorColumn.setCellValueFactory(new PropertyValueFactory<>("proveedor"));

        // Celdas tabla INSUMOS
        tipoInsumoColumn.setCellValueFactory(new PropertyValueFactory<>("nombreInsumo"));
        precioInsumoColumn.setCellValueFactory(new PropertyValueFactory<>("precioInsumo"));
        cantidadInsumoColumn.setCellValueFactory(new PropertyValueFactory<>("cantidadInsumo"));
        proveedorInsumoColumn.setCellValueFactory(new PropertyValueFactory<>("proveedorInsumo"));
        fechaInsumoColumn.setCellValueFactory(new PropertyValueFactory<>("fechaInsumo"));
        descripcionInsumoColumn.setCellValueFactory(new PropertyValueFactory<>("descripcionInsumo"));


        // Agrega la lista de productos a la tabla
        tableProduct.setItems(productList);
        tableBuyRes.setItems(purchasesList);
        tableBuyInsumo.setItems(insumosList);

        /*for (InsumoModel insu : insumosList) {
            System.out.println("Id: " + insu.getId());
            System.out.println("Nombre: " + insu.getNombreInsumo());
            System.out.println("Cantidad: " + insu.getCantidadInsumo());
            System.out.println("Precio: " + insu.getPrecioInsumo());
            System.out.println("Proveedor: " + insu.getProveedorInsumo());
            System.out.println("Fecha: "+ insu.getFechaInsumo());
            System.out.println("------------------------");
        }*/

    }

    public void updateProducts() {
        ObservableList<ProductModel> productList = productController.getAllProducts();
        tableProduct.setItems(productList);
    }
    public void updatePurchases() {
        ObservableList<BuyResModel> purchasesList = buyResController.getAllPurchases();
        tableBuyRes.setItems(purchasesList);
    }
    public void updateInsumos(){
        ObservableList<InsumoModel> insumoList = insumoController.getAllInsumos();
        tableBuyInsumo.setItems(insumoList);
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
                lblFechaCompra.setValue(null);
                cbxTipoAnimal.setValue(0);
                updatePurchases();
            }else{
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

    public void verifyInsumo(){
        if(!txtPrecioInsumo.getText().isEmpty() && !txtCantidadInsumo.getText().isEmpty() && !txtProveedorInsumo.getText().isEmpty() && lblFechaInsumo.getValue() != null && cbxTipoInsumo.getValue() != null){
            String precioInsumo = txtPrecioInsumo.getText();
            String cantidadInsumo = txtCantidadInsumo.getText();
            String proveedorInsumo = txtProveedorInsumo.getText();
            String insumo = cbxTipoInsumo.getValue().toString();
            String descripcionInsumo = txtDescripcionInsumo.getText();
            LocalDate fechaInsumo = lblFechaInsumo.getValue();

            InsumoModel insMdl = new InsumoModel();
            insMdl.setNombreInsumo(insumo);
            insMdl.setCantidadInsumo(cantidadInsumo);
            insMdl.setPrecioInsumo(precioInsumo);
            insMdl.setProveedorInsumo(proveedorInsumo);
            insMdl.setFechaInsumo(String.valueOf(fechaInsumo));
            insMdl.setDescripcionInsumo(descripcionInsumo);
            boolean insumoSuccess = insumoController.registerInsumo(insMdl);
            if(insumoSuccess){
                JOptionPane.showMessageDialog(null, "Registro de insumo exitoso", "Exito", JOptionPane.INFORMATION_MESSAGE);
                txtProveedorInsumo.setText("");
                txtCantidadInsumo.setText("");
                txtPrecioInsumo.setText("");
                cbxTipoInsumo.setValue(0);
                lblFechaInsumo.setValue(null);
                txtDescripcionInsumo.setText("");
                updateInsumos();
            }else{
                JOptionPane.showMessageDialog(null, "Error al registrar la compra", "Error", JOptionPane.ERROR_MESSAGE);
                txtProveedorInsumo.setText("");
                txtCantidadInsumo.setText("");
                txtCantidadInsumo.setText("");
                cbxTipoInsumo.setValue(0);
                txtDescripcionInsumo.setText("");
                lblFechaInsumo.setValue(null);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Faltan campos por completar", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

}
