package peer;

import static common.UDPBase.bufferSize;

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class DatagramReceiver extends Thread {
  byte[] receiveData;
  DatagramSocket socket;

  public DatagramReceiver(DatagramSocket socket) {
    this.socket = socket;
    receiveData = new byte[bufferSize];
  }

  @Override public void run() {
    try {
      while (true) {
        DatagramPacket packet = new DatagramPacket(receiveData,
            receiveData.length);
        socket.receive(packet);

        InetAddress senderAddress = packet.getAddress();
        int senderPort = packet.getPort();

        String sender = senderAddress.getHostName() + ":" +
          senderPort;

        String msg = new String(packet.getData(), 0, packet.getLength());
        System.out.println(sender + " - " + msg);
      }
    } catch (SocketException e) {
      // output thread closed the socket
    } catch (IOException e) {
      System.err.println(e);
    }
  }

}
