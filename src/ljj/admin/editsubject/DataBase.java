package ljj.admin.editsubject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ljj.database.util.DBConnectionManager;

/**
 * 连接数据库
 * 
 * @author thinkpad_ljj
 *
 */
public class DataBase {

	protected Connection conn = null;		//Connection接口
	protected Statement stmt = null;		//Statement接口
	protected ResultSet rs = null;		//记录结果集
	protected PreparedStatement prepstmt = null;	//PreparedStatement接口
	protected String sqlStr;		//sql String
	protected boolean isConnect=true;	//与数据库连接标识
	
	public DataBase() {
		try
		{
			sqlStr = "";
			DBConnectionManager dcm = new DBConnectionManager();
			conn = dcm.getConnection();
			stmt = conn.createStatement();
		}
		catch (Exception e)
		{
			System.out.println(e);
			System.out.println("无法连接数据库");
			isConnect=false;
		}
		
	}

	public Statement getStatement() {
		return stmt;
	}

	public Connection getConnection() {
		return conn;
	}

	public PreparedStatement getPreparedStatement() {
		return prepstmt;
	}

	public ResultSet getResultSet() {
		return rs;
	}


	public String getSql() {
		return sqlStr;
	}

	public void close() throws SQLException {
		if ( stmt != null )
		{
			stmt.close();
			stmt = null;
		}
		conn.close();
		conn = null;
	}
	
}
