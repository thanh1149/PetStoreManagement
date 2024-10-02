package com.petstoremanagement.Controller;

import com.petstoremanagement.Global.AppProperties;
import com.petstoremanagement.Service.LoginService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;
    @FXML private Button btnLogin;
    @FXML private Label lbInform;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean isLogin = true;
                String username = txtUsername.getText();
                String password = txtPassword.getText();

                if(username.isEmpty() || password.isEmpty()){
                    lbInform.setText("Username or Password must not empty.");
                    lbInform.setStyle("-fx-text-fill: red");
                    isLogin = false;
                }else {
                    if(!LoginService.login(username,password)){
                        lbInform.setText("Invalid username or password");
                        lbInform.setStyle("-fx-text-fill: red");
                        isLogin = false;
                    }
                }
                if (isLogin){
                    AppProperties.setProperty("user.name",username);
                    AppProperties.setProperty("user.loggedin","true");
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                    //loading layout
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/petstoremanagement/view/master.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(),800,600);
                        stage.setTitle("PetStoreManagement");
                        stage.setScene(scene);
                        stage.show();
                    }catch (IOException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
    }
}
