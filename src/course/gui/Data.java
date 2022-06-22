package course.gui;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;

import course.domain.AviaKompanii;
import othersClass.TimerThread;

public class Data {
	public TimerThread timerThread;
	/*
	 * Основні кнопки
	 */
	public JButton cmdClose;
	public JButton cmdOpenAeroport;
	public JButton cmdOpenRozklad;
	public JButton cmdNew;
	public JButton cmdUpdate;
	public JButton cmdRemove;
	public JButton cmdDay;
	public JButton cmdAllDay;
	public JButton cmdReport;
	public JButton cmdPrint;
	public JButton cmdSort;
	public JButton jbtGetByID;
	public JButton jbtGetByName;
	public JButton jbtAll;
	/**
	 * Поля для вводу тексту для запитів.
	 */
	public JTextField jtxEdit;
	/**
	 * Таблицi.
	 */
	public JTable aTable;
	public JTable rozkladTable;
	public JTable aeroportTable;
	/**
	 * Створення контекстного меню
	 */
	public JPopupMenu popupMenu;
	public AviaKompaniiTableModel aviaKompaniiTableModel;
	public RozkladsTableModel rozkladTableModel;
	public AeroportsTableModel aeroportTableModel;
	public AviaKompanii aviaKompaniis;
	public NewAviaKompanii newAviaKompanii;
	public NewAeroport newAeroport;

	public JMenuItem add;
	public JMenuItem update;
	public JMenuItem delete;
	public JMenuItem print;
	public JMenuItem openAeroport;
	public JMenuItem openRozklad;
	public JMenuItem onClose;
	public NewRozklad newRozklad;

	// конструктори
	public Data(NewAviaKompanii newAviaKompanii, JPopupMenu popupMenu) {
		this.newAviaKompanii = newAviaKompanii;
		this.popupMenu = popupMenu;
	}

	public Data(NewRozklad newRozklad, JPopupMenu popupMenu) {
		this.newRozklad = newRozklad;
		this.popupMenu = popupMenu;
	}

	public Data(NewAeroport newAeroport, JPopupMenu popupMenu) {
		this.newAeroport = newAeroport;
		this.popupMenu = popupMenu;
	}
}