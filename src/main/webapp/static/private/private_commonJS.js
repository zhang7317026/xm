//判断是否为空
function isEmpty (value){
	if(value==""||value==null||value==undefined){
		return true;
	}else{
		return false;
	}
};
//判断是否非空
function isNotEmpty (value){
	if(value==""||value==null||value==undefined){
		return false;
	}else{
		return true;
	}
};

//自定义左滑，右滑
jQuery.fn.slideLeftHide = function( speed, callback ) {  
    this.animate({  
        width : "hide",  
        paddingLeft : "hide",  
        paddingRight : "hide",  
        marginLeft : "hide",  
        marginRight : "hide"  
    }, speed, callback );  
};  
jQuery.fn.slideLeftShow = function( speed, callback ) {  
    this.animate({  
        width : "show",  
        paddingLeft : "show",  
        paddingRight : "show",  
        marginLeft : "show",  
        marginRight : "show"  
    }, speed, callback );  
};