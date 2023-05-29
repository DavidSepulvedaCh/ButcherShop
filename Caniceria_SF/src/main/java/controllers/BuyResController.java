package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.ConnectionBD;

import java.sql.*;

import models.BuyResModel;

public class BuyResController {
    Connection conBD;
    ConnectionBD con = new ConnectionBD();
    PreparedStatement consulta;
    ResultSet rta;
    BuyResModel buyRes = new BuyResModel();

    public boolean registerBuyRes(BuyResModel buyModel) {
        String sql = "INSERT INTO compra_animal (tipo, peso, precio, fecha, proveedor) VALUES (?, ?, ?, ?, ?)";
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
        String sql = "SELECT * FROM compra_animal";
        ObservableList<BuyResModel> purchasesList = FXCollections.observableArrayList();
        try {
            ConnectionBD conBD = new ConnectionBD();
            PreparedStatement consulta = conBD.getConnection().prepareStatement(sql);
            ResultSet rta = consulta.executeQuery();
            while (rta.next()) {
                BuyResModel buyRes = new BuyResModel();
                buyRes.setId(rta.getString("id"));
                buyRes.setTipoAnimal(rta.getString("tipo"));
                buyRes.setProveedor(rta.getString("proveedor"));
                buyRes.setPesoArrobas(rta.getString("peso"));
                buyRes.setPrecioArroba(rta.getString("precio"));
                buyRes.setFechaCompra(rta.getString("fecha"));
                purchasesList.add(buyRes);
            }
            rta.close();
            consulta.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return purchasesList;
    }
}
