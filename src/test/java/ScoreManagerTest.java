import com.example.game2048.ScoreManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreManagerTest {
    @Test
    @DisplayName("Счет должен обновляться корректно")
    void shouldUpdateCurrentScore() {
        ScoreManager manager = new ScoreManager("Player1", 4);
        manager.setCurrentScore(100);
        assertEquals(100, manager.getCurrentScore());
    }

    @Test
    @DisplayName("Рекорд должен обновляться при новом максимуме")
    void shouldUpdateBestScore() {
        ScoreManager manager = new ScoreManager("Player1", 4);
        manager.setCurrentScore(200);
        manager.setCurrentScore(150);
        assertEquals(200, manager.getBestScore()); // Лучший счет остаётся 200
    }
}
