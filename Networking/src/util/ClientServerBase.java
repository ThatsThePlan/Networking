package util;

import java.io.PrintStream;


/**
 * A shared base class for a client/server pair. Defines standard
 * command-line arguments, standard state (port number and verbose
 * level) as well as utility methods. Sharing this file means the
 * client and server share the same default values (for port number,
 * for example). The command-line switches are the union of the
 * command-line switches of the two programs; here ARG_PORT and
 * ARG_VERBOSE are shared.
 */

public abstract class ClientServerBase {

  /** Default port number; used if none is provided. */
  public final static int DEFAULT_PORT_NUMBER = 8909;

  /** Default machine name is the local machine; used if none provided. */
  public final static String DEFAULT_MACHINE_NAME = "localhost";

  /** Default verbose level */
  public final static int DEFAULT_VERBOSE = 0;

  /** Command-line switches */
  public final static String ARG_PORT = "--port";
  public final static String ARG_MACHINE = "--machine";
  public final static String ARG_VERBOSE = "--verbose";

  /** Port number where the server will be listening */
  protected int port;

  /** The log stream */
  private PrintStream logStream;

  /** Verbose output flag. */
  private int verbose;

  /** "Default" constructor (there is no actual default constructor
   * for this class); set the port to the given value and use default
   * values for the logStream and verbose. */
  public ClientServerBase(int port) {
    this(port, System.out, DEFAULT_VERBOSE);
  }

  /** Construct a ClientSeverBase with a default log stream and the
   * given level of verbosity. */
  public ClientServerBase(int port, int verbose) {
    this(port, System.out, verbose);
  }

  /** Construct a ClientServerBase with a default verbosity and the
   * given stream for logging. */
  public ClientServerBase(int port, PrintStream logStream) {
    this(port, logStream, DEFAULT_VERBOSE);
  }

  /** The "real" constructor: all other constructors call this
   * constructor after setting up the appropriate parameter
   * values. This makes sure that there is one canonical place to
   * put all initialization for ClientServerBase (here)
  */
  public ClientServerBase(int port, PrintStream logStream, int verbose) {
    this.port = port;
    this.verbose = verbose;
    this.logStream = logStream;
  }

  /** The run method is the main method of the program this is
   * associated with. Each subclass of this class should provide its
   * own definition. */
  public abstract void run();

  /** returns whether or not this ClientServerBase is in verbose mode */
  public int getVerbose() {
    return verbose;
  }

  /** increment the verbosity level */
  public void incrementVerbose() {
    ++verbose;
  }

  /** Print the string to the log stream */
  public void print(int level, String msg) {

    if (getVerbose() >= level)
      logStream.print(msg);
  }

  /** Print the string to the log stream and start a new line. */
  public void println(int level, String msg) {

    if (getVerbose() >= level)
      logStream.println(msg);
  }

  /** set the verbose flag to the provided value */
  public void setVerbose(int verbose) {
    this.verbose = verbose;
  }

} // ClientServerBase
