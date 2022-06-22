package course.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ��� ���� ������ ���� ������ ��� ���������� �� ���� �����.
 */
public class DataAccessUtil {
	/**
	 * ���������, �� ������ ����� ����� JDBC �������� ��� ���������� �� ���� �����
	 * M�SQL.
	 */
	private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";

	private static final String CONNECTION_USERNAME = "root";
	private static final String CONNECTION_PASSWORD = "";

	/**
	 * JDBC ����, ��� ���������� �� ������� ���� �����. ������ ���� �����
	 * ��������� �� �������� �� ������� 127.0.0.1. ���� ����� ����������
	 * �argoAirTransportation.
	 */
	private static final String CONNECTION_URL = "jdbc:mysql://127.0.0.1:3306/�argoAirTransportation";

	/**
	 * �������� ���� ���������� �� ���� ����� �������������� �������� ��������.
	 * 
	 * @return ���� ����������
	 * @throws SQLException
	 *             ������� �������� �������� ��� ���������� �� ���� �����.
	 */
	public static Connection createConnection() throws SQLException {
		try {
			Class.forName(DRIVER_CLASS);

		} catch (Exception e) {
			System.err.println("Driver class is not found, cause:" + e.getMessage());
		}
		return DriverManager.getConnection(CONNECTION_URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
	}

	/**
	 * ������� ���������� �� ���� �����.
	 * 
	 * @param c
	 *            ���������� �� ���� �����.
	 */
	public static void close(Connection c) {
		try {
			if (c != null && !c.isClosed()) {
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������� ������� ���������� ��������� ������ �� ������.
	 * 
	 * @param rs
	 *            ������� ����������.
	 */
	public static void close(ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������� � ���������� � ������� ����������.
	 * 
	 * @param c
	 *            ���������� �� ���� �����.
	 * @param rs
	 *            ������� ����������.
	 */
	public static void close(Connection c, ResultSet rs) {
		close(c);
		close(rs);
	}

	/**
	 * �������� ��������� ���� �������������� ���� ��� ������ �����.
	 */
	public static int getNewRowKey(PreparedStatement statement) throws Exception {
		ResultSet rs = statement.getGeneratedKeys();
		if (rs.next()) {
			return rs.getInt(1);
		}
		return -1;
	}

}
