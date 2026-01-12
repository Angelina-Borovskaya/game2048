import com.example.game2048.GameBoard;
import com.example.game2048.GameLogic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameLogicTest {
    @Test
    @DisplayName("Движение вверх: плитки должны объединяться")
    void moveUp_ShouldMergeTiles() {
        GameBoard board = new GameBoard(4);
        board.getBoard()[0][0] = 2;
        board.getBoard()[1][0] = 2;
        GameLogic logic = new GameLogic(board);

        logic.move(GameLogic.Direction.UP);
        assertEquals(4, board.getBoard()[0][0]);
        assertEquals(0, board.getBoard()[1][0]);
    }

    @Test
    @DisplayName("Движение вниз: плитки должны объединяться")
    void moveDown_ShouldMergeTiles() {
        GameBoard board = new GameBoard(4);
        board.getBoard()[2][0] = 2;
        board.getBoard()[3][0] = 2;
        GameLogic logic = new GameLogic(board);

        logic.move(GameLogic.Direction.DOWN);
        assertEquals(4, board.getBoard()[3][0]);
        assertEquals(0, board.getBoard()[2][0]);
    }

    @Test
    @DisplayName("Движение вправо: плитки должны объединяться")
    void moveRight_ShouldMergeTiles() {
        GameBoard board = new GameBoard(4);
        board.getBoard()[0][2] = 2;
        board.getBoard()[0][3] = 2;
        GameLogic logic = new GameLogic(board);

        logic.move(GameLogic.Direction.RIGHT);
        assertEquals(4, board.getBoard()[0][3]);
        assertEquals(0, board.getBoard()[0][2]);
    }

    @Test
    @DisplayName("Движение влево: плитки должны объединяться")
    void moveLeft_ShouldMergeTiles() {
        GameBoard board = new GameBoard(4);
        board.getBoard()[0][0] = 2;
        board.getBoard()[0][1] = 2;
        GameLogic logic = new GameLogic(board);

        logic.move(GameLogic.Direction.LEFT);
        assertEquals(4, board.getBoard()[0][0]);
        assertEquals(0, board.getBoard()[0][1]);
    }


    @Test
    @DisplayName("Победа при достижении 2048")
    void hasWon_ShouldReturnTrueFor2048() {
        GameBoard board = new GameBoard(4);
        board.getBoard()[0][0] = 2048;
        GameLogic logic = new GameLogic(board);
        assertTrue(logic.hasWon());
    }

    @Test
    @DisplayName("Движение вправо: плитки должны объединиться через пустую клетку")
    void moveRight_ShouldMergeTilesOverGap() {
        GameBoard board = new GameBoard(4);
        board.getBoard()[0][0] = 2;
        board.getBoard()[0][2] = 2;
        GameLogic logic = new GameLogic(board);
        logic.move(GameLogic.Direction.RIGHT);

        assertEquals(4, board.getBoard()[0][3]);
        assertEquals(0, board.getBoard()[0][2]);
        assertEquals(0, board.getBoard()[0][1]);
        assertEquals(0, board.getBoard()[0][0]);
    }

    @Test
    @DisplayName("Движение влево: плитки должны объединиться через пустую клетку")
    void moveLeft_ShouldMergeTilesOverGap() {
        GameBoard board = new GameBoard(4);
        board.getBoard()[0][3] = 2;
        board.getBoard()[0][1] = 2; // через пустую клетку
        GameLogic logic = new GameLogic(board);
        logic.move(GameLogic.Direction.LEFT);

        assertEquals(4, board.getBoard()[0][0]);
        assertEquals(0, board.getBoard()[0][1]);
        assertEquals(0, board.getBoard()[0][2]);
        assertEquals(0, board.getBoard()[0][3]);
    }

    @Test
    @DisplayName("Движение вверх: плитки должны объединиться через пустую клетку")
    void moveUp_ShouldMergeTilesOverGap() {
        GameBoard board = new GameBoard(4);
        board.getBoard()[3][0] = 2;
        board.getBoard()[1][0] = 2; // через пустую клетку
        GameLogic logic = new GameLogic(board);
        logic.move(GameLogic.Direction.UP);

        assertEquals(4, board.getBoard()[0][0]);
        assertEquals(0, board.getBoard()[1][0]);
        assertEquals(0, board.getBoard()[2][0]);
        assertEquals(0, board.getBoard()[3][0]);
    }

    @Test
    @DisplayName("Движение вниз: плитки должны объединиться через пустую клетку")
    void moveDown_ShouldMergeTilesOverGap() {
        GameBoard board = new GameBoard(4);
        board.getBoard()[0][0] = 2;
        board.getBoard()[2][0] = 2; // через пустую клетку
        GameLogic logic = new GameLogic(board);
        logic.move(GameLogic.Direction.DOWN);

        assertEquals(4, board.getBoard()[3][0]);
        assertEquals(0, board.getBoard()[2][0]);
        assertEquals(0, board.getBoard()[1][0]);
        assertEquals(0, board.getBoard()[0][0]);
    }

    @Test
    @DisplayName("Поражение при заполненном поле без возможных ходов")
    void isGameOver_ShouldReturnTrueWhenNoMovesLeft() {
        GameBoard board = new GameBoard(4);
        int[][] testBoard = {
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2}
        };
        board.setBoard(testBoard);
        GameLogic logic = new GameLogic(board);
        assertTrue(logic.isGameOver());
    }

    @Test
    @DisplayName("Объединение трёх плиток: должно получиться [4, 2]")
    void moveLeft_ShouldMergeThreeTilesCorrectly() {
        GameBoard board = new GameBoard(4);
        board.getBoard()[0][0] = 2;
        board.getBoard()[0][1] = 2;
        board.getBoard()[0][2] = 2;
        GameLogic logic = new GameLogic(board);

        logic.move(GameLogic.Direction.LEFT);
        assertArrayEquals(new int[]{4, 2, 0, 0}, board.getBoard()[0]);
    }
}
