package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ConnectionBD;
import models.VentaModel;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class VentaController {
    Connection conBD;
    ConnectionBD con = new ConnectionBD();
    PreparedStatement consulta;
    VentaModel ventaModel = new VentaModel();
    ObservableList<VentaModel> ventaList = FXCollections.observableArrayList();

    public ObservableList<VentaModel> addTable(String codigo, String producto, String cantidad, String precio, String total){
        try {
            VentaModel ventaModel = new VentaModel();
            ventaModel.setCodigoProducto(codigo);
            ventaModel.setProducto(producto);
            ventaModel.setCantidad(cantidad);
            ventaModel.setPrecio(precio);
            ventaModel.setPrecio(total);
            ventaList.add(ventaModel);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return ventaList;
    }


    public void updateVenta(TableView<VentaModel> tableVenta){
        tableVenta.setItems(ventaList);
    }

    public void showInsumos(TableView<VentaModel> tableVenta, TableColumn<VentaModel, String> codigoVentaColumn, TableColumn<VentaModel, String> productoVentaColumn, TableColumn<VentaModel, String> cantidadVentaColumn, TableColumn<VentaModel, String> precioVentaColumn, TableColumn<VentaModel, String> totalProductoVentaColumn){

        codigoVentaColumn.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
        productoVentaColumn.setCellValueFactory(new PropertyValueFactory<>("producto"));
        cantidadVentaColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        precioVentaColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        totalProductoVentaColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tableVenta.setItems(ventaList);
    }
}
