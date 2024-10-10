module com.petstoremanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;

    requires java.sql;

    requires jbcrypt;

    opens com.petstoremanagement to javafx.fxml;
    exports com.petstoremanagement;

    opens com.petstoremanagement.Controller to javafx.fxml;
    exports com.petstoremanagement.Controller;

    opens com.petstoremanagement.Service to javafx.fxml;
    exports com.petstoremanagement.Service;

    opens com.petstoremanagement.Model to javafx.fxml;
    exports com.petstoremanagement.Model;
    exports com.petstoremanagement.Global;
    opens com.petstoremanagement.Global to javafx.fxml;

    exports com.petstoremanagement.Controller.staff;
    opens com.petstoremanagement.Controller.staff to javafx.fxml;

    exports com.petstoremanagement.Controller.service;
    opens com.petstoremanagement.Controller.service to javafx.fxml;

    exports com.petstoremanagement.Controller.product;
    opens com.petstoremanagement.Controller.product to javafx.fxml;

    exports com.petstoremanagement.Controller.customer;
    opens com.petstoremanagement.Controller.customer to javafx.fxml;

}