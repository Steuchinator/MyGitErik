package Git;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

public class GitOverlord {
	Index index = new Index();
	Commit current;
	File head;
	
	public GitOverlord() throws IOException {
		index.init();
		head = new File("HEAD");
		MrTopicsMan.writeTo(head, "");
	}
	
	public void addBlob(String name) {
		index.add(name);
	}
	
	public void makeCommit(String author, String summary) throws Exception {
		current = new Commit(author,summary);
		index.clear();
	}
}
