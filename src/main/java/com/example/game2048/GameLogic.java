package com.example.game2048;

public class GameLogic {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private final GameBoard gameBoard;
    private int score;

    public GameLogic(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.score = 0;
    }

    public boolean move(Direction direction) {
        int[][] oldBoard = copyBoard(gameBoard.getBoard());

        switch (direction) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
        }

        return !boardsEqual(oldBoard, gameBoard.getBoard());
    }

    private void moveUp() {
        int[][] board = gameBoard.getBoard();
        int size = gameBoard.getSize();

        for (int j = 0; j < size; j++) {

            int[] newCol = new int[size];
            int index = 0;
            for (int i = 0; i < size; i++) {
                if (board[i][j] != 0) {
                    newCol[index++] = board[i][j];
                }
            }

            for (int i = 0; i < size - 1; i++) {
                if (newCol[i] != 0 && newCol[i] == newCol[i + 1]) {
                    newCol[i] *= 2;
                    score += newCol[i];
                    newCol[i + 1] = 0;
                }
            }

            int[] finalCol = new int[size];
            index = 0;
            for (int i = 0; i < size; i++) {
                if (newCol[i] != 0) {
                    finalCol[index++] = newCol[i];
                }
            }

            for (int i = 0; i < size; i++) {
                board[i][j] = finalCol[i];
            }
        }
    }

    private void moveDown() {
        int[][] board = gameBoard.getBoard();
        int size = gameBoard.getSize();

        for (int j = 0; j < size; j++) {
            // 1. Сдвигаем все плитки вниз
            int[] newCol = new int[size];
            int index = size - 1;
            for (int i = size - 1; i >= 0; i--) {
                if (board[i][j] != 0) {
                    newCol[index--] = board[i][j];
                }
            }

            // 2. Объединяем плитки
            for (int i = size - 1; i > 0; i--) {
                if (newCol[i] != 0 && newCol[i] == newCol[i - 1]) {
                    newCol[i] *= 2;
                    score += newCol[i];
                    newCol[i - 1] = 0;
                }
            }

            // 3. Снова сдвигаем вниз после объединения
            int[] finalCol = new int[size];
            index = size - 1;
            for (int i = size - 1; i >= 0; i--) {
                if (newCol[i] != 0) {
                    finalCol[index--] = newCol[i];
                }
            }

            // Запись обратно
            for (int i = 0; i < size; i++) {
                board[i][j] = finalCol[i];
            }
        }
    }

    private void moveLeft() {
        int[][] board = gameBoard.getBoard();
        int size = gameBoard.getSize();

        for (int i = 0; i < size; i++) {
            // 1. Сдвигаем все плитки влево
            int[] newRow = new int[size];
            int index = 0;
            for (int j = 0; j < size; j++) {
                if (board[i][j] != 0) {
                    newRow[index++] = board[i][j];
                }
            }

            // 2. Объединяем плитки
            for (int j = 0; j < size - 1; j++) {
                if (newRow[j] != 0 && newRow[j] == newRow[j + 1]) {
                    newRow[j] *= 2;
                    score += newRow[j];
                    newRow[j + 1] = 0;
                }
            }

            // 3. Снова сдвигаем влево после объединения
            int[] finalRow = new int[size];
            index = 0;
            for (int j = 0; j < size; j++) {
                if (newRow[j] != 0) {
                    finalRow[index++] = newRow[j];
                }
            }

            // Запись обратно
            System.arraycopy(finalRow, 0, board[i], 0, size);
        }
    }

    private void moveRight() {
        int[][] board = gameBoard.getBoard();
        int size = gameBoard.getSize();

        for (int i = 0; i < size; i++) {
            // 1. Сдвигаем все плитки вправо
            int[] newRow = new int[size];
            int index = size - 1;
            for (int j = size - 1; j >= 0; j--) {
                if (board[i][j] != 0) {
                    newRow[index--] = board[i][j];
                }
            }

            // 2. Объединяем плитки
            for (int j = size - 1; j > 0; j--) {
                if (newRow[j] != 0 && newRow[j] == newRow[j - 1]) {
                    newRow[j] *= 2;
                    score += newRow[j];
                    newRow[j - 1] = 0;
                }
            }

            // 3. Снова сдвигаем вправо после объединения
            int[] finalRow = new int[size];
            index = size - 1;
            for (int j = size - 1; j >= 0; j--) {
                if (newRow[j] != 0) {
                    finalRow[index--] = newRow[j];
                }
            }

            // Запись обратно
            System.arraycopy(finalRow, 0, board[i], 0, size);
        }
    }

    public boolean isGameOver() {
        if (!isBoardFull()) {
            return false;
        }

        int[][] board = gameBoard.getBoard();
        int size = gameBoard.getSize();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i < size-1 && board[i][j] == board[i+1][j]) ||
                        (j < size-1 && board[i][j] == board[i][j+1])) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean hasWon() {
        int[][] board = gameBoard.getBoard();
        for (int[] row : board) {
            for (int value : row) {
                if (value == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isBoardFull() {
        int[][] board = gameBoard.getBoard();
        for (int[] row : board) {
            for (int value : row) {
                if (value == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] copyBoard(int[][] original) {
        int size = gameBoard.getSize();
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, size);
        }
        return copy;
    }

    private boolean boardsEqual(int[][] a, int[][] b) {
        int size = gameBoard.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (a[i][j] != b[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}