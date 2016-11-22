import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookManager {

	private static Statement stmt;
	private static ResultSet rs;

	static void updatePage(int id, int p) throws SQLException {
		stmt = Database.db.createStatement();
		String sql = "UPDATE Book " + "SET current_page = " + p + " WHERE id="
				+ id + ";";
		// System.out.println(sql);
		stmt.executeUpdate(sql);
	}

	static void updateStatus(int id, int s) throws SQLException {
		stmt = Database.db.createStatement();
		String sql = "UPDATE Book " + "SET status = " + s + " WHERE id=" + id
				+ ";";
		stmt.executeUpdate(sql);
	}

	static Book getBook(int i) throws SQLException {
		Book book = null;
		stmt = Database.db.createStatement();
		String sql = "Select * FROM Book WHERE id=" + i + ";";
		rs = stmt.executeQuery(sql);
		while (rs.next()) {
			int id = rs.getInt(1);
			String name = rs.getString(2);
			String authtor = rs.getString(3);
			String genre = rs.getString(4);
			int status = rs.getInt(5);
			String lan = rs.getString(6);
			int cur_p = rs.getInt(7);
			book = new Book(id, name, authtor, genre, status, lan, cur_p);
		}
		return book;
	}

	static void addBook(Book book) throws SQLException {
		stmt = Database.db.createStatement();
		int id = getCountOfBooks() + 1;
		String sql = "INSERT INTO Book(id,name,authtor,genre,status,language,current_page) "
				+ "VALUES ("
				+ id
				+ ",\""
				+ book.getName()
				+ "\", \""
				+ book.getAuthtor()
				+ "\",\""
				+ book.getGenre()
				+ "\", "
				+ book.getStatus()
				+ ", \""
				+ book.getLanguage()
				+ "\", "
				+ book.getPage() + ");";
		// System.out.println(sql);
		stmt.executeUpdate(sql);
	}

	static int getCountOfBooks() throws SQLException {
		int count = 0;
		String query = "select count(*) from Book";
		stmt = Database.db.createStatement();

		// executing SELECT query
		rs = stmt.executeQuery(query);

		while (rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

	static int genIdByName(String name) throws SQLException {
		String sql = "Select id FROM Book WHERE name = \"" + name + "\";";
		// System.out.println(sql);
		stmt = Database.db.createStatement();

		// executing SELECT query
		rs = stmt.executeQuery(sql);

		int id = 0;
		while (rs.next()) {
			id = rs.getInt(1);
		}
		return id;
	}

	static Book[] getList(int c) {
		Book[] books = new Book[c];
		int i = 0;
		String query = "select * from Book";
		try {

			// getting Statement object to execute query
			stmt = Database.db.createStatement();

			// executing SELECT query
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String authtor = rs.getString(3);
				String genre = rs.getString(4);
				int status = rs.getInt(5);
				String language = rs.getString(6);
				int current_page = rs.getInt(7);
				books[i] = new Book(id, name, authtor, genre, status, language,
						current_page);
				i++;
			}
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
		return books;
	}
}
