package metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AndroidMetricsImplTest {

    private AndroidMetricsImpl metrics;
    private MeterRegistry meterRegistry;

    @BeforeEach
    void setUp() {
        meterRegistry = new SimpleMeterRegistry();
        metrics = new AndroidMetricsImpl(meterRegistry);
    }

    @Test
    void initialQueueSizeShouldBeZero() {
        assertEquals(0, metrics.getCurrentQueueSize());
        // Проверяем, что Gauge отражает это значение
        Double gaugeValue = meterRegistry.get("android.queue.size").gauge().value();
        assertEquals(0.0, gaugeValue);
    }

    @Test
    void shouldRecordCompletedTasksPerAuthor() {
        metrics.incrementCompletedTasks("Ripley");
        metrics.incrementCompletedTasks("Ripley");
        metrics.incrementCompletedTasks("Ash");

        // Проверяем счётчик для Ripley
        Counter ripleyCounter = meterRegistry.get("android.commands.completed").tag("author", "Ripley").counter();
        assertNotNull(ripleyCounter);
        assertEquals(2.0, ripleyCounter.count());

        // Проверяем счётчик для Ash
        Counter ashCounter = meterRegistry.get("android.commands.completed").tag("author", "Ash").counter();
        assertNotNull(ashCounter);
        assertEquals(1.0, ashCounter.count());
    }

    @Test
    void shouldIncrementAndDecrementQueueSize() {
        metrics.incrementQueueSize();
        metrics.incrementQueueSize();
        assertEquals(2, metrics.getCurrentQueueSize());
        Double gaugeValue = meterRegistry.get("android.queue.size").gauge().value();
        assertEquals(2.0, gaugeValue);

        metrics.decrementQueueSize();
        assertEquals(1, metrics.getCurrentQueueSize());
        gaugeValue = meterRegistry.get("android.queue.size").gauge().value();
        assertEquals(1.0, gaugeValue);
    }
}