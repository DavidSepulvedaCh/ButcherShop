package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.ConnectionBD;
import models.InsumoModel;

import java.sql.*;

public class InsumoController {
    Connection conBD;
    ConnectionBD con = new ConnectionBD();
    PreparedStatement consulta;
    InsumoModel insumoModel = new InsumoModel();

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
}
