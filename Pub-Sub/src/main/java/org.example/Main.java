package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        long base = 10; // First Term of Geometric Progression
        int terms = 15;   //Number of terms in Geometric Progression
        int size=3; //length of the waiting queue
        WaitQueue<Long> queue = new WaitQueue<>(size);
        List<Long> destination = Collections.synchronizedList(new ArrayList<Long>());

        Thread producer = new Thread(new Producer(queue, base, terms));
        Thread consumer = new Thread(new Consumer(queue, terms, destination));

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("Received Geometric Progression: " + destination);
        }
    }
