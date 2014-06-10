package common;

import java.io.PrintStream;

import util.ClientServerBase;


/**
 * A reasonable base class for UDP (and other) networking
 * classes. Encapsulates the command-line switches and some simple
 * level based logging.
 */
public abstract class UDPBase 
  extends ClientServerBase
  implements Runnable {
  /** Command-line switches */
  public final static String ARG_LOCAL_PORT = "--localport";

  /**
   * Carriage-return, line-feed character pair; add to end of every message
   */
  public final static String crlf = "\r\n";
  public final static String formatMessage = "%s" + crlf;

  public final static String commandPrefix = "/";

  public final static int bufferSize = 1024;

  /** What port number am I using? */
  protected int localPortNumber;

  /**
   * Default constructor; the log is assumed to be the standard output.
   */
  public UDPBase(int port) {
    this(port, 0);
  }

  /**
   * Default output, given verbose levels.
   */
  public UDPBase(int port, int verbose) {
    this(port, System.out, verbose);
  }

  /**
   * Construct a NetworkingBase which logs on the provide PrintStream and
   * initial level of verbosity.
   */
  public UDPBase(int port, PrintStream logStream, int verbose) {
    super(port, logStream, verbose);
  }

  /**
   * The run method is the main method of the program this is associated with.
   * Each subclass of this class should provide its own definition.
   */
  public abstract void run();

} // NetworkingBase
