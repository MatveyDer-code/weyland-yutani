package metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AndroidMetricsImpl implements AndroidMetrics {
    private final MeterRegistry meterRegistry;

    private final Map<String, Counter> completedTasksCounters = new ConcurrentHashMap<>();

    private final AtomicInteger queueSize = new AtomicInteger(0);

    public AndroidMetricsImpl(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;

        Gauge.builder("android.queue.size", queueSize, AtomicInteger::get)
                .register(meterRegistry);
    }

    @Override
    public void incrementCompletedTasks(String author) {
        completedTasksCounters
                .computeIfAbsent(author, a ->
                        Counter.builder("android.commands.completed")
                                .tag("author", a)
                                .register(meterRegistry))
                .increment();
    }

    @Override
    public int getCompletedTasks(String author) {
        Counter counter = completedTasksCounters.get(author);
        return counter == null ? 0 : (int) counter.count();
    }

    @Override
    public void incrementQueueSize() {
        queueSize.incrementAndGet();
    }

    @Override
    public void decrementQueueSize() {
        queueSize.decrementAndGet();
    }

    @Override
    public int getCurrentQueueSize() {
        return queueSize.get();
    }
}