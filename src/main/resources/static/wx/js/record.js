
function next_note(e) {
	var note = $(e).parent().parent();
	$(note).animate({
		letf : "50px"
	}, function() {
		// $(note).fadeOut();
	});
}

function getRechargeRrcord(month, e, callback) {
	var data = {}
	var year = $("#year_value").val()
	if (month) {
		if (0 < month && month < 10) {
			month = "0" + month;
		}

		if (month <= 0) {
			month = 12;
			year -= 1;
		}
		if (month >= 13) {
			month = 1;
			year += 1;
		}
		data["month"] = year + month;
	}
	$(".note").find(".note_body").html("");
	comm_ajax_function_entrance_return_all("/user/getRechargeRrcord", data,
			function(res) {
				showRechargeRecod(res, e);
				if (callback) {
					callback(e);
				}

			});
	comm_ajax_function_entrance_return_all("/user/getRechargeRrcordTotal",
			data, function(res) {
				showRechargeTotal(res, e, month)
			});

}
function showRechargeRecod(res, e) {
	if (200 == res.status && res.context.length > 0) {
		html = "";
		var record_list = res.context;
		for (var i = 0; i < record_list.length; i++) {
			var record = record_list[i];
			html += "<div class=\"note_ul\">" + "<div class=\"f_l\">"
					+ "	<p class=\"note_text\">" + record.create_time_str
					+ "</p>" + "</div>" + "<div class=\"f_r\">"
					+ "	<p class=\"note_text\">" + record.rmb + "元</p>"
					+ "</div>" + "</div>";
		}
		if (e) {
			$(e).parent().parent().siblings().find(".note_body").html(html);
		} else {
			$("#defult_note").find(".note_body").html(html);
		}
	}
}
function showRechargeTotal(res, e, month) {
	if (200 == res.status && res.context != null) {
		if (e) {
			$(e).parent().parent().siblings().find(".month").html(month);
			$(e).parent().parent().siblings().find(".month_price").html(
					res.context.total_price);
		} else {
			$("#defult_note").find(".month").html(month);
			$("#defult_note").find(".month_price")
					.html(res.context.total_price);
		}

	}
}

function getDrawlRrcord(month, e, callback){
	var data = {}
	var year = $("#year_value").val()
	if (month) {
		if (0 < month && month < 10) {
			month = "0" + month;
		}

		if (month <= 0) {
			month = 12;
			year -= 1;
		}
		if (month >= 13) {
			month = 1;
			year += 1;
		}
		data["month"] = year + month;
	}
	$(".note").find(".note_body").html("");
	comm_ajax_function_entrance_return_all("/wallet/getDrawlRcord", data,
			function(res) {
				showRechargeRecod(res, e);
				if (callback) {
					callback(e);
				}

			});
	comm_ajax_function_entrance_return_all("/wallet/getDrawlRrcordTotal",
			data, function(res) {
				showRechargeTotal(res, e, month)
			});
}