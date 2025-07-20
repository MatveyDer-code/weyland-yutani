package starter.metrics;

public interface AndroidMetrics {
    void incrementCompletedTasks(String author);

    int getCompletedTasks(String author);

    void incrementQueueSize();

    void decrementQueueSize();

    int getCurrentQueueSize();
}