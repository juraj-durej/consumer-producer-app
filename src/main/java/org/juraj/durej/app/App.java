package org.juraj.durej.app;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.juraj.durej.app.commands.Command;
import org.juraj.durej.app.database.UserRepository;
import org.juraj.durej.app.producerconsumer.Consumer;
import org.juraj.durej.app.producerconsumer.Producer;

public class App {
    public static void main(String[] args) {

        BlockingQueue<Command> queue = new ArrayBlockingQueue<>(10);
        UserRepository userRepository = new UserRepository();
        AtomicBoolean isRunning = new AtomicBoolean(true);
        AtomicInteger commandCount = new AtomicInteger(0);

        Producer producer = new Producer(queue, isRunning, commandCount);
        Consumer consumer = new Consumer(queue, userRepository, isRunning, commandCount);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        try {
            // Wait for the threads to complete
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
