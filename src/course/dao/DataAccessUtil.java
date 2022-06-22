package course.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Цей клас містить набір методів для підключення до бази даних.
 */
public class DataAccessUtil {
	/**
	 * Константа, що містить назву класу JDBC драйвера для підключення до бази даних
	 * MуSQL.
	 */
	private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";

	private static final String CONNECTION_USERNAME = "root";
	private static final String CONNECTION_PASSWORD = "";

	/**
	 * JDBC шлях, для підключення до потрібної бази даних. Сервер бази даних
	 * розміщений на комп’ютері із адресом 127.0.0.1. База даних називається
	 * СargoAirTransportation.
	 */
	private static final String CONNECTION_URL = "jdbc:mysql://127.0.0.1:3306/СargoAirTransportation";

	/**
	 * Створити нове підключення до бази даних використовуючи значення констант.
	 * 
	 * @return нове підключення
	 * @throws SQLException
	 *             помилка загрузки драйвера або підключення до бази даних.
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
	 * Закрити підключення до бази даних.
	 * 
	 * @param c
	 *            підключення до бази даних.
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
	 * Закрити множину результатів виконнаня запиту на вибірку.
	 * 
	 * @param rs
	 *            множина результатів.
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
	 * Закрити і підплючення і множину результатів.
	 * 
	 * @param c
	 *            підключення до бази даних.
	 * @param rs
	 *            множина результатів.
	 */
	public static void close(Connection c, ResultSet rs) {
		close(c);
		close(rs);
	}

	/**
	 * Отримати первинний ключ цілочисельного типу для нового рядка.
	 */
	public static int getNewRowKey(PreparedStatement statement) throws Exception {
		ResultSet rs = statement.getGeneratedKeys();
		if (rs.next()) {
			return rs.getInt(1);
		}
		return -1;
	}

}
