module com.mycompany.caniceria_sf {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;
    requires java.sql;
    //requires itext5.itextpdf;
    requires javafx.graphics;
    requires itextpdf;

    opens com.mycompany.caniceria_sf to javafx.fxml;
    opens controllers to javafx.fxml;
    opens models to javafx.base;

    exports com.mycompany.caniceria_sf;
    exports controllers;
    exports controllers.BuyRes;
    opens controllers.BuyRes to javafx.fxml;
    exports controllers.Insumos;
    opens controllers.Insumos to javafx.fxml;
    exports controllers.Productos;
    opens controllers.Productos to javafx.fxml;
    exports controllers.Sale;
    opens controllers.Sale to javafx.fxml;
}
