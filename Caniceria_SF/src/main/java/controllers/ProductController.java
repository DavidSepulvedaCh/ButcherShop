package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.MapValueFactory;
import models.ConnectionBD;
import models.ProductModel;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class ProductController {

    Connection conBD;
    ConnectionBD con = new ConnectionBD();

    PreparedStatement consulta;
    ResultSet rta;
    ProductModel pro = new ProductModel();

    public boolean registerProduct(ProductModel prModel) throws SQLException {
        String vld = "SELECT * FROM productos WHERE codigo = ?";
        conBD = con.getConnection();
        consulta = conBD.prepareStatement(vld);
        consulta.setString(1, prModel.getCodigo());
        rta = consulta.executeQuery();

        if (rta.next()) {
            JOptionPane.showMessageDialog(null, "El código de producto ya está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = "INSERT INTO productos (codigo, nombre, precio) VALUES (?, ?, ?)";
        try {
            conBD = con.getConnection();
            consulta = conBD.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            consulta.setString(1, prModel.getCodigo());
            consulta.setString(2, prModel.getNombre());
            consulta.setDouble(3, Double.parseDouble(prModel.getPrecio()));
            consulta.executeUpdate();

            ResultSet generatedKeys = consulta.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                prModel.setId(String.valueOf(generatedId));
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }


    public ObservableList<ProductModel> getAllProducts() {
        String sql = "SELECT * FROM productos";
        ObservableList<ProductModel> productList = FXCollections.observableArrayList();
        try {
            ConnectionBD conBD = new ConnectionBD();
            PreparedStatement consulta = conBD.getConnection().prepareStatement(sql);
            ResultSet rta = consulta.executeQuery();
            while (rta.next()) {
                ProductModel prModel = new ProductModel();
                prModel.setId(rta.getString("id"));
                prModel.setCodigo(rta.getString("codigo"));
                prModel.setNombre(rta.getString("nombre"));
                prModel.setPrecio(rta.getString("precio"));

                productList.add(prModel);
            }
            rta.close();
            consulta.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return productList;
    }

    public ProductModel searchProduct (String codigo){
        ProductModel producto = new ProductModel();
        String sql = "SELECT * FROM productos WHERE codigo = ? ";
        try{
            conBD = con.getConnection();
            consulta = conBD.prepareStatement(sql);
            consulta.setString(1, codigo);
            rta = consulta.executeQuery();
            if(rta.next()){
                producto.setCodigo(rta.getString("codigo"));
                producto.setNombre(rta.getString("nombre"));
                producto.setPrecio(rta.getString("precio"));
            }
        }catch (SQLException e){
            System.out.println(e.toString());
        }
        return producto;
    }
}
