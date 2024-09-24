package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ConnectionBD;
import models.InventarioModel;

import java.sql.*;

public class InventarioController {
    Connection conBD;
    ConnectionBD con = new ConnectionBD();
    PreparedStatement consulta;
    InventarioModel ventaModel = new InventarioModel();
    ObservableList<InventarioModel> ventaList = FXCollections.observableArrayList();


    public ObservableList<InventarioModel> getAllVentas(){
        String sql = "SELECT * FROM venta";
        ObservableList<InventarioModel> ventas = FXCollections.observableArrayList();
        try {
            ConnectionBD conBD = new ConnectionBD();
            PreparedStatement consulta = conBD.getConnection().prepareStatement(sql);
            ResultSet rta = consulta.executeQuery();
            while(rta.next()){
                InventarioModel vntModel = new InventarioModel();
                vntModel.setId(rta.getString("id"));
                vntModel.setCliente(rta.getString("cliente"));
                vntModel.setVendedor(rta.getString("vendedor"));
                vntModel.setTotal(rta.getString("total"));
                vntModel.setFecha(rta.getString("fecha"));

                ventas.add(vntModel);
            }
        }catch (SQLException e){
            System.out.println(e.toString());
        }
        return ventas;
    }

    public void updateVenta(TableView<InventarioModel> tableVenta){
        tableVenta.setItems(ventaList);
    }

    public void showVentas(TableView<InventarioModel> tableVenta, TableColumn<InventarioModel, String> idVentaColumn, TableColumn<InventarioModel, String> vendedorColumn, TableColumn<InventarioModel, Double> totalColumn, TableColumn<InventarioModel, String> fechaColumn){
        ObservableList<InventarioModel> ventas = getAllVentas();

        idVentaColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        vendedorColumn.setCellValueFactory(new PropertyValueFactory<>("vendedor"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tableVenta.setItems(ventas);
    }
}
