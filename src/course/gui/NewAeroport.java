package course.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import course.Main;
import course.dao.AeroportDao;
import course.domain.Aeroport;

public class NewAeroport extends JDialog {
	private static final long serialVersionUID = -1825383007192086322L;

	private Aeroport Aero;

	private JTextField textnazva;
	private JTextField textkraina;
	private JTextField textmicto;
	private JTextField textadresa;
	private JTextField texttelephon;
	private JTextField textdovjuna;
	private JTextField textshuruna;
	private JLabel JLabel_1 = new JLabel();
	private JLabel JLabel_2 = new JLabel();
	private JLabel JLabel_3 = new JLabel();
	private JLabel JLabel_4 = new JLabel();
	private JLabel JLabel_5 = new JLabel();
	private JLabel JLabel_6 = new JLabel();
	private JLabel JLabel_7 = new JLabel();

	public NewAeroport() {
		setTitle("Форма введення даних");
		setSize(500, 360);
		setLocationByPlatform(true);
		setModal(true);
		setResizable(false);

		final JButton cmdSave = new JButton("Зберегти", Main.createIcon("/img/ok.png"));
		final JButton cmdCancel = new JButton("Відмінити", Main.createIcon("/img/cancel.png"));
		final JPanel buttonsPanel = new JPanel(new FlowLayout());
		final JPanel commandsPanelBorder = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		commandsPanelBorder.add(buttonsPanel);
		commandsPanelBorder.setOpaque(false);

		JPanel MYPanel = new JPanel();

		final JPanel fieldsPanel = new JPanel(new GridLayout(7, 2, 2, 10));
		final JPanel fieldsPanelBorder = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		fieldsPanel.setOpaque(false);
		fieldsPanelBorder.setOpaque(false);
		fieldsPanelBorder.add(fieldsPanel);
		buttonsPanel.setOpaque(false);
		buttonsPanel.add(cmdSave);
		buttonsPanel.add(cmdCancel);
		MYPanel.add(fieldsPanelBorder, BorderLayout.NORTH);
		MYPanel.add(commandsPanelBorder, BorderLayout.SOUTH);
		Container c = getContentPane();
		c.add(MYPanel);

		textnazva = new JTextField(20);
		textkraina = new JTextField(20);
		textmicto = new JTextField(20);
		textadresa = new JTextField(20);
		try {
			MaskFormatter mTel = new MaskFormatter("+##(###)###-##-##");
			texttelephon = new JFormattedTextField(mTel);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textdovjuna = new JTextField(20);
		textshuruna = new JTextField(20);
		JLabel_1.setText("Назва аеропорту");
		JLabel_2.setText("Країна прописки");
		JLabel_3.setText("Штаб-квартира");
		JLabel_4.setText("Юридична адреса");
		JLabel_5.setText("Контактний телефон");
		JLabel_6.setText("Довжина злітно-посадкової смуги, м");
		JLabel_7.setText("Ширина злітно-посадкової смуги, м");
		JLabel_7.setForeground(Color.BLACK);
		JLabel_6.setForeground(Color.BLACK);
		JLabel_5.setForeground(Color.BLACK);
		JLabel_4.setForeground(Color.BLACK);
		JLabel_3.setForeground(Color.BLACK);
		JLabel_2.setForeground(Color.BLACK);
		JLabel_1.setForeground(Color.BLACK);

		fieldsPanelBorder.add(fieldsPanel);
		fieldsPanel.add(JLabel_1);
		fieldsPanel.add(textnazva);
		fieldsPanel.add(JLabel_2);
		fieldsPanel.add(textkraina);
		fieldsPanel.add(JLabel_3);
		fieldsPanel.add(textmicto);
		fieldsPanel.add(JLabel_4);
		fieldsPanel.add(textadresa);
		fieldsPanel.add(JLabel_5);
		fieldsPanel.add(texttelephon);
		fieldsPanel.add(JLabel_6);
		fieldsPanel.add(textdovjuna);
		fieldsPanel.add(JLabel_7);
		fieldsPanel.add(textshuruna);

		cmdSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAeroport();
			}
		});

		cmdCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelSave();
			}
		});

	}

	public Aeroport getAeroport() {
		return Aero;
	}

	public void setAeroport(Aeroport aeroport) {
		this.Aero = aeroport;
		textnazva.setText(aeroport.getnazva());
		textkraina.setText(aeroport.getkraina());
		textmicto.setText(aeroport.getmicto());
		textadresa.setText(aeroport.getadresa());
		texttelephon.setText(aeroport.gettelephon());
		textdovjuna.setText(Integer.toString(aeroport.getdovjuna()));
		textshuruna.setText(Integer.toString(aeroport.getshuruna()));
	}

	private void saveAeroport() {
		try {
			Aero.setnazva(textnazva.getText());
			Aero.setkraina(textkraina.getText());
			Aero.setmicto(textmicto.getText());
			Aero.setadresa(textadresa.getText());
			Aero.settelephon(texttelephon.getText());
			Aero.setdovjuna(Integer.parseInt(textdovjuna.getText()));
			Aero.setshuruna(Integer.parseInt(textshuruna.getText()));

			if (Aero.getId() == null) {
				int newId = new AeroportDao().ins(Aero);
				Aero.setId(newId);
			} else {
				new AeroportDao().upd(Aero);
			}
			this.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "Помилка при збереженні даних:",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cancelSave() {
		this.setVisible(false);
	}
}