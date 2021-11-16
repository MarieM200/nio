package nio2;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Path path = Paths.get(".");
		Dirmonitor dm = new Dirmonitor(path);
		dm.affichage2(10);
		System.out.println(dm.sizeOfFiles());
		System.out.println(dm.sizeOfFiles2(10));
		System.out.println(dm.mostRecent());
		System.out.println(dm.mostRecent2(10));
	}

}
