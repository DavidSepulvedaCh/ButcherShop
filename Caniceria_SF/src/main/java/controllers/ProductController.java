package controllers;

import com.itextpdf.text.*;
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
import javax.swing.*;
import java.io.FileOutputStream;
import java.sql.*;

public class ProductController {

    Connection conBD;
    ConnectionBD con = new ConnectionBD();
    PreparedStatement consulta;
    ResultSet rta;
    ProductModel pro = new ProductModel();
    boolean registroExitoso;

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

    public void printProducts() {
        try {
            String ruta = System.getProperty("user.home");
            String archivoPDF = ruta + "/OneDrive/Escritorio/productos.pdf";

            Document productsList = new Document();
            PdfWriter.getInstance(productsList, new FileOutputStream(archivoPDF));
            productsList.open();

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font contentFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            Paragraph header = new Paragraph();
            header.setAlignment(Element.ALIGN_CENTER);

            Image logo = Image.getInstance("D:/UPB/VII/Emprendimiento/Logo.png");
            logo.scaleAbsolute(100f, 100f);
            header.add(logo);

            Chunk chunk = new Chunk("Chavez Meat\n", headerFont);
            chunk.append("Dirección: La Carrera N.de.S\n");
            chunk.append("Teléfono: 3223126566\n");
            header.add(chunk);

            productsList.add(header);

            PdfPTable tableProducts = new PdfPTable(3);
            tableProducts.setWidthPercentage(100);
            tableProducts.setSpacingBefore(20f);
            tableProducts.setSpacingAfter(20f);

            PdfPCell cell;

            cell = new PdfPCell(new Phrase("Producto", headerFont));
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5f);
            tableProducts.addCell(cell);

            cell = new PdfPCell(new Phrase("Codigo", headerFont));
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5f);
            tableProducts.addCell(cell);

            cell = new PdfPCell(new Phrase("Precio", headerFont));
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5f);
            tableProducts.addCell(cell);

            try {
                conBD = con.getConnection();
                consulta = conBD.prepareStatement("SELECT * FROM productos");
                ResultSet rta = consulta.executeQuery();
                while (rta.next()) {
                    cell = new PdfPCell(new Phrase(rta.getString(3), contentFont));
                    cell.setBorderColor(BaseColor.LIGHT_GRAY);
                    cell.setPadding(5f);
                    tableProducts.addCell(cell);

                    cell = new PdfPCell(new Phrase(rta.getString(2), contentFont));
                    cell.setBorderColor(BaseColor.LIGHT_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(5f);
                    tableProducts.addCell(cell);

                    cell = new PdfPCell(new Phrase(rta.getString(4), contentFont));
                    cell.setBorderColor(BaseColor.LIGHT_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cell.setPadding(5f);
                    tableProducts.addCell(cell);
                }
            } catch (Exception e) {
                System.out.println(e);
            }

            productsList.add(tableProducts);
            productsList.close();

            JOptionPane.showMessageDialog(null, "Documento de productos creado!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
