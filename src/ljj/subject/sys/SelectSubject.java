package ljj.subject.sys;

import java.io.IOException;
import java.io.PrintWriter;
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
 * 学生选择课程并提交到数据库
 * 
 * Servlet implementation class SelectSubject
 */
@WebServlet(name = "Select", description = "Select", urlPatterns = { "/Select" })
public class SelectSubject extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DataBase db = new DataBase();
	Connection conn = db.getConnection();
	Statement stmt;
	ResultSet rs;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SelectSubject() {
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
	 * 接收selectSubject.jsp传来的信息，即学生的选课信息，根据学号找到学生的姓名，接收传来的已选课程号，并根据课程号在课程表当中找到课程信息，将学号、姓名以及课程信息插入到数据库当中
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		insert2DB(request, response);

	}

	/**
	 * 将学生的选课信息插入到数据库
	 * 
	 * @param request
	 * @param response
	 */
	public void insert2DB(HttpServletRequest request, HttpServletResponse response) {
		// 从selectSubject.jsp获得学生选的课程编号，因为获得的是数组，根据“，”进行分割
		String stringSubNo = request.getParameter("checkedSubNo");
		String[] strings = stringSubNo.split(",");

		// 登陆学生账号和名字
		String account = request.getParameter("account");
		String sName = "";
		sName = getStuName(account, sName);

		// 从数据库获得对应课程号的课程信息并将学生的姓名、学号即选的课程信息插入数据库
		for (String s : strings) {

			// 课程号
			int subNo = Integer.parseInt(s);

			String subTeacher = "";
			String subName = "";
			String subTime = "";

			try {
				// 从数据库获得对应课程号的课程信息
				String sqlSelect = "select * from subjecttable where subNo=" + subNo;

				stmt = conn.createStatement();
				rs = stmt.executeQuery(sqlSelect);

				while (rs.next()) {
					subTeacher = rs.getString("subTeacher");
					subName = rs.getString("subName");
					subTime = rs.getString("subTime");
				}

				// 判断选择的课程和已选课程是否重复
				boolean isRepeat = false;
				isRepeat = checkSubRepeat(response, account, subNo, isRepeat);

				// 如果选择的课程已选择的课程不重复，将学生姓名、学号及课程信息插入数据库中的表stuselecttable
				if (!isRepeat) {
					selectInfo2DB(account, sName, subNo, subTeacher, subName, subTime, response);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("SelectSubject.java中无法将学生选择的课程插入数据库");
			}

		}
	}

	/**
	 * 判断选课和已选课是否重复，根据表单传来的学号和课程号，和已选课表进行判断是否重复。重复则反馈给用户，并将isRepeat置位true。
	 * 
	 * @param response
	 * @param account  学号
	 * @param subNo    课程号
	 * @param isRepeat true为重复，false为不重复
	 * @return
	 * @throws SQLException
	 */
	public boolean checkSubRepeat(HttpServletResponse response, String account, int subNo, boolean isRepeat)
			throws SQLException {
		// 判断选课是否重复
		String checkRepeat = "select sNo,subNo from stuselecttable";
		stmt = conn.createStatement();
		rs = stmt.executeQuery(checkRepeat);

		while (rs.next()) {
			if (account.equals(rs.getString(1)) && subNo == rs.getInt(2)) {
				PrintWriter out;
				try {
					out = response.getWriter();
					out.print("<script type='text/javascript'>");
					out.print("alert('选课有重复');");
					out.print("location.href='selectSubject.jsp';");
					out.print("</script>");
					out.flush();
					out.close();

					isRepeat = true;
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		return isRepeat;
	}

	/**
	 * 将学生选择的课程插入stuselecttable表中
	 * 
	 * @param account    学生名
	 * @param sName      课程名
	 * @param subNo      课程号
	 * @param subTeacher 任课教师
	 * @param subName    课程名
	 * @param subTime    学时
	 * @param response
	 * @throws SQLException
	 */
	public void selectInfo2DB(String account, String sName, int subNo, String subTeacher, String subName,
			String subTime, HttpServletResponse response) throws SQLException {
		// 数据放入到数据库的选课表当中
		String sqlInsert = "insert into stuselecttable (sName,sNo,subName,subNo,subTime,subTeacher) value(?,?,?,?,?,?)";
		PreparedStatement prestmt = conn.prepareStatement(sqlInsert);
		prestmt.setString(1, sName);
		prestmt.setString(2, account);
		prestmt.setString(3, subName);
		prestmt.setInt(4, subNo);
		prestmt.setString(5, subTime);
		prestmt.setString(6, subTeacher);
		prestmt.executeUpdate();

		// 转向学生已选课页面
		try {
			PrintWriter out = response.getWriter();
			out.print("<script type='text/javascript'>");
			out.print("alert('选课成功！');");
			out.print("location.href='selectSubject.jsp';");
			out.print("</script>");
			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("SelectSubject.java转向我的选课表失败");
		}
	}

	/**
	 * 从学生表当中获得学生名和学号，并获得对应学号的姓名
	 * 
	 * @param account 学生账号
	 * @param sName   学生姓名
	 * @return
	 */
	public String getStuName(String account, String sName) {
		Statement stmt;
		ResultSet rs;
		// 获得学生的学号和姓名
		String sql = "select sName,sNo from student";

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			// 先找到学号对应的名字方便插入到数据库当中的已选课表
			while (rs.next()) {
				String sNo = rs.getString("sNo");
				if (account.equals(sNo)) {
					sName = rs.getString("sName");
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("无法获取学生姓名");
		}
		return sName;
	}

}
