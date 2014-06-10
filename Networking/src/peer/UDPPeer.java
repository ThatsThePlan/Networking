package peer;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import common.UDPBase;


/**
 * UDPPeer - A peer-to-peer, UDP (or datagram) based echo program. The
 * UDP protocol is a message-oriented, best-effort-delivery
 * protocol. The UDPPeer class's main() method begins by checking the
 * command-line for opptional command-line parameters:
 *
 * UDPPeer [--localPort <lp>] [--machine <name>] [--port <port>]
 *
 * where <lp> is the UDP port this instance of the program is to use
 * (will default to having the OS assign one). <name> is the name of
 * the machine to which this UDPPeer should connect <port> is the UDP
 * port number to which this instance of the program should address
 * messages
 *
 * This program was developed, in part, during the 14 February 2008
 * Computer Networking class. For your consideration: How could this
 * client be changed to support more than one distant client? Let us
 * assume that any message typed should go to ALL known peers and,
 * further, that any message we receive adds the sender to our list of
 * peers. What data structures would you use (and what synchronization
 * problems might there be)?
 */

public class UDPPeer extends UDPBase {

  /** the executor (controller of threads) */
  private Executor executor;

  /** local machine Internet address */
  private InetAddress localMachineAddress;
  private InetAddress distantMachineAddress;

  /** portNumber inherited from NetworkingCommon */
  /** distant machine name as a human-readable string */
  private String distantMachineName;

  /** Communication queue between the keyboard and the command
   * processor */
  private BlockingQueue<String> commandQueue; 

  /** Communication queue between the keyboard and the send port */
  private BlockingQueue<Message> messageQueue;

  /** The single socket used by this process; it is used for receiving
      from any other "calling" process. */
  private DatagramSocket socket;

  /** Construct a brand new UDPPeer object. Need to know the local
   * port number and the remote machine and port. Note that the next
   * step is to add multiple machine/port pairs */
  public UDPPeer(int localPortNumber, String machineName, int portNumber,
    int verbose) {
    super(portNumber, verbose);
    this.localPortNumber = localPortNumber;
    this.distantMachineName = machineName;
  }

  /**
   * The main program. Processed command-line arguments, creates a
   * UDPPeer object and calls run().
   *
   * @param args
   *          provided command-line arguments
   */
  public static void main(String[] args) {
    int localPort = 0;
    int port = DEFAULT_PORT_NUMBER;
    String machine = DEFAULT_MACHINE_NAME;
    int verbosity = 0;

    /*
     * Parsing parameters. argNdx will move forward across the indices;
     * remember for arguments that have their own parameters, you must
     * advance past the value for the argument too.
     */
    int argNdx = 0;

    while (argNdx < args.length) {
      String curr = args[argNdx];

      if (curr.equals(ARG_PORT)) {
        ++argNdx;

        String numberStr = args[argNdx];
        port = Integer.parseInt(numberStr);
      } else if (curr.equals(ARG_LOCAL_PORT)) {
        ++argNdx;

        String numberStr = args[argNdx];
        localPort = Integer.parseInt(numberStr);
      } else if (curr.equals(ARG_MACHINE)) {
        ++argNdx;
        machine = args[argNdx];
      } else if (curr.equals(ARG_VERBOSE)) {
        ++argNdx;

        String numberStr = args[argNdx];
        verbosity = Integer.parseInt(numberStr);
      } else {

        // if there is an unknown parameter, give usage and quit
        System.err.println("Unknown parameter \"" + curr + "\"");
        System.exit(1);
      }

      ++argNdx;
    }

    new UDPPeer(localPort, machine, port, verbosity).run();
  } // main

  /**
   * The run method; overrides the version defined in the abstract
   * base class */
  @Override 
  public void run() {
    
    // linked: use a linked list; queue: FIFO data structure 
    // blocking: enhanced to permit waiting until there is something
    // to read or room to write into the queue
    this.commandQueue = new LinkedBlockingQueue<String>();
    this.messageQueue = new LinkedBlockingQueue<Message>();



    // cached thread pool: reuse threads when possible, generate new
    // threads when necessary
    this.executor = Executors.newCachedThreadPool();

    try {
      this.localMachineAddress = InetAddress.getByName("localhost");

      this.distantMachineAddress = InetAddress.getByName(distantMachineName);
      this.socket = new DatagramSocket(localPortNumber);

      // set "global" information for the message queue (the from address)
      Message.setLocalMachine(localMachineAddress);
      Message.setLocalPort(localPortNumber);
      
      // KeyboardToQueue reads the keyboard and posts lines into
      // the message and command queues (all communication is through
      // safe queues so communication is thread safe)
      KeyboardToQueue keyboard = new KeyboardToQueue(System.in,
          messageQueue, commandQueue);

      // DatagramReceiver watches the wire; when something is ready it
      // is read and put on the screen.
      DatagramReceiver receiver = new DatagramReceiver(socket);
      
      // DatagramSender watches a message queue; when something is
      // ready it is copied to the wire.
      DatagramSender sender = new DatagramSender(socket, messageQueue, distantMachineAddress, port);

      System.out.print("UDP Socket on " + socket.getLocalPort());
      executor.execute(keyboard);
      System.out.print(".");
      executor.execute(receiver);
      System.out.print(".");
      executor.execute(sender);
      System.out.println(".");

      while (true) {}
    } catch (SocketException e) {
      System.err.println(
        "Error in establishing local or distant connection.");
      e.printStackTrace();
      System.exit(2);
    } catch (UnknownHostException e) {
      System.err.println("Unknown host " + distantMachineName);
      e.printStackTrace();
    }
  }
}
