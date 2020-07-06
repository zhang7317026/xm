<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>连线Demo</title>
	<link rel="stylesheet" href="/xm/static/line/reset.css?randomId=<%=Math.random()%>">
	<link rel="stylesheet" href="/xm/static/line/style.css?randomId=<%=Math.random()%>">
</head>
<body>
<%@include file="../home/JS.jsp"%>

  <div>
        <div id="draw" style="height: 500px;">
        	<div class="col-md-12">
	            <div style="position: absolute" class="btn-group">
	                <a href="javascript:;" id="j-default" title="点击会删除已选择答案">自动连线</a>
	                <a href="javascript:;" id="j-reset">重置</a>
	                <a href="javascript:;" id="j-result">结果</a>
	            </div>
            </div>
            <div class="col-md-12" style="margin-top: 50px;position: absolute;">
            	<div class="col-md-4" style="position: relative;">
	            	<div class="table-responsive">
			            <table class="table table-striped table-bordered table-hover">
							<thead>
								<th>来源列</th>
								<th>类型</th>
							</thead>
							<tbody class="left-list">
							
							</tbody>
			            </table>
		            </div>
	            </div>
	            <div class="col-md-4">
	            </div>
	            <div class="col-md-4" style="position: relative;">
		            <div class="table-responsive">
			            <table class="table table-striped table-bordered table-hover">
							<thead>
								<th>目标列</th>
								<th>类型</th>
							</thead>
							<tbody class="right-list">
							
							</tbody>
			            </table>
		            </div>
				</div>
			</div>
        </div>
    </div>
  
  
  
</body>

<script src="/xm/static/line/jquery.min.js?randomId=<%=Math.random()%>"></script>
<script src="/xm/static/line/svg.min.js?randomId=<%=Math.random()%>"></script>
<script src="/xm/static/line/line.js?randomId=<%=Math.random()%>"></script>
</html>
