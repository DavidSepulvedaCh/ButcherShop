package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.ConnectionBD;
import models.DetalleVentaModel;
import models.InventarioModel;

import javax.swing.*;
import java.sql.*;

public class DetalleVentaController {

    @FXML
    private TableView<DetalleVentaModel> tableDetail;
    @FXML
    private TableColumn<DetalleVentaModel, String> productDtColumn;
    @FXML
    private TableColumn<DetalleVentaModel, String> quantityDtColumn;
    @FXML
    private TableColumn<DetalleVentaModel, String> priceDtColumn;
    @FXML
    public TableView<InventarioModel> tableInventario;
    @FXML
    public TableColumn<InventarioModel, String> idVentaColumn;
    @FXML
    public TableColumn<InventarioModel, String> vendedorColumn;
    @FXML
    public TableColumn<InventarioModel, Double> totalColumn;
    @FXML
    public TableColumn<InventarioModel, String> fechaVentaColumn;
    @FXML
    private Label dateDt;
    @FXML
    private Label sellerDt;
    @FXML
    private Label totalDt;
    @FXML
    private Label clientDt;
    ConnectionBD con = new ConnectionBD();
    private String idVenta = "";
    private Stage detailStage;
    private InventarioController inventarioController;

    public ObservableList<DetalleVentaModel> getDetail(String id) {
        idVenta = id;
        String sql = "SELECT d.producto, d.precio, d.cantidad, v.vendedor, v.total, v.fecha, v.cliente " +
                "FROM detalleventa d " +
                "JOIN venta v ON d.idventa = v.id " +
                "WHERE d.idventa = ?";
        ObservableList<DetalleVentaModel> detalles = FXCollections.observableArrayList();
        try {
            PreparedStatement consulta = con.getConnection().prepareStatement(sql);
            consulta.setString(1, id);
            ResultSet rta = consulta.executeQuery();
            while (rta.next()) {
                DetalleVentaModel detalleVentaModel = new DetalleVentaModel();
                detalleVentaModel.setProducto(rta.getString("producto"));
                detalleVentaModel.setPrecio(rta.getString("precio"));
                detalleVentaModel.setCantidad(rta.getString("cantidad"));
                detalles.add(detalleVentaModel);

                sellerDt.setText(rta.getString("vendedor"));
                totalDt.setText(rta.getString("total"));
                dateDt.setText(rta.getString("fecha"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los detalles de la venta: " + e.toString());
        }
        return detalles;
    }

    public void fetchAndShowDetail(String idVenta, Stage stage, InventarioController inventarioController) {
        this.detailStage = stage;
        this.inventarioController = inventarioController;
        showDetail(idVenta);
    }


    public void showDetail(String idVenta) {
        productDtColumn.setCellValueFactory(new PropertyValueFactory<>("producto"));
        quantityDtColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        priceDtColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));

        ObservableList<DetalleVentaModel> detalles = getDetail(idVenta);
        tableDetail.setItems(detalles);
    }

    public void deleteSale() {
        String sqlDetalleVenta = "DELETE FROM detalleventa WHERE idVenta = ?";
        String sqlVenta = "DELETE FROM venta WHERE id = ?";

        Connection conBD = null;
        PreparedStatement consultaDetalle = null;
        PreparedStatement consultaVenta = null;

        try {
            conBD = con.getConnection();
            conBD.setAutoCommit(false);

            consultaDetalle = conBD.prepareStatement(sqlDetalleVenta);
            consultaDetalle.setString(1, idVenta);
            consultaDetalle.executeUpdate();

            consultaVenta = conBD.prepareStatement(sqlVenta);
            consultaVenta.setString(1, idVenta);
            consultaVenta.executeUpdate();

            conBD.commit();

            JOptionPane.showMessageDialog(null, "¡La venta se ha eliminado exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            if (detailStage != null) {
                detailStage.close();
            }

            inventarioController.showInventario(tableInventario, idVentaColumn,  vendedorColumn, totalColumn, fechaVentaColumn);

        } catch (SQLException e) {
            if (conBD != null) {
                try {
                    conBD.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException(e);

        } finally {
            try {
                if (consultaDetalle != null) consultaDetalle.close();
                if (consultaVenta != null) consultaVenta.close();
                if (conBD != null) conBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
