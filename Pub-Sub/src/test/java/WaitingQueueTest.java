import org.example.WaitQueue;
import org.junit.Test;

import static org.junit.Assert.*;

public class WaitingQueueTest {

    @Test
    public void testPutAndTakeSingleThread() throws Exception {
        WaitQueue<Integer> queue = new WaitQueue<>(3);

        queue.put(10);
        queue.put(20);
        queue.put(40);
        assertEquals(3, queue.size());
        assertEquals(Integer.valueOf(10), queue.take());
        assertEquals(Integer.valueOf(20), queue.take());
        assertEquals(Integer.valueOf(40), queue.take());
        assertEquals(0, queue.size());
    }

    @Test
    public void testBlockingPutAndTake() throws Exception {
        final WaitQueue<Integer> queue = new WaitQueue<>(1);

        Thread producer = new Thread(() -> {
            try {
                queue.put(1);
                queue.put(2); // should block until consumer takes 1
            } catch (InterruptedException ignored) {
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                Thread.sleep(200); // let producer potentially block
                assertEquals(Integer.valueOf(1), queue.take());
                Thread.sleep(200);
                assertEquals(Integer.valueOf(2), queue.take());
            } catch (Exception ignored) {
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        assertEquals(0, queue.size());
    }
}