package course.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import course.Main;
import course.dao.RozkladDao;
import course.domain.AviaKompanii;
import course.domain.Rozklad;

public class RozkladForm extends JDialog implements ActionListener {
	private static final long serialVersionUID = 5100838031842180129L;

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == data.add) {
			add();
		} else if (e.getSource() == data.update) {
			update();
		} else if (e.getSource() == data.delete) {
			delete();
		} else if (e.getSource() == data.print) {
			print();
		} else if (e.getSource() == data.onClose) {
			onClose();
		}
	}

	Data data = new Data(new NewRozklad(), new JPopupMenu());

	void mainMenu() {
		Font fontMenu = new Font("Calibri", Font.PLAIN, 13);
		JMenuBar MenuBar = new JMenuBar();
		JMenu mFile = new JMenu("Файл");
		mFile.setFont(fontMenu);
		ImageIcon icon = Main.createIcon("/img/add.png");
		data.add = new JMenuItem("Додати ", icon);
		data.add.setToolTipText("Додати новий запис до бази");
		data.add.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		data.add.setFont(fontMenu);
		data.add.addActionListener(this);
		mFile.add(data.add);

		ImageIcon icon1 = Main.createIcon("/img/edit.png");
		data.update = new JMenuItem("Внести зміни", icon1);
		data.update.setToolTipText("Внести зміни у вже створений запис");
		data.update.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		data.update.setFont(fontMenu);
		data.update.addActionListener(this);
		mFile.add(data.update);

		ImageIcon icon2 = Main.createIcon("/img/delete.png");
		data.delete = new JMenuItem("Видалити ", icon2);
		data.delete.setToolTipText("Видалити запис у базі");
		data.delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.SHIFT_MASK));
		data.delete.setFont(fontMenu);
		data.delete.addActionListener(this);
		mFile.add(data.delete);

		mFile.addSeparator();

		ImageIcon icon3 = Main.createIcon("/img/print.png");
		data.print = new JMenuItem("Вивести на друк", icon3);
		data.print.setToolTipText("Роздрукувати інформацію з таблиці");
		data.print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		data.print.setFont(fontMenu);
		data.print.addActionListener(this);
		mFile.add(data.print);

		mFile.addSeparator();

		ImageIcon icon4 = Main.createIcon("/img/Home.png");
		data.onClose = new JMenuItem("Закрити вікно", icon4);
		data.onClose.setToolTipText("Перейти до головного вікна");
		data.onClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		data.onClose.setFont(fontMenu);
		data.onClose.addActionListener((ActionListener) this);
		mFile.add(data.onClose);

		MenuBar.add(mFile);

		setJMenuBar(MenuBar);

	}

	public RozkladForm(AviaKompanii c) throws IOException {
		this.data.aviaKompaniis = c;
		// заголовок вікна
		setTitle("Розклад руху літаків авіакомпанії: " + c.getnazva());
		mainMenu();
		// робимо вікно фіксованого розміру
		setLocationByPlatform(true);

		// створення елементів контекстного меню
		Font pMenu = new Font("Calibri", Font.PLAIN, 13);

		ImageIcon kmNewicon = Main.createIcon("/img/add.png");
		JMenuItem cmdNew = new JMenuItem("Новий запис", kmNewicon);
		cmdNew.setFont(pMenu);
		cmdNew.addActionListener(this);
		data.popupMenu.add(cmdNew);

		cmdNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add();
			}
		});

		ImageIcon kmUpdateicon = Main.createIcon("/img/edit.png");
		JMenuItem cmdUpdate = new JMenuItem("Редагувати запис", kmUpdateicon);
		cmdUpdate.setFont(pMenu);
		cmdUpdate.addActionListener(this);
		data.popupMenu.add(cmdUpdate);

		cmdUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});

		data.popupMenu.addSeparator();

		ImageIcon kmRemoveicon = Main.createIcon("/img/delete.png");
		JMenuItem cmdRemove = new JMenuItem("Видалити запис", kmRemoveicon);
		cmdRemove.setFont(pMenu);
		cmdRemove.addActionListener(this);
		data.popupMenu.add(cmdRemove);

		cmdRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});

		// створення панелі інструментів
		JToolBar tools = new JToolBar();
		Color ColorBar = Color.DARK_GRAY;
		tools.setBackground(ColorBar);

		// створення та додавання елементів панелі інструментів
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

		data.cmdPrint = new JButton(Main.createIcon("/img/print.png"));
		data.cmdPrint.setToolTipText("Роздрукувати інформацію");
		tools.add(data.cmdPrint);

		data.cmdPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				print();
			}
		});
		tools.addSeparator();
		data.cmdDay = new JButton(Main.createIcon("/img/day.png"));
		data.cmdDay.setToolTipText("Рейси у непарні дні");
		tools.add(data.cmdDay);

		data.cmdDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				day(data.aviaKompaniis);
			}
		});
		data.cmdAllDay = new JButton(Main.createIcon("/img/allDay.png"));
		data.cmdAllDay.setToolTipText("Усі рейси");
		tools.add(data.cmdAllDay);

		data.cmdAllDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				allDay(data.aviaKompaniis);
			}
		});
		tools.addSeparator();
		data.cmdClose = new JButton(Main.createIcon("/img/Home.png"));
		data.cmdClose.setToolTipText("Закрити вікно");
		tools.add(data.cmdClose);

		data.cmdClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onClose();
			}
		});
		// ------------------------------------------------------
		// table
		data.rozkladTableModel = getTableModel(c);
		data.rozkladTable = new JTable(data.rozkladTableModel);
		data.rozkladTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		data.rozkladTable.setPreferredScrollableViewportSize(new Dimension(870, 300));
		data.rozkladTable.setBackground(Color.WHITE);
		data.rozkladTable.setForeground(Color.darkGray);
		data.rozkladTable.setSelectionBackground(Color.darkGray);
		data.rozkladTable.setSelectionForeground(Color.RED);
		data.rozkladTable.setRowHeight(30);
		data.rozkladTable.getColumnModel().getColumn(0).setMinWidth(150);
		data.rozkladTable.getColumnModel().getColumn(1).setMinWidth(200);
		data.rozkladTable.getColumnModel().getColumn(2).setMinWidth(200);
		data.rozkladTable.getColumnModel().getColumn(3).setMinWidth(100);
		data.rozkladTable.getColumnModel().getColumn(4).setMinWidth(100);
		data.rozkladTable.getColumnModel().getColumn(5).setMinWidth(120);
		Font FontGrid = new Font("Calibri", Font.PLAIN, 12);
		data.rozkladTable.setFont(FontGrid);
		data.rozkladTable.setOpaque(false);
		// ------------------------------------------------------
		data.cmdClose = new JButton("Головна форма", Main.createIcon("/img/Home.png"));
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
		data.cmdPrint = new JButton("Вивести на друк", Main.createIcon("/img/print.png"));
		data.cmdPrint.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		data.cmdPrint.setHorizontalAlignment(SwingConstants.LEFT);
		data.cmdPrint.setFont(FontGrid);

		data.cmdDay = new JButton("Непарні дні", Main.createIcon("/img/day.png"));
		data.cmdDay.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		data.cmdDay.setHorizontalAlignment(SwingConstants.LEFT);
		data.cmdDay.setFont(FontGrid);
		data.cmdAllDay = new JButton("Тиждень", Main.createIcon("/img/allDay.png"));
		data.cmdAllDay.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		data.cmdAllDay.setHorizontalAlignment(SwingConstants.LEFT);
		data.cmdAllDay.setFont(FontGrid);

		// --------------------------------------------
		JPanel commandsPanel = new JPanel();
		JPanel commandsPanel1 = new JPanel(new GridLayout(7, 1, 5, 5));
		commandsPanel1.add(data.cmdNew);
		commandsPanel1.add(data.cmdUpdate);
		commandsPanel1.add(data.cmdRemove);
		commandsPanel1.add(data.cmdPrint);
		commandsPanel1.add(data.cmdDay);
		commandsPanel1.add(data.cmdAllDay);
		commandsPanel1.add(Box.createVerticalGlue());

		commandsPanel.add(commandsPanel1);
		// --------------------------------------------

		// поміщаю таблицю в панель
		// ---------------------------------------------------------
		JScrollPane scrollPane = new JScrollPane(data.rozkladTable);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);

		// ---------------------------------------------------------

		setSize(1000, 550);// розміри основного вікна

		getContentPane().add(tools, BorderLayout.NORTH);
		getRootPane().setDefaultButton(data.cmdClose);
		getContentPane().add(new JScrollPane(scrollPane), BorderLayout.CENTER);
		getContentPane().add(commandsPanel, BorderLayout.WEST);
		// ------------------------------------------------------
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
		data.cmdDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				day(data.aviaKompaniis);
			}
		});
		data.cmdAllDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				allDay(data.aviaKompaniis);
			}
		});

		data.cmdRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});

		data.cmdPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				print();
			}
		});

		data.cmdClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onClose();
			}
		});

		data.rozkladTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.isPopupTrigger())
					data.popupMenu.show(me.getComponent(), me.getX(), me.getY());
			}

			public void mouseReleased(MouseEvent me) {
				if (me.isPopupTrigger())
					data.popupMenu.show(me.getComponent(), me.getX(), me.getY());
			}
		});

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				onClose();
			}
		});
		/**
		 * Реакція на подвійний клік мишею. Відкривається метод update();
		 */

		data.rozkladTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					update();
				}
			}
		});

	}

	private RozkladsTableModel getTableModel(AviaKompanii c) {
		try {
			RozkladDao dao = new RozkladDao();
			final List<Rozklad> rozklad = dao.findByAerokompanii(c.getId());

			return new RozkladsTableModel(rozklad);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "Помилка при одержанні даних:",
					JOptionPane.ERROR_MESSAGE);
		}
		return new RozkladsTableModel(new ArrayList<Rozklad>(0));
	}

	private void onClose() {
		dispose();
	}

	private void add() {
		Rozklad rozklad = new Rozklad();
		rozklad.setRozkladAerokompaniiID(this.data.aviaKompaniis.getId());
		data.newRozklad.setRozklad(rozklad);
		data.newRozklad.setVisible(true);
		if (data.newRozklad.getRozklad().getId() != null) {
			data.rozkladTableModel.addRozklad(data.newRozklad.getRozklad());
		}
	}

	private void day(AviaKompanii c) {
		try {
			RozkladDao dao = new RozkladDao();
			final List<Rozklad> rozklad = dao.findByDay(c.getId());

			data.rozkladTableModel.setRozklad(rozklad);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "Помилка при одержанні даних:",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void allDay(AviaKompanii c) {
		try {
			RozkladDao dao = new RozkladDao();
			final List<Rozklad> rozklad = dao.findByAerokompanii(c.getId());

			data.rozkladTableModel.setRozklad(rozklad);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "Помилка при одержанні даних:",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void update() {
		int index = data.rozkladTable.getSelectedRow();

		if (index == -1) {
			JOptionPane.showMessageDialog(RozkladForm.this, "Спочатку виберіть запис", "Увага!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		Rozklad rozklad = data.rozkladTableModel.getRowRozklad(index);
		if (rozklad != null) {
			data.newRozklad.setRozklad(rozklad);
			data.newRozklad.setTitle("Редагування");
			data.newRozklad.setVisible(true);
			data.rozkladTableModel.refreshUpdatedTable();
		}
	}

	private void delete() {
		if (JOptionPane.showConfirmDialog(RozkladForm.this, "Ви хочете видалити рейс?", "Видалення",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			int index = data.rozkladTable.getSelectedRow();
			if (index == -1) {
				JOptionPane.showMessageDialog(RozkladForm.this, "Необхідно виділити запис в списку ", "Увага!",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				Rozklad g = data.rozkladTableModel.getRowRozklad(index);
				if (g != null) {
					RozkladDao dao = new RozkladDao();
					dao.del(g.getId());
					data.rozkladTableModel.removeRow(index);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(RozkladForm.this, e.getMessage());
			}
		}
	}

	private void print() {
		try {
			MessageFormat headerFormat = new MessageFormat("Список рейсів");
			MessageFormat footerFormat = new MessageFormat("~ {0} ~");
			data.rozkladTable.print(JTable.PrintMode.FIT_WIDTH, headerFormat, footerFormat);
		} catch (PrinterException pe) {
			JOptionPane.showMessageDialog(this, pe.getMessage(), "Помилка при виведенні на друк:",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}