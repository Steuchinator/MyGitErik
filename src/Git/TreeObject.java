package Git;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class TreeObject {
	private String listString;
	private String SHA1str;
	private byte[] SHA1;
	
	
	public TreeObject(ArrayList<String> input) throws NoSuchAlgorithmException, FileNotFoundException
	{
		int i;
		listString="";
		for(i = 0;i < input.size() ;i++) {
		    listString += input.get(i) + "\n";
		}
//		File folder = new File("objects");
//		folder.mkdir();
		SHA1str = "";
		makeSHA1(listString);
//		System.out.println(SHA1str);
		createFile();
		
	}
	
	public String getTreeName() {
		return SHA1str;
	}
	
	public void makeSHA1(String x) throws NoSuchAlgorithmException
	{

	    MessageDigest sha1 = MessageDigest.getInstance("SHA1");
	    SHA1 = sha1.digest((x).getBytes()); 
	    for(byte b : SHA1 ) {
	    	  SHA1str += String.format("%02x",b);
	    	}
	}
	
	public void createFile() throws FileNotFoundException
	{
		Path p = Paths.get(".\\objects\\" + SHA1str);
        try {
            Files.writeString(p, listString, StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	}
	}
}


//package Git;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.PrintWriter;
//import java.math.BigInteger;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
//
//public class TreeObject {
//	ArrayList<String> data;
//	String dataSha;
//	public TreeObject(ArrayList<String> data) throws FileNotFoundException
//	{
//	 data = new ArrayList<String>();
//	 this.data = data;
//	 dataSha = encryptThisString(makeToOneString());
//	 writeDataFile();
//	}
//	
//	
//	
//	
//	public static String encryptThisString(String input)
//	{
//		try {	         
//			MessageDigest md = MessageDigest.getInstance("SHA-1");	      
//			byte[] messageDigest = md.digest(input.getBytes());	     
//			BigInteger no = new BigInteger(1, messageDigest);         
//			String hashtext = no.toString(16); 
//			while (hashtext.length() < 32) {
//				hashtext = "0" + hashtext;
//			}	       
//			return hashtext;
//		}	 
//		// For specifying wrong message digest algorithms
//		catch (NoSuchAlgorithmException e) {
//			throw new RuntimeException(e);
//		}
//	}
//	
//	private String makeToOneString() {
//		String allData = "";
//		for(int i = 0; i < data.size(); i++) {
//			allData += data.get(i) + (i<data.size()-1 ? "\n" : "");
//		}
//		return allData;
//	}
//	
//	
//	private void writeDataFile() throws FileNotFoundException {
//		File f = new File(".\\objects\\" + dataSha);
//		PrintWriter pw = new PrintWriter(f);
//		
//		for (String dataPiece : data)
//		{
//			pw.write(dataPiece);
//			
//		}
//		pw.close();
//	}
//	
//}
