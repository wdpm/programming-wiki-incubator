<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Users - Maple</title>
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
            <a class="navbar-brand" href="users">Maple</a>
          </div>
          <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
              <li class="active"><a href="users">列表</a></li>
              <li><a href="user/add">添加</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </nav>
		
      <div class="jumbotron">
        <table class="table table-striped">
        	<tr>
        		<th>&nbsp;</th>
        		<th>姓名</th>
        		<th>年龄</th>
        		<th>生日</th>
        		<th>操作</th>
        	</tr>
        	<c:forEach var="item" items="${users}">
        		<tr>
        			<td>${item.id }</td>
        			<td>${item.name }</td>
        			<td>${item.age }</td>
        			<td>${item.birthday }</td>
        			<td>
        				<a href="user/edit?id=${item.id}">编辑</a> |
        				<a href="user/del?id=${item.id}">删除</a>
        			</td>
        		</tr>
        	</c:forEach>
		</table>
      </div>

    </div> <!-- /container -->
  </body>
</html>