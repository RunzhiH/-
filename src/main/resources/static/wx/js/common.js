function goto(url){
	window.location.href=url;
}
function goBack(){
	history.back(-1);
}
/**
 * 获取指定的URL参数值 URL:http://www.quwan.com/index?name=tyler 参数：paramName URL参数
 * 调用方法:getParam("name") 返回值:tyler
 */ 
function getParam(paramName) { 
    paramValue = "", isFound = !1; 
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) { 
        arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0; 
        while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++ 
    } 
    return paramValue == "" && (paramValue = null), paramValue 
} 

/**
 * 通用的ajax调用接口
 * 
 * @param url
 *            ajax调用的后台路径
 * @param data
 *            要传到后台的数据
 * @param success
 *            调用成功后执行的方法，不传或者传空则不执行(将接口返回的所有数据都返回)
 * @param error
 *            调用出错后执行的方法，不传或者传空则不执行(将接口返回的所有数据都返回)
 * @param async
 *            是否异步，默认为true
 * @param type
 *            请求类型，String类型的参数，请求方式（post或get）默认为get
 * @param dataType
 *            要求为String类型的参数，预期服务器返回的数据类型，默认为json
 */
function comm_ajax_function_entrance_return_all(url, data, success, error, async, type, dataType) {
	if (async == null || typeof async == "undefined") {
		async = true;
	}
	if (type == null || typeof type == "undefined") {
		type = "GET";
	}
	if (dataType == null || typeof dataType == "undefined") {
		dataType = "json";
	}
	// data中没有键值对时会导致404错误，因此增加默认键值对
	var i = 0;
	for ( var key in data) {
		if (data[key] != "" && data[key] != null && typeof data[key] != "undefind") {
			i++;
			break;
		}
	}
	if (data == null) {
		data = {};
	}
	if (i == 0) {
		data["1"] = "1";
	}
	// data中没有键值对时会导致404错误，因此增加默认键值对结束
	$.ajax({
		async : async,
		url : url,
		data : data,
		type : type,
		dataType : dataType,
		success : function(res) {// 客户端jquery预先定义好的callback函数,成功获取跨域服务器上的json数据后,会动态执行这个callback函数
			success(res);
		},
		error : function(xhr) {
			if (error != null && typeof error != "undefined") {
				error(xhr);
			}
		}
	});
}