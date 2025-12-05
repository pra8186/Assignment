package org.example;

/**
Producer Generated the terms in the geometric progression in series for the given starting number 'base' and number of terms in the progression
 to be 'terms'
 */
public class Producer implements Runnable {

    private final WaitQueue<Long> queue;
    private final long base; //final so once initialized can not be changed
    private final int terms; //final so once initialized can not be changed

    public Producer(WaitQueue<Long> queue, long base, int terms) {
        this.queue = queue;
        this.base = base;
        this.terms = terms;
    }

    @Override
    public void run() {
        try {
            long value = base;
            for (int i = 1; i <= terms; i++) {
                queue.put(value); //puts the data into the queue
                value *= base;  // next GP term = perv_term * base
            }
        } catch (InterruptedException e) { // Exception Generated
            Thread.currentThread().interrupt();
        }
    }
}