import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	static DefaultListModel<String> listModel;
	JList<String> list;

	public ListWindow() throws SQLException {
		super("Дневник читателя");
		createGUI();
	}

	private void createGUI() throws SQLException {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GridBagConstraints constraints = new GridBagConstraints();
		GridBagLayout gr = new GridBagLayout();
		JPanel main_panel = new JPanel();
		main_panel.setLayout(gr);
		constraints.fill = GridBagConstraints.BOTH;

		JPanel panel = new JPanel();
		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		createList(0, null);
		panel.add(new JScrollPane(list));
		list.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Книги"));
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					JList list = (JList) e.getSource();
					if (!list.isSelectionEmpty()) {
						String value = (String) list.getSelectedValue();
						// /System.out.println(value);
						// list.clearSelection();
						int f = value.indexOf("</strong></u><br>");
						String name = value.substring(17, f);
						BookWindow bw;
						try {
							int id = BookManager.genIdByName(name);
							bw = new BookWindow(id);
							bw.pack();
							bw.setLocationRelativeTo(null);
							bw.setVisible(true);

						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});

		JPanel genrespanel = new JPanel();
		genrespanel.setLayout(new BoxLayout(genrespanel, BoxLayout.X_AXIS));
		JLabel genre_label = new JLabel("Жанр: ");
		String[] genres = { "", "Русская классика", "Зарубежная классика",
				"Фэнтези", "Фантастика", "Приключения", "Детские", "Детективы",
				"Роман", "Поэзия", "Наука", "Другое" };
		JComboBox<String> combo_genres = new JComboBox<String>(genres);
		combo_genres.setSelectedIndex(0);
		combo_genres.addActionListener(new GenreComboBoxListener());
		genrespanel.add(genre_label);
		genrespanel.add(combo_genres);
		JPanel combo_panel = new JPanel();
		combo_panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				"Описание"));
		combo_panel.setLayout(new BoxLayout(combo_panel, BoxLayout.Y_AXIS));
		combo_panel.add(genrespanel);

		JPanel lanspanel = new JPanel();
		lanspanel.setLayout(new BoxLayout(lanspanel, BoxLayout.X_AXIS));
		JLabel lan_label = new JLabel("Язык:   ");
		String[] languages = { "", "Русский", "Английский", "Французский",
				"Другое" };
		JComboBox<String> combo_lan = new JComboBox<String>(languages);
		combo_lan.setSelectedIndex(0);
		combo_lan.addActionListener(new LanguageComboBoxListener());
		lanspanel.add(lan_label);
		lanspanel.add(combo_lan);
		combo_panel.add(lanspanel);

		JPanel stpanel = new JPanel();
		stpanel.setLayout(new BoxLayout(stpanel, BoxLayout.X_AXIS));
		JLabel st_label = new JLabel("Статус:");
		String[] statuses = { "", "Хочу прочитать", "Читаю", "Прочитано" };
		JComboBox<String> combo_st = new JComboBox<String>(statuses);
		combo_st.setSelectedIndex(0);
		combo_st.addActionListener(new StatusComboBoxListener());
		stpanel.add(st_label);
		stpanel.add(combo_st);
		combo_panel.add(stpanel);

		JPanel button_panel = new JPanel();
		JButton add_button = new JButton("Добавить книгу");
		add_button.addActionListener(new ButtonListener());
		button_panel.add(add_button);

		constraints.gridx = 0;
		constraints.gridy = 0;
		gr.setConstraints(combo_panel, constraints);
		main_panel.add(combo_panel);

		constraints.gridx = 0;
		constraints.gridy = 1;
		gr.setConstraints(button_panel, constraints);
		main_panel.add(button_panel);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridheight = 2;
		gr.setConstraints(panel, constraints);
		main_panel.add(panel);
		getContentPane().add(main_panel);
	}

	static String genre_flag = "";
	static String lan_flag = "";
	static String st_flag = "";

	public static void createList(int a, String s) throws SQLException {
		listModel.clear();

		int c = BookManager.getCountOfBooks();
		Book books[] = BookManager.getList(c);
		String data[] = new String[c];
		String status;

		if (a == 1) {
			genre_flag = s;
			//System.out.println(s);
		}
		if (a == 2) {
			lan_flag = s;
			//System.out.println(s);
		}
		if (a == 3) {
			st_flag = s;
			//System.out.println(s);
		}

		for (int i = 0; i < c; i++) {
			switch (books[i].getStatus()) {
			case 1:
				status = "Хочу прочитать";
				break;
			case 2:
				status = "Читаю";
				break;
			case 3:
				status = "Прочитано";
				break;
			default:
				status = "";
				break;
			}
			data[i] = "<html><strong><u>" + books[i].getName()
					+ "</strong></u><br>" + books[i].getAuthtor() + "<br>"
					+ status + "<br>";
			if (!genre_flag.equals("") || !lan_flag.equals("")
					|| !st_flag.equals("")) {
				if (!st_flag.equals("")) {
					if ((!lan_flag.equals("")) && (!genre_flag.equals(""))) {
						if ((books[i].getGenre().equals(genre_flag))
								&& (books[i].getLanguage().equals(lan_flag))
								&& (status.equals(st_flag))) {
							listModel.addElement(data[i]);
							continue;
						}
					} else {
						if ((!lan_flag.equals("")) || (!genre_flag.equals(""))) {
							if (!lan_flag.equals("")) {
								if ((status.equals(st_flag))
										&& (books[i].getLanguage()
												.equals(lan_flag))) {
									listModel.addElement(data[i]);
									continue;
								}
							}
							if (!genre_flag.equals("")) {
								if ((status.equals(st_flag))
										&& (books[i].getGenre()
												.equals(genre_flag))) {
									listModel.addElement(data[i]);
									continue;
								}
							}
						} else {
							if (status.equals(st_flag)) {
								listModel.addElement(data[i]);
								continue;
							}
						}
					}
				}
				if (!lan_flag.equals("")) {
					if ((!genre_flag.equals("")) && (!st_flag.equals(""))) {
						if ((books[i].getGenre().equals(genre_flag))
								&& (books[i].getLanguage().equals(lan_flag))
								&& (status.equals(st_flag))) {
							listModel.addElement(data[i]);
							continue;
						}
					} else {
						if ((!st_flag.equals("")) || (!genre_flag.equals(""))) {

							if (!st_flag.equals("")) {
								if ((status.equals(st_flag))
										&& (books[i].getLanguage()
												.equals(lan_flag))) {
									listModel.addElement(data[i]);
									continue;
								}
							}
							if (!genre_flag.equals("")) {
								if ((books[i].getLanguage().equals(lan_flag))
										&& (books[i].getGenre()
												.equals(genre_flag))) {
									listModel.addElement(data[i]);
									continue;
								}
							}
						} else {
							if (books[i].getLanguage().equals(lan_flag)) {
								listModel.addElement(data[i]);
								continue;
							}
						}
					}
				}
				if (!genre_flag.equals("")) {
					if ((!lan_flag.equals("")) && (!st_flag.equals(""))) {
						if ((books[i].getGenre().equals(genre_flag))
								&& (books[i].getLanguage().equals(lan_flag))
								&& (status.equals(st_flag))) {
							listModel.addElement(data[i]);
							continue;
						}
					} else {
						if ((!lan_flag.equals("")) || (!st_flag.equals(""))) {

							if (!lan_flag.equals("")) {
								if ((books[i].getGenre().equals(genre_flag))
										&& (books[i].getLanguage()
												.equals(lan_flag))) {
									listModel.addElement(data[i]);
									continue;
								}
							}
							if (!st_flag.equals("")) {
								if ((status.equals(st_flag))
										&& (books[i].getGenre()
												.equals(genre_flag))) {
									listModel.addElement(data[i]);
									continue;
								}
							}
						} else {
							if (books[i].getGenre().equals(genre_flag)) {
								listModel.addElement(data[i]);
								continue;
							}
						}
					}
				}

			} else {
				listModel.addElement(data[i]);
			}

		}
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			AddWindow aw;
			try {
				aw = new AddWindow();
				aw.pack();
				aw.setLocationRelativeTo(null);
				aw.setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	private class GenreComboBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox<String> box = (JComboBox<String>) e.getSource();
			String item = (String) box.getSelectedItem();
			try {
				createList(1, item);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	private class LanguageComboBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox<String> box = (JComboBox<String>) e.getSource();
			String item = (String) box.getSelectedItem();
			try {
				createList(2, item);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	private class StatusComboBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox<String> box = (JComboBox<String>) e.getSource();
			String item = (String) box.getSelectedItem();
			try {
				createList(3, item);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}
}
