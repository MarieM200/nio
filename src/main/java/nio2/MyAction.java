package nio2;

import java.io.IOException;
import java.nio.file.Path;

public interface MyAction {
	long size = 0;

	void perform(Path p) throws IOException;

	Object getInfo();
}
