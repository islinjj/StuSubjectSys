<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="db" scope="page" class="ljj.admin.editsubject.DataBase" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我选择的课程</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>

	<!-- 导航栏 -->
	<nav class="navbar navbar-expand-sm navbar-light bg-light">
		<a class="navbar-brand" href="selectSubject.jsp">选课</a>
		<button class="navbar-toggler d-lg-none" type="button"
			data-toggle="collapse" data-target="#collapsibleNavId"
			aria-controls="collapsibleNavId" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="collapsibleNavId">
			<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
				<li class="nav-item"><a class="nav-link"
					href="selectSubject.jsp">可选课程</a></li>
				<li class="nav-item"><a class="nav-link"
					href="mySelect.jsp?stuAccount=<%=session.getAttribute("account")%>">已选课程</a></li>
			</ul>
		</div>
	</nav>

		<table class="table">
			<thead>
				<tr>
					<th>课程名</th>
					<th>课程号</th>
					<th>任课教师</th>
					<th>课程学时</th>
					<th>操作</th>
				</tr>
			</thead>

			<!-- 从数据库获得已选课程 -->
			<tbody>
				<%
					Connection conn = db.getConnection();
					Statement stm = conn.createStatement();
					ResultSet rs = stm
							.executeQuery("select * from stuselecttable where sNo=" + request.getParameter("stuAccount"));
					while (rs.next()) {
						String subName = rs.getString("subName");
						int subNo = rs.getInt("subNo");
						String subTime = rs.getString("subTime");
						String subTeacher = rs.getString("subTeacher");
				%>
				<tr>
					<td><%=subName%></td>
					<td><%=subNo%></td>
					<td><%=subTeacher%></td>
					<td><%=subTime%></td>
					<td>
						<div>
							<form name="delete"
								action="<%=request.getContextPath()%>/StuDelSub" method="post">
								<input type="hidden" class="form-control" name="myAccount"
									aria-describedby="helpId"
									value="<%=session.getAttribute("account")%>">
								<input type="hidden" class="form-control" name="mySub"
									aria-describedby="helpId"
									value="<%=subNo%>">
								<button type="submit" class="btn btn-danger btn-lg">删除</button>
							</form>
						</div>
					</td>
				</tr>

				<%
					}
				%>
			</tbody>
		</table>
</body>
</html>