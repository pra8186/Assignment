package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ProducerConsumer.class, args);

    }
}
