package course.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import course.domain.AviaKompanii;

public class AviaKompaniiDao {

	private static final String INSERT_QUERY = "insert into aerokompanii "
			+ "(nazva, misto, adresa, telephon, fax, rik_zasnyvanna) values (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_QUERY = "update aerokompanii "
			+ "set nazva = ?, misto = ?, adresa = ?,  telephon = ?, fax = ?, rik_zasnyvanna = ? "
			+ "where AerokompaniiID = ?";
	private static final String DELETE_QUERY = "delete from aerokompanii where AerokompaniiID = ?";
	private static final String SELECT_QUERY = "select * from aerokompanii where AerokompaniiID = ?";
	private static final String SELECT_ALL_QUERY = "select * from aerokompanii";

	public int ins(AviaKompanii aviaKompanii) throws Exception {

		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);

		try {
			statement.setString(1, aviaKompanii.getnazva());
			statement.setString(2, aviaKompanii.getmisto());
			statement.setString(3, aviaKompanii.getadresa());
			statement.setString(4, aviaKompanii.gettelephon());
			statement.setString(5, aviaKompanii.getfax());
			statement.setInt(6, aviaKompanii.getrik_zasnyvanna());
			statement.executeUpdate();

			return DataAccessUtil.getNewRowKey(statement);
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public void upd(AviaKompanii aviaKompanii) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);

		try {
			statement.setString(1, aviaKompanii.getnazva());
			statement.setString(2, aviaKompanii.getmisto());
			statement.setString(3, aviaKompanii.getadresa());
			statement.setString(4, aviaKompanii.gettelephon());
			statement.setString(5, aviaKompanii.getfax());
			statement.setInt(6, aviaKompanii.getrik_zasnyvanna());
			statement.setInt(7, aviaKompanii.getId());
			statement.executeUpdate();
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public void del(int AerokompaniiID) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);

		try {
			statement.setInt(1, AerokompaniiID);
			statement.executeUpdate();
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public AviaKompanii findById(int AerokompaniiID) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);

		try {
			statement.setInt(1, AerokompaniiID);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return getAerokompaniiFromRow(rs);
			}
			return null;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public List<AviaKompanii> findAll() throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);

		try {
			ResultSet rs = statement.executeQuery();
			List<AviaKompanii> result = new ArrayList<AviaKompanii>();
			while (rs.next()) {
				result.add(getAerokompaniiFromRow(rs));
			}
			return result;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public List<AviaKompanii> findByName(String aerokompaniiName) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement("select * from aerokompanii where nazva = ?");

		try {
			statement.setString(1, "%" + aerokompaniiName + "%");
			ResultSet rs = statement.executeQuery();
			List<AviaKompanii> result = new ArrayList<AviaKompanii>();
			while (rs.next()) {
				result.add(getAerokompaniiFromRow(rs));
			}
			return result;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	private static AviaKompanii getAerokompaniiFromRow(ResultSet rs) throws Exception {
		AviaKompanii aviaKompanii = new AviaKompanii();
		aviaKompanii.setId(rs.getInt(1));
		aviaKompanii.setnazva(rs.getString(2));
		aviaKompanii.setmisto(rs.getString(3));
		aviaKompanii.setadresa(rs.getString(4));
		aviaKompanii.settelephon(rs.getString(5));
		aviaKompanii.setfax(rs.getString(6));
		aviaKompanii.setrik_zasnyvanna(rs.getInt(7));
		return aviaKompanii;
	}
}
