module com.petstoremanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;

    opens com.petstoremanagement to javafx.fxml;
    exports com.petstoremanagement;
}