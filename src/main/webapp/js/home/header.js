var headerVue = new Vue({
	el : "#headerDiv",
	data : {
		userId : "",
		imgUrl : "",
		name : "",
		level : "11",
		gold : 0
	},
	methods : {
		getUserInfo : function(){
			
			$.ajax({
				url : "/xm/getUserInfo",
				type : "POST",
				data : {},
				success : function(data){
					//记录id
					headerVue.userId = data.userInfo.id;
					headerVue.imgUrl = "//osk1v0s4f.bkt.clouddn.com/img/head_level/"+data.userInfo.level0+".png";
					headerVue.name = data.userInfo.name;
					headerVue.level = data.userInfo.level0;
					headerVue.gold = data.userInfo.gold;
				},
				error : function(data){
					
				}
			});
		}
	}
	
});

headerVue.getUserInfo();


