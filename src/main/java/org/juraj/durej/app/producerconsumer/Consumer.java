package org.juraj.durej.app.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.juraj.durej.app.commands.Command;
import org.juraj.durej.app.database.UserRepository;

public class Consumer implements Runnable {
  private BlockingQueue<Command> queue;
  private UserRepository userRepository;
  private AtomicBoolean isRunning;
  private AtomicInteger commandCount;

  public Consumer(BlockingQueue<Command> queue, UserRepository userRepository, AtomicBoolean isRunning, AtomicInteger commandCount) {
    this.queue = queue;
    this.userRepository = userRepository;
    this.isRunning = isRunning;
    this.commandCount = commandCount;
  }

  @Override
  public void run() {
    while (isRunning.get() || commandCount.get() > 0) {
      try {
        Command command = queue.take();

        switch (command.getType()) {
          case ADD -> userRepository.addUser(command.getUser());
          case PRINT_ALL -> System.out.println(userRepository.getUsers());
          case DELETE_ALL -> userRepository.deleteAllUsers();
        }

        commandCount.decrementAndGet();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }
}
