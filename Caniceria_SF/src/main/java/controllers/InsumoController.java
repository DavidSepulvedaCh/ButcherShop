package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ConnectionBD;
import models.InsumoModel;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;

public class InsumoController {
    Connection conBD;
    ConnectionBD con = new ConnectionBD();
    PreparedStatement consulta;
    InsumoModel insumoModel = new InsumoModel();

    boolean insumoSuccess;

    public boolean registerInsumo (InsumoModel insuMdl){
        String sql = "INSERT INTO insumos (nombre, precio, cantidad, proveedor, fecha, descripcion) VALUES (?, ?, ?, ?, ?, ?)";
        try{
            conBD = con.getConnection();
            consulta = conBD.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            consulta.setString(1, insuMdl.getNombreInsumo());
            consulta.setDouble(2, Double.parseDouble(insuMdl.getPrecioInsumo()));
            consulta.setInt(3, Integer.parseInt(insuMdl.getCantidadInsumo()));
            consulta.setString(4, insuMdl.getProveedorInsumo());
            consulta.setString(5, insuMdl.getFechaInsumo());
            consulta.setString(6, insuMdl.getDescripcionInsumo());
            consulta.executeUpdate();
            ResultSet generatedKeys = consulta.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                insuMdl.setId(String.valueOf(generatedId));
            }
            return true;
        }catch (SQLException e){
            System.out.println(e.toString());
            return false;
        }
    }

    public ObservableList<InsumoModel> getAllInsumos(){
        String sql = "SELECT * FROM insumos";
        ObservableList<InsumoModel> insumosList  = FXCollections.observableArrayList();
        try{
            conBD = con.getConnection();
            consulta = conBD.prepareStatement(sql);
            ResultSet rta = consulta.executeQuery();
            while(rta.next()){
                InsumoModel insMdl = new InsumoModel();
                insMdl.setId(rta.getString("id"));
                insMdl.setNombreInsumo(rta.getString("nombre"));
                insMdl.setPrecioInsumo(rta.getString("precio"));
                insMdl.setCantidadInsumo(rta.getString("cantidad"));
                insMdl.setProveedorInsumo(rta.getString("proveedor"));
                insMdl.setFechaInsumo(rta.getString("fecha"));
                insMdl.setDescripcionInsumo(rta.getString("descripcion"));
                insumosList.add(insMdl);
            }
            rta.close();
            consulta.close();
        }catch (SQLException e){
            System.out.println(e.toString());
        }
        return insumosList;
    }

    public void verifyInsumo(String precioInsumo, String cantidadInsumo, String proveedorInsumo, LocalDate fechaInsumo, String insumo, String descripcionInsumo){
        InsumoModel insMdl = new InsumoModel();
        insMdl.setNombreInsumo(insumo);
        insMdl.setCantidadInsumo(cantidadInsumo);
        insMdl.setPrecioInsumo(precioInsumo);
        insMdl.setProveedorInsumo(proveedorInsumo);
        insMdl.setFechaInsumo(String.valueOf(fechaInsumo));
        insMdl.setDescripcionInsumo(descripcionInsumo);
        insumoSuccess = registerInsumo(insMdl);
        if(insumoSuccess){
            JOptionPane.showMessageDialog(null, "Registro de insumo exitoso", "Exito", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Error al registrar la compra", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateInsumos(TableView<InsumoModel> tableBuyInsumo){
        ObservableList<InsumoModel> insumoList = getAllInsumos();
        tableBuyInsumo.setItems(insumoList);
    }

    public void showInsumos(TableView<InsumoModel> tableBuyInsumo, TableColumn<InsumoModel, String> tipoInsumoColumn, TableColumn<InsumoModel, String> precioInsumoColumn, TableColumn<InsumoModel, String> cantidadInsumoColumn, TableColumn<InsumoModel, String> proveedorInsumoColumn, TableColumn<InsumoModel, String> fechaInsumoColumn, TableColumn<InsumoModel, String> descripcionInsumoColumn){
        ObservableList<InsumoModel> insumosList = getAllInsumos();

        tipoInsumoColumn.setCellValueFactory(new PropertyValueFactory<>("nombreInsumo"));
        precioInsumoColumn.setCellValueFactory(new PropertyValueFactory<>("precioInsumo"));
        cantidadInsumoColumn.setCellValueFactory(new PropertyValueFactory<>("cantidadInsumo"));
        proveedorInsumoColumn.setCellValueFactory(new PropertyValueFactory<>("proveedorInsumo"));
        fechaInsumoColumn.setCellValueFactory(new PropertyValueFactory<>("fechaInsumo"));
        descripcionInsumoColumn.setCellValueFactory(new PropertyValueFactory<>("descripcionInsumo"));

        tableBuyInsumo.setItems(insumosList);
    }

}
