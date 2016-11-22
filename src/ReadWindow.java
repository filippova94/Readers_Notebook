import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

public class ReadWindow extends JFrame {

	private int book_id;
	StringBuilder stext;
	int cur_page = 0;
	int count_page = 0;
	JTextArea text;
	JLabel str;
	TxtPageController pc;

	public ReadWindow(String name, int id) throws SQLException, IOException {
		super(name);
		book_id = id;
		createGUI();
	}

	private void closeWindow() {
		super.dispose();
	}

	private void createGUI() throws SQLException, IOException {

		JPanel main_panel = new JPanel();
		main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));

		Book book = BookManager.getBook(book_id);
		File content = FileManager.getFile(book_id);
		stext = new StringBuilder();
		BufferedReader in = new BufferedReader(new FileReader(
				content.getAbsoluteFile()));
		String s;
		while ((s = in.readLine()) != null) {
			stext.append(s);
			stext.append("\n");
		}

		cur_page = book.getPage();
		// System.out.println(stext.length());
		pc = new TxtPageController();
		String text_page = pc.getPage(stext.toString(), cur_page);

		text = new JTextArea(40, 60);
		text.setLineWrap(true);
		text.setEditable(false);
		text.setText(text_page);
		text.setVisible(true);
		text.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		JPanel buttonpanel = new JPanel();
		buttonpanel.setLayout(new BoxLayout(buttonpanel, BoxLayout.X_AXIS));
		JButton exit = new JButton("Выход");
		exit.addActionListener(new ExitListener());
		buttonpanel.add(exit);
		JButton back = new JButton("Назад");
		back.addActionListener(new BackListener());
		buttonpanel.add(back);
		JButton next = new JButton("Вперед");
		next.addActionListener(new NextListener());
		buttonpanel.add(next);
		count_page = stext.toString().length() / 2400;
		str = new JLabel(cur_page + "/" + count_page);
		buttonpanel.add(str);

		main_panel.add(text);
		main_panel.add(buttonpanel);

		getContentPane().add(main_panel);

	}

	private class NextListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if ((cur_page++) <= count_page) {
				text.setText(pc.getPage(stext.toString(), cur_page));
				str.setText(cur_page + "/" + count_page);
			} else
				cur_page--;
		}
	}

	private class BackListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if ((cur_page--) > 0) {
				text.setText(pc.getPage(stext.toString(), cur_page));
				str.setText(cur_page + "/" + count_page);
			} else
				cur_page++;
		}
	}

	private class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				BookManager.updatePage(book_id, cur_page);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			closeWindow();
		}

	}

}
