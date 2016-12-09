import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddWindow extends JFrame {

	String name;




	String authtor;
	String genre;
	String language;
	String status;
	JLabel result;

	public AddWindow() throws SQLException {
		super("Добавить книгу");
		createGUI();
	}

	public void closeWindow() {
		super.dispose();
	}

	private void createGUI() {
		GridBagConstraints constraints = new GridBagConstraints();
		GridBagLayout gr = new GridBagLayout();
		JPanel main_panel = new JPanel();
		main_panel.setLayout(gr);
		constraints.fill = GridBagConstraints.BOTH;

		JLabel lname = new JLabel("Название книги: ");
		JTextField tname = new JTextField();
		tname.addActionListener(new NameTextFieldListener());

		JLabel lauth = new JLabel("Автор: ");
		JTextField tauth = new JTextField();
		tauth.addActionListener(new AuthtorTextFieldListener());

		JLabel lgenre = new JLabel("Жанр: ");
		String[] genres = { "", "Русская классика", "Зарубежная классика",
				"Фэнтези", "Фантастика", "Приключения", "Детские", "Детективы",
				"Роман", "Поэзия", "Наука", "Другое" };
		JComboBox<String> combo_genres = new JComboBox<String>(genres);
		combo_genres.setSelectedIndex(0);
		combo_genres.addActionListener(new GenreComboBoxListener());

		JLabel llan = new JLabel("Язык: ");
		String[] languages = { "", "Русский", "Английский", "Французский",
				"Другое" };
		JComboBox<String> combo_lan = new JComboBox<String>(languages);
		combo_lan.setSelectedIndex(0);
		combo_lan.addActionListener(new LanguageComboBoxListener());

		JLabel lst = new JLabel("Статус: ");
		String[] statuses = { "", "Хочу прочитать", "Читаю", "Прочитано" };
		JComboBox<String> combo_st = new JComboBox<String>(statuses);
		combo_st.setSelectedIndex(0);
		combo_st.addActionListener(new StatusComboBoxListener());

		JButton save = new JButton("Сохранить");
		save.addActionListener(new ButtonListener());
		result = new JLabel("\n");

		constraints.gridx = 0;
		constraints.gridy = 0;
		gr.setConstraints(lname, constraints);
		main_panel.add(lname);

		constraints.gridx = 1;
		constraints.gridy = 0;
		gr.setConstraints(tname, constraints);
		main_panel.add(tname);

		constraints.gridx = 0;
		constraints.gridy = 1;
		gr.setConstraints(lauth, constraints);
		main_panel.add(lauth);

		constraints.gridx = 1;
		constraints.gridy = 1;
		gr.setConstraints(tauth, constraints);
		main_panel.add(tauth);

		constraints.gridx = 0;
		constraints.gridy = 2;
		gr.setConstraints(lgenre, constraints);
		main_panel.add(lgenre);

		constraints.gridx = 1;
		constraints.gridy = 2;
		gr.setConstraints(combo_genres, constraints);
		main_panel.add(combo_genres);

		constraints.gridx = 0;
		constraints.gridy = 3;
		gr.setConstraints(llan, constraints);
		main_panel.add(llan);

		constraints.gridx = 1;
		constraints.gridy = 3;
		gr.setConstraints(combo_lan, constraints);
		main_panel.add(combo_lan);

		constraints.gridx = 0;
		constraints.gridy = 4;
		gr.setConstraints(lst, constraints);
		main_panel.add(lst);

		constraints.gridx = 1;
		constraints.gridy = 4;
		gr.setConstraints(combo_st, constraints);
		main_panel.add(combo_st);

		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 2;
		gr.setConstraints(result, constraints);
		main_panel.add(result);

		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.gridwidth = 2;
		gr.setConstraints(save, constraints);
		main_panel.add(save);

		getContentPane().add(main_panel);
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if ((name == null) || (authtor == null) || (genre == null)
					|| (language == null) || (status == null)) {
				result.setText("Заполните все поля!");
			} else {
				int int_status;
				switch (status) {
				case "Хочу прочитать":
					int_status = 1;
					break;
				case "Читаю":
					int_status = 2;
					break;
				case "Прочитано":
					int_status = 3;
					break;
				default:
					int_status = -1;
					break;
				}
				Book book = new Book(name, authtor, genre, int_status,
						language, 0);
				try {
					BookManager.addBook(book);
					result.setText("Сохранено");
					ListWindow.createList(0, null);
					closeWindow();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private class NameTextFieldListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JTextField tname = (JTextField) e.getSource();
			name = tname.getText();
		}
	}

	private class AuthtorTextFieldListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JTextField tauth = (JTextField) e.getSource();
			authtor = tauth.getText();
		}
	}

	private class GenreComboBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox<String> box = (JComboBox<String>) e.getSource();
			genre = (String) box.getSelectedItem();
		}
	}

	private class LanguageComboBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox<String> box = (JComboBox<String>) e.getSource();
			language = (String) box.getSelectedItem();
		}
	}

	private class StatusComboBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox<String> box = (JComboBox<String>) e.getSource();
			status = (String) box.getSelectedItem();
		}
	}
}
