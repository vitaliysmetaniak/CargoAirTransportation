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
import course.dao.AviaKompaniiDao;
import course.domain.AviaKompanii;

public class NewAviaKompanii extends JDialog {

	private static final long serialVersionUID = -7265530307974489903L;

	private AviaKompanii aviaKompanii;

	private JTextField nazvaText;
	private JTextField mistoText;
	private JTextField adresaText;
	private JTextField telephonText;
	private JTextField faxText;
	private JTextField rik_zasnyvannaText;
	private JLabel JLabel_1 = new JLabel();
	private JLabel JLabel_2 = new JLabel();
	private JLabel JLabel_3 = new JLabel();
	private JLabel JLabel_4 = new JLabel();
	private JLabel JLabel_5 = new JLabel();
	private JLabel JLabel_6 = new JLabel();

	public NewAviaKompanii() {
		setTitle("Форма введення даних");
		setSize(500, 320);
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

		final JPanel fieldsPanel = new JPanel(new GridLayout(6, 2, 2, 10));
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

		nazvaText = new JTextField(20);
		mistoText = new JTextField(20);
		adresaText = new JTextField(20);
		try {
			MaskFormatter mTel = new MaskFormatter("+##(###)###-##-##");
			telephonText = new JFormattedTextField(mTel);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			MaskFormatter mTel = new MaskFormatter("+##(###)###-##-##");
			faxText = new JFormattedTextField(mTel);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		rik_zasnyvannaText = new JTextField(5);

		JLabel_1.setText("Назва компанії");
		JLabel_2.setText("Місто");
		JLabel_3.setText("Юридична адреса");
		JLabel_4.setText("Контактний телефон");
		JLabel_5.setText("Факс");
		JLabel_6.setText("Рік заснування");
		JLabel_6.setForeground(Color.BLACK);
		JLabel_5.setForeground(Color.BLACK);
		JLabel_4.setForeground(Color.BLACK);
		JLabel_3.setForeground(Color.BLACK);
		JLabel_2.setForeground(Color.BLACK);
		JLabel_1.setForeground(Color.BLACK);
		fieldsPanel.add(JLabel_1);
		fieldsPanel.add(nazvaText);
		fieldsPanel.add(JLabel_2);
		fieldsPanel.add(mistoText);
		fieldsPanel.add(JLabel_3);
		fieldsPanel.add(adresaText);
		fieldsPanel.add(JLabel_4);
		fieldsPanel.add(telephonText);
		fieldsPanel.add(JLabel_5);
		fieldsPanel.add(faxText);
		fieldsPanel.add(JLabel_6);
		fieldsPanel.add(rik_zasnyvannaText);

		cmdSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAero();
			}
		});

		cmdCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelSave();
			}
		});
	}

	public AviaKompanii getAerokompanii() {
		return aviaKompanii;
	}

	public void setAerokompanii(AviaKompanii aviaKompanii) {
		this.aviaKompanii = aviaKompanii;
		nazvaText.setText(aviaKompanii.getnazva());
		mistoText.setText(aviaKompanii.getmisto());
		adresaText.setText(aviaKompanii.getadresa());
		telephonText.setText(aviaKompanii.gettelephon());
		faxText.setText(aviaKompanii.getfax());
		rik_zasnyvannaText.setText(Integer.toString(aviaKompanii.getrik_zasnyvanna()));
	}

	private void saveAero() {
		try {
			aviaKompanii.setnazva(nazvaText.getText());
			aviaKompanii.setmisto(mistoText.getText());
			aviaKompanii.setadresa(adresaText.getText());
			aviaKompanii.settelephon(telephonText.getText());
			aviaKompanii.setfax(faxText.getText());
			aviaKompanii.setrik_zasnyvanna(Integer.parseInt(rik_zasnyvannaText.getText()));

			if (aviaKompanii.getId() == null) {
				int newId = new AviaKompaniiDao().ins(aviaKompanii);
				aviaKompanii.setId(newId);
			} else {
				new AviaKompaniiDao().upd(aviaKompanii);
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