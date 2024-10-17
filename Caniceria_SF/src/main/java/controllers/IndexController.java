package controllers;

import controllers.BuyRes.BuyResController;
import controllers.BuyRes.BuyResPrint;
import controllers.Insumos.InsumoController;
import controllers.Insumos.InsumosPrint;
import controllers.Productos.ProductController;
import controllers.Productos.ProductPrint;
import controllers.Sale.VentaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import models.*;
import services.BuyResVerifier;
import services.InsumoVerifier;
import services.Producto.DeleteProduct;
import services.Producto.RegProductoVerifier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class IndexController {
    @FXML
    private Label lbl_username;
    @FXML
    private TableView tableVenta;
    @FXML
    private TableView tableInventario;
    @FXML
    private TableColumn<InventarioModel, String> idVentaColumn;
    @FXML
    private TableColumn<InventarioModel, String> vendedorColumn;
    @FXML
    private TableColumn<InventarioModel, Double> totalColumn;
    @FXML
    private TableColumn<InventarioModel, String> fechaVentaColumn;
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
    private VentaController ventaController;
    private BuyResPrint buyResPrint;
    private ProductPrint productPrint;
    private InsumoVerifier insumoVerifier;
    private BuyResVerifier buyResVerifier;
    private RegProductoVerifier regProductoVerifier;
    private InsumosPrint insumosPrint;
    private BuyResController buyResController;
    private InsumoController insumoController;
    private InventarioController inventarioController;
    private DeleteProduct deleteProduct;
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
    private TableColumn<InventarioModel, String> totalProductoVentaColumn;
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
    private TableColumn<DetalleVentaModel, String> codigoVentaColumn;
    @FXML
    private TableColumn<DetalleVentaModel, String> productoVentaColumn;
    @FXML
    private TableColumn<DetalleVentaModel, String> cantidadVentaColumn;
    @FXML
    private TableColumn<DetalleVentaModel, String> precioVentaColumn;
    @FXML
    private TableColumn<DetalleVentaModel, String> totalVentaColumn;
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
    double totalVenta = 0.00;

    public IndexController() {

        productController = new ProductController();
        buyResController = new BuyResController();
        insumoController = new InsumoController();
        inventarioController = new InventarioController();
        ventaController = new VentaController(txtCodgoProductoVenta, txtProducto, txtPrecioProducto, txtCantidadProducto, txtClienteVenta, lblTotal, tableVenta, productController, lbl_username);
        buyResPrint = new BuyResPrint();
        insumosPrint = new InsumosPrint();
        productPrint = new ProductPrint();
        insumoVerifier = new InsumoVerifier();
        buyResVerifier = new BuyResVerifier();
        regProductoVerifier = new RegProductoVerifier();
        deleteProduct = new DeleteProduct(productController);
    }

    public void initialize() {
        ObservableList<String> opcionesTipAnimal = FXCollections.observableArrayList("Res", "Cerdo");
        cbxTipoAnimal.setItems(opcionesTipAnimal);
        ObservableList<String> opcionesInsumos = FXCollections.observableArrayList("Bolsas plasticas", "Bolsas al vacio", "Limpido", "Vinagre", "Carbon");
        cbxTipoInsumo.setItems(opcionesInsumos);

        ventaController = new VentaController(txtCodgoProductoVenta, txtProducto, txtPrecioProducto, txtCantidadProducto, txtClienteVenta, lblTotal, tableVenta, productController, lbl_username);

        productController.showTableProducts(tableProduct, codigoColumn, precioColumn, productoColumn);
        buyResController.showBuyRes(tableBuyRes, tipoColumn, pesoColumn, precioCompraColumn, fechaColumn, proveedorColumn);
        insumoController.showInsumos(tableBuyInsumo, tipoInsumoColumn, precioInsumoColumn, cantidadInsumoColumn, proveedorInsumoColumn, fechaInsumoColumn, descripcionInsumoColumn);
        inventarioController.showInventario(tableInventario, idVentaColumn,  vendedorColumn, totalColumn, fechaVentaColumn);
        ventaController.showProducts(tableVenta, codigoVentaColumn, productoVentaColumn, cantidadVentaColumn, precioVentaColumn, totalVentaColumn);
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

    //Eliminar x producto registrado en la BD
    @FXML
    public void deleteProduct() { deleteProduct.deleteProduct(idProducto, nombreProducto, tableProduct); }

    @FXML
    public void enterCode(KeyEvent event) { ventaController.handleEnterCode(event); }
    public void handleVentaButton(){ tabPane.getSelectionModel().select(0); }
    public void handleProductosButton(){ tabPane.getSelectionModel().select(1); }
    public void handleinventarioButton(){ tabPane.getSelectionModel().select(2); }
    public void handleCompraResButton(){ tabPane.getSelectionModel().select(3); }
    public void handleCompraInsumoButton(){ tabPane.getSelectionModel().select(4); }
    public void setUserName(String userName) { lbl_username.setText(userName); }

    public void verificarRegProduct() throws SQLException {
        regProductoVerifier.verificarRegProduct(tableProduct, txtCodigoProductoReg, txtProductoReg, txtPrecioReg);
    }

    public void vefiryBuyRes() { buyResVerifier.vefiryBuyRes(txtPesoArroba, txtPrecioArroba, txtProveedor, lblFechaCompra, cbxTipoAnimal, tableBuyRes); }
    public void verifyInsumo() { insumoVerifier.verifyInsumo(txtPrecioInsumo, txtCantidadInsumo, txtProveedorInsumo, lblFechaInsumo, cbxTipoInsumo, txtDescripcionInsumo, tableBuyInsumo); }

    // Agregar producto a la tabla de venta
    public void addProduct() {
        nombreProducto = txtProducto.getText();
        codigoProducto = txtCodgoProductoVenta.getText();
        precioProducto = txtPrecioProducto.getText();
        cliente = txtClienteVenta.getText();
        int cantidad = Integer.parseInt(txtCantidadProducto.getText());

        if (!nombreProducto.isEmpty() && !codigoProducto.isEmpty() && !precioProducto.isEmpty() && cantidad > 0) {
            ventaController.addProduct(txtCodgoProductoVenta, txtProducto, txtCantidadProducto, txtPrecioProducto, lblTotal, tableVenta);
        } else {
            JOptionPane.showMessageDialog(null, "Faltan campos por completar", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    //Eliminar producto de la tabla de venta
    @FXML
    private void handleRemoveProduct() {
        ventaController.removeProduct();
    }

    @FXML
    public void registerSale() {
        if (!txtClienteVenta.getText().isEmpty() && !ventaController.ventaList.isEmpty()) {
            ventaController.addSale();
            inventarioController.showInventario(tableInventario, idVentaColumn, vendedorColumn, totalColumn, fechaVentaColumn);

        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar un cliente y agregar productos a la venta", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void printProductos(){ productPrint.printProducts();}
    public void printCompras(){buyResPrint.printProducts();}
    public void printInsimos(){insumosPrint.printProducts();}
    public void viewDetail(MouseEvent mouseEvent) {
        InventarioModel fila = (InventarioModel) tableInventario.getSelectionModel().getSelectedItem();
        if (fila != null) {
            idProducto = fila.getId();
        } else {
            System.out.println("No se ha seleccionado ninguna fila.");
        }
        inventarioController.alerta(idProducto);
    }

}