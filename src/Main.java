import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		Database.getInstance();
		ListWindow lw = new ListWindow();
		lw.pack();
		lw.setLocationRelativeTo(null);
		lw.setVisible(true);

	}

}
