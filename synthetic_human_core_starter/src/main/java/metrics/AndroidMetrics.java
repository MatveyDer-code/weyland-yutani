package metrics;

import java.util.Map;

public interface AndroidMetrics {
    int getCurrentQueueSize();
    Map<String, Integer> getCompletedCommandsPerAuthor();
}