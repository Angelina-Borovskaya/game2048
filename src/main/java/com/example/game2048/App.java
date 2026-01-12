package com.example.game2048;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static Stage primaryStage;
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        App.primaryStage = primaryStage;
        showStartView();
        primaryStage.show();
    }

    public static void showStartView() throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/example/game2048/StartView.fxml"));
        Parent root = loader.load();


        double width = primaryStage.getWidth() > 0 ? primaryStage.getWidth() : DEFAULT_WIDTH;
        double height = primaryStage.getHeight() > 0 ? primaryStage.getHeight() : DEFAULT_HEIGHT;

        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(App.class.getResource("/com/example/game2048/start.css").toExternalForm());

        primaryStage.setTitle("2048 Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}