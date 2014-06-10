package ClientServer;
//Reverse Server( For reversing!)
import java.io.*;
import java.net.*;

public class Server {
	
	public static void main(String[] args) throws Exception{
		//Creating a Server from the server class just created
		Server S = new Server();
		//Run server command to start her up
		S.run();
	}

	//The good old run method
	public void run() throws Exception{
		
		//Server socket for the server to watch port 9999 and wait for input
		ServerSocket ss = new ServerSocket(9999);
		//Socket for accepting the input from the client
		Socket ss_accept = ss.accept();
		String temp,reverse = null;
		//Our trusty Buffered Reader for storing Strings sent to the server from the client
		BufferedReader BR = new BufferedReader(new InputStreamReader(ss_accept.getInputStream()));
		
		while(true){
			temp = BR.readLine();
			if(temp.equals("goodbye"))
				break;
				
			//Taking the string sent from the client and reversing it to be sent back
			reverse = new StringBuffer(temp).reverse().toString();	
			//Spit that string out reversed please
			System.out.println("Server: "+reverse);
		}
		ss.close();
	}
}
