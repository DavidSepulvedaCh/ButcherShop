package controllers.Sale;

import controllers.InventarioController;
import controllers.Productos.ProductController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;

import models.ConnectionBD;
import models.DetalleVentaModel;
import models.InventarioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentaController {
    private TextField txtCodgoProductoVenta;
    private TextField txtProducto;
    private TextField txtPrecioProducto;
    private TextField txtCantidadProducto;
    private TextField txtClienteVenta;
    private Label lblTotal;
    private Label lbl_username;
    private TableView<DetalleVentaModel> tableVenta;
    private double totalVenta = 0.00;
    private ProductController productController;
    private InventarioController inventarioController;
    Connection conBD;
    ConnectionBD con = new ConnectionBD();
    PreparedStatement consulta;
    public ObservableList<DetalleVentaModel> ventaList = FXCollections.observableArrayList();

    public VentaController(TextField txtCodgoProductoVenta, TextField txtProducto, TextField txtPrecioProducto, TextField txtCantidadProducto, TextField txtClienteVenta, Label lblTotal,  TableView<DetalleVentaModel> tableVenta, ProductController productController, Label lbl_username) {
        this.txtCodgoProductoVenta = txtCodgoProductoVenta;
        this.txtProducto = txtProducto;
        this.txtPrecioProducto = txtPrecioProducto;
        this.txtCantidadProducto = txtCantidadProducto;
        this.txtClienteVenta = txtClienteVenta;
        this.lblTotal = lblTotal;
        this.tableVenta = tableVenta;
        this.productController = productController;
        this.lbl_username = lbl_username;
        this.inventarioController = inventarioController;
    }

    public void handleEnterCode(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            if (!txtCodgoProductoVenta.getText().isEmpty()) {
                String code = txtCodgoProductoVenta.getText();
                var product = productController.searchProduct(code);
                if (product.getNombre() != null) {
                    txtProducto.setText(product.getNombre());
                    txtPrecioProducto.setText(product.getPrecio());
                    txtCantidadProducto.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Código no registrado", "Error", JOptionPane.ERROR_MESSAGE);
                    clearFields();
                }
            }
        }
    }

    public void addProduct(TextField txtCodgoProductoVenta, TextField txtProducto, TextField txtCantidadProducto, TextField txtPrecioProducto, Label lblTotal, TableView tableVenta) {
        String nombreProducto = this.txtProducto.getText();
        String codigoProducto = this.txtCodgoProductoVenta.getText();
        String precioProducto = this.txtPrecioProducto.getText();
        String cliente = txtClienteVenta.getText();
        int cantidad = Integer.parseInt(this.txtCantidadProducto.getText());

        if (!nombreProducto.isEmpty() && !codigoProducto.isEmpty() && !precioProducto.isEmpty() && cantidad > 0) {
            double precio = Double.parseDouble(precioProducto);
            double totalProducto = precio * cantidad;

            addTable(codigoProducto, nombreProducto, String.valueOf(cantidad), precioProducto, String.valueOf(totalProducto));
            updateVenta(this.tableVenta);

            totalVenta += totalProducto;
            this.txtProducto.setText("");
            this.txtCodgoProductoVenta.setText("");
            this.txtCantidadProducto.setText("");
            this.txtPrecioProducto.setText("");

            this.lblTotal.setText(String.valueOf(totalVenta));
        } else {
            JOptionPane.showMessageDialog(null, "Faltan campos por completar", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearFields() {
        txtProducto.setText("");
        txtCantidadProducto.setText("");
        txtPrecioProducto.setText("");
        txtCodgoProductoVenta.setText("");
        txtCodgoProductoVenta.requestFocus();
    }

    public ObservableList<DetalleVentaModel> addTable(String codigo, String producto, String cantidad, String precio, String total){
        try {
            DetalleVentaModel ventaModel = new DetalleVentaModel();
            ventaModel.setCodigoProducto(codigo);
            ventaModel.setProducto(producto);
            ventaModel.setCantidad(cantidad);
            ventaModel.setPrecio(precio);
            ventaModel.setTotal(total);
            ventaList.add(ventaModel);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return ventaList;
    }

    public void updateVenta(TableView<DetalleVentaModel> tableVenta){
        tableVenta.setItems(ventaList);
    }

    public void showProducts(TableView<DetalleVentaModel> tableVenta,TableColumn<DetalleVentaModel, String> codigoVentaColumn,
                             TableColumn<DetalleVentaModel, String> productoVentaColumn, TableColumn<DetalleVentaModel, String> cantidadVentaColumn,
                             TableColumn<DetalleVentaModel, String> precioVentaColumn, TableColumn<DetalleVentaModel, String> totalVentaColumn) {

        codigoVentaColumn.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
        productoVentaColumn.setCellValueFactory(new PropertyValueFactory<>("producto"));
        cantidadVentaColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        precioVentaColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        totalVentaColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        tableVenta.setItems(ventaList);
    }

    public void addSale() {
        PreparedStatement ventaStmt = null;
        PreparedStatement detalleVentaStmt = null;
        ResultSet generatedKeys = null;

        try {
            conBD = con.getConnection();
            conBD.setAutoCommit(false);

            String sqlVenta = "INSERT INTO Venta (cliente, vendedor, total, fecha) VALUES (?, ?, ?, CURDATE())";
            ventaStmt = conBD.prepareStatement(sqlVenta, PreparedStatement.RETURN_GENERATED_KEYS);
            ventaStmt.setString(1, txtClienteVenta.getText());
            ventaStmt.setString(2, lbl_username.getText());
            ventaStmt.setDouble(3, totalVenta);
            ventaStmt.executeUpdate();

            generatedKeys = ventaStmt.getGeneratedKeys();
            int idVenta = 0;
            if (generatedKeys.next()) {
                idVenta = generatedKeys.getInt(1);
            }

            String sqlDetalleVenta = "INSERT INTO DetalleVenta (codigoProducto, producto, cantidad, precio, idVenta) VALUES (?, ?, ?, ?, ?)";
            detalleVentaStmt = conBD.prepareStatement(sqlDetalleVenta);

            for (DetalleVentaModel detalle : ventaList) {
                detalleVentaStmt.setString(1, detalle.getCodigoProducto());
                detalleVentaStmt.setString(2, detalle.getProducto());
                detalleVentaStmt.setInt(3, Integer.parseInt(detalle.getCantidad()));
                detalleVentaStmt.setDouble(4, Double.parseDouble(detalle.getPrecio()));
                detalleVentaStmt.setInt(5, idVenta);  // Usamos el ID generado aquí
                detalleVentaStmt.addBatch();
            }

            detalleVentaStmt.executeBatch();
            conBD.commit();

            JOptionPane.showMessageDialog(null, "Venta registrada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);


        } catch (SQLException e) {
            try {
                if (conBD != null) {
                    conBD.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (ventaStmt != null) ventaStmt.close();
                if (detalleVentaStmt != null) detalleVentaStmt.close();
                if (generatedKeys != null) generatedKeys.close();
                if (conBD != null) conBD.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
