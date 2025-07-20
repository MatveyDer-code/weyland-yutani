package metrics;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AndroidMetricsImpl {
    private final AtomicInteger currentQueueSize = new AtomicInteger(0);
    private final ConcurrentHashMap<String, AtomicInteger> completedTasksPerAuthor = new ConcurrentHashMap<>();

    public int getCurrentQueueSize() {
        return currentQueueSize.get();
    }

    public void incrementQueueSize() {
        currentQueueSize.incrementAndGet();
    }

    // Метод для уменьшения текущего количества задач в очереди
    public void decrementQueueSize() {
        currentQueueSize.decrementAndGet();
    }

    // Увеличивает счетчик выполненных задач для автора
    public void incrementCompletedTasks(String author) {
        completedTasksPerAuthor
                .computeIfAbsent(author, a -> new AtomicInteger(0))
                .incrementAndGet();
    }

    public Map<String, Integer> getCompletedCommandsPerAuthor() {
        // Преобразуем AtomicInteger в Integer для удобства
        Map<String, Integer> result = new ConcurrentHashMap<>();
        completedTasksPerAuthor.forEach((key, value) -> result.put(key, value.get()));
        return result;
    }
}