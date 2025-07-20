package metrics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import metrics.AndroidMetricsImpl;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AndroidMetricsImplTest {

    private AndroidMetricsImpl metrics;

    @BeforeEach
    void setUp() {
        metrics = new AndroidMetricsImpl();
    }

    @Test
    void initialQueueSizeShouldBeZero() {
        assertEquals(0, metrics.getCurrentQueueSize());
    }

    @Test
    void shouldRecordCompletedTasksPerAuthor() {
        metrics.incrementCompletedTasks("Ripley");
        metrics.incrementCompletedTasks("Ripley");
        metrics.incrementCompletedTasks("Ash");

        Map<String, Integer> stats = metrics.getCompletedCommandsPerAuthor();

        assertEquals(2, stats.get("Ripley"));
        assertEquals(1, stats.get("Ash"));
    }
}