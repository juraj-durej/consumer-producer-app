package org.juraj.durej.app.producerconsumer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.hibernate.annotations.SQLDelete;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.juraj.durej.app.commands.Command;
import org.juraj.durej.app.commands.CommandType;
import org.juraj.durej.app.database.UserRepository;
import org.juraj.durej.app.models.User;

class ProducerTest {

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

  @AfterEach
  void afterEach(){
    userRepository.deleteAllUsers();
  }

  @Test
  void publishCommandShouldPublishCommandIntoQueueSuccessfully() throws InterruptedException {

    Producer producer = new Producer(queue, isRunning, commandCount);
    producer.publishCommand(CommandType.PRINT_ALL, null);

    assertEquals(1, queue.size());
    assertEquals(1, commandCount.get());
  }

  @Test
  void publishCommandShouldPublishCommandAddIntoQueueSuccessfully() throws InterruptedException {

    Producer producer = new Producer(queue, isRunning, commandCount);
    User user = new User(1, "a1", "Robert");
    producer.publishCommand(CommandType.ADD, user);

    assertEquals(1, queue.size());
    assertEquals(1, commandCount.get());
  }
}
