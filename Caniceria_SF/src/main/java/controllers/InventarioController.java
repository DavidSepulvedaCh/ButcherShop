package controllers;

import com.mycompany.caniceria_sf.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.ConnectionBD;
import models.DetalleVentaModel;
import models.InventarioModel;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class InventarioController {
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

    ConnectionBD con = new ConnectionBD();
    ObservableList<InventarioModel> ventaList = FXCollections.observableArrayList();



    public ObservableList<InventarioModel> getAllVentas() {
        String sql = "SELECT * FROM venta";
        ObservableList<InventarioModel> ventas = FXCollections.observableArrayList();
        try {
            PreparedStatement consulta = con.getConnection().prepareStatement(sql);
            ResultSet rta = consulta.executeQuery();
            while (rta.next()) {
                InventarioModel vntModel = new InventarioModel();
                vntModel.setId(rta.getString("id"));
                vntModel.setCliente(rta.getString("cliente"));
                vntModel.setVendedor(rta.getString("vendedor"));
                vntModel.setTotal(rta.getString("total"));
                vntModel.setFecha(rta.getString("fecha"));
                ventas.add(vntModel);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return ventas;
    }

    public void showInventario(TableView<InventarioModel> tableInventario, TableColumn<InventarioModel, String> idVentaColumn, TableColumn<InventarioModel, String> vendedorColumn, TableColumn<InventarioModel, Double> totalColumn,  TableColumn<InventarioModel, String> fechaColumn) {
        ObservableList<InventarioModel> ventas = getAllVentas();

        idVentaColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        vendedorColumn.setCellValueFactory(new PropertyValueFactory<>("vendedor"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        tableInventario.setItems(ventas);
    }

    public void alerta(String idVenta){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Detalle de venta");
        alert.setHeaderText("¿Deseas ver el detalle de la venta "+idVenta +"?");

        ButtonType buttonYes = new ButtonType("Sí");
        ButtonType buttonNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonYes) {
            System.out.println("Seleccionaste 'Sí'");
            openDetailWindow(idVenta);
        } else {
            System.out.println("Seleccionaste 'No'");
        }

    }

    public void openDetailWindow(String idVenta) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("detail_sale.fxml"));
            Parent root = loader.load();

            DetalleVentaController detalleVentaController = loader.getController();

            detalleVentaController.fetchAndShowDetail(idVenta);

            Stage detailStage = new Stage();
            detailStage.setTitle("Detalles de la Venta");
            detailStage.initModality(Modality.APPLICATION_MODAL);
            Scene detailScene = new Scene(root);
            detailStage.setScene(detailScene);
            detailStage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo FXML: " + e.getMessage());
        }
    }

}
