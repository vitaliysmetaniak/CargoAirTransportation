package course.gui;

// !
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
import course.dao.RozkladDao;
import course.domain.Rozklad;

public class NewRozklad extends JDialog {

	private static final long serialVersionUID = -7265530307974489903L;

	private Rozklad rozklad;

	private JTextField poch_pynktText;
	private JTextField kinc_pynktText;
	private JTextField reisText;
	private JFormattedTextField dniText;
	private JTextField vidpravText;
	private JTextField prubyttyaText;

	private JLabel JLabel_2 = new JLabel();
	private JLabel JLabel_3 = new JLabel();
	private JLabel JLabel_4 = new JLabel();
	private JLabel JLabel_5 = new JLabel();
	private JLabel JLabel_6 = new JLabel();
	private JLabel JLabel_7 = new JLabel();

	public NewRozklad() {
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

		poch_pynktText = new JTextField(20);
		kinc_pynktText = new JTextField(20);
		reisText = new JTextField(20);
		try {
			MaskFormatter mTel1 = new MaskFormatter("**., **., **., **., **., **., **.,");
			mTel1.setPlaceholderCharacter(' ');
			dniText = new JFormattedTextField(mTel1);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			MaskFormatter mTel = new MaskFormatter("##:##");
			mTel.setPlaceholderCharacter('0');
			vidpravText = new JFormattedTextField(mTel);

		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			MaskFormatter mTel = new MaskFormatter("##:##");
			mTel.setPlaceholderCharacter('0');
			prubyttyaText = new JFormattedTextField(mTel);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		JLabel_2.setText("Пункт відправки");
		JLabel_3.setText("Пункт прибуття");
		JLabel_4.setText("Номер рейсу");
		JLabel_5.setText("Дні відправки");
		JLabel_6.setText("Час відправлення");
		JLabel_7.setText("Час прибуття");
		JLabel_7.setForeground(Color.BLACK);
		JLabel_6.setForeground(Color.BLACK);
		JLabel_5.setForeground(Color.BLACK);
		JLabel_4.setForeground(Color.BLACK);
		JLabel_3.setForeground(Color.BLACK);
		JLabel_2.setForeground(Color.BLACK);

		fieldsPanel.add(JLabel_2);
		fieldsPanel.add(poch_pynktText);
		fieldsPanel.add(JLabel_3);
		fieldsPanel.add(kinc_pynktText);
		fieldsPanel.add(JLabel_4);
		fieldsPanel.add(reisText);
		fieldsPanel.add(JLabel_5);
		fieldsPanel.add(dniText);
		fieldsPanel.add(JLabel_6);
		fieldsPanel.add(vidpravText);
		fieldsPanel.add(JLabel_7);
		fieldsPanel.add(prubyttyaText);

		cmdSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRozklad();
			}
		});

		cmdCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelSave();
			}
		});
	}

	public Rozklad getRozklad() {
		return rozklad;
	}

	public void setRozklad(Rozklad rozklad) {
		this.rozklad = rozklad;

		poch_pynktText.setText(rozklad.getpoch_pynkt());
		kinc_pynktText.setText(rozklad.getkinc_pynkt());
		reisText.setText(rozklad.getreis());
		dniText.setText(rozklad.getdni());
		vidpravText.setText(rozklad.getvidprav());
		prubyttyaText.setText(rozklad.getprubyttya());
	}

	private void saveRozklad() {
		try {

			rozklad.setpoch_pynkt(poch_pynktText.getText());
			rozklad.setkinc_pynkt(kinc_pynktText.getText());
			rozklad.setreis(reisText.getText());
			rozklad.setdni(dniText.getText());
			rozklad.setvidprav(vidpravText.getText());
			rozklad.setprubyttya(prubyttyaText.getText());

			if (rozklad.getId() == null) {
				int newId = new RozkladDao().ins(rozklad);
				rozklad.setId(newId);
			} else {
				new RozkladDao().upd(rozklad);
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