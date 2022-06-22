package course.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import course.domain.Aeroport;

public class AeroportsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -2677658636846257452L;

	private String[] columns = new String[] { "Назва", "Країна", "Місто", "Адреса", "Телефон", "Довжина ЗПС",
			"Ширина ЗПС" };

	private List<Aeroport> aeroports;

	public AeroportsTableModel(List<Aeroport> aeroports) {
		this.aeroports = aeroports;
	}

	public void addAeroport(Aeroport aeroport) {
		aeroports.add(aeroport);
		fireTableRowsInserted(0, aeroports.size());
	}

	public Aeroport getRowAeroport(int rowIndex) {
		return aeroports.get(rowIndex);
	}

	public void removeRow(int rowIndex) {
		aeroports.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	public void refreshUpdatedTable() {
		fireTableRowsUpdated(0, aeroports.size());
	}

	public void setAeroport(List<Aeroport> aeroport) {
		int size = this.aeroports.size();
		this.aeroports = aeroport;
		fireTableRowsUpdated(0, aeroport.size() > size ? aeroport.size() : size);
		refreshUpdatedTable();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Aeroport g = aeroports.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return g.getnazva();
		case 1:
			return g.getkraina();
		case 2:
			return g.getmicto();
		case 3:
			return g.getadresa();
		case 4:
			return g.gettelephon();
		case 5:
			return g.getdovjuna();
		case 6:
			return g.getshuruna();
		}
		return "";
	}

	public int getRowCount() {
		return aeroports.size();
	}

	public String getColumnName(int columnIndex) {
		return columns[columnIndex];
	}

	public int getColumnCount() {
		return columns.length;
	}

}