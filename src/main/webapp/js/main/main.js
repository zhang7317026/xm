var width = document.documentElement.clientWidth;
var height = document.documentElement.clientHeight;

var imgW = (width-15*2-20)/2;
var imgH = imgW*3/2;


	
$(function() {
	init();
	
	
});

function init(){
	//加载用户信息
	$.ajax({
        url:"/xm/fund/mainPanel",
        type:"POST",
        data:{},
        success:function(dataList){
        	$("#mainPanel").empty().append($("#mainPanelTmpl").tmpl(dataList));
        },
        error:function(){
        }
    });
	
	//加载操作信息
	$.ajax({
        url:"/xm/fund/optList",
        type:"POST",
        data:{},
        success:function(dataList){
        	$("#optList").empty().append($("#optListTmpl").tmpl(dataList).html());
        },
        error:function(){
        }
    });
	
	//加载policyList
	$.ajax({
        url:"/xm/fund/getPolicyList",
        type:"POST",
        data:{},
        success:function(dataList){
        	$("#policyList").empty().append($("#policyTmpl").tmpl(dataList));
        },
        error:function(){
        }
    });
}
