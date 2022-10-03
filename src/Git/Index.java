package Git;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Index {
    final String INDEX_PATH = "index";
    HashMap<String, String> map;
    boolean haveReadFile = false;

    public Index() {
        map = new HashMap<>();
    
    }
    
    public void init() {
    	try {
	        new File("objects").mkdirs();
	        File f = new File(INDEX_PATH);
	        if(f.exists() && !f.isDirectory()) {
	            getMapFromFile(f);
	        } else {
	        	f.createNewFile();
	        }
    	} catch (Exception e) {
    		System.out.println(e.toString());
    	}
    }

    public void getMapFromFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.equals("")) continue;
                String hash = line.substring(line.length()-39);
                String filename = line.substring(0, line.length()-42);
                System.out.println(hash);
                System.out.println(filename);
                map.put(filename, hash);
                System.out.println(map.get(filename));
            }
            haveReadFile = true;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void setFile() {
        try {
        	new File(INDEX_PATH).delete();
            PrintWriter writer = new PrintWriter(INDEX_PATH);
            
            TreeMap<String, String> sorted = new TreeMap<>();
            sorted.putAll(map);
            
            for (Map.Entry<String, String> entry : sorted.entrySet()) {
                writer.println(entry.getKey() + " : " + entry.getValue());  
	        }
            
            writer.close();
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    public void add(String filename) {
        Blob b = new Blob(filename);
        String hash = b.hash;
        map.put(filename, hash);
        setFile();
    }

    public void remove(String filename) {
        if(!map.containsKey(filename)) {
        	System.out.println("No key found for " + filename);
        	System.out.println("Have read file: " + haveReadFile);
        	return;
        };

        String path = ".\\objects\\" + map.get(filename);
        System.out.println(path);
        File file = new File(path);

        if (file.delete()) {
            System.out.println("File deleted successfully");
        }
        else {
            System.out.println("Failed to delete the file");
        }

        map.remove(filename);
        setFile();
    }
}
