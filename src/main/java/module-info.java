module com.azaderdogan {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.base;
    opens com.azaderdogan to javafx.fxml,javafx.graphics;
    exports com.azaderdogan;
}