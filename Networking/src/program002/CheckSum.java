package program002;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CheckSum {
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException{
		String path;
		
		if( args.length == 0)
			System.out.println("No file entered.");		
		//If one command line arg is given returns files hex value or file not found error
		if(args.length == 1){
			path = args[0];
			System.out.print(checkSum(path));
			System.exit(0);
		}	
		//Checks file hex and given hex to see if equal
		if(args.length == 2){
			path = args[0];
			//Exit 0 on comparison match
			if(checkSum(path) == args[1]){
				System.exit(0);
			}
			//Exit 1 on comparison fail
			else
				System.exit(1);
		}
		//Exit 2 if params exceed 2
		else
			System.exit(2);
	}
	
	
	//method for file conversion to HEX
	static String checkSum(String path) throws NoSuchAlgorithmException, IOException{
		try{ 
			MessageDigest md = MessageDigest.getInstance("SHA1");
			FileInputStream fis = new FileInputStream(path);
			byte[] dataBytes = new byte[1024];
	 
			int nread = 0; 
	 
			while ((nread = fis.read(dataBytes)) != -1) {
				md.update(dataBytes, 0, nread);
			}
	 
			byte[] mdbytes = md.digest();
	 
			//convert the byte to hex format
			StringBuffer sb = new StringBuffer("");
			for (int i = 0; i < mdbytes.length; i++) {
				sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			fis.close();
			return sb.toString();
		}
		catch (FileNotFoundException e){
			System.err.println("FileNotFoundException: " + e.getMessage());
			System.exit(3);
			return null;
		}
	}
}