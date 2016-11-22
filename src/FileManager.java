import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FileManager {

	private static PreparedStatement stmt;
	private static ResultSet rs;

	static File getFile(int book_id) throws SQLException, IOException {
		String sql = "SELECT book FROM File WHERE book_id= " + book_id + ";";
		PreparedStatement stmt = Database.db.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		File file = null;
		while (rs.next()) {
			Blob blob = rs.getBlob(1);
			// System.out.println("Read "+ blob.length() + " bytes ");
			byte[] array = blob.getBytes(1, (int) blob.length());
			file = File.createTempFile("something-", ".binary", new File("."));
			FileOutputStream out = new FileOutputStream(file);
			out.write(array);
			out.close();
		}

		return file;
	}

	static void updateFile(Files f) throws SQLException, IOException {

		String sql = "UPDATE File SET book = ? WHERE id = " + f.getId();
		stmt = Database.db.prepareStatement(sql);
		File file = f.getFile();
		FileInputStream fis = new FileInputStream(file);
		stmt.setBinaryStream(1, fis, (int) file.length());
		// System.out.println(stmt);
		stmt.executeUpdate();
	}

	static void addFile(Files f) throws SQLException, IOException {

		String sql = "INSERT INTO File (id, book_id, book) VALUES (?, ?, ?)";
		stmt = Database.db.prepareStatement(sql);
		stmt.setInt(1, f.getId());
		stmt.setInt(2, f.getBookId());

		File file = f.getFile();
		FileInputStream fis = new FileInputStream(file);
		stmt.setBinaryStream(3, fis, (int) file.length());

		stmt.execute();
		// System.out.println(stmt);
		// Database.db.commit();
		fis.close();
		// System.out.println("Файл добавлен");

	}

	static int getCountofFilesToBook(int book_id) throws SQLException {
		int count = 0;
		String query = "select count(*) from File WHERE book_id =" + book_id
				+ ";";
		Statement stmt = Database.db.createStatement();

		// executing SELECT query
		rs = stmt.executeQuery(query);

		while (rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

	static int getFileId(int book_id) throws SQLException {
		String query = "select id from File WHERE book_id=" + book_id;
		Statement stmt = Database.db.createStatement();
		int id = 0;
		// executing SELECT query
		rs = stmt.executeQuery(query);

		while (rs.next()) {
			id = rs.getInt(1);
		}
		return id;
	}

	static int getCountOfFiles() throws SQLException {
		int count = 0;
		String query = "select count(*) from File";
		Statement stmt = Database.db.createStatement();

		// executing SELECT query
		rs = stmt.executeQuery(query);

		while (rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

}