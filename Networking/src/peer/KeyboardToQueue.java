package peer;

import static common.UDPBase.commandPrefix;

import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;


/**
 * Thread that copies lines typed by the user into the message or command
 * queues.
 *
 * @author blad
 */
class KeyboardToQueue extends Thread {
  private final BlockingQueue<String> commandQueue;
  private final Scanner fin;
  private final InputStream in;
  private final BlockingQueue<Message> messageQueue;

  /**
   * Construct a new KeyboardToQueue instance. Set the scanner to scan the
   * keyboard and initialize the server and regular message queue values (these
   * are where server and regular messages are copied after a line is read).
   *
   * @param in
   *          Where to read information from (permits redirection to a file
   *          rather than hard coding the keyboard)
   * @param queue
   *          The regular message queue for messages to be dispatched to all
   *          members of the chat group
   * @param serverQueue
   *          The message queue for messages to be sent to the server.
   */
  public KeyboardToQueue(InputStream in,
    BlockingQueue<Message> messageQueue,
    BlockingQueue<String> commandQueue) {
    this.in = in;
    this.messageQueue = messageQueue;
    this.commandQueue = commandQueue;
    this.fin = new Scanner(in);
  }

  @Override public void run() {
    String line;
    System.out.print("> ");

    while ((line = fin.nextLine().trim()) != null) {

      try {

        if (line.startsWith(commandPrefix))
          commandQueue.put(line);
        else
          messageQueue.put(new Message(line));

      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }

      System.out.print("> ");
    }
  }
}
