package course.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import course.domain.AviaKompanii;

public class AviaKompaniiTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -2677658636846257452L;

	private String[] columns = new String[] { "ID", "Назва", "Місто", "Адреса", "Телефон", "Факс", "Рік заснування" };

	private List<AviaKompanii> aviaKompaniis;

	public AviaKompaniiTableModel(List<AviaKompanii> aviaKompanii) {
		this.aviaKompaniis = aviaKompanii;
	}

	public void addAerokompanii(AviaKompanii aviaKompanii) {
		aviaKompaniis.add(aviaKompanii);
		fireTableRowsInserted(0, aviaKompaniis.size());
	}

	public AviaKompanii getRowAerokompanii(int rowIndex) {
		return aviaKompaniis.get(rowIndex);
	}

	public void removeRow(int rowIndex) {
		aviaKompaniis.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	public void refreshUpdatedTable() {
		fireTableRowsUpdated(0, aviaKompaniis.size());
	}

	public void setAerokompanii(List<AviaKompanii> aviaKompanii) {
		int size = this.aviaKompaniis.size();
		this.aviaKompaniis = aviaKompanii;
		fireTableRowsUpdated(0, aviaKompanii.size() > size ? aviaKompanii.size() : size);
		refreshUpdatedTable();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		AviaKompanii g = aviaKompaniis.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return Integer.toString(g.getId());
		case 1:
			return g.getnazva();
		case 2:
			return g.getmisto();
		case 3:
			return g.getadresa();
		case 4:
			return g.gettelephon();
		case 5:
			return g.getfax();
		case 6:
			return Integer.toString(g.getrik_zasnyvanna());
		}
		return "";
	}

	public int getRowCount() {
		return aviaKompaniis.size();
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