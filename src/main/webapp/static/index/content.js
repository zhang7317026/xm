$(function() {
	init();
	
	toastr.success("祝贺你成功了");
});

function init(){
	
	//加载card信息
	$.ajax({
        url:"/xm/fund/mainPanel",
        type:"POST",
        data:{},
        success:function(data){
        	$("#card_nowAll").text("￥ "+data.nowAll);
        	$("#card_todayUp").text("up ￥"+data.nowAll);
        	$("#card_makeAll").text("￥ "+data.makeAll);
        	$("#card_inputAll").text("￥"+data.inputAll);
        	$("#card_makeRate").text(data.makeRate+" %");
        	$("#card_surplus").text("￥"+data.surplus);
        	$("#card_valueAll").text("￥"+data.nowAll);
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
        	$("#optList").html($("#optListTmpl").tmpl(dataList));
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
