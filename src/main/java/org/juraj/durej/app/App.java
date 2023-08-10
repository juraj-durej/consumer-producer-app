package org.juraj.durej.app;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.juraj.durej.app.commands.Command;
import org.juraj.durej.app.database.UserRepository;
import org.juraj.durej.app.producerconsumer.Consumer;
import org.juraj.durej.app.producerconsumer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

  private static final Logger log = LoggerFactory.getLogger(App.class);

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
      producerThread.join();
      consumerThread.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error(e.getMessage());
    }
  }
}
