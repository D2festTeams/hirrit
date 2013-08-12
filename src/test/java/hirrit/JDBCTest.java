package hirrit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTest {

	public static void main(String[] args) {
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		Connection conn = null;
		try{
			conn = DriverManager.getConnection("jdbc:mysql://localhost/hirrit", "hirrit", "1234");
		} catch(SQLException e){
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
		
		Statement stmt = null;
		ResultSet rs = null;
		try{
			String query = "SELECT * FROM TEST";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()){
				System.out.println(">>> " + rs.getString("id") + " / " + rs.getString("name") + " / " + rs.getString("addr"));
			}
			
			query = "INSERT INTO TEST (NAME, ADDR) VALUES (?, ?)";
			PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, "햄햄");
			ps.setString(2, "이천시 갈산동 우성아파");
			int result = ps.executeUpdate();
			ResultSet res = ps.getGeneratedKeys();
			while(res.next()){
				System.out.println(">>>>>> key : " + res.getObject(1));
			}
			
			query = "select * from source_info where commit_id = ? and path = ? ";
			ps = conn.prepareStatement(query);
			ps.setObject(1, 1234);
			ps.setObject(2, "/ham/ham");
			rs = ps.executeQuery();
			while(rs.next()){
				//System.out.println(">>> " + rs.getObject("commit_id"));
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}
