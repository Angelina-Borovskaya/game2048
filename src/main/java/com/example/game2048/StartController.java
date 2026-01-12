package com.example.game2048;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import java.io.IOException;

public class StartController {
    @FXML
    private TextField playerNameField;

    @FXML
    private ComboBox<Integer> gridSizeComboBox;

    @FXML
    private Button exitButton;

    @FXML
    private void initialize() {
        exitButton.setFocusTraversable(false);
        gridSizeComboBox.getItems().addAll(4, 6, 8);
        gridSizeComboBox.getSelectionModel().selectFirst();
        exitButton.setOnAction(e -> System.exit(0));
    }

    @FXML
    private void handleStartGame() {
        String playerName = playerNameField.getText().trim();
        int gridSize = gridSizeComboBox.getValue();

        if (playerName.isEmpty()) {
            playerNameField.setStyle("-fx-border-color: red;");
            return;
        }

        try {
            // 1. Загружаем игровое view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/game2048/GameView.fxml"));
            Parent gameRoot = loader.load();

            // 2. Получаем контроллер игрового экрана
            GameController gameController = loader.getController();

            // 3. Передаем параметры в игровой контроллер
            gameController.initializeGame(playerName, gridSize);

            // 4. Создаем новую сцену
            Stage stage = (Stage) playerNameField.getScene().getWindow();
            Scene gameScene = new Scene(gameRoot);

            // 5. Настраиваем сцену
            gameScene.getStylesheets().add(getClass().getResource("/com/example/game2048/game.css").toExternalForm());
            stage.setScene(gameScene);
            stage.setTitle("2048 - Играет " + playerName);
            stage.centerOnScreen();

        } catch (IOException e) {
            System.err.println("Ошибка загрузки игрового экрана: " + e.getMessage());
            e.printStackTrace();
        }
    }
}