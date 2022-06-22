package course.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import course.domain.Rozklad;

public class RozkladDao {

	private static final String INSERT_QUERY = "insert into rozklad "
			+ "(RozkladAerokompaniiID, poch_pynkt, kinc_pynkt, reis, " + "dni, vidprav, prubyttya) "
			+ "values (?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_QUERY = "update rozklad set "
			+ "poch_pynkt = ?, kinc_pynkt = ?, reis = ?, dni = ?, vidprav = ?, prubyttya = ? " + "where RozkladID = ?";
	private static final String DELETE_QUERY = "delete from rozklad where RozkladID = ?";
	private static final String SELECT_QUERY = "select * from rozklad  where RozkladID = ?";
	private static final String SELECT_ALL_QUERY = "SELECT  * FROM ñargoairtransportation.rozklad WHERE  RozkladAerokompaniiID= ? "
			+ "AND (dni LIKE '%Ïí%' OR dni LIKE  '%Ñð%'  OR dni LIKE '%Ïò%' OR dni LIKE'%Íä%') "
			+ "order by length(dni) desc";
	private static final String SELECT_BY_AEROKOMPANII_QUERY = "select * from rozklad "
			+ "where RozkladAerokompaniiID = ?";

	public int ins(Rozklad rozklad) throws Exception {

		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);

		try {
			statement.setInt(1, rozklad.getRozkladAerokompaniiID());
			statement.setString(2, rozklad.getpoch_pynkt());
			statement.setString(3, rozklad.getkinc_pynkt());
			statement.setString(4, rozklad.getreis());
			statement.setString(5, rozklad.getdni());
			statement.setString(6, rozklad.getvidprav());
			statement.setString(7, rozklad.getprubyttya());
			statement.executeUpdate();

			return DataAccessUtil.getNewRowKey(statement);
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public void upd(Rozklad rozklad) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);

		try {

			statement.setString(1, rozklad.getpoch_pynkt());
			statement.setString(2, rozklad.getkinc_pynkt());
			statement.setString(3, rozklad.getreis());
			statement.setString(4, rozklad.getdni());
			statement.setString(5, rozklad.getvidprav());
			statement.setString(6, rozklad.getprubyttya());
			statement.setInt(7, rozklad.getId());
			statement.executeUpdate();
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public void del(int RozkladID) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);

		try {
			statement.setInt(1, RozkladID);
			statement.executeUpdate();
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public Rozklad findById(int RozkladID) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);

		try {
			statement.setInt(1, RozkladID);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return getRozkladFromRow(rs);
			}
			return null;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public List<Rozklad> findByAerokompanii(int groupId) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(SELECT_BY_AEROKOMPANII_QUERY);

		try {
			statement.setInt(1, groupId);
			ResultSet rs = statement.executeQuery();
			List<Rozklad> result = new ArrayList<Rozklad>();
			while (rs.next()) {
				result.add(getRozkladFromRow(rs));
			}
			return result;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public List<Rozklad> findByDay(int groupId) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);

		try {
			statement.setInt(1, groupId);
			ResultSet rs = statement.executeQuery();
			List<Rozklad> result = new ArrayList<Rozklad>();
			while (rs.next()) {
				result.add(getRozkladFromRow(rs));
			}
			return result;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	private static Rozklad getRozkladFromRow(ResultSet rs) throws Exception {
		Rozklad rozklad = new Rozklad();
		rozklad.setId(rs.getInt(1));
		rozklad.setRozkladAerokompaniiID(rs.getInt(2));
		rozklad.setpoch_pynkt(rs.getString(3));
		rozklad.setkinc_pynkt(rs.getString(4));
		rozklad.setreis(rs.getString(5));
		rozklad.setdni(rs.getString(6));
		rozklad.setvidprav(rs.getString(7));
		rozklad.setprubyttya(rs.getString(8));
		return rozklad;
	}
}