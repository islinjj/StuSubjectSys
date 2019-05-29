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
 * 管理员添加新课程
 * 
 * Servlet implementation class AddSubject
 */
@WebServlet(name = "Add", description = "Add", urlPatterns = { "/Add" })
public class AddSubject extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddSubject() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * 添加新课程，从editCourse.jsp页面获得新添加课程的各属性，添加进数据库，成功后转向editCourse.jsp页面
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// 连接数据库
		DataBase db = new DataBase();

		// 从DataBase当中获得的一系列操作数据库的类
		PreparedStatement prepstmt = db.getPreparedStatement();
		Connection conn = db.getConnection();

		// 从editCourse.jsp当中获得新添加的各个属性的值
		String subName = request.getParameter("subName");
		String subTeacher = request.getParameter("subTeacher");
		int subNo = Integer.parseInt(request.getParameter("subNo"));
		String subTime = request.getParameter("subTime");

		try {
			// 添加新课程入数据库
			prepstmt = conn
					.prepareStatement("insert into subjecttable (subName,subNo,subTeacher,subTime) values(?,?,?,?)");
			prepstmt.setString(1, subName);
			prepstmt.setInt(2, subNo);
			prepstmt.setString(3, subTeacher);
			prepstmt.setString(4, subTime);
			prepstmt.executeUpdate();

			// 关闭数据库
			db.close();

			// 返回到editCourse页面
			RequestDispatcher dispatcher = request.getRequestDispatcher("admin/editCourse.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("在AddSerlet操作失败");
		}

	}

}
