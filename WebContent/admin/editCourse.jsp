<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.PreparedStatement" %>
<%@page import="java.sql.ResultSet" %>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="db" scope="page" class="ljj.admin.editsubject.DataBase" />

<!doctype html>
<html>

<head>
  <title>管理员维护课程</title>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

  <!-- Vue.js -->
  <script src="vue.js"></script>
</head>

<body>

  <div>
    <!-- 导航栏 -->
	<nav class="navbar navbar-expand-sm navbar-light bg-light">
		<a class="navbar-brand" href="selectSubject.jsp">管理员</a>
		<button class="navbar-toggler d-lg-none" type="button"
			data-toggle="collapse" data-target="#collapsibleNavId"
			aria-controls="collapsibleNavId" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="collapsibleNavId">
			<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
				<li class="nav-item"><a class="nav-link"
					href="admin/editCourse.jsp">管理课程</a></li>
			</ul>
		</div>
	</nav>

    <div class="container-fluid">
      <div class="row">

        <!-- 添加课程 -->
        <div class="col-lg-4 col-sm-12">
          <div class="card">
            <div class="card-body">
              <!-- 卡片的标题 -->
              <h4 class="card-title">添加课程</h4>
              <form name="add" action="<%=request.getContextPath() %>/Add" method="post">
                <div class="form-group">
                  <label for="">课程名</label>
                  <input type="text" class="form-control" name="subName" id="subName" aria-describedby="helpId">
                </div>
                <div class="form-group">
                  <label for="">课程号</label>
                  <input type="text" class="form-control" name="subNo" id="subNo" aria-describedby="helpId">
                </div>
                <div class="form-group">
                  <label for="">任课教师</label>
                  <input type="text" class="form-control" name="subTeacher" id="subTeacher" aria-describedby="helpId">
                </div>
                <div class="form-group">
                  <label for="">课程学时</label>
                  <input type="text" class="form-control" name="subTime" id="subTime" aria-describedby="helpId">
                </div>
                <button type="submit" class="btn btn-block btn-primary">Submit</button>
                <button type="reset" class="btn btn-block btn-danger">Reset</button>
              </form>
            </div>
          </div>
        </div>

        <!-- 编辑课程 -->
        <div class="col-lg-8 col-sm-12">
          <table class="table">
            <thead>
              <tr>
                <th>课程名</th>
                <th>课程号</th>
                <th>任课教师</th>
                <th>课程学时</th>
                <th style="width: 30%">操作</th>
              </tr>
            </thead>
            <tbody>
                <!-- 遍历显示新添加课程 -->
                <%
            Connection conn = db.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select * from subjecttable");
            while(rs.next()){
            	String subName = rs.getString("subName");
            	int subNo = rs.getInt("subNo");
            	String subTime = rs.getString("subTime");
            	String subTeacher = rs.getString("subTeacher");
              %>
                <tr>
                  <td><%=subName %></td>
                  <td><%=subNo %></td>
                  <td><%=subTeacher %></td>
                  <td><%=subTime %></td>
                  <td>
                    <div class="row">
                      <div class="col-lg-6">
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#edit<%=subNo%>">
                          编辑
                        </button>
                        <!-- Modal -->
                        <div class="modal fade" id="edit<%=subNo%>" tabindex="-1" role="dialog" aria-labelledby="modelTitleId"
                          aria-hidden="true">
                          <div class="modal-dialog" role="document">
                            <div class="modal-content">
                              <div class="modal-header">
                                <h5 class="modal-title">编辑课程</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                  <span aria-hidden="true">&times;</span>
                                </button>
                              </div>
                              <div class="modal-body">
                              <!-- 开始编辑 -->
                                <form action="<%=request.getContextPath() %>/Edit" name="edit" method="post">
                                  <div class="form-group">
                                    <label for="">课程名</label>
                                    <input type="text" class="form-control" name="newName" id="newName"
                                      aria-describedby="helpId" value="<%=subName %>">
                                  </div>
                                  <div class="form-group">
                                    <!-- 课程号作为id，不显示，根据id进行修改 -->
                                    <input type="hidden" class="form-control" name="newNo" id="newNo"
                                      aria-describedby="helpId" value="<%=subNo %>">
                                  </div>
                                  <div class="form-group">
                                    <label for="">任课教师</label>
                                    <input type="text" class="form-control" name="newTeacher" id="newTeacher"
                                      aria-describedby="helpId" value="<%=subTeacher %>">
                                  </div>
                                  <div class="form-group">
                                    <label for="">课程学时</label>
                                    <input type="text" class="form-control" name="newTime" id="newTime"
                                      aria-describedby="helpId" value="<%=subTime %>">
                                  </div>
                                  <button type="submit" class="btn btn-block btn-primary">Submit</button>
                                  <button type="button" class="btn  btn-block btn-secondary"
                                    data-dismiss="modal">Close</button>
                                </form>
                                
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <!-- 删除课程 -->
                      <div class="col-lg-6">
                      	<form name="delete" action="<%=request.getContextPath() %>/Delete" method="post">
                        <input type="hidden" class="form-control" name="deleteSubNo" id="deleteSubNo"
                          aria-describedby="helpId" value="<%=subNo %>">
                        <button type="submit" class="btn btn-danger btn-lg">删除</button>
						</form>
                      </div>
                      
                    </div>
                  </td>
                </tr>

                <%}%>
              
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>

  <!-- Optional JavaScript -->
  <!-- jQuery first, then Popper.js, then Bootstrap JS -->
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
    integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
    crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
    integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
    crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
    integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
    crossorigin="anonymous"></script>
</body>

</html>