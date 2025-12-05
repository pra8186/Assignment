package org.example;

import java.util.LinkedList;
import java.util.Queue;

/**
 * WaitingQueue that implements the wait and notify mechanism
 */
public class WaitQueue<T> {

    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    public WaitQueue(int capacity) {
        if (capacity <= 0) { // Length of Queue can not be negative
            throw new IllegalArgumentException("Capacity must be > 0");
        }
        this.capacity = capacity;
    }

    public void put(T item) throws InterruptedException {
        synchronized (this) {
            while (queue.size() == capacity) { // if the queue is full then producer can not put the data until there is space in queue
                wait();
            }
            queue.add(item); // If queue has space then put the terms into it
            notifyAll();
        }
    }

    public T take() throws InterruptedException {
        synchronized (this) {
            while (queue.isEmpty()) { // if Queue is empty then consumer can not consume until producer generates the terms
                wait();
            }
            T item = queue.poll(); // if queue has items then oull in the terms form the queue
            notifyAll();
            return item;
        }
    }

    public synchronized int size() {
        return queue.size(); //returns the size of the queue
    }
}