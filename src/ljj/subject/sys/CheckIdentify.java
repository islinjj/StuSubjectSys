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
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;

import ljj.admin.editsubject.DataBase;

/**
 * 检查登陆的身份，如果是学生就进入选课界面，管理员则进入管理界面
 * 
 * Servlet implementation class CheckIdentify
 */
@WebServlet(name = "Check", description = "Check", urlPatterns = { "/Check" })
public class CheckIdentify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckIdentify() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

		// 获得login.jsp传来的identify的值
		String identify = request.getParameter("identify");
		String account = request.getParameter("account");
		String pwd = request.getParameter("pwd");

		// 管理员身份
		if (identify.equals("admin")) {
			dealAdmin(account, pwd, request, response);
		}

		// 学生身份
		else if (identify.equals("student")) {
			dealStu(account, pwd, request, response);
		}
	}

	/**
	 * 检查学生的账号和密码，并判断该用户是否已经注册
	 * 
	 * @param account  账号
	 * @param pwd      密码
	 * @param request
	 * @param response
	 */
	private void dealStu(String account, String password, HttpServletRequest request, HttpServletResponse response) {
		// 连接数据库
		DataBase db = new DataBase();

		// 查找语句
		String sql = "select * from student";

		// 从DataBase当中获得的一系列操作数据库的类
		Connection conn = db.getConnection();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				// 从数据库获得学生账号和密码
				String acc = rs.getString("sNo");
				String pwd = rs.getString("sPwd");

				// 学生账号和密码正确
				if (acc.equals(account) && pwd.equals(password)) {
					HttpSession session = request.getSession();
					// 账号和密码正确则记录学生账号，每个学生有自己的已选课表
					session.setAttribute("account", account);

					// 转向登陆之后的界面
					RequestDispatcher dispatcher = request.getRequestDispatcher("selectSubject.jsp");
					dispatcher.forward(request, response);

					// 不加return，程序正常进行但是会报错：Cannot forward after response has been committed
					// 原因这个是错误是由于response多次提交或者是由于有页面显示后仍然含请求转向产生的，
					//就是说程序在return之前就已经执行了跳转或者执行过response，之后遇到return的话，
					//程序想再次执行跳转，也就是重定向，这时功能也许会实现了，但是控制台会报错，所以控制好跳转是很重要的，
					return;
				}
			}

			// 转向登陆出错页面
			RequestDispatcher dispatcher = request.getRequestDispatcher("loginError.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("CheckIdentify.java的数据库操作有误");
		} catch (ServletException e) {
			e.printStackTrace();
			System.out.println("CheckIdentify.java的dispatcher出错，转向页面出错");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("CheckIdentify.java的dispatcher出错，IO");
		}

	}

	/**
	 * 检查管理员的账号和密码
	 * 
	 * @param account
	 * @param pwd
	 * @param response
	 * @param request
	 */
	public void dealAdmin(String account, String pwd, HttpServletRequest request, HttpServletResponse response) {
		// 判断管理员账号和密码
		if (account.equals("1187688514") && pwd.equals("123")) {

			try {
				// 返回到editCourse页面
				RequestDispatcher dispatcher = request.getRequestDispatcher("admin/editCourse.jsp");
				dispatcher.forward(request, response);

			} catch (ServletException e) {
				e.printStackTrace();
				System.out.println("CheckIdentify.java中dealAdmin的dispatcher出错");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("CheckIdentify.java中dealAdmin的dispatcher出错");
			}
		} else {
			// 错误处理
		}
	}

}
