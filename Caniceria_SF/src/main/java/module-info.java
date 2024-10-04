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
}
