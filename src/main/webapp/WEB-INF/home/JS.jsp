<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String pathJS = request.getContextPath();
  String basePathJS = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathJS;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>index</title>

</head>

<body>

<%-- jquery --%>
<script type="text/javascript" src="/xm/static/trd/jquery/jquery.min.js"></script>
<%-- bootstrap --%>
<script type="text/javascript" src="/xm/static/trd/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="/xm/static/trd/bootstrap-3.3.7-dist/css/bootstrap.css">
<%-- 百度echarts 
<script type="text/javascript" src="/xm/home/js/echarts.min.js"></script>--%>
<%-- cookie --%>
<script type="text/javascript" src="/xm/static/trd/jquery/jquery.cookie.js"></script>
<%-- jquery.tmpl --%>
<script type="text/javascript" src="/xm/static/trd/jquery/jquery.tmpl.js"></script>

<!-- bootstrap-toastr -->
<link rel="stylesheet" href="/xm/static/trd/bootstrap-toastr/toastr.css" />
<script type="text/javascript" src="/xm/static/trd/bootstrap-toastr/toastr.min.js"></script>



<!-- 个人共用JS -->
<script type="text/javascript" src="/xm/static/private/private_commonJS.js?randomId=<%=Math.random()%>"></script>
<!-- 个人共用css -->
<link rel="stylesheet" type="text/css"	href="/xm/static/private/private-commonCSS.css?randomId=<%=Math.random()%>">

</body>
</html>