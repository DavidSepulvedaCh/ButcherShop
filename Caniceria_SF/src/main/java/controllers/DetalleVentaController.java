package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ConnectionBD;
import models.DetalleVentaModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetalleVentaController {
    ConnectionBD con = new ConnectionBD();

    @FXML
    private TableView<DetalleVentaModel> tableDetail;
    @FXML
    private TableColumn<DetalleVentaModel, String> productDtColumn;
    @FXML
    private TableColumn<DetalleVentaModel, String> quantityDtColumn;
    @FXML
    private TableColumn<DetalleVentaModel, String> priceDtColumn;

    @FXML
    private Label dateDt;
    @FXML
    private Label sellerDt;
    @FXML
    private Label totalDt;
    @FXML
    private Label clientDt;

    public ObservableList<DetalleVentaModel> getDetail(String id) {
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

    public void fetchAndShowDetail(String idVenta) {
        showDetail(idVenta);
    }

    public void showDetail(String idVenta) {
        productDtColumn.setCellValueFactory(new PropertyValueFactory<>("producto"));
        quantityDtColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        priceDtColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));

        ObservableList<DetalleVentaModel> detalles = getDetail(idVenta);
        tableDetail.setItems(detalles);
    }
}
