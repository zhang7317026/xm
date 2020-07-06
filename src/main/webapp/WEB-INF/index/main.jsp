<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0 
	,target-densitydpi=high-dpi">
</head>
<body>
<%@include file="../home/JS.jsp"%>
<link rel="stylesheet" type="text/css" href="/xm/css/main/main.css?randomId=<%=Math.random()%>">

<div id="line" class="">
	<div class="col-md-12 col-sm-12 col-xs-12" style="height: 50px;"></div>
	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="panel panel-info">
			<div id="mainPanel" class="panel-body alert-danger">
			</div>
		</div>
		<div id="optList" class="panel-body alert alert-danger">
			
		</div>
		
		<div id="policyList" class=""></div>
		
	</div>
	<div class="col-md-12 col-sm-12 col-xs-12" style="height: 50px;"></div>
</div>

<script id="optListTmpl" type="text/x-jquery-tmpl">
<div class="col-md-12 col-sm-12 col-xs-12">
	<div class="table-responsive" style="border-radius: 15px;">
		<table class="table str-table">
			<tr>
				<td class="str-left info">
					{{= policyName}}
				</td>
				<td class="str-right">
					{{each(i,str) listStr}}
						<div>{{html str}}</div>
					{{/each}}
				</td>
			</tr>
		</table>
	</div>
</div>
</script>

<script id="mainPanelTmpl" type="text/x-jquery-tmpl">
	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="col-md-6 col-sm-6 col-xs-6">
			<span class="left">用户名称：</span>
			<span class="right">{{= userName}}</span>
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6">
			<span class="left">总收益率：</span>
			<span class="right">{{= makeRate}}%</span>
		</div>
	</div>
	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="col-md-6 col-sm-6 col-xs-6">
			<span class="left">当前总值：</span>
			<span class="right">{{= nowAll}}</span>
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6">
			<span class="left">总收益：</span>
			<span class="right">{{= makeAll}}</span>
		</div>
	</div>
	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="col-md-6 col-sm-6 col-xs-6">
			<span class="left">总投入：</span>
			<span class="right">{{= inputAll}}</span>
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6">
			<span class="left">活期总额：</span>
			<span class="right">{{= surplus}}</span>
		</div>
	</div>
</script>
				
<script id="policyTmpl" type="text/x-jquery-tmpl">
<div class="">
	<div class="panel panel-info">
		<div class="panel-heading" style="line-height: 25px;">
			<span class="policy-name">{{= policy.policyName}}策略</span>
			<span class="policy-startDate">开始时间({{= policy.startDate}})</span>
		</div>
		<div class="panel-body alert alert-warning">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="col-md-6 col-sm-6 col-xs-6">
					<span class="left">当前总值：</span>
					<span class="right">{{= policy.nowAll}}</span>
				</div>
				<div class="col-md-6 col-sm-6 col-xs-6">
					<span class="left">活期余额：</span>
					<span class="right">{{= policy.surplus}}</span>
				</div>
			</div>
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="col-md-6 col-sm-6 col-xs-6">
					<span class="left">总投入：</span>
					<span class="right">{{= policy.inputAll}}</span>
				</div>
				<div class="col-md-6 col-sm-6 col-xs-6">
					<span class="left">活期利率：</span>
					<span class="right">{{= policy.surplusInterest}}%</span>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				基金列表
	        </div>
			<div class="panel-body">
	            <div class="table-responsive">
					<table class="table">
						<thead>
							<tr>
								<th>序号</th>
								<th>基金代码</th>
								<th>基金名称</th>
								<th>当前市值</th>
								<th>持有份额</th>
								<th>可以交易份额</th>
								<th>成本</th>
								<th>当前盈亏</th>
								<th>当前净值</th>
							</tr>
						</thead>
						<tbody>
						{{each(i,fund) fundList}}
							{{if i%4==1}}
								<tr class="success">
							{{else i%4==2}}
								<tr class="info">
							{{else i%4==3}}
								<tr class="warning">
							{{else i%4==0}}
								<tr class="warning">
							{{/if}}
									<td>{{= i}}</td>
									<td>{{= fund.fundCode}}</td>
									<td>{{= fund.fundName}}</td>
									<td>{{= fund.nowMoney}}</td>
									<td>{{= fund.sumAccount}}</td>
									<td>{{= fund.day7}}</td>
									<td>{{= fund.inputMoney}}</td>
									<td>{{= fund.floatMoney}}</td>
									<td>{{= fund.close0}}</td>
							    </tr>
						{{/each}}
						</tbody>
	                </table>
	            </div>
	        </div>
	    </div>
	    
   	</div>
</div>
</script>


<script type="text/javascript" src="/xm/js/main/main.js?randomId=<%=Math.random()%>"></script>
</body>

</html>