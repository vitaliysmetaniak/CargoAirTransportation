package course.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import course.dao.AviaKompaniiDao;
import course.domain.AviaKompanii;
import othersClass.JStatusBar;
import othersClass.TimerThread;
import course.Main;

public class MainForm extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;


	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == data.add) {
			add();
		} else if (e.getSource() == data.update) {
			update();
		} else if (e.getSource() == data.delete) {
			delete();
		} else if (e.getSource() == data.print) {
			print();
		} else if (e.getSource() == data.openAeroport) {
			try {
				openAeroport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == data.openRozklad) {
			try {
				openRozklad();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == data.onClose) {
			if (JOptionPane.showConfirmDialog(data.onClose, "Підтвердіть вихід з програми!") == JOptionPane.YES_OPTION)
				System.exit(0);
		}
	}

	Data data = new Data(new NewAviaKompanii(), new JPopupMenu());

	void mainMenu() {

		Font fontMenu = new Font("Calibri", Font.PLAIN, 13);
		JMenuBar MenuBar = new JMenuBar();

		JMenu mFile = new JMenu("Файл");
		JMenu mInform = new JMenu("Правка");

		mFile.setFont(fontMenu);
		mInform.setFont(fontMenu);

		ImageIcon icon = Main.createIcon("/img/add.png");
		data.add = new JMenuItem("Додати ", icon);
		data.add.setToolTipText("Додати новий запис до бази");
		data.add.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		data.add.setFont(fontMenu);
		data.add.addActionListener(this);
		mInform.add(data.add);

		ImageIcon icon1 = Main.createIcon("/img/edit.png");
		data.update = new JMenuItem("Внести зміни", icon1);
		data.update.setToolTipText("Внести зміни у вже створений запис");
		data.update.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		data.update.setFont(fontMenu);
		data.update.addActionListener(this);
		mInform.add(data.update);

		ImageIcon icon2 = Main.createIcon("/img/delete.png");
		data.delete = new JMenuItem("Видалити ", icon2);
		data.delete.setToolTipText("Видалити запис у базі");
		data.delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.SHIFT_MASK));
		data.delete.setFont(fontMenu);
		data.delete.addActionListener(this);
		mInform.add(data.delete);

		mInform.addSeparator();

		ImageIcon icon3 = Main.createIcon("/img/print.png");
		data.print = new JMenuItem("Вивести на друк", icon3);
		data.print.setToolTipText("Роздрукувати інформацію з таблиці");
		data.print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		data.print.setFont(fontMenu);
		data.print.addActionListener(this);
		mFile.add(data.print);
		ImageIcon icon5 = Main.createIcon("/img/open.png");
		data.openAeroport = new JMenuItem("Аеропорт", icon5);
		data.openAeroport.setToolTipText("Одержати інформацію про аеропорт");
		data.openAeroport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		data.openAeroport.setFont(fontMenu);
		data.openAeroport.addActionListener(this);
		mFile.add(data.openAeroport);

		ImageIcon icon6 = Main.createIcon("/img/open1.png");
		data.openRozklad = new JMenuItem("Розклад", icon6);
		data.openRozklad.setToolTipText("Одержати інформацію про розклад");
		data.openRozklad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		data.openRozklad.setFont(fontMenu);
		data.openRozklad.addActionListener(this);
		mFile.add(data.openRozklad);
		mFile.addSeparator();

		ImageIcon icon4 = Main.createIcon("/img/exit.png");
		data.onClose = new JMenuItem("Закрити програму", icon4);
		data.onClose.setToolTipText("Завершити роботу з програмою");
		data.onClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		data.onClose.setFont(fontMenu);
		data.onClose.addActionListener((ActionListener) this);
		mFile.add(data.onClose);

		MenuBar.add(mFile);
		MenuBar.add(mInform);
		setJMenuBar(MenuBar);

	}

	public MainForm() {
		super();
		setTitle("Авіаційні перевезення");
		setSize(1000, 430);
		setLocationByPlatform(true);
		setResizable(false);

		URL imgURL = Main.class.getResource("/img/avia.png");
		Image icon = Toolkit.getDefaultToolkit().getImage(imgURL);
		this.setIconImage(icon);
		mainMenu();// виклик головного меню

		Font pMenu = new Font("Calibri", Font.PLAIN, 13);// шрифт
		// контекстне меню
		ImageIcon Newicon = Main.createIcon("/img/add.png");
		JMenuItem cmdNew = new JMenuItem("Новий запис", Newicon);
		cmdNew.setFont(pMenu);
		cmdNew.addActionListener(this);
		data.popupMenu.add(cmdNew);

		cmdNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add();
			}
		});

		ImageIcon Updateicon = Main.createIcon("/img/edit.png");
		JMenuItem cmdUpdate = new JMenuItem("Редагувати запис", Updateicon);
		cmdUpdate.setFont(pMenu);
		cmdUpdate.addActionListener(this);
		data.popupMenu.add(cmdUpdate);

		cmdUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		data.popupMenu.addSeparator();
		ImageIcon Removeicon = Main.createIcon("/img/delete.png");
		JMenuItem cmdRemove = new JMenuItem("Видалити запис", Removeicon);
		cmdRemove.setFont(pMenu);
		cmdRemove.addActionListener(this);
		data.popupMenu.add(cmdRemove);

		cmdRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		// панель інструментів
		JToolBar tools = new JToolBar();
		Color ColorBar = Color.DARK_GRAY;
		tools.setBackground(ColorBar);

		data.cmdNew = new JButton(Main.createIcon("/img/add.png"));
		data.cmdNew.setToolTipText("Новий запис");
		tools.add(data.cmdNew);

		data.cmdNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add();
			}
		});

		data.cmdUpdate = new JButton(Main.createIcon("/img/edit.png"));
		data.cmdUpdate.setToolTipText("Редагувати запис");
		tools.add(data.cmdUpdate);

		data.cmdUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});

		data.cmdRemove = new JButton(Main.createIcon("/img/delete.png"));
		data.cmdRemove.setToolTipText("Видалити запис");
		tools.add(data.cmdRemove);

		data.cmdRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		tools.addSeparator();
		data.cmdPrint = new JButton(Main.createIcon("/img/print.png"));
		data.cmdPrint.setToolTipText("Роздрукувати інформацію");
		tools.add(data.cmdPrint);

		data.cmdPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				print();
			}
		});
		data.cmdReport = new JButton(Main.createIcon("/img/report.png"));
		data.cmdReport.setToolTipText("Експорт у Excel");
		tools.add(data.cmdReport);

		data.cmdReport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int option = fc.showSaveDialog(MainForm.this);
				if (option == JFileChooser.APPROVE_OPTION) {
					String filename = fc.getSelectedFile().getName();
					String path = fc.getSelectedFile().getParentFile().getPath();

					int len = filename.length();
					String ext = "";
					String file = "";

					if (len > 4) {
						ext = filename.substring(len - 4, len);
					}

					if (ext.equals(".xls")) {
						file = path + "\\" + filename;
					} else {
						file = path + "\\" + filename + ".xls";
					}
					toExcel(data.aTable, new File(file));
				}
			}
		});
		tools.addSeparator();
		data.cmdOpenAeroport = new JButton(Main.createIcon("/img/open.png"));
		data.cmdOpenAeroport.setToolTipText("Аеропорт");
		tools.add(data.cmdOpenAeroport);

		data.cmdOpenAeroport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					openAeroport();
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			}
		});

		data.cmdOpenRozklad = new JButton(Main.createIcon("/img/open1.png"));
		data.cmdOpenRozklad.setToolTipText("Розклад");
		tools.add(data.cmdOpenRozklad);

		data.cmdOpenRozklad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					openRozklad();
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			}
		});
		tools.addSeparator();
		data.cmdClose = new JButton(Main.createIcon("/img/exit.png"));
		data.cmdClose.setToolTipText("Закрити програму");
		tools.add(data.cmdClose);

		data.cmdClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(data.onClose, "Вийти з програми?") == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		// --------------------------------------------
		data.aviaKompaniiTableModel = getTableModel();
		data.aTable = new JTable(data.aviaKompaniiTableModel);
		data.aTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		data.aTable.setPreferredScrollableViewportSize(new Dimension(1000, 400));
		data.aTable.setBackground(Color.WHITE);
		data.aTable.setForeground(Color.darkGray);
		data.aTable.setSelectionBackground(Color.darkGray);
		data.aTable.setSelectionForeground(Color.RED);
		data.aTable.setRowHeight(30);
		data.aTable.getColumnModel().getColumn(0).setResizable(false);
		data.aTable.getColumnModel().getColumn(0).setMaxWidth(35);
		data.aTable.getColumnModel().getColumn(0).setMinWidth(35);
		data.aTable.getColumnModel().getColumn(1).setMinWidth(100);
		data.aTable.getColumnModel().getColumn(2).setMinWidth(80);
		data.aTable.getColumnModel().getColumn(3).setMinWidth(270);
		data.aTable.getColumnModel().getColumn(4).setMinWidth(100);
		data.aTable.getColumnModel().getColumn(5).setMinWidth(100);
		data.aTable.getColumnModel().getColumn(6).setMinWidth(40);
		data.aTable.setRowHeight(30);
		Font FontGrid = new Font("Calibri", Font.PLAIN, 12);
		data.aTable.setFont(FontGrid);
		data.aTable.setOpaque(false);
		// --------------------------------------------
		data.cmdClose = new JButton("Закрити", Main.createIcon("/img/exit.png"));
		data.cmdClose.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		data.cmdClose.setHorizontalAlignment(SwingConstants.LEFT);
		data.cmdClose.setFont(FontGrid);
		data.cmdNew = new JButton("Додати", Main.createIcon("/img/add.png"));
		data.cmdNew.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		data.cmdNew.setHorizontalAlignment(SwingConstants.LEFT);
		data.cmdNew.setFont(FontGrid);
		data.cmdUpdate = new JButton("Редагувати", Main.createIcon("/img/edit.png"));
		data.cmdUpdate.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		data.cmdUpdate.setHorizontalAlignment(SwingConstants.LEFT);
		data.cmdUpdate.setFont(FontGrid);
		data.cmdRemove = new JButton("Видалити", Main.createIcon("/img/delete.png"));
		data.cmdRemove.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		data.cmdRemove.setHorizontalAlignment(SwingConstants.LEFT);
		data.cmdRemove.setFont(FontGrid);
		data.cmdOpenAeroport = new JButton("Аеропорт", Main.createIcon("/img/open.png"));
		data.cmdOpenAeroport.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		data.cmdOpenAeroport.setHorizontalAlignment(SwingConstants.LEFT);
		data.cmdOpenAeroport.setFont(FontGrid);
		data.cmdOpenRozklad = new JButton("Розклад", Main.createIcon("/img/open1.png"));
		data.cmdOpenRozklad.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		data.cmdOpenRozklad.setHorizontalAlignment(SwingConstants.LEFT);
		data.cmdOpenRozklad.setFont(FontGrid);
		data.cmdPrint = new JButton("Вивести на друк", Main.createIcon("/img/print.png"));
		data.cmdPrint.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		data.cmdPrint.setHorizontalAlignment(SwingConstants.LEFT);
		data.cmdPrint.setFont(FontGrid);
		data.cmdReport = new JButton("Експорт у Excel", Main.createIcon("/img/report.png"));
		data.cmdReport.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		data.cmdReport.setHorizontalAlignment(SwingConstants.LEFT);
		data.cmdReport.setFont(FontGrid);
		// --------------------------------------------
		JPanel commandsPanel = new JPanel();
		JPanel commandsPanel1 = new JPanel(new GridLayout(8, 1, 5, 5));
		commandsPanel1.add(data.cmdNew);
		commandsPanel1.add(data.cmdUpdate);
		commandsPanel1.add(data.cmdRemove);
		commandsPanel1.add(data.cmdPrint);
		commandsPanel1.add(data.cmdReport);
		commandsPanel1.add(Box.createVerticalGlue());
		commandsPanel1.add(data.cmdOpenAeroport);
		commandsPanel1.add(data.cmdOpenRozklad);
		commandsPanel.add(commandsPanel1);
		// --------------------------------------------
		JPanel btPanel1 = new JPanel();
		btPanel1.setLayout(new BoxLayout(btPanel1, BoxLayout.X_AXIS));
		btPanel1.add(Box.createHorizontalGlue());
		btPanel1.add(data.cmdClose);
		data.cmdClose.setMinimumSize(data.cmdClose.getPreferredSize());

		// Створення об’єкту класу JStatusBar рядка-статутсу та додавання на
		// нього дати, часу
		JStatusBar statusBar = new JStatusBar();
		final JLabel dateLabel = new JLabel();
		dateLabel.setHorizontalAlignment(JLabel.CENTER);
		statusBar.addRightComponent(dateLabel);
		final JLabel timeLabel = new JLabel();
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		timeLabel.setForeground(Color.RED);
		statusBar.addRightComponent(timeLabel);
		data.timerThread = new TimerThread(dateLabel, timeLabel);
		data.timerThread.start();
		// --------------------------------------------
		// створюю кнопку для вибірки
		// постачальників за ID
		data.jbtGetByID = new JButton("Пошук по ID", Main.createIcon("/img/search_by_id.png"));
		data.jbtGetByID.setHorizontalAlignment(SwingConstants.LEFT);
		data.jbtGetByID.setFont(FontGrid);
		data.jbtGetByName = new JButton("Пошук по назві", Main.createIcon("/img/search_by_name.png"));
		data.jbtGetByName.setHorizontalAlignment(SwingConstants.LEFT);
		data.jbtGetByName.setFont(FontGrid);
		data.jbtAll = new JButton("Всі записи", Main.createIcon("/img/all.png"));
		data.jbtAll.setHorizontalAlignment(SwingConstants.LEFT);
		data.jbtAll.setFont(FontGrid);
		data.jtxEdit = new JTextField();// створюю текстове поле для вводу
										// тексту для запитів вибірки
		data.jtxEdit.setFont(new Font("Calibri", Font.BOLD, 16));// встановлюю шрифт для текстового поля
		// --------------------------------------------
		JLabel label = new JLabel(" Введіть параметр пошуку: ");
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Calibri", Font.BOLD, 13));
		label.setForeground(Color.RED);
		label.setSize(100, 15);
		// --------------------------------------------
		JPanel SearchPanel = new JPanel(new GridLayout(1, 5, 1, 5));
		SearchPanel.add(label);
		SearchPanel.add(data.jtxEdit);
		SearchPanel.add(data.jbtGetByID);
		SearchPanel.add(data.jbtGetByName);
		SearchPanel.add(data.jbtAll);

		// поміщаю таблицю в панель
		// ---------------------------------------------------------
		JScrollPane scrollPane = new JScrollPane(data.aTable);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);

		// ----------------------------------------------
		JPanel Panel2 = new JPanel(new GridLayout(3, 1, 1, 5));
		Panel2.add(SearchPanel, BorderLayout.NORTH);
		Panel2.add(btPanel1, BorderLayout.CENTER);
		Panel2.add(statusBar, BorderLayout.SOUTH);
		getRootPane().setDefaultButton(data.cmdClose);
		// ---------------------------------------------------------
		setSize(1100, 680);// розміри основного вікна
		setResizable(true);
		getContentPane().add(new JScrollPane(scrollPane), BorderLayout.CENTER);

		getContentPane().add(Panel2, BorderLayout.SOUTH);
		getContentPane().add(tools, BorderLayout.NORTH);
		getContentPane().add(commandsPanel, BorderLayout.WEST);
		// --------------------------------------------
		data.aTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.isPopupTrigger())
					data.popupMenu.show(me.getComponent(), me.getX(), me.getY());
			}

			public void mouseReleased(MouseEvent me) {
				if (me.isPopupTrigger())
					data.popupMenu.show(me.getComponent(), me.getX(), me.getY());
			}
		});

		// ---------------------------------------------------------
		// при натисканні на кнопку отримати запис за ID, викликатиметься
		// метод
		// filterById()
		data.jbtGetByID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filterById();
			}
		});

		data.jbtGetByName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filterByName();
			}
		});
		data.jbtAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				all();
			}
		});
		// --------------------------------------------

		data.cmdNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add();
			}
		});

		data.cmdUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});

		data.cmdRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});

		data.cmdOpenAeroport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					openAeroport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		data.cmdOpenRozklad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					openRozklad();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		data.cmdPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				print();
			}
		});

		data.cmdClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(data.onClose, "Вийти з програми?") == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(data.onClose, "Вийти з програми?") == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		/**
		 * Реакція на подвійний клік мишею. Відкривається метод update();
		 */

		data.aTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					update();
				}
			}
		});
		data.cmdReport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int option = fc.showSaveDialog(MainForm.this);
				if (option == JFileChooser.APPROVE_OPTION) {
					String filename = fc.getSelectedFile().getName();
					String path = fc.getSelectedFile().getParentFile().getPath();

					int len = filename.length();
					String ext = "";
					String file = "";

					if (len > 4) {
						ext = filename.substring(len - 4, len);
					}

					if (ext.equals(".xls")) {
						file = path + "\\" + filename;
					} else {
						file = path + "\\" + filename + ".xls";
					}
					toExcel(data.aTable, new File(file));
				}
			}
		});
	}

	/**
	 * Генерує звіт. якщо у Excel не відкриватиме з першого разу то необхідно ще раз
	 * спробувати бо файл розуміє як пошкоджений і нібито він містить для Excel
	 * небезпеку
	 */

	public void toExcel(JTable table, File file) {
		try {
			AviaKompaniiTableModel model = (AviaKompaniiTableModel) table.getModel();
			FileWriter excel = new FileWriter(file);

			for (int i = 0; i < model.getColumnCount(); i++) {
				excel.write(model.getColumnName(i) + "\t");
			}

			excel.write("\n");

			for (int i = 0; i < model.getRowCount(); i++) {
				for (int j = 0; j < model.getColumnCount(); j++) {
					excel.write(model.getValueAt(i, j).toString() + "\t");
				}
				excel.write("\n");
			}

			excel.close();

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private AviaKompaniiTableModel getTableModel() {
		try {
			AviaKompaniiDao dao = new AviaKompaniiDao();
			final List<AviaKompanii> aviaKompaniis = dao.findAll();

			return new AviaKompaniiTableModel(aviaKompaniis);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "Помилка при одержанні даних:",
					JOptionPane.ERROR_MESSAGE);
		}
		return new AviaKompaniiTableModel(new ArrayList<AviaKompanii>(0));
	}

	private void print() {
		try {
			MessageFormat headerFormat = new MessageFormat("Сторінка {0}");
			MessageFormat footerFormat = new MessageFormat("- {0} -");
			data.aTable.print(JTable.PrintMode.FIT_WIDTH, headerFormat, footerFormat);
		} catch (PrinterException pe) {
			JOptionPane.showMessageDialog(this, pe.getMessage(), "Помилка при виведенні на друк:",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void openAeroport() throws Exception {
		int index = data.aTable.getSelectedRow();
		if (index == -1) {
			JOptionPane.showMessageDialog(MainForm.this, "Спочатку виберіть запис ", "Увага!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		Integer id = Integer.parseInt((String) (data.aTable.getValueAt(index, 0)));
		AviaKompaniiDao dao = new AviaKompaniiDao();
		AviaKompanii aviaKompanii = dao.findById(id);
		AeroportForm aeroportForm = new AeroportForm(aviaKompanii);
		aeroportForm.setVisible(true);
	}

	private void openRozklad() throws Exception {
		int index = data.aTable.getSelectedRow();
		if (index == -1) {
			JOptionPane.showMessageDialog(MainForm.this, "Спочатку виберіть запис ", "Увага!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		Integer id = Integer.parseInt((String) (data.aTable.getValueAt(index, 0)));
		AviaKompaniiDao dao = new AviaKompaniiDao();
		AviaKompanii aviaKompanii = dao.findById(id);
		RozkladForm aerokompaniiAeroportForm = new RozkladForm(aviaKompanii);
		aerokompaniiAeroportForm.setVisible(true);
	}

	private void add() {
		data.newAviaKompanii.setAerokompanii(new AviaKompanii());
		data.newAviaKompanii.setVisible(true);
		if (data.newAviaKompanii.getAerokompanii().getId() != null) {
			data.aviaKompaniiTableModel.addAerokompanii(data.newAviaKompanii.getAerokompanii());
		}
	}

	private void update() {
		int index = data.aTable.getSelectedRow();
		if (index == -1) {
			JOptionPane.showMessageDialog(MainForm.this, "Необхідно виділити запис в списку ", "Увага!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		AviaKompanii aviaKompanii = data.aviaKompaniiTableModel.getRowAerokompanii(index);
		if (aviaKompanii != null) {
			data.newAviaKompanii.setAerokompanii(aviaKompanii);
			data.newAviaKompanii.setTitle("Редагування даних");
			data.newAviaKompanii.setVisible(true);
			data.aviaKompaniiTableModel.refreshUpdatedTable();
		}
	}

	private void delete() {
		if (JOptionPane.showConfirmDialog(MainForm.this, "Ви хочете видалити інформацію?", "Видалення даних",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			int index = data.aTable.getSelectedRow();
			if (index == -1) {
				JOptionPane.showMessageDialog(MainForm.this, "Необхідно виділити запис в списку ", "Увага!",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				AviaKompanii c = data.aviaKompaniiTableModel.getRowAerokompanii(index);
				if (c != null) {

					AviaKompaniiDao dao = new AviaKompaniiDao();
					dao.del(c.getId());
					data.aviaKompaniiTableModel.removeRow(index);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(MainForm.this, e.getMessage());

			}
		}
	}

	// -----------------------------------------
	/**
	 * Виводить інформацію про авіакомпанії за ID
	 */
	private void filterById() {
		try {
			if (data.jtxEdit.getText().isEmpty()) {
				JOptionPane.showMessageDialog(MainForm.this, "Введіть ID авіакомпанії для пошуку.");
			}
			Integer id = Integer.valueOf(data.jtxEdit.getText());

			AviaKompanii aviaKompanii = new AviaKompaniiDao().findById(id);
			List<AviaKompanii> aviaKompaniis = new ArrayList<AviaKompanii>();
			aviaKompaniis.add(aviaKompanii);

			data.aviaKompaniiTableModel.setAerokompanii(aviaKompaniis);

			if (aviaKompanii == null)
				JOptionPane.showMessageDialog(MainForm.this, "Авіакомпанії з таким реєстраційним кодом не знайдено.",
						"Увага!", JOptionPane.WARNING_MESSAGE);
		} catch (Exception e) {

			e.printStackTrace();
		}
		data.jtxEdit.setText("");

	}

	/**
	 * Виводить інформацію вказаної авіакомпанії
	 */
	private void filterByName() {
		try {
			if (data.jtxEdit.getText().isEmpty()) {
				JOptionPane.showMessageDialog(MainForm.this, "Введіть назву авіакомпанії для пошуку.");
			}

			String text = data.jtxEdit.getText();

			List<AviaKompanii> aviaKompanii = new AviaKompaniiDao().findByName(text);
			data.aviaKompaniiTableModel.setAerokompanii(aviaKompanii);

			if (aviaKompanii == null) {
				JOptionPane.showMessageDialog(MainForm.this, "Авіакомпанія з такою назвою не знайдено!!!", "Увага!",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.jtxEdit.setText("");

	}

	public void all() {
		try {

			AviaKompaniiDao dao = new AviaKompaniiDao();
			final List<AviaKompanii> aviaKompanii = dao.findAll();

			data.aviaKompaniiTableModel.setAerokompanii(aviaKompanii);

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"Помилка при виведенні всіх записів з таблиці авіакомпаній: " + e.getMessage());
		}

	}
	// ------------------------------------------------
}