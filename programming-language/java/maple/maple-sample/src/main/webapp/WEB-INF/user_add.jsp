<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>User Add - Maple</title>
    <!-- Bootstrap -->
    <link href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
  </head>
  <body>
     <div class="container">
      <!-- Static navbar -->
      <nav class="navbar navbar-default">
        <div class="container-fluid">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
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
              <li class="active"><a href="add">添加</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </nav>
		
      <div class="jumbotron">
      	<c:if test="${null != res && res == 'success' }">
      		<div class="alert alert-success" role="alert">添加成功！</div>
      	</c:if>
      	<c:if test="${null != res && res != 'success' }">
      		<div class="alert alert-danger" role="alert">添加失败！</div>
      	</c:if>
        <form action="save" method="post">
		  <div class="form-group">
		    <label for="exampleInputEmail1">姓名</label>
		    <input type="text" class="form-control" name="name" placeholder="请输入姓名">
		  </div>
		  <div class="form-group">
		    <label for="exampleInputEmail1">年龄</label>
		    <input type="text" class="form-control" name="age" placeholder="请输入年龄">
		  </div>
		  <div class="form-group">
		    <label for="exampleInputEmail1">生日</label>
		    <input type="text" class="form-control" name="birthday" placeholder="请输入生日[yyyy-MM-dd]格式">
		  </div>
		  
		  <button type="submit" class="btn btn-default">添加</button>
		</form>
      </div>
    </div>
  </body>
</html>