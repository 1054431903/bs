/*******************************************************************************
 * 
 * 
 * 农户合作档案的服务调用
 * 
 * 
 * 
 ******************************************************************************/
//
//
//
//
/*---------------------------------- 新增农户合作档案 开始 --------------------------------------- */
// 新增农户合作档案的数据
var jsonDataAdd = {
	document : "协议编号",// 协议编号
	name : "协议名称",// 协议名称
	begintime : "2016-03-22",// 协议执行开始时间
	endtime : "2018-03-20",// 协议执行截止时间
	peasantid : 2,// 合作农户
	explain : "其他说明",
	operateid : 100,
	loginorgid : "5aa73c9146e0fb00013b6ee4",
	resourcehistorylist : [ {
		resourcepath : "http://wt-yunchuang.oss-cn-beijing.aliyuncs.com/tenant-log-1520921769268.png",
		resourcename : "tenant-log-1520921769268.png",
		timelength : null
	} ]
};

/**
 * 新增农户合作档案
 * 
 * @returns
 */
function add() {

	if (!(window.confirm("确定要新增农户合作档案吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/peasantprotocol/add"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataAdd),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 新增农户合作档案 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 查看农户合作档案 开始 --------------------------------------- */
// 查看农户合作档案的数据
var jsonDataQuerybyid = {
	id : 1
};

/**
 * 查看农户合作档案
 * 
 * @returns
 */
function querybyid() {

	if (!(window.confirm("确定要查看农户合作档案吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/peasantprotocol/querybyid"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQuerybyid),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 查看农户合作档案 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 修改农户合作档案 开始 --------------------------------------- */
// 修改农户合作档案的数据
var jsonDataUpdate = {
	id : 5,// 业务主键
	document : "协议编号",// 协议编号
	name : "协议名称",// 协议名称
	begintime : "2016-03-22",// 协议执行开始时间
	endtime : "2018-03-29",// 协议执行截止时间
	peasantid : 2,// 合作农户
	explain : "其他说明",
	operateid : 100,
	loginorgid : "5aa73c9146e0fb00013b6ee4",
	resourcehistorylist : [ {
		resourcepath : "http://wt-yunchuang.oss-cn-beijing.aliyuncs.com/tenant-log-1520921769268.png",
		resourcename : "tenant-log-1520921769268.png",
		timelength : null
	} ]
};

/**
 * 修改农户合作档案
 * 
 * @returns
 */
function update() {

	if (!(window.confirm("确定要修改农户合作档案吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/peasantprotocol/update"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataUpdate),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 修改农户合作档案 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 查询农户合作档案 开始 --------------------------------------- */
// 查询农户合作档案的数据
var jsonDataQuerylist = {
	parcelid : null,
	keyword : null,
	taskparticsuserid : 1,// 我参与的
	createuserid : null,// 我发布的
	taskcopyuserid : null,// 抄送给我
	currentPage : 1,
	pageSize : 100
};

/**
 * 查询农户合作档案
 * 
 * @returns
 */
function querylist() {

	if (!(window.confirm("确定要查询农户合作档案吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/peasantprotocol/querylist"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQuerylist),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 查询农户合作档案 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 删除农户合作档案 开始 --------------------------------------- */
// 删除农户合作档案的数据
var jsonDataDeletebyid = {
	id : null
};

/**
 * 删除农户合作档案
 * 
 * @returns
 */
function deletebyid() {

	if (!(window.confirm("确定要删除农户合作档案吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/peasantprotocol/deletebyid"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataDeletebyid),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 删除农户合作档案 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 批量删除农户合作档案 开始 --------------------------------------- */
// 删除农户合作档案的数据
var jsonDataBatchdeletebyid = {
	id : [ null ]
};

/**
 * 批量删除农户合作档案
 * 
 * @returns
 */
function batchdeletebyid() {

	if (!(window.confirm("确定要批量删除农户合作档案吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/peasantprotocol/deletebyid"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataBatchdeletebyid),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 批量删除农户合作档案 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 获取地块 开始 --------------------------------------- */
// 获取地块
var jsonDataQueryparcellist = {
	currentPage : 1
};

/**
 * 获取地块
 * 
 * @returns
 */
function queryparcellist() {

	if (!(window.confirm("确定要获取地块吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/peasantprotocol/queryparcellist"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQueryparcellist),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 获取地块 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 查询我参与的农户合作档案 开始 --------------------------------------- */
// 查询我参与的农户合作档案的数据
var jsonDataQuerylistpartics = {
	parcelid : null,
	keyword : null,
	taskparticsuserid : 1,// 我参与的
	createuserid : null,// 我发布的
	taskcopyuserid : null,// 抄送给我
	currentPage : 1,
	pageSize : 100
};

/**
 * 查询我参与的农户合作档案
 * 
 * @returns
 */
function querylistpartics() {

	if (!(window.confirm("确定要查询我参与的农户合作档案吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/peasantprotocol/querylist"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQuerylistpartics),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 查询我参与的农户合作档案 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 查询我发布的农户合作档案 开始 --------------------------------------- */
// 查询我发布的农户合作档案的数据
var jsonDataQuerylistrelease = {
	parcelid : null,
	keyword : null,
	taskparticsuserid : null,// 我参与的
	createuserid : 1,// 我发布的
	taskcopyuserid : null,// 抄送给我
	currentPage : 1,
	pageSize : 100
};

/**
 * 查询我发布的农户合作档案
 * 
 * @returns
 */
function querylistrelease() {

	if (!(window.confirm("确定要查询我发布的农户合作档案吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/peasantprotocol/querylist"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQuerylistrelease),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 查询我发布的农户合作档案 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 查询抄送我的农户合作档案 开始 --------------------------------------- */
// 查询抄送我的农户合作档案的数据
var jsonDataQuerylistcopy = {
	parcelid : null,
	keyword : null,
	taskparticsuserid : null,// 我参与的
	createuserid : null,// 我发布的
	taskcopyuserid : 1,// 抄送给我
	currentPage : 1,
	pageSize : 100
};

/**
 * 查询抄送我的农户合作档案
 * 
 * @returns
 */
function querylistcopy() {

	if (!(window.confirm("确定要查询抄送我的农户合作档案吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/peasantprotocol/querylist"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQuerylistcopy),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 查询抄送我的农户合作档案 结束 --------------------------------------- */
