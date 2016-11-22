import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DescriptionManager {

	private static Statement stmt;
	private static ResultSet rs;

	public static String getDesc(int id) throws SQLException {
		String text = "";
		String query = "select text from Description WHERE book_id = " + id
				+ ";";
		stmt = Database.db.createStatement();

		// executing SELECT query
		rs = stmt.executeQuery(query);

		while (rs.next()) {
			text = rs.getString(1);
		}
		return text;
	}

	public static void updateText(Description d) throws SQLException {
		stmt = Database.db.createStatement();
		String sql = "UPDATE Description SET text =\"" + d.getText()
				+ "\" WHERE book_id=" + d.getBookId();
		// System.out.println(sql);
		stmt.executeUpdate(sql);
	}

	public static void insertText(Description d) throws SQLException {
		stmt = Database.db.createStatement();
		String sql = "INSERT INTO Description(book_id,text) VALUES ("
				+ d.getBookId() + ",\"" + d.getText() + "\");";
		// System.out.println(sql);
		stmt.executeUpdate(sql);
	}

	public static boolean hasDesc(int id) throws SQLException {
		int count = 0;
		String query = "select count(*) from Description WHERE book_id="+id+";";
		stmt = Database.db.createStatement();

		rs = stmt.executeQuery(query);

		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count > 0)
			return true;
		else
			return false;
	}
}
