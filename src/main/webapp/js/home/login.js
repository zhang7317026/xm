/**
 * 
 */

$(function() {
	
	
});

function login(){
	$("#index_div").hide("normal");
	$("#register_div").hide("normal");
	$("#loading").hide("normal");
	//展示
	$("#login_div").show("normal");
	//back
	$("#body").removeClass("loading");
	$("#body").addClass("back");
}
function register(){
	$("#index_div").hide("normal");
	$("#login_div").hide("normal");
	$("#loading").hide("normal");
	//展示
	$("#register_div").show("normal");
	//back
	$("#body").removeClass("loading");
	$("#body").addClass("back");
}
function back(){
	$("#register_div").hide("normal");
	$("#loading").hide("normal");
	$("#login_div").hide("normal");
	//展示
	$("#index_div").show("normal");
	//back
	$("#body").removeClass("loading");
	$("#body").addClass("back");
	//清空message
	$("#message_login").text("");
	$("#message_register").text("");
	
}

function loading(){
	//隐藏
	$("#index_div").hide(0);
	$("#login_div").hide(0);
	$("#register_div").hide(0);
	//展示loading
	//loading
	$("#body").removeClass("back");
	$("#body").addClass("loading");
}

function loginCommit(){
	var account = $("#account_login").val();
	var password = $("#password_login").val();
	if(isEmpty(account)){
		$("#message_login").text("账号不能为空！");
		return;
	}
	if(isEmpty(password)){
		$("#message_login").text("密码不能为空！");
		return;
	}
	
	//loading
	loading();
	
	$.ajax({
        url:"/xm/loginCommit",
        type:"POST",
        data:{
        	account : $("#account_login").val(),
        	password : $("#password_login").val()
        },
        success:function(data){
            if(data.flag == "SUCCESS"){
            	window.location.href = "/xm";
            }else{
            	login();
            	$("#message_login").text(data.message);
            }
        },
        error:function(){
        	login();
        	$("#message_login").text("连接失败。。。");
        }
    });
}
function registerCommit(){
	var account = $("#account").val();
	var name = $("#name").val();
	var password = $("#password").val();
	var password2 = $("#password2").val();
	if(isEmpty(account)){
		$("#message_register").text("账号不能为空！");
		return;
	}
	if(isEmpty(name)){
		$("#message_register").text("昵称不能为空！");
		return;
	}
	if(isEmpty(password)){
		$("#message_register").text("密码不能为空！");
		return;
	}
	if(isEmpty(password2)){
		$("#message_register").text("确认密码不能为空！");
		return;
	}
	if(password!=password2){
		$("#message_register").text("两次输入的密码不一致！");
		return;
	}
	
	//loading
	loading();
	
	$.ajax({
        url:"/xm/registerCommit",
        type:"POST",
        data:{
        	account : $("#account").val(),
        	name : $("#name").val(),
        	password : $("#password").val()
        },
        success:function(data){
            if(data.flag == "SUCCESS"){
            	window.location.href = "/xm";
            }else{
            	register();
            	$("#message_register").text(data.message);
            }
        },
        error:function(){
        	register();
        	$("#message_register").text("连接失败。。。");
        }
    });
}