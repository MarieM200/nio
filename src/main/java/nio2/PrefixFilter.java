package nio2;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class PrefixFilter implements DirectoryStream.Filter<Path>{

	private long n;
	
	public PrefixFilter(long n) {
		// TODO Auto-generated constructor stub
		this.n = n;
	}
	
	public boolean accept(Path entry) throws IOException {
		// TODO Auto-generated method stub
		return (Files.size(entry)>= this.n);
	}
	
}
