import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QuoteManager {

	private static Statement stmt;
	private static ResultSet rs;

	static int getCountOfQuotes() throws SQLException {
		int count = 0;
		String query = "select count(*) from Quote";
		stmt = Database.db.createStatement();

		// executing SELECT query
		rs = stmt.executeQuery(query);

		while (rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

	static int getCountofBookQuotes(int book_id) throws SQLException {
		int count = 0;
		String query = "select count(*) from Quote WHERE book_id =" + book_id
				+ ";";
		stmt = Database.db.createStatement();

		// executing SELECT query
		rs = stmt.executeQuery(query);

		while (rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

	static void addQuote(Quote quote) throws SQLException {
		stmt = Database.db.createStatement();
		String sql = "INSERT INTO Quote(id,book_id, content) " + "VALUES ("
				+ quote.getId() + "," + quote.getBookId() + ", \""
				+ quote.getQuote() + "\");";
		// System.out.println(sql);
		stmt.executeUpdate(sql);

	}

	static Quote[] getAllQuotes(int book_id) throws SQLException {
		int c = getCountofBookQuotes(book_id);
		Quote[] quotes = new Quote[c];
		int i = 0;
		String query = "select * from Quote WHERE book_id = " + book_id + ";";
		try {

			// getting Statement object to execute query
			stmt = Database.db.createStatement();

			// executing SELECT query
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				int id = rs.getInt(1);
				int bookid = rs.getInt(2);
				String content = rs.getString(3);
				quotes[i] = new Quote(id, bookid, content);
				System.out.println(content);
				i++;
			}
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
		return quotes;
	}

}
