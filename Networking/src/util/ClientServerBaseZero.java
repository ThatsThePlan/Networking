package util;

/**
 * Base class for FirstClient and FirstServer. This permits them to
 * share various constant definitions.
 *
 *  An "abstract" class is a class that defines some method(s) WITHOUT
 *  providing definitions. An abstract class CANNOT be directly
 *  instantiated (what does that mean?); subclasses must either
 *  be abstract too -or- define all abstract methods.
 *
 *  An abstract class is, conceptually, somewhere between a standard
 *  Java class and a Java interface.
 */
public abstract class ClientServerBaseZero {

  /** Default port number; used if none is provided. */
  public final static int DEFAULT_PORT_NUMBER = 3939;

  /** Default machine name is the local machine; used if none provided. */
  public final static String DEFAULT_MACHINE_NAME = "localhost";

  /** Command-line switches */
  public final static String ARG_PORT = "--port";
  public final static String ARG_MACHINE = "--machine";

  /** Message op-codes */
  public final static String MSG_HELLO = "Hello";
  public final static String MSG_GOODBYE = "Goodbye";

  /** Port number where the server will be listening */
  protected int portNumber;

  /**
   * The meat of the class. The main program typically starts by
   * processing the command-line and then instantiates an object
   * extending ClientServerBase(Zero) and calls run.
   */
  public abstract void run();
}
