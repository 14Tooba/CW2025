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

        // Set minimum window size
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(600);

        // Set initial size (make it bigger)
        primaryStage.setWidth(300);
        primaryStage.setHeight(600);

        // Allow window to be resizable
        primaryStage.setResizable(true);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
