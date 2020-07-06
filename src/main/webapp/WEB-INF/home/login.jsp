<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0 
	,target-densitydpi=high-dpi">
<title>good</title>
</head>
<body class="back" style="width: 100%; height: 100%;" id="body">
	<%@include file="../home/JS.jsp"%>
	<link rel="stylesheet" type="text/css" href="/xm/css/home/login.css">
	<script type="text/javascript" src="/xm/js/home/login.js"></script>

	<div id="index_div" class="col-md-12 text-center" 
		style="bottom: 50px; position: fixed;left: 50%;width: 100%;-webkit-transform: translateX(-50%);">
		<center>
			<div class="col-md-12">
				<img alt="" src="//osk1v0s4f.bkt.clouddn.com/img/home/login_btn.png" width="60%"	onclick="login()">
			</div>
			<div class="col-md-12">
				<img alt="" src="//osk1v0s4f.bkt.clouddn.com/img/home/register_btn.png" width="60%" onclick="register()">
			</div>
		</center>
	</div>
	
	<div id="login_div" style="bottom: 50px; position: fixed;display: none;">
		<div class="">
			<div class="col-md-12 " style="height: 80px;">
				<img alt="" src="/xm/img/home/back.png" width="50px" onclick="back()">
			</div>
			<div class="col-md-12">
				<div class="form-group input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-tag"></i></span> 
					<input id="account_login" class="form-control input-lg" type="text" placeholder="请输入账号"> 
				</div>
				<div class="form-group input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> 
					<input id="password_login" class="form-control input-lg" type="password" placeholder="请输入密码">
				</div>
				<div class="form-group input-group">
					<span id="message_login" style="color:#FFF;font-size: x-large;"></span>
				</div>
			</div>
		</div>
		<div class="col-md-12 text-center">
			<img alt="" src="/xm/img/home/login_btn.png" width="60%" onclick="loginCommit()">
		</div>
	</div>

	<div id="register_div" style="bottom: 50px; position: fixed;display: none;">
		<div class="">
			<div class="col-md-12 " style="height: 80px;">
				<img alt="" src="/xm/img/home/back.png" width="50px" onclick="back()">
			</div>
			<div class="col-md-12">
				<div class="form-group input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-tag"></i></span> 
					<input id="account" class="form-control input-lg" type="text" placeholder="请输入账号"> 
				</div>
				<div class="form-group input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 
					<input id="name"    class="form-control input-lg" type="text" placeholder="请输入昵称"> 
				</div>
				<div class="form-group input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> 
					<input id="password" class="form-control input-lg" type="password" placeholder="请输入密码">
				</div>
				<div class="form-group input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> 
					<input id="password2" class="form-control input-lg" type="password"	placeholder="请输入确认密码">
				</div>
				<div class="form-group input-group">
					<span id="message_register" style="color:#FFF;font-size: x-large;"></span>
				</div>
			</div>
		</div>
		<div class="col-md-12 text-center">
			<img alt="" src="/xm/img/home/register_btn.png" width="60%" onclick="registerCommit()">
		</div>
	</div>
	

</body>

</html>