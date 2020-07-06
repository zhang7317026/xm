$(function() {
	//全屏事件
	$("#fullScreen").click(function(){
		$("#sidebar").hide("slow");
		$("#header").addClass("displayNone");
		$("#mainContent").addClass("allScreen");
	});
	
});