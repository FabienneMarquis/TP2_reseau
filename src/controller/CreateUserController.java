package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Context;
import model.LocatedImage;
import model.User;

import java.io.IOException;
import java.util.Optional;

public class CreateUserController {

    @FXML
    private Button acceptBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField username;

    @FXML
    private ImageView image;

    @FXML
    void accept(ActionEvent event) {
        if(!username.getText().isEmpty() ){

            Context.getInstance().setUser(new User(username.getText(),((LocatedImage)image.getImage()).getURL()));
        }
    }



    @FXML
    void cancel(ActionEvent event) {

    }

}
