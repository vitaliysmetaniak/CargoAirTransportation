package course.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import course.domain.Rozklad;

public class RozkladsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -2677658636846257452L;

	private String[] columns = new String[] { "Початковий пункт", "Кінцевий пункт", "Рейс", "Дні", "Відправлення",
			"Прибуття" };

	private List<Rozklad> rozklads;

	public RozkladsTableModel(List<Rozklad> rozklad) {
		this.rozklads = rozklad;
	}

	public void addRozklad(Rozklad rozklad) {
		rozklads.add(rozklad);
		fireTableRowsInserted(0, rozklads.size());
	}

	public Rozklad getRowRozklad(int rowIndex) {
		return rozklads.get(rowIndex);
	}

	public void removeRow(int rowIndex) {
		rozklads.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	public void refreshUpdatedTable() {
		fireTableRowsUpdated(0, rozklads.size());
	}

	public void setRozklad(List<Rozklad> rozklad) {
		int size = this.rozklads.size();
		this.rozklads = rozklad;
		fireTableRowsUpdated(0, rozklad.size() > size ? rozklad.size() : size);
		refreshUpdatedTable();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Rozklad g = rozklads.get(rowIndex);
		switch (columnIndex) {

		case 0:
			return g.getpoch_pynkt();
		case 1:
			return g.getkinc_pynkt();
		case 2:
			return g.getreis();
		case 3:
			return g.getdni();
		case 4:
			return g.getvidprav();
		case 5:
			return g.getprubyttya();
		}
		return "";
	}

	public int getRowCount() {
		return rozklads.size();
	}

	public String getColumnName(int columnIndex) {
		return columns[columnIndex];
	}

	public int getColumnCount() {
		return columns.length;
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}
}
