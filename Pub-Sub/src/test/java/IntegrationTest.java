import org.example.Consumer;
import org.example.Producer;
import org.example.WaitQueue;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
    @Test
    public void testGeometricProgressionGeneration() throws Exception {
        long base = 2;
        int terms = 6;

        WaitQueue<Long> queue = new WaitQueue<>(2);
        List<Long> destination = Collections.synchronizedList(new ArrayList<Long>());

        Thread producer = new Thread(new Producer(queue, base, terms));
        Thread consumer = new Thread(new Consumer(queue, terms, destination));

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        assertEquals(terms, destination.size());

        long expected = base;
        for (int i = 0; i < terms; i++) {
            assertEquals(Long.valueOf(expected), destination.get(i));
            expected *= base; // expected: 2, 4, 8, 16,32 ,64
        }

        assertEquals(0, queue.size());
    }

    @Test
    public void testWaitNotifyMechanismWithSmallQueueSize() throws Exception {
        long base = 2;
        int terms = 10;

        WaitQueue<Long> queue = new WaitQueue<>(1);
        List<Long> destination = Collections.synchronizedList(new ArrayList<Long>());

        Thread producer = new Thread(new Producer(queue, base, terms));
        Thread consumer = new Thread(new Consumer(queue, terms, destination));

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        assertEquals(terms, destination.size());

        long expected = base;
        for (int i = 0; i < terms; i++) {
            assertEquals(Long.valueOf(expected), destination.get(i));
            expected *= base; // expected : 2, 4, 8, 16,32 ,64 .....
        }
    }
}
