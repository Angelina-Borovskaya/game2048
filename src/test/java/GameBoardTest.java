import com.example.game2048.GameBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameBoardTest {
    @Test
    @DisplayName("Доска должна инициализироваться с двумя плитами")
    void shouldInitializeWithTwoTiles() {
        GameBoard board = new GameBoard(4);
        int nonZeroTiles = (int) Arrays.stream(board.getBoard())
                .flatMapToInt(Arrays::stream)
                .filter(value -> value != 0)
                .count();
        assertEquals(2, nonZeroTiles);
    }

    @Test
    @DisplayName("Добавление плитки на пустую клетку")
    void shouldAddTileToEmptyCell() {
        GameBoard board = new GameBoard(4);

        // Очистить всё поле
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board.getBoard()[i][j] = 0;
            }
        }

        board.addRandomTile();

        int nonEmptyCount = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board.getBoard()[i][j] != 0) {
                    nonEmptyCount++;
                    assertTrue(board.getBoard()[i][j] == 2 || board.getBoard()[i][j] == 4);
                }
            }
        }

        assertEquals(1, nonEmptyCount);
    }
}
