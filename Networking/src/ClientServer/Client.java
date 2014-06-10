package ClientServer;
//Client for String reverse Server
import java.io.*;
import java.net.*;

public class Client {
	
	public static void main(String[] args) throws Exception{
		//Create a client from the new client class
		Client C = new Client();
		//Run dat client
		C.run();
		
	}
	//Run method for the client Sets up a Socket to spit information to and then Sends the String that are input.
	public void run() throws Exception{
		Socket skt = new Socket("localhost", 9999);
		PrintStream ps = new PrintStream(skt.getOutputStream());
		ps.println("hello server");
		
	}

}
