package org.juraj.durej.app.producerconsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.juraj.durej.app.commands.Command;
import org.juraj.durej.app.commands.CommandType;
import org.juraj.durej.app.models.User;

class ProducerTest {

  BlockingQueue<Command> queue;
  AtomicBoolean isRunning;
  AtomicInteger commandCount;

  @BeforeEach
  void setUp() {
    queue = new ArrayBlockingQueue<>(10);
    isRunning = new AtomicBoolean(true);
    commandCount = new AtomicInteger(0);
  }

  @Test
  void publishCommandShouldPublishCommandIntoQueueSuccessfully() throws InterruptedException {
    // given
    Producer producer = new Producer(queue, isRunning, commandCount);

    // when
    producer.publishCommand(CommandType.PRINT_ALL, null);

    // then
    assertEquals(1, queue.size());
    assertEquals(1, commandCount.get());
  }

  @Test
  void publishCommandShouldPublishCommandAddIntoQueueSuccessfully() throws InterruptedException {
    // given
    Producer producer = new Producer(queue, isRunning, commandCount);
    User user = new User(1, "a1", "Robert");

    // when
    producer.publishCommand(CommandType.ADD, user);

    // then
    assertEquals(1, queue.size());
    assertEquals(1, commandCount.get());
  }
}
