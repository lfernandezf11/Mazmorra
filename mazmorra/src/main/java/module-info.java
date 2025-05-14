module com.mazmorra {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.mazmorra to javafx.fxml;
    opens com.mazmorra.Controllers to javafx.fxml;
    exports com.mazmorra;
    exports com.mazmorra.Controllers;
}
