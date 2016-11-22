public class Book {

	private int id;
	private String name;
	private String authtor;
	private String genre;
	private int status;
	private String language;
	private int current_page;

	Book(int i, String n, String a, String g, int s, String l, int c) {
		id = i;
		name = n;
		authtor = a;
		genre = g;
		status = s;
		language = l;
		current_page = c;
	}

	Book(String n, String a, String g, int s, String l, int c) {
		name = n;
		authtor = a;
		genre = g;
		language = l;
		status = s;
		current_page = c;
	}

	public void print() {
		System.out.println("Name: " + name + "\nAuthtor: " + authtor
				+ "\nGenre: " + genre + "\nStatus: " + status + "\nLanguage: "
				+ language + "\nCurrent_page: " + current_page + "\n");
	}

	public String getName() {
		return name;
	}

	public String getAuthtor() {
		return authtor;
	}

	public int getStatus() {
		return status;
	}

	public String getGenre() {
		return genre;
	}

	public String getLanguage() {
		return language;
	}

	public int getPage() {
		return current_page;
	}
}
