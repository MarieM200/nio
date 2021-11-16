package nio2;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.NonReadableChannelException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;

public class Dirmonitor {
	private Path path;
	
	public Dirmonitor(Path p) throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		this.path = p;
		if (!Files.isReadable(this.path))
	        throw new NonReadableChannelException();
		if(!Files.isDirectory(this.path)) {
			throw new FileNotFoundException();
		}
	}
	
	public void applyAction(Path path, MyAction action, int n) throws IOException{
		if (Files.size(path)>= n) action.perform(path);
	}
	
	public void affichage() throws IOException {
		DirectoryStream<Path> paths = Files.newDirectoryStream(this.path, new DirectoryStream.Filter<Path>(){
			public boolean accept(Path entry) throws IOException {
				// TODO Auto-generated method stub
				return (Files.size(entry)>= 10);
			}
			
		});
		//DirectoryStream<Path> paths = Files.newDirectoryStream(this.path, new PrefixFilter(10));
		for(Path p : paths) {
			System.out.println(p);
		}
	}
	
	public void affichage2(int n) throws IOException {
		DirectoryStream<Path> paths = Files.newDirectoryStream(this.path);
		for(Path p : paths) {
			this.applyAction(p, new MyAction() {
				public void perform(Path path) throws IOException {
					// TODO Auto-generated method stub
					System.out.println(path);
				}
				public Object getInfo() {
					// TODO Auto-generated method stub
					return 0;
				}
			}, n);
		}
	}
	
	public long sizeOfFiles() throws IOException {
		long size = 0;
		DirectoryStream<Path> paths = Files.newDirectoryStream(this.path);
		for(Path p : paths) {
			size = size + Files.size(p);
		}
		return size;
	}
	
	public long sizeOfFiles2(int n) throws IOException {
		MyAction action = new MyAction() {
			long size;
			
			public void perform(Path path) throws IOException {
				// TODO Auto-generated method stub
				size = size + Files.size(path);
			}
			
			public Long getInfo() {
				return size;
			}
		};
		DirectoryStream<Path> paths = Files.newDirectoryStream(this.path);
		for(Path p : paths) {
			this.applyAction(p, action, n);
		}
		return (Long) action.getInfo();
	}
	
	public Path mostRecent() throws IOException {
		BasicFileAttributes attr;
		Path path = Paths.get(".");
		long time = 0, temp;
		DirectoryStream<Path> paths = Files.newDirectoryStream(this.path);
		for(Path p : paths) {
			attr = Files.readAttributes(p, BasicFileAttributes.class);
			FileTime fileTime = attr.lastModifiedTime();
			temp = fileTime.toMillis();
			if(temp>time) {
				path = p;
				time = temp;
			}
		}
		return path;
	}
	
	public Path mostRecent2(int n) throws IOException {
		MyAction action = new MyAction() {
			BasicFileAttributes attr;
			Path p = Paths.get(".");
			long time = 0, temp;
			
			public void perform(Path path) throws IOException {
				// TODO Auto-generated method stub
				attr = Files.readAttributes(path, BasicFileAttributes.class);
				FileTime fileTime = attr.lastModifiedTime();
				temp = fileTime.toMillis();
				if(temp>time) {
					p = path;
					time = temp;
				}
			}
			
			public Path getInfo() {
				return p;
			}
		};
		DirectoryStream<Path> paths = Files.newDirectoryStream(this.path);
		for(Path p : paths) {
			this.applyAction(p, action, n);
		}
		return (Path) action.getInfo();
	}
	
	/*public class PrefixFilter implements DirectoryStream.Filter<Path>{

		private long n;
		
		public PrefixFilter(long n) {
			// TODO Auto-generated constructor stub
			this.n = n;
		}
		
		public boolean accept(Path entry) throws IOException {
			// TODO Auto-generated method stub
			return (Files.size(entry)>= this.n);
		}
		
	}*/
}
