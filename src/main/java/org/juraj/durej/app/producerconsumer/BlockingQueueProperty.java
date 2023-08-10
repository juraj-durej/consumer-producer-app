package org.juraj.durej.app.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.juraj.durej.app.commands.Command;

public abstract class BlockingQueueProperty implements Runnable{

  protected BlockingQueue<Command> queue;
  protected AtomicBoolean isRunning;
  protected AtomicInteger commandCount;
}
