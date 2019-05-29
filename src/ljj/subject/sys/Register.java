package ljj.subject.sys;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ljj.admin.editsubject.DataBase;

/**
 * 学生注册
 * 
 * Servlet implementation class Register
 */
@WebServlet(name = "Reg", description = "Reg", urlPatterns = { "/Reg" })
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * 学生注册学号等信息，并写入数据库
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		DataBase db = new DataBase();

		//从注册页面获得学生注册信息
		String stuName = request.getParameter("sRegName");
		String stuNo = request.getParameter("sRegNo");
		String stuPwd = request.getParameter("sRegPwd");
		
		//查询学号语句
		String sql = "select sNo from student";
		
		try {
			Connection conn= db.getConnection();
			Statement stmt= conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			String sqlInsert = "insert into student (sName,sNo,sPwd) value(?,?,?)";
			PreparedStatement prestmt = conn.prepareStatement(sqlInsert);
			
			//需要判断账号是否已经注册
			while(rs.next()) {
				//从数据库获得已注册的学号
				String sNo = rs.getString(1);
				
				//若存在了学号则反馈给用户该学号已经注册
				if(sNo.equals(stuNo)) {
					RequestDispatcher dispatcher = request.getRequestDispatcher("exitUser.jsp");
					dispatcher.forward(request, response);
				}
			}
			
			//用户不存在就存入数据库
			prestmt.setString(1, stuName);
			prestmt.setString(2, stuNo);
			prestmt.setString(3, stuPwd);
			prestmt.executeUpdate();
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("regSuccess.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
