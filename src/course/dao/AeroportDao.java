package course.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import course.domain.Aeroport;

/**
 * Цей клас містить методи необхідні для роботи із таблицею aeroport. В програмі
 * рядок із таблиці aeroport представлений класом domain.Aeroport. Використовує
 * клас DataAccessUtil для доступу до бази даних.
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
	private static final String SORT_BY_SIZE = "SELECT * FROM сargoairtransportation.aeroport where aeroID = ? ORDER BY dovjuna DESC";

	/**
	 * Добавляє новий рядок в таблицю aeroport використовуючи дані із обєкта
	 * aeroport.
	 * 
	 * @param aeroport
	 *            обєкт, що містить інформацію про аеропорт, що повинна бути
	 *            добавлена в базу даних.
	 * @return ключ добавленого рядка.
	 * @throws Exception
	 *             помилка при добавлені рядків.
	 */
	public int ins(Aeroport aeroport) throws Exception {
		Connection connection = DataAccessUtil.createConnection();// Отримуємо підключення до бази даних
		PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS); // Підготовлюємо
																													// SQL
																													// запит
																													// на
																													// виконання

		try { // Встановлюємо відповідні параметри запиту
				// В даному випадку число - перший параметр - це індекс ? в запиті
				// (починаючи з 1),
				// а другий параметр - це значення, що буде підставлено в шаблон
				// замість ?
			statement.setString(1, aeroport.getnazva());
			statement.setString(2, aeroport.getkraina());
			statement.setString(3, aeroport.getmicto());
			statement.setString(4, aeroport.getadresa());
			statement.setString(5, aeroport.gettelephon());
			statement.setInt(6, aeroport.getdovjuna());
			statement.setInt(7, aeroport.getshuruna());
			statement.setInt(8, aeroport.getAeroID());
			// виконуємо запит на обновлення бази даних
			statement.executeUpdate();
			// повертаємо ключ нового рядка
			return DataAccessUtil.getNewRowKey(statement);
		} finally {// закриваємо підключення до бази даних у будь-якому випадку
			DataAccessUtil.close(connection);
		}
	}

	/**
	 * Обновляємо значення вже існуючого рядка. В даному випадку поле id обєкта
	 * aeroport повинно бути встановлене.
	 * 
	 * @param aeroport
	 *            обєкт із новою інформацією про аеропорт.
	 * @throws Exception
	 *             помилка виконання обновлення.
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
	 * Знаходить та повертає інформацію про аеропорт із вказаним ключем. Якщо
	 * аеропорт не знайдений, то повертається null.
	 */
	public Aeroport findById(int AeroportID) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);

		try {
			statement.setInt(1, AeroportID);
			ResultSet rs = statement.executeQuery(); // executeQuery() повертає множину знайдених рядків.
			// Якщо є хоч один рядок, то переходимо на нього, зчитуємо і
			// повертаємо у вигляді
			// нового обєкта класу Aeroport
			if (rs.next()) {
				return getAeroportFromRow(rs);
			}
			return null;// повертаємо null, якщо рядок із вказаним ключем не знайдений
		} finally {
			DataAccessUtil.close(connection); // закриваємо підключення до бази даних у будь-якому випадку
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
	 * Виконує зчитування рядка із бази даних, та повертає обєкт класу Aeroport із
	 * відповідними даними.
	 * 
	 * @param rs
	 *            множина рядків із бази даних
	 * @return новий обєкт класу Aeroport із даними рядка, отриманого в результаті
	 *         запиту.
	 * @throws Exception
	 *             помилка зчитування рядка.
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