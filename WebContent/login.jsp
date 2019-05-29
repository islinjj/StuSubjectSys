<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
    <title>登陆</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <link rel="stylesheet" href="css/container.css">
</head>

<body>
    <!-- 导航栏 -->
    <nav class="navbar navbar-expand-sm navbar-light bg-light">
        <a class="navbar-brand" href="#">学生选课系统</a>
        <button class="navbar-toggler d-lg-none" type="button" data-toggle="collapse" data-target="#collapsibleNavId"
            aria-controls="collapsibleNavId" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavId">
            <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                <li class="nav-item active">
                    <a class="nav-link" href="index.jsp">首页 <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="register.jsp">学生注册</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="login.jsp">登陆</a>
                </li>
            </ul>

            <!-- 搜索框 -->
            <form class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" type="text" placeholder="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>
    </nav>

    <!-- 登陆 -->
    <div class="container">
        <h4>登陆</h4>
        <br>
        
        <form name="check" action="<%=request.getContextPath() %>/Check" method="post">
            <div class="form-group row">
                <label for="inputName" class="col-sm-1-12 col-form-label">账号</label>
                <div class="col-sm-1-12">
                    <input type="text" class="form-control" name="account" id="account" placeholder="账号" required>
                </div>
            </div>
            <div class="form-group row">
                <label for="inputName" class="col-sm-1-12 col-form-label">密码</label>
                <div class="col-sm-1-12">
                    <input type="password" class="form-control" name="pwd" id="pwd" placeholder="密码" required>
                </div>
            </div>
            <div class="form-group">
                <input type="radio" name="identify" id="admin" autocomplete="off" value="admin" checked>
                管理员
                <input type="radio" name="identify" id="student" value="student" autocomplete="off">
                学生
            </div>
            <div class="form-group row">
                <div class="offset-sm-1">
                    <button type="submit" class="btn btn-primary">提交</button>
                </div>
            </div>
        </form>
        
    </div>
</body>

</html>