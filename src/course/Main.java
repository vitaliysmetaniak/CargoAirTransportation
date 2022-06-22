package course;

import java.awt.Image;
import java.awt.Toolkit;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import course.gui.MainForm;

public class Main extends JFrame {

	private static final long serialVersionUID = -2417473070354657461L;

	public static void main(String[] args) {
		// встановлення стилю форм
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		MainForm mainForm = new MainForm();
		mainForm.setVisible(true);// встановлюємо прапорець видимості для
		// об'єкту візуальної
		// форми mainForm у true

		URL imgURL = Main.class.getResource("/img/avia.png");
		Image icon = Toolkit.getDefaultToolkit().getImage(imgURL);
		mainForm.setIconImage(icon);
		mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// при закритті форми, повиннен відбуватися вихід із
																// програми

	}

	// ---------------------------------------------------------
	public static ImageIcon createIcon(String path) {
		URL imgURL = Main.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Файл з такою назвою не знайдено: " + path);
			return null;
		}
	}

	// ---------------------------------------------------------
}
