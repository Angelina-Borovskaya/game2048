package com.example.game2048;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

public class GameController {
    // Элементы UI
    @FXML private GridPane gameGrid;
    @FXML private Label scoreLabel;
    @FXML private Label bestScoreLabel;
    @FXML private Label playerNameLabel;
    @FXML private Button newGameButton;
    @FXML private Button restartGame;


    // Компоненты игры
    private GameBoard gameBoard;
    private GameLogic gameLogic;
    private ScoreManager scoreManager;

    @FXML
    private void initialize() {
        newGameButton.setFocusTraversable(false);
        restartGame.setFocusTraversable(false); // Добавьте эту строку


        newGameButton.setOnAction(e -> {
            try {
                Stage currentStage = (Stage) newGameButton.getScene().getWindow();
                double currentWidth = currentStage.getWidth();
                double currentHeight = currentStage.getHeight();

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/example/game2048/StartView.fxml")
                );
                Parent root = loader.load();
                Scene scene = new Scene(root, 400, 600);
                currentStage.setScene(scene);
                currentStage.centerOnScreen();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        restartGame.setOnAction(e -> resetGame());

        gameGrid.setOnKeyPressed(this::handleKeyPressed);
        gameGrid.setOnMouseClicked(e -> gameGrid.requestFocus());
        Platform.runLater(() -> gameGrid.requestFocus());
    }

    public void initializeGame(String playerName, int gridSize) {
        this.gameBoard = new GameBoard(gridSize);
        this.gameLogic = new GameLogic(gameBoard);
        this.scoreManager = new ScoreManager(playerName, gridSize);

        gameGrid.setFocusTraversable(true);

        // Загрузка CSS
        Scene scene = gameGrid.getScene();
        try {
            URL cssUrl = getClass().getResource("/com/example/game2048/game.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateUI();
    }

    private void updateUI() {
        gameGrid.getChildren().clear();

        int[][] board = gameBoard.getBoard();
        int size = gameBoard.getSize();
        double tileSize = calculateTileSize();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                gameGrid.add(createTile(board[row][col], tileSize), col, row);
            }
        }

        updateScoreDisplay();
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP:    makeMove(GameLogic.Direction.UP); break;
            case DOWN:  makeMove(GameLogic.Direction.DOWN); break;
            case LEFT:  makeMove(GameLogic.Direction.LEFT); break;
            case RIGHT: makeMove(GameLogic.Direction.RIGHT); break;
            case R:     resetGame(); break;
        }
    }

    private void makeMove(GameLogic.Direction direction) {
        if (gameLogic.move(direction)) {
            scoreManager.setCurrentScore(gameLogic.getScore());
            gameBoard.addRandomTile();
            updateUI();

            if (gameLogic.hasWon()) {
                showAlert("Победа!", "Вы достигли 2048!");
            } else if (gameLogic.isGameOver()) {
                showAlert("Конец игры", "Нет возможных ходов!");
            }
        }
    }

    private StackPane createTile(int value, double size) {
        StackPane tile = new StackPane();
        tile.getStyleClass().add("game-tile");
        tile.setMinSize(size, size);
        tile.setMaxSize(size, size);
        tile.setPrefSize(size, size);

        if (value != 0) {
            tile.getStyleClass().add("tile-" + value);
            Label label = new Label(String.valueOf(value));
            label.setStyle("-fx-font-size: " + (size * 0.4) + "px;");
            tile.getChildren().add(label);
        }
        return tile;
    }

    private void updateScoreDisplay() {
        playerNameLabel.setText(String.format("Игрок: %s", scoreManager.getPlayerName()));
        scoreLabel.setText(String.format("Счет: %,d", scoreManager.getCurrentScore()));
        bestScoreLabel.setText(String.format("Рекорд: %,d", scoreManager.getBestScore()));

        Platform.runLater(() -> {
            scoreLabel.setMinWidth(calculateLabelWidth(scoreLabel.getText()));
            bestScoreLabel.setMinWidth(calculateLabelWidth(bestScoreLabel.getText()));
        });

        if (scoreManager.getCurrentScore() > 999999) {
            scoreLabel.setText(String.format("Счет: %.1fM", scoreManager.getCurrentScore() / 1000000.0));
        }
    }

    private double calculateLabelWidth(String text) {
        return text.length() * 10 + 30;
    }

    private void resetGame() {
        gameBoard = new GameBoard(gameBoard.getSize());
        gameLogic = new GameLogic(gameBoard);
        scoreManager.resetCurrentScore();
        updateUI();
    }

    private double calculateTileSize() {
        return (450.0 - 30 - (gameBoard.getSize() - 1) * 10) / gameBoard.getSize();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.initOwner(gameGrid.getScene().getWindow());

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/game2048/icon.png")));

        alert.showAndWait();
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}