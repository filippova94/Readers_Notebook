import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class BookWindow extends JFrame {

	private int id;
	private JTextArea newQuote;
	private JTextArea description;
	private JLabel quote_result;
	private JTextArea quotes;
	private JLabel addFile;
	private String book_name;

	private boolean hasfile = false;

	public BookWindow(int i) throws SQLException {
		super("Редактирование книги");
		id = i;
		createGUI();
	}

	public void closeWindow() {
		super.dispose();
	}

	private void createGUI() throws SQLException {

		GridBagConstraints constraints = new GridBagConstraints();
		GridBagLayout gr = new GridBagLayout();
		JPanel main_panel = new JPanel();
		main_panel.setLayout(gr);

		Book book = BookManager.getBook(id);

		JPanel info = new JPanel();
		info.setLayout(new GridLayout(4, 1));
		info.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		book_name = book.getName();
		JLabel name = new JLabel("<html><big><h1><i>" + book_name
				+ "</i></big></b>");
		JLabel authtor = new JLabel("<html><big><h1><i>" + book.getAuthtor()
				+ "</i></big></b>");
		JLabel genre = new JLabel("<html><b><i>" + book.getGenre() + "</i></b>");

		String[] statuses = { "Статус", "Хочу прочитать", "Читаю", "Прочитано" };
		JComboBox<String> combo_st = new JComboBox<String>(statuses);
		combo_st.setSelectedIndex(book.getStatus());
		combo_st.addActionListener(new ComboBoxListener());

		info.add(name);
		info.add(authtor);
		info.add(genre);
		info.add(combo_st);

		description = new JTextArea(10, 30);
		description.getDocument().addDocumentListener(new MyDocumentListener());
		description.append(DescriptionManager.getDesc(id));
		JScrollPane scrollV1 = new JScrollPane(description);
		scrollV1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		description.setLineWrap(true);
		description.setVisible(true);
		description.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				"Описание/отзыв"));

		if (FileManager.getCountofFilesToBook(id) > 0) {
			addFile = new JLabel("Файл добавлен");
			hasfile = true;
		} else {
			addFile = new JLabel("\n");
			hasfile = false;
		}

		JButton addFileB = new JButton("Добавить файл");
		addFileB.addActionListener(new AddFileListener());

		JButton readButton = new JButton("Читать книгу");
		readButton.addActionListener(new ReadListener());

		newQuote = new JTextArea(10, 30);
		JScrollPane scrollV2 = new JScrollPane(newQuote);
		scrollV2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		newQuote.setLineWrap(true);
		// newQuote.setEditable(false);
		newQuote.setVisible(true);
		newQuote.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				"Новая цитата"));

		quote_result = new JLabel("\n");
		JButton addQuote = new JButton("Добавть цитату");
		addQuote.addActionListener(new AddQuoteListener());

		quotes = new JTextArea(23, 30);
		JScrollPane scrollV3 = new JScrollPane(quotes);
		scrollV3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		int count = QuoteManager.getCountofBookQuotes(id);
		//System.out.println("Count " + count);
		Quote[] quote = new Quote[count + 1];
		quote = QuoteManager.getAllQuotes(id);
		for (int i = 0; i < count; i++) {
			quotes.append(quote[i].getQuote());
			quotes.append("\n");
		}
		quotes.setLineWrap(true);
		quotes.setVisible(true);
		quotes.setEditable(false);
		quotes.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				"Цитаты"));

		constraints.gridx = 0;
		constraints.gridy = 0;
		gr.setConstraints(info, constraints);
		main_panel.add(info);

		constraints.gridx = 0;
		constraints.gridy = 1;
		gr.setConstraints(addFile, constraints);
		main_panel.add(addFile);

		constraints.gridx = 0;
		constraints.gridy = 2;
		gr.setConstraints(addFileB, constraints);
		main_panel.add(addFileB);

		constraints.insets = new Insets(10, 0, 10, 0);
		constraints.gridx = 0;
		constraints.gridy = 3;
		gr.setConstraints(readButton, constraints);
		main_panel.add(readButton);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 10, 0, 10);
		gr.setConstraints(description, constraints);
		main_panel.add(description);

		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.insets = new Insets(0, 10, 0, 10);
		gr.setConstraints(newQuote, constraints);
		main_panel.add(newQuote);

		constraints.gridx = 1;
		constraints.gridy = 2;
		gr.setConstraints(quote_result, constraints);
		main_panel.add(quote_result);

		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridheight = 2;
		gr.setConstraints(addQuote, constraints);
		main_panel.add(addQuote);

		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.insets = new Insets(12, 10, 0, 10);
		constraints.gridheight = 2;
		gr.setConstraints(quotes, constraints);
		main_panel.add(quotes);

		getContentPane().add(main_panel);
	}

	class MyDocumentListener implements DocumentListener {

		public void insertUpdate(DocumentEvent e) {
			String text = description.getText();
			try {
				if (DescriptionManager.hasDesc(id))
					DescriptionManager.updateText(new Description(id, text));
				else
					DescriptionManager.insertText(new Description(id, text));
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		}

		public void removeUpdate(DocumentEvent e) {
			String text = description.getText();
			try {
				if (DescriptionManager.hasDesc(id))
					DescriptionManager.updateText(new Description(id, text));
				else
					DescriptionManager.insertText(new Description(id, text));
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		}

		public void changedUpdate(DocumentEvent e) {
			String text = description.getText();
			try {
				if (DescriptionManager.hasDesc(id))
					DescriptionManager.updateText(new Description(id, text));
				else
					DescriptionManager.insertText(new Description(id, text));
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		}
	}

	private class ReadListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (hasfile) {
				ReadWindow rw;
				try {
					rw = new ReadWindow(book_name, id);
					rw.pack();
					rw.setLocationRelativeTo(null);
					rw.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				addFile.setText("Добавьте файл");
			}

		}
	}

	private class AddFileListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				JFileChooser dialog = new JFileChooser();
				dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
				dialog.setApproveButtonText("Выбрать");
				dialog.setDialogTitle("Выберите файл для загрузки");
				dialog.setDialogType(JFileChooser.OPEN_DIALOG);
				dialog.showOpenDialog(new JFrame());
				File file = dialog.getSelectedFile();
				int count = FileManager.getCountOfFiles();
				// System.out.println("Count of files"+count);

				if (!hasfile) {
					Files f = new Files(count + 1, id, file);
					FileManager.addFile(f);
					addFile.setText("Файл добавлен");
					hasfile = true;
				} else {
					int file_id = FileManager.getFileId(id);
					FileManager.updateFile(new Files(file_id, id, file));
					addFile.setText("Файл обновлен");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	private class ComboBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox box = (JComboBox) e.getSource();
			int item = box.getSelectedIndex();
			try {
				BookManager.updateStatus(id, item);
				ListWindow.createList(0, null);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	private class AddQuoteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String content;
			int count;
			try {
				count = QuoteManager.getCountOfQuotes();
				if ((content = newQuote.getText()).length() == 0) {
					quote_result.setText("Введите цитату");
				} else {
					QuoteManager.addQuote(new Quote(count + 1, id, content));
					quote_result.setText("\n");
					quotes.append(content);
					quotes.append("\n");
					newQuote.setText("");
				}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}

	}
}
