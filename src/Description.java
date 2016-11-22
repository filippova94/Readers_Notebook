public class Description {

	private int book_id;
	private String text;

	Description(int id, String t) {
		book_id = id;
		text = t;
	}

	public int getBookId() {
		return book_id;
	}

	public String getText() {
		return text;
	}
}
