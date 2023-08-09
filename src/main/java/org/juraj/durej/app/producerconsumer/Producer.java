package org.juraj.durej.app.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.juraj.durej.app.commands.Command;
import org.juraj.durej.app.commands.CommandType;
import org.juraj.durej.app.models.User;

public class Producer implements Runnable {
  private BlockingQueue<Command> queue;
  private AtomicBoolean isRunning;
  private AtomicInteger commandCount;

  public Producer(BlockingQueue<Command> queue, AtomicBoolean isRunning, AtomicInteger commandCount) {
    this.queue = queue;
    this.isRunning = isRunning;
    this.commandCount = commandCount;
  }

  void publishCommand(CommandType commandType, User user) throws InterruptedException{
    queue.put(new Command(commandType, user));
    commandCount.getAndAdd(1);
  }

  @Override
  public void run() {
    try {
      User user1 = new User(1, "a1", "Robert");
      User user2 = new User(2, "a2", "Martin");

      publishCommand(CommandType.ADD, user1);
      publishCommand(CommandType.ADD, user2);
      publishCommand(CommandType.PRINT_ALL, null);
      publishCommand(CommandType.DELETE_ALL, null);
      publishCommand(CommandType.PRINT_ALL, null);

      isRunning.set(false);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
