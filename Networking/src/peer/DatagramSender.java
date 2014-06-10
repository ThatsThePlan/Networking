package peer;

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.util.concurrent.BlockingQueue;

public class DatagramSender extends Thread {

  private InetAddress distantMachine;
  private int distantPort;

  private BlockingQueue<Message> messageQueue;
  private byte[] sendData;
  private DatagramSocket socket;

  public DatagramSender(DatagramSocket socket,
      BlockingQueue<Message> messageQueue, InetAddress distantMachine,
      int distantPort) {
    this.messageQueue = messageQueue;
    this.socket = socket;
    this.distantMachine = distantMachine;
    this.distantPort = distantPort;
  }

  @Override
  public void run() {
    while (true) {
      try {
        Message message = messageQueue.take();
        sendData = message.getMsg().getBytes();

        DatagramPacket packet = new DatagramPacket(sendData,
            sendData.length, distantMachine, distantPort);
        socket.send(packet);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }
}
