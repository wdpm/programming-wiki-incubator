<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>User Edit - Maple</title>
    <!-- Bootstrap -->
    <link href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <!-- Static navbar -->
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                        aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="../users">Maple</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li><a href="../users">列表</a></li>
                    <li class="active"><a href="#">编辑</a></li>
                </ul>
            </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
    </nav>

    <div class="jumbotron">
        <c:if test="${null != res && res == 'success' }">
            <div class="alert alert-success" role="alert">编辑成功！</div>
        </c:if>
        <c:if test="${null != res && res != 'success' }">
            <div class="alert alert-danger" role="alert">编辑失败！</div>
        </c:if>
        <form action="update" method="post">
            <input type="hidden" name="id" value="${user.id }"/>
            <div class="form-group">
                <label for="exampleInputEmail1">姓名</label>
                <input type="text" class="form-control" name="name" placeholder="请输入姓名" value="${user.name }"/>
            </div>
            <div class="form-group">
                <label for="exampleInputEmail1">年龄</label>
                <input type="text" class="form-control" name="age" placeholder="请输入年龄" value="${user.age }"/>
            </div>
            <div class="form-group">
                <label for="exampleInputEmail1">生日</label>
                <input type="text" class="form-control" placeholder="请输入生日[yyyy-MM-dd]格式" value="${user.birthday }"
                       readonly="readonly"/>
            </div>

            <button type="submit" class="btn btn-default">编辑</button>
        </form>
    </div>
</div>
</body>
</html>