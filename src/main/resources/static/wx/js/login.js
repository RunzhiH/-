function showPwd(e) {
	var status = $(e).attr("status");
	if (status == 0) {
		$(e).attr("src", "/wx/image/register/register_ic_eyes@2x.png");
		$(e).attr("status", "1");
		$(e).prev().attr("type", "text");
	} else {
		$(e).attr("src", "/wx/image/register/register_ic_biyan@2x.png");
		$(e).attr("status", "0");
		$(e).prev().attr("type", "password");
	}
}
function sendSMSByLogin(e) {
	var phone=$("#phone").val();
	if(!phone){
		alert("请输入手机号");
		return;
	}
	$.ajax({
		url : "/login/sendSMSByLogin",
		data : {
			phone : phone
		},
		success : function(res) {
			if("200"==res.status){
				alert("短信验证码发送成功")
			}else{
				alert(res.message);
			}
		},
		error : function(xhr) {
			alert(xhr.message);
		}
	});
	var num =60;
	var flag=setInterval(() => {
		$(e).val(num+"秒");
		$(e).attr("disabled","disabled")
		if(num==0){
			$(e).val("获取");
			$(e).removeAttr("disabled")
			clearTimeout(flag);
		}
		num --;
	}, 1000);
	
}

function sendSMS(e) {
	var phone=$("#phone").val();
	if(!phone){
		alert("请输入手机号");
		return;
	}
	$.ajax({
		url : "/login/sendSMS",
		data : {
			phone : phone
		},
		success : function(res) {
			if("200"==res.status){
				alert("短信验证码发送成功")
			}else{
				alert(res.message);
			}
		},
		error : function(xhr) {
			alert(xhr.message);
		}
	});
	var num =60;
	var flag=setInterval(() => {
		$(e).val(num+"秒");
		$(e).attr("disabled","disabled")
		if(num==0){
			$(e).val("获取");
			$(e).removeAttr("disabled")
			clearTimeout(flag);
		}
		num --;
	}, 1000);
}
function updatePwd(){
	var password= $("#password").val();
	var password_sure= $("#password_sure").val();
	if(password!=password_sure){
		alert("两次密码不一致");
		return;
	}
	var data={};
	data["phone"] =phone;
	data["password"] =password;
	comm_ajax_function_entrance_return_all("/login/redisterUpdatePassword",data,function(){
		if(res.status==200){
			window.location.href="/wx/html/mine.html";
		}else{
			alert(res.message)
		}
	},function(){
		
	})
}

$(function(){
	$("#login_submit").click(function(){
		$("#login_form").submit();
	});
})