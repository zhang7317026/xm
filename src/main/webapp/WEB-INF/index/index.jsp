<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="../home/a.jsp" %>
	<link rel="stylesheet" href="/xm/static/index/index.css?randomId=<%=Math.random()%>">
</head>
<body style="background-color: #fff">
  <div class="container-scroller">
	
    <div class="container-fluid page-body-wrapper">
      <div class="row row-offcanvas row-offcanvas-right">
      	<%@include file="left.jsp" %>
        <%@include file="content.jsp" %>
      </div>
    </div>
  </div>
  
  <script src="/xm/static/index/index.js?randomId=<%=Math.random()%>"></script>
</body>

</html>
