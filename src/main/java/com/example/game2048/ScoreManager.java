package com.example.game2048;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreManager {
    private static final String SCORE_FILE = "highscores.txt";
    private String playerName;
    private int bestScore;
    private int currentScore;
    private int gridSize;

    public ScoreManager(String playerName, int gridSize) {
        this.playerName = playerName;
        this.gridSize = gridSize;
        this.bestScore = loadBestScore();
        this.currentScore = 0;
    }

    private int loadBestScore() {
        File file = new File(SCORE_FILE);
        if (!file.exists()) {
            return 0;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(getScoreKey())) {
                    return Integer.parseInt(parts[1]);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Ошибка при чтении файла с рекордами: " + e.getMessage());
        }
        return 0;
    }

    public void saveBestScore() {
        if (currentScore <= bestScore) {
            return;
        }

        bestScore = currentScore;
        File file = new File(SCORE_FILE);

        try {
            // Создаём файл, если его нет
            if (!file.exists()) {
                file.createNewFile();
            }


            List<String> lines = new ArrayList<>();
            boolean updated = false;

            if (file.length() > 0) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length == 2 && parts[0].equals(getScoreKey())) {
                            lines.add(getScoreKey() + "," + bestScore);
                            updated = true;
                        } else {
                            lines.add(line);
                        }
                    }
                }
            }

            // Если запись не найдена, добавляем новую
            if (!updated) {
                lines.add(getScoreKey() + "," + bestScore);
            }

            // Перезаписываем файл
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Не удалось сохранить рекорд: " + e.getMessage());
        }
    }

    private String getScoreKey() {
        return playerName + ":" + gridSize;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getBestScore() {
        return bestScore;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
        if (this.currentScore > this.bestScore) {
            this.bestScore = this.currentScore;
        }
    }

    public void addToScore(int points) {
        this.currentScore += points;
        if (this.currentScore > this.bestScore) {
            this.bestScore = this.currentScore;
        }
    }

    public void resetCurrentScore() {
        this.currentScore = 0;
    }
}