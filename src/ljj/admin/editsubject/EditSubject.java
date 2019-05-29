package ljj.admin.editsubject;

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

/**
 * 管理员修改课程
 * 
 * Servlet implementation class EditSubject
 */
@WebServlet(name = "Edit", description = "Edit", urlPatterns = { "/Edit" })
public class EditSubject extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditSubject() {
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
			String sql = "update subjecttable set subName=?,subTeacher=?,subTime=? where subNo=?";

			// 从DataBase当中获得的一系列操作数据库的类
			Connection conn = db.getConnection();
			PreparedStatement prepstmt = conn.prepareStatement(sql);

			// 从editCourse.jsp当中获得需要编辑的各个属性的值
			int subNo = Integer.parseInt(request.getParameter("newNo"));
			String subName = request.getParameter("newName");
			String subTeacher = request.getParameter("newTeacher");
			String subTime = request.getParameter("newTime");

			// 开始更新数据库
			prepstmt.setString(1, subName);
			prepstmt.setString(2, subTeacher);
			prepstmt.setString(3, subTime);
			prepstmt.setInt(4, subNo);
			prepstmt.executeUpdate();

			// 关闭数据库
			db.close();

			// 返回到editCourse页面
			RequestDispatcher dispatcher = request.getRequestDispatcher("admin/editCourse.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
