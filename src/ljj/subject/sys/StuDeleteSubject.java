package ljj.subject.sys;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ljj.admin.editsubject.DataBase;

/**
 * Servlet implementation class StuDeleteSubject
 */
@WebServlet(name = "StuDelSub", description = "StuDelSub", urlPatterns = { "/StuDelSub" })
public class StuDeleteSubject extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StuDeleteSubject() {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		try {
			// 连接数据库
			DataBase db = new DataBase();

			// 更新语句
			String sql = "delete from stuselecttable where sNo=? and subNo=?";

			// 从DataBase当中获得的一系列操作数据库的类
			Connection conn = db.getConnection();
			PreparedStatement prepstmt = conn.prepareStatement(sql);

			// 从editCourse.jsp当中获得课程编号和学号，以便删除对应的选选课
			String  sNo = request.getParameter("myAccount");
			int subNo =Integer.parseInt(request.getParameter("mySub"));
			
			// 开始更新数据库
			prepstmt.setString(1, sNo);
			prepstmt.setInt(2, subNo);
			prepstmt.executeUpdate();

			// 关闭数据库
			db.close();

			// 返回到editCourse页面
			RequestDispatcher dispatcher = request.getRequestDispatcher("mySelect.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
