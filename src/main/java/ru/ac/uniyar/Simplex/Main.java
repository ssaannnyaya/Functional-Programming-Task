package ru.ac.uniyar.Simplex;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Locale.setDefault(new Locale("ru", "RU"));
        primaryStage.setTitle("Simplex method");

        ApplicationController applicationController = new ApplicationController();

        BorderPane root = applicationController.getRoot();

        Scene scene = new Scene(root, 1080, 860);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}