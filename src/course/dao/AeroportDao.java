package course.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import course.domain.Aeroport;

/**
 * ��� ���� ������ ������ �������� ��� ������ �� �������� aeroport. � �������
 * ����� �� ������� aeroport ������������� ������ domain.Aeroport. �����������
 * ���� DataAccessUtil ��� ������� �� ���� �����.
 */
public class AeroportDao {

	private static final String INSERT_QUERY = "insert into aeroport "
			+ "(nazva, kraina, micto, adresa, telephon, dovjuna, shuruna, aeroID) " + "values (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_QUERY = "update aeroport "
			+ "set nazva = ?, kraina = ?, micto = ?, adresa = ?, telephon = ?, " + "dovjuna = ?, shuruna = ? "
			+ "where AeroportID = ?";
	private static final String DELETE_QUERY = "delete from aeroport where AeroportID = ?";
	private static final String SELECT_QUERY = "select * from aeroport  where AeroportID = ?";
	private static final String SELECT_ALL_QUERY = "select * from aeroport";
	private static final String SELECT_BY_AEROKOMPANII_QUERY = "select * from aeroport where aeroID = ?";
	private static final String SORT_BY_SIZE = "SELECT * FROM �argoairtransportation.aeroport where aeroID = ? ORDER BY dovjuna DESC";

	/**
	 * �������� ����� ����� � ������� aeroport �������������� ��� �� �����
	 * aeroport.
	 * 
	 * @param aeroport
	 *            ����, �� ������ ���������� ��� ��������, �� ������� ����
	 *            ��������� � ���� �����.
	 * @return ���� ����������� �����.
	 * @throws Exception
	 *             ������� ��� �������� �����.
	 */
	public int ins(Aeroport aeroport) throws Exception {
		Connection connection = DataAccessUtil.createConnection();// �������� ���������� �� ���� �����
		PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS); // ϳ�����������
																													// SQL
																													// �����
																													// ��
																													// ���������

		try { // ������������ ������� ��������� ������
				// � ������ ������� ����� - ������ �������� - �� ������ ? � �����
				// (��������� � 1),
				// � ������ �������� - �� ��������, �� ���� ���������� � ������
				// ������ ?
			statement.setString(1, aeroport.getnazva());
			statement.setString(2, aeroport.getkraina());
			statement.setString(3, aeroport.getmicto());
			statement.setString(4, aeroport.getadresa());
			statement.setString(5, aeroport.gettelephon());
			statement.setInt(6, aeroport.getdovjuna());
			statement.setInt(7, aeroport.getshuruna());
			statement.setInt(8, aeroport.getAeroID());
			// �������� ����� �� ���������� ���� �����
			statement.executeUpdate();
			// ��������� ���� ������ �����
			return DataAccessUtil.getNewRowKey(statement);
		} finally {// ��������� ���������� �� ���� ����� � ����-����� �������
			DataAccessUtil.close(connection);
		}
	}

	/**
	 * ���������� �������� ��� ��������� �����. � ������ ������� ���� id �����
	 * aeroport ������� ���� �����������.
	 * 
	 * @param aeroport
	 *            ���� �� ����� ����������� ��� ��������.
	 * @throws Exception
	 *             ������� ��������� ����������.
	 */
	public void upd(Aeroport aeroport) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);

		try {
			statement.setString(1, aeroport.getnazva());
			statement.setString(2, aeroport.getkraina());
			statement.setString(3, aeroport.getmicto());
			statement.setString(4, aeroport.getadresa());
			statement.setString(5, aeroport.gettelephon());
			statement.setInt(6, aeroport.getdovjuna());
			statement.setInt(7, aeroport.getshuruna());
			statement.setInt(8, aeroport.getId());
			statement.executeUpdate();
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public void del(int AeroportID) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);

		try {
			statement.setInt(1, AeroportID);
			statement.executeUpdate();
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	/**
	 * ��������� �� ������� ���������� ��� �������� �� �������� ������. ����
	 * �������� �� ���������, �� ����������� null.
	 */
	public Aeroport findById(int AeroportID) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);

		try {
			statement.setInt(1, AeroportID);
			ResultSet rs = statement.executeQuery(); // executeQuery() ������� ������� ��������� �����.
			// ���� � ��� ���� �����, �� ���������� �� �����, ������� �
			// ��������� � ������
			// ������ ����� ����� Aeroport
			if (rs.next()) {
				return getAeroportFromRow(rs);
			}
			return null;// ��������� null, ���� ����� �� �������� ������ �� ���������
		} finally {
			DataAccessUtil.close(connection); // ��������� ���������� �� ���� ����� � ����-����� �������
		}
	}

	public List<Aeroport> findAll() throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);

		try {
			ResultSet rs = statement.executeQuery();
			List<Aeroport> result = new ArrayList<Aeroport>();
			while (rs.next()) {
				result.add(getAeroportFromRow(rs));
			}
			return result;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public List<Aeroport> sortBySize(int groupId) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(SORT_BY_SIZE);
		try {
			statement.setInt(1, groupId);
			ResultSet rs = statement.executeQuery();
			List<Aeroport> result = new ArrayList<Aeroport>();
			while (rs.next()) {
				result.add(getAeroportFromRow(rs));
			}
			return result;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public List<Aeroport> findByAerokompanii(int groupId) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(SELECT_BY_AEROKOMPANII_QUERY);
		try {
			statement.setInt(1, groupId);
			ResultSet rs = statement.executeQuery();
			List<Aeroport> result = new ArrayList<Aeroport>();
			while (rs.next()) {
				result.add(getAeroportFromRow(rs));
			}
			return result;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	/**
	 * ������ ���������� ����� �� ���� �����, �� ������� ���� ����� Aeroport ��
	 * ���������� ������.
	 * 
	 * @param rs
	 *            ������� ����� �� ���� �����
	 * @return ����� ���� ����� Aeroport �� ������ �����, ���������� � ���������
	 *         ������.
	 * @throws Exception
	 *             ������� ���������� �����.
	 */
	private static Aeroport getAeroportFromRow(ResultSet rs) throws Exception {
		Aeroport aeroport = new Aeroport();
		aeroport.setId(rs.getInt(1));
		aeroport.setnazva(rs.getString(2));
		aeroport.setkraina(rs.getString(3));
		aeroport.setmicto(rs.getString(4));
		aeroport.setadresa(rs.getString(5));
		aeroport.settelephon(rs.getString(6));
		aeroport.setdovjuna(rs.getInt(7));
		aeroport.setshuruna(rs.getInt(8));
		return aeroport;
	}
}