package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import models.*;
import controllers.InsumoController;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


import javax.swing.*;
import java.awt.event.ActionEvent;
//import java.awt.event.KeyEvent;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class IndexController {
    @FXML
    private Label lbl_username;
    @FXML
    private TableView tableVenta;
    @FXML
    private TableColumn<VentaModel, String> codigoVentaColumn;
    @FXML
    private TableColumn<VentaModel, String> productVentaColumn;
    @FXML
    private TableColumn<VentaModel, String> cantidadVentaColumn;
    @FXML
    private TableColumn<VentaModel, String> precioVentaColumn;
    @FXML
    private TextField txtCantidadProducto;
    @FXML
    private TextField txtPrecioProducto;
    @FXML
    private TextField txtCodigoProductoReg;
    @FXML
    private TextField txtProductoReg;
    @FXML
    private TextField txtPrecioReg;
    private ProductController productController;
    private BuyResController buyResController;
    private InsumoController insumoController;
    private VentaController ventaController;
    @FXML
    private TextField txtClienteVenta;
    @FXML
    private Label lblTotal;
    @FXML
    private TableView<ProductModel> tableProduct;
    @FXML
    private TableColumn<ProductModel, String> codigoColumn;
    @FXML
    private TableColumn<ProductModel, Double> precioColumn;
    @FXML
    private TableColumn<ProductModel, String> productoColumn;
    @FXML
    private TableColumn<VentaModel, String> totalProductoVentaColumn;
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
    private  TextField txtCodgoProductoVenta;
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
    private TextField txtProducto;
    @FXML
    private TabPane tabPane;
    private String idProducto = "";
    private String nombreProducto;
    private String precioProducto;
    private  String codigoProducto;
    private String cliente;
    double total = 0.00;


    public IndexController() {

        productController = new ProductController();
        buyResController = new BuyResController();
        insumoController = new InsumoController();
        ventaController = new VentaController();
    }

    public void initialize() {
        ObservableList<String> opcionesTipAnimal = FXCollections.observableArrayList("Res", "Cerdo");
        cbxTipoAnimal.setItems(opcionesTipAnimal);
        ObservableList<String> opcionesInsumos = FXCollections.observableArrayList("Bolsas plasticas", "Bolsas al vacio", "Limpido", "Vinagre", "Carbon");
        cbxTipoInsumo.setItems(opcionesInsumos);

        productController.showTableProducts(tableProduct, codigoColumn, precioColumn, productoColumn);
        buyResController.showBuyRes(tableBuyRes, tipoColumn, pesoColumn, precioCompraColumn, fechaColumn, proveedorColumn);
        insumoController.showInsumos(tableBuyInsumo, tipoInsumoColumn, precioInsumoColumn, cantidadInsumoColumn, proveedorInsumoColumn, fechaInsumoColumn, descripcionInsumoColumn);
        ventaController.showInsumos(tableVenta, codigoVentaColumn, productVentaColumn, cantidadVentaColumn, precioVentaColumn, totalProductoVentaColumn);
    }
    @FXML
    public void getValues(MouseEvent mouseEvent){
        ProductModel fila = tableProduct.getSelectionModel().getSelectedItem();
        if (fila != null) {
            idProducto = fila.getId();
            nombreProducto = fila.getNombre();
            precioProducto = fila.getPrecio();
            codigoProducto = fila.getCodigo();
        } else {
            System.out.println("No se ha seleccionado ninguna fila.");
        }
    }

    public void deleteProduct (){
        if(idProducto != ""){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar producto");
            alert.setHeaderText("¿Estás seguro de que deseas elimnar el producto "+ nombreProducto + " ?");

            ButtonType buttonTypeYes = new ButtonType("Eliminar");
            ButtonType buttonTypeNo = new ButtonType("Cancelar");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeYes) {
                    Connection conBD;
                    PreparedStatement consulta;
                    ResultSet rta;
                    ConnectionBD con = new ConnectionBD();
                    String sql = "DELETE FROM productos WHERE id = ?";
                    try{
                        conBD = con.getConnection();
                        consulta = conBD.prepareStatement(sql);
                        consulta.setString(1, idProducto);
                        consulta.executeUpdate();
                        JOptionPane.showMessageDialog(null, "El producto se eliminó exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
                        productController.updateProducts(tableProduct);
                    }catch (SQLException e){
                        System.out.println(e.toString());
                    }
                } else {
                    System.out.println("Acción cancelada por el usuario.");
                }
            });
        }else{
            JOptionPane.showMessageDialog(null, "No has seleccionado un producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    @FXML
    public void enterCode(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            if (!"".equals(txtCodgoProductoVenta.getText())) {
                String code = txtCodgoProductoVenta.getText();
                if (productController.searchProduct(code).getNombre() != null) {
                    txtProducto.setText("" + productController.searchProduct(code).getNombre());
                    txtPrecioProducto.setText("" + productController.searchProduct(code).getPrecio());
                    txtCantidadProducto.requestFocus();
                }else{
                    JOptionPane.showMessageDialog(null, "Codigo no registrado", "Error", JOptionPane.ERROR_MESSAGE);
                    txtProducto.setText("");
                    txtCantidadProducto.setText("");
                    txtPrecioProducto.setText("");
                    txtCodgoProductoVenta.setText("");
                    txtCodgoProductoVenta.requestFocus();
                }
            }
        }
    }

    public void handleVentaButton(){ tabPane.getSelectionModel().select(0); }
    public void handleProductosButton(){ tabPane.getSelectionModel().select(1); }
    public void handleinventarioButton(){ tabPane.getSelectionModel().select(2); }
    public void handleCompraResButton(){ tabPane.getSelectionModel().select(3); }
    public void handleCompraInsumoButton(){ tabPane.getSelectionModel().select(4); }

    public void setUserName(String userName) { lbl_username.setText(userName); }

    public void verificarRegProduct() throws SQLException {
        if (!txtCodigoProductoReg.getText().isEmpty() && !txtProductoReg.getText().isEmpty() && !txtPrecioReg.getText().isEmpty()) {
            String codeProduct = txtCodigoProductoReg.getText();
            String nameProduct = txtProductoReg.getText();
            String priceProduct = txtPrecioReg.getText();

            productController.verifyRegProducts(codeProduct,nameProduct, priceProduct);

            if (productController.registroExitoso) {
                JOptionPane.showMessageDialog(null, "Registro de producto exitoso", "Exito", JOptionPane.INFORMATION_MESSAGE);
                txtCodigoProductoReg.setText("");
                txtProductoReg.setText("");
                txtPrecioReg.setText("");
                productController.updateProducts(tableProduct);

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
        if (!txtPesoArroba.getText().isEmpty() && !txtPrecioArroba.getText().isEmpty() && !txtProveedor.getText().isEmpty() && cbxTipoAnimal.getValue() != null && lblFechaCompra.getValue() != null) {
            String pesoArroba = txtPesoArroba.getText();
            String precioArroba = txtPrecioArroba.getText();
            String proveedor = txtProveedor.getText();
            LocalDate fecha = lblFechaCompra.getValue();
            String tipo = cbxTipoAnimal.getValue().toString();

            buyResController.verifyBuyRes(pesoArroba,precioArroba,proveedor,fecha,tipo);
            if(buyResController.buySuccess){
                txtProveedor.setText("");
                txtPrecioArroba.setText("");
                txtPesoArroba.setText("");
                lblFechaCompra.setValue(null);
                cbxTipoAnimal.setValue(0);
                buyResController.updatePurchases(tableBuyRes);
            }else{
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

            insumoController.verifyInsumo(precioInsumo, cantidadInsumo, proveedorInsumo, fechaInsumo, insumo, descripcionInsumo);
            if(insumoController.insumoSuccess == true){
                txtProveedorInsumo.setText("");
                txtCantidadInsumo.setText("");
                txtPrecioInsumo.setText("");
                cbxTipoInsumo.setValue(0);
                lblFechaInsumo.setValue(null);
                txtDescripcionInsumo.setText("");
                insumoController.updateInsumos(tableBuyInsumo);
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

    public void addProduct(){
        nombreProducto = txtProducto.getText();
        codigoProducto = txtCodgoProductoVenta.getText();
        precioProducto = txtPrecioProducto.getText();
        cliente = txtClienteVenta.getText();
        int cantidad = Integer.parseInt(txtCantidadProducto.getText());

        if(nombreProducto != "" && codigoProducto != "" && precioProducto != "" && cantidad > 0){
            Double total;
            total = Double.parseDouble(precioProducto + cantidad);
            ventaController.addTable(codigoProducto,nombreProducto,String.valueOf(cantidad),precioProducto, String.valueOf(1));
            ventaController.updateVenta(tableVenta);
            txtProducto.setText("");
            txtCodgoProductoVenta.setText("");
            txtCantidadProducto.setText("");
            txtPrecioProducto.setText("");
            total = total + (Double.parseDouble(precioProducto)* cantidad);
            lblTotal.setText(String.valueOf(total));
        }else{
            JOptionPane.showMessageDialog(null, "Faltan campos por completar", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}