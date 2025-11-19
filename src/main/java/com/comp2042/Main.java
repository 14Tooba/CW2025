package com.comp2042;

import com.comp2042.controller.GameController;
import com.comp2042.view.GuiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.comp2042.controller.MenuController;

import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("TetrisJFX");
        primaryStage.setResizable(false);

        MenuController menuController = new MenuController(primaryStage);
        primaryStage.setScene(menuController.getMenuScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
