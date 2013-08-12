import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;


public class MysqlDB {
	
	private Connection getConnection() throws SQLException {
		String jdbcUrl = "jdbc:mysql://localhost/hirrit";
		String user = "hirrit";
		String passwd = "1234";
		return DriverManager.getConnection(jdbcUrl, user, passwd);
	}
	
	public ResultSet getResult(String query) throws SQLException {
		Statement stmt = this.getConnection().createStatement();
		return stmt.executeQuery(query);
	}
	
	public ResultSet getResult(String query, Map<Integer, Object> params) throws SQLException {
		PreparedStatement ps = this.getConnection().prepareStatement(query);
		for(int i=0; i<params.size(); i++){
			ps.setObject(i+1, params.get(i+1));
		}
		return ps.executeQuery();
	}
	
	public long setValue(String query, Map<Integer, Object> params) throws SQLException {
		PreparedStatement ps = this.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		for(int i=0; i<params.size(); i++){
			ps.setObject(i+1, params.get(i+1));
		}
		ps.executeUpdate();
		long id = 0L;
		ResultSet generatedKey = ps.getGeneratedKeys();
		while(generatedKey.next()){
			id = generatedKey.getLong(1);
		}
		return id;
	}

}
