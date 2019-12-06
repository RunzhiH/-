function choosePayWay(e) {
	var flag = $(e).attr("flag");
	var mark = $(e).attr("mark");
	var img = $(".icon_img");
	for (var i = 0; i < img.length; i++) {
		$(img[i]).removeClass($(img[i]).attr("mark") + $(img[i]).attr("flag"));
		$(img[i]).addClass($(img[i]).attr("mark") + "0");
		$(img[i]).attr("flag", "0");
	}
	$(e).removeClass(mark + flag);
	if (0 == flag) {
		$(e).attr("flag", "1");
		flag = 1;
		$("#account").show()
	} else {
		$(e).attr("flag", "0");
		flag = 0;
	}
	$(e).addClass(mark + flag);

}
function getBalence() {
	$.ajax({
		url : "/wallet/getWalletInfo",
		success : function(res) {
			if (res.status == 200) {
				var wallet = res.context;
				$("#balance").text(wallet.balance);
				$("#rmb").text(wallet.rmb);
			}
		}
	})
}
function doDrawl() {
	var drawl_rmb = $("#drawl_rmb").val();
	var balance = $("#balance").text();
	var account = $("#account").val();
	if (Number(drawl_rmb) > Number(balance)) {
		alert("余额不足");
		return;
	}
	if (Number(balance) <= 0) {
		alert("余额不足");
		return;
	}
	if(!account){
		alert("请输入到账账号");
		return;
	}
	var icon = $(".icon_img");
	var pay_way = 0;
	for (var i = 0; i < icon.length; i++) {
		if (1 == $(icon[i]).attr("flag")) {
			pay_way = $(icon[i]).attr("pay_way");
			break;
		}
	}
	$.ajax({
		url : "/wallet/doWithdrawal",
		data : {
			rmb : drawl_rmb,
			record_type : pay_way,
			account : account
		},
		success : function(res) {
			if(res.status==200){
				alert("提现申请成功");
			}
		}
	})
}