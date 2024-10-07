package controllers.Productos;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ConnectionBD;
import models.ProductModel;

import  com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;

public class ProductController {

    Connection conBD;
    ConnectionBD con = new ConnectionBD();
    PreparedStatement consulta;
    ResultSet rta;
    ProductModel pro = new ProductModel();
    public boolean registroExitoso;

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

    public boolean deleteProduct(String productId) {
        String sql = "DELETE FROM productos WHERE id = ?";
        try {
            conBD = con.getConnection();
            consulta = conBD.prepareStatement(sql);
            consulta.setString(1, productId);
            int rowsAffected = consulta.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
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

    public void verifyRegProducts(String codigo, String producto, String precio) throws SQLException {
        ProductModel prModel = new ProductModel();
        prModel.setCodigo(codigo);
        prModel.setNombre(producto);
        prModel.setPrecio(precio);

        registroExitoso = registerProduct(prModel);
        if(registroExitoso){

        }
    }

    public void updateProducts(TableView<ProductModel> tableProduct) {
        ObservableList<ProductModel> productList = getAllProducts();
        tableProduct.setItems(productList);
    }

    public void showTableProducts(TableView<ProductModel> tableProduct, TableColumn<ProductModel, String> codigoColumn, TableColumn<ProductModel, Double> precioColumn, TableColumn<ProductModel, String> productoColumn){
        ObservableList<ProductModel> productList = getAllProducts();
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        productoColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tableProduct.setItems(productList);
    }

}