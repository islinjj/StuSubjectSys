package ljj.database.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库连接工具
 * 
 * @author thinkpad_ljj
 *
 */
public class DBConnectionManager {

	private String driverName = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost/stusubjectsys?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
	private String user = "root";
	private String password = "123456";

	public void setDriverName(String newDriverName) {
		driverName = newDriverName;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setUrl(String newUrl) {
		url = newUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUser(String newUser) {
		user = newUser;
	}

	public String getUser() {
		return user;
	}

	public void setPassword(String newPassword) {
		password = newPassword;
	}

	public String getPassword() {
		return password;
	}

	public Connection getConnection() {
		try {
			Class.forName(driverName);
			return DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
