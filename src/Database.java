import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

	private static Database instance;

	private Database() {
	}

	private static final String url = "jdbc:mysql://localhost:3306/BOOK_DIARY";
	private static final String user = "root";
	private static final String password = "2";
	public static Connection db;

	public static Database getInstance() throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		if (instance == null) {
			instance = new Database();

			Properties p = new Properties();
			p.setProperty("user", user);
			p.setProperty("password", password);
			p.setProperty("useUnicode", "true");
			p.setProperty("charSet", "UTF-8");
			p.setProperty("characterEncoding", "UTF-8");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			db = DriverManager.getConnection(url, p);
		}
		return instance;
	}

}
