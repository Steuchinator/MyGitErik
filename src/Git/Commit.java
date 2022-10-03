package Git;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;  

public class Commit {
	Commit parent;
	Commit child;
	
	TreeObject pTree;
	String summary, author, date;
	
	public Commit(String _summary, String _author, Commit _parent) throws Exception {
		summary = _summary;
		author = _author;
		parent = _parent;
		if (parent!=null) {
			parent.setChild(this);
			parent.writeFile();
		}
		
		makeTree();
		writeFile();
		File file = new File("HEAD");
		MrTopicsMan.writeTo(file, this.createMySHA());
	}
	
	public void setChild(Commit input) {
		child = input;
	}
	
	public void makeTree() throws NoSuchAlgorithmException, IOException {
		ArrayList<String> strList = new ArrayList<String>();
		String str = "";
		String temp = "";
		File f = new File("index");
		Scanner scanner = new Scanner(f);
		while(scanner.hasNext()) {
			str = scanner.nextLine();
			if(str.substring(0,4)=="blob")
				temp+="blob";
			else
				temp+="tree";	
			
			temp+=str.substring(str.indexOf(" : "));
			temp+=" "+str.substring(0,str.indexOf(" : "));
			strList.add(temp);
			
			temp = "";
			str = "";
		}
		pTree = new TreeObject(strList);
		MrTopicsMan.writeTo(f, "");
		
		//System.out.println("TEMP: " + temp);
	}
	
    public String generateSHAfromString(String input) {
        try {
            // getInstance() method is called with algorithm SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        } catch(Exception e) {
        	System.out.println(e.toString());
        	return null;
        }
    }
    
    public String getDate() {
    	Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(date);  
        return strDate;
        
    }
    
    public String createMySHA() {
    	return generateSHAfromString(pTree.getTreeName() + summary);
    }
    
    public void writeFile() throws Exception {
    	String sha = ".\\objects\\" + createMySHA();
    	PrintWriter writer = new PrintWriter(sha);
		writer.println(pTree.getTreeName());
		writer.println(parent == null ? "null" : parent.createMySHA());
		writer.println(child == null ? "null" : child.createMySHA());
		writer.println(author);
		writer.println(getDate());
		writer.println(summary);
		
		writer.close();
    }
    
    
}
