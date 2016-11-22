public class Quote {

	private int id;
	private int book_id;
	private String quote;

	Quote(int i, int b, String q) {
		id = i;
		book_id = b;
		quote = q;
	}

	public int getId() {
		return id;
	}

	public int getBookId() {
		return book_id;
	}

	public String getQuote() {
		return quote;
	}
}
