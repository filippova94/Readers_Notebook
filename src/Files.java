import java.io.File;

public class Files {

	private int id;
	private int book_id;
	private File file;

	Files(int i, int b, File f) {
		id = i;
		book_id = b;
		file = f;
	}

	public int getId() {
		return id;
	}

	public int getBookId() {
		return book_id;
	}

	public File getFile() {
		return file;
	}
}
