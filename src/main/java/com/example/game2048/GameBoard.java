package com.example.game2048;

import java.util.Random;

public class GameBoard {
    private int size;
    private int[][] board;
    private Random random;

    public GameBoard(int size) {
        this.size = size;
        this.board = new int[size][size];
        this.random = new Random();
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = 0;
            }
        }
        addRandomTile();
        addRandomTile();
    }

    public void addRandomTile() {
        int value = random.nextInt(10) < 9 ? 2 : 4;

        while (true) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);

            if (board[x][y] == 0) {
                board[x][y] = value;
                break;
            }
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getSize() {
        return size;
    }
}