package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ConnectionBD;

import java.sql.*;
import java.time.LocalDate;

import models.BuyResModel;

import javax.swing.*;

public class BuyResController {
    Connection conBD;
    ConnectionBD con = new ConnectionBD();
    PreparedStatement consulta;
    ResultSet rta;
    BuyResModel buyRes = new BuyResModel();
    boolean buySuccess;

    public boolean registerBuyRes(BuyResModel buyModel) {
        String sql = "INSERT INTO BuyRes (tipoAnimal, pesoArrobas, precioArroba, fechaCompra, proveedor) VALUES (?, ?, ?, ?, ?)";
        try {
            conBD = con.getConnection();
            consulta = conBD.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            consulta.setString(1, buyModel.getTipoAnimal());
            consulta.setDouble(2, Double.parseDouble(buyModel.getPesoArrobas()));
            consulta.setDouble(3, Double.parseDouble(buyModel.getPrecioArroba()));
            consulta.setString(4, buyModel.getFechaCompra());
            consulta.setString(5, buyModel.getProveedor());

            consulta.executeUpdate();

            ResultSet generatedKeys = consulta.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                buyModel.setId(String.valueOf(generatedId));
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public ObservableList<BuyResModel> getAllPurchases() {
        String sql = "SELECT * FROM BuyRes";
        ObservableList<BuyResModel> purchasesList = FXCollections.observableArrayList();
        try {
            ConnectionBD conBD = new ConnectionBD();
            PreparedStatement consulta = conBD.getConnection().prepareStatement(sql);
            ResultSet rta = consulta.executeQuery();
            while (rta.next()) {
                BuyResModel buyRes = new BuyResModel();
                buyRes.setId(rta.getString("id"));
                buyRes.setTipoAnimal(rta.getString("tipoAnimal"));
                buyRes.setProveedor(rta.getString("proveedor"));
                buyRes.setPesoArrobas(rta.getString("pesoArrobas"));
                buyRes.setPrecioArroba(rta.getString("precioArroba"));
                buyRes.setFechaCompra(rta.getString("fechaCompra"));
                purchasesList.add(buyRes);
            }
            rta.close();
            consulta.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return purchasesList;
    }

    public void verifyBuyRes(String pesoArroba, String precioArroba, String proveedor, LocalDate fecha, String tipo){
        BuyResModel buyMdl = new BuyResModel();
        buyMdl.setPesoArrobas(pesoArroba);
        buyMdl.setPrecioArroba(precioArroba);
        buyMdl.setProveedor(proveedor);
        buyMdl.setFechaCompra(String.valueOf(fecha));
        buyMdl.setTipoAnimal(tipo);

        buySuccess = registerBuyRes(buyMdl);
        if(buySuccess){
            JOptionPane.showMessageDialog(null, "Registro de compra exitoso", "Exito", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Error al registrar la compra", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void updatePurchases(TableView<BuyResModel> tableBuyRes) {
        ObservableList<BuyResModel> purchasesList = getAllPurchases();
        tableBuyRes.setItems(purchasesList);
    }

    public void showBuyRes(TableView<BuyResModel> tableBuyRes, TableColumn<BuyResModel, String> tipoColumn, TableColumn<BuyResModel, String> pesoColumn, TableColumn<BuyResModel, String> precioCompraColumn, TableColumn<BuyResModel, String> fechaColumn, TableColumn<BuyResModel, String> proveedorColumn ){
        ObservableList<BuyResModel> purchasesList = getAllPurchases();
        // Celdas tabla COMPRA ANIMAL
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipoAnimal"));
        pesoColumn.setCellValueFactory(new PropertyValueFactory<>("pesoArrobas"));
        precioCompraColumn.setCellValueFactory(new PropertyValueFactory<>("precioArroba"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaCompra"));
        proveedorColumn.setCellValueFactory(new PropertyValueFactory<>("proveedor"));
        tableBuyRes.setItems(purchasesList);
    }
}
