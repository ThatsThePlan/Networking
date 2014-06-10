package peer;

import static common.UDPBase.crlf;

import java.net.InetAddress;

/**
 * A message to be sent to another machine. The UDP protocol requires an
 * address and a port to which it is being sent.
 */
public class Message {
  /**
   * the initiating machine and port number; static because it is the
   * same across all messages
   */
  static InetAddress localMachine = null;
  static int localPort = 0;

  /**
   * the distant machine and port; since this machine might be contacted
   * from more than one other machine, this is tied to each message
   * individually
   */
  InetAddress distantMachine;
  int distantPort;

  /** the content of the message */
  String msg;

  /** Construct an un-addressed message */
  public Message(String msg) {
    this(msg, null, 0);
  }

  /** Construct a message for the given address */
  public Message(String msg, InetAddress distantMachine, int distantPort) {
    this.msg = msg;
    this.distantMachine = distantMachine;
    this.distantPort = distantPort;
  }

  /**
   * @return the currentMachine
   */
  public static InetAddress getLocalMachine() {
    return localMachine;
  }

  /**
   * @return the currentPort
   */
  public static int getLocalPort() {
    return localPort;
  }

  /**
   * @param localMachine
   *          the address to set
   */
  public static void setLocalMachine(InetAddress localMachine) {
    Message.localMachine = localMachine;
  }

  /**
   * @param localPort
   *          the port to set
   */
  public static void setLocalPort(int localPort) {
    Message.localPort = localPort;
  }

  /**
   * @return the distantMachine
   */
  public InetAddress getDistantMachine() {
    return distantMachine;
  }

  /**
   * @return the message content
   */
  public String getMsg() {
    return msg;
  }

  /**
   * @return the distantPort
   */
  public int getDistantPort() {
    return distantPort;
  }

  /**
   * @param distantMachine
   *          the address to set
   */
  public void setDistantMachine(InetAddress distantMachine) {
    this.distantMachine = distantMachine;
  }

  /**
   * @param msg
   *          the message to set
   */
  public void setMsg(String msg) {
    this.msg = msg;
  }

  /**
   * @param distantPort
   *          the distantPort to set
   */
  public void setDistantPort(int distantPort) {
    this.distantPort = distantPort;
  }
}
