package org.juraj.durej.app.producerconsumer;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.juraj.durej.app.commands.Command;
import org.juraj.durej.app.commands.CommandType;
import org.juraj.durej.app.database.UserRepository;
import org.juraj.durej.app.models.User;

class ConsumerTest {
  BlockingQueue<Command> queue;
  AtomicBoolean isRunning;
  AtomicInteger commandCount;
  UserRepository userRepository = new UserRepository();

  @BeforeEach
  void setUp() {
    queue = new ArrayBlockingQueue<>(10);
    isRunning = new AtomicBoolean(true);
    commandCount = new AtomicInteger(0);
  }

  @Test
  void processCommandShouldConsumeAddCommandSuccessfully() throws InterruptedException {
    Producer producer = new Producer(queue, isRunning, commandCount);
    User user = new User(1, "a1", "Robert");
    producer.publishCommand(CommandType.ADD, user);

    Consumer consumer = new Consumer(queue, userRepository, isRunning, commandCount);
    consumer.processCommand(queue.take());

    List<User> users = userRepository.getUsers();

    assertEquals(1, users.size());
    assertEquals(0, queue.size());
    assertEquals(0, commandCount.get());
  }

  @Test
  void processCommandShouldConsumeDeleteCommandSuccessfully() throws InterruptedException {
    Producer producer = new Producer(queue, isRunning, commandCount);
    User user = new User(1, "a1", "Robert");

    producer.publishCommand(CommandType.ADD, user);
    producer.publishCommand(CommandType.DELETE_ALL, null);

    Consumer consumer = new Consumer(queue, userRepository, isRunning, commandCount);
    consumer.processCommand(queue.take());
    consumer.processCommand(queue.take());

    List<User> users = userRepository.getUsers();

    assertEquals(0, users.size());
    assertEquals(0, queue.size());
    assertEquals(0, commandCount.get());
  }
}
