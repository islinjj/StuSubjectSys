<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="db" scope="page" class="ljj.admin.editsubject.DataBase" />
<!doctype html>
<html>
<head>
<title>学生选课</title>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">

<!-- Vue.js -->
<script src="vue.js"></script>

<!-- jquery-3.4.0.min.js -->
<script src="jquery-3.4.0.min.js"></script>

<script type="text/javascript">
	//复选框至少选择一项
	$(function() {
		let check_box = function() {
			$i = 0;
			$(':input:checkbox').each(function() {
				if ($(this).is(':checked')) {
					$i++;
				}
			});
			if ($i == 0) {
				alert('至少选择一个!');
				return false;
			} else {
				return true;
			}
		}
		$('form').bind('submit', check_box);
	});
</script>

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

	<form id="app" name="select"
		action="<%=request.getContextPath()%>/Select" method="post">
		<table class="table">
			<thead>
				<tr>
					<th>选择</th>
					<th>课程名</th>
					<th>课程号</th>
					<th>任课教师</th>
					<th>课程学时</th>
				</tr>
			</thead>

			<!-- 从数据库获得可选课程 -->
			<tbody>
				<%
					Connection conn = db.getConnection();
					Statement stm = conn.createStatement();
					ResultSet rs = stm.executeQuery("select * from subjecttable");
					while (rs.next()) {
						String subName = rs.getString("subName");
						int subNo = rs.getInt("subNo");
						String subTime = rs.getString("subTime");
						String subTeacher = rs.getString("subTeacher");
				%>
				<tr>
					<td style="width: 4em"><input type="checkbox" name="checkSub"
						value="<%=subNo%>" @click="checkboxOnclick($event)"></td>
					<td><%=subName%></td>
					<td><%=subNo%></td>
					<td><%=subTeacher%></td>
					<td><%=subTime%></td>
				</tr>

				<%
					}
				%>
			</tbody>
		</table>
		<input type="hidden" class="form-control" name="account"
			aria-describedby="helpId"
			value="<%=session.getAttribute("account")%>"> <input
			type="hidden" name="checkedSubNo" :value="arr"> <input
			class="btn btn-primary btn-block" type="submit" value="提交">
	</form>

	<script>
		let v = new Vue({
			el : '#app',

			data : {
				arr : [],
			//selectid : {},
			},

			methods : {
				checkboxOnclick : function(e) {
					//判断按钮是否被选中
					let checked = e.target.checked;

					//获取选中按钮的值
					let value = e.target.value;

					//将选中的课程号放入数组
					this.arr.push(e.target.value);

					//转换成json
					//this.selectid = JSON.stringify(this.arr);
				}
			}
		})
	</script>

</body>
</html>