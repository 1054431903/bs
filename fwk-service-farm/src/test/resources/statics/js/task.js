/*******************************************************************************
 * 
 * 
 * 农事任务的服务调用
 * 
 * 
 * 
 ******************************************************************************/
//
//
//
//
/*---------------------------------- 新增农事任务 开始 --------------------------------------- */
// 新增农事任务的数据
var jsonDataAdd = {
	type : "task-work",
	describe : "农事任务 — 施肥",
	begintime : "2018-02-05 08:00:00",
	endtime : "2018-02-06 08:00:00",
	isaffirmpartics : 1,
	remark : "备注备注。。。。。。。。",
	operateid : 33,
	loginorgid : "5abdb0a146e0fb00013b70fd",
	taskparcellist : [ {
		parcelid : 4
	}, {
		parcelid : 5
	}],
	taskparticslist : [ {
		userid : 105
	}, {
		userid : 106
	}, {
		userid : 107
	}],
	taskcopylist : [ {
		userid : 103
	} ],
	taskwarnlist : [ {
		type : "remind-note",
		warntimes : 45,
		timeunit : "farm-time-unit-minutes"
	} ]
};

/**
 * 新增农事任务
 * 
 * @returns
 */
function add() {

	if (!(window.confirm("确定要新增农事任务吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/task/add"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataAdd),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 新增农事任务 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 查看农事任务 开始 --------------------------------------- */
// 查看农事任务的数据
var jsonDataQuerybyid = {
	id : 1
};

/**
 * 查看农事任务
 * 
 * @returns
 */
function querybyid() {

	if (!(window.confirm("确定要查看农事任务吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/task/querybyid"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQuerybyid),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 查看农事任务 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 修改农事任务 开始 --------------------------------------- */
// 修改农事任务的数据
var jsonDataUpdate = {
	id : 7,
	type : "task-work",
	describe : "农事任务 — 施肥xxxxxoooooxxx",
	begintime : "2018-02-05 08:00:00",
	endtime : "2018-02-06 08:00:00",
	isaffirmpartics : 1,
	remark : "备注备注。。。。。。。。",
	operateid : 100,
	loginorgid : "5aa73c9146e0fb00013b6ee4",
	taskparcellist : [ {
		parcelid : 4
	}, {
		parcelid : 5
	}],
	taskparticslist : [ {
		userid : 105
	}, {
		userid : 106
	}, {
		userid : 107
	}],
	taskcopylist : [ {
		userid : 103
	} ],
	taskwarnlist : [ {
		type : "remind-note",
		warntimes : 45,
		timeunit : "farm-time-unit-minutes"
	} ]
};

/**
 * 修改农事任务
 * 
 * @returns
 */
function update() {

	if (!(window.confirm("确定要修改农事任务吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/task/update"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataUpdate),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 修改农事任务 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 查询农事任务 开始 --------------------------------------- */
// 查询农事任务的数据
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
 * 查询农事任务
 * 
 * @returns
 */
function querylist() {

	if (!(window.confirm("确定要查询农事任务吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/task/querylist"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQuerylist),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 查询农事任务 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 删除农事任务 开始 --------------------------------------- */
// 删除农事任务的数据
var jsonDataDeletebyid = {
	id : null
};

/**
 * 删除农事任务
 * 
 * @returns
 */
function deletebyid() {

	if (!(window.confirm("确定要删除农事任务吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/task/deletebyid"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataDeletebyid),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 删除农事任务 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 批量删除农事任务 开始 --------------------------------------- */
// 删除农事任务的数据
var jsonDataBatchdeletebyid = {
	id : [ null ]
};

/**
 * 批量删除农事任务
 * 
 * @returns
 */
function batchdeletebyid() {

	if (!(window.confirm("确定要批量删除农事任务吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/task/deletebyid"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataBatchdeletebyid),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 批量删除农事任务 结束 --------------------------------------- */
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
		url : (api_recode_base_url + "/task/queryparcellist"),
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
/*---------------------------------- 查询我参与的农事任务 开始 --------------------------------------- */
// 查询我参与的农事任务的数据
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
 * 查询我参与的农事任务
 * 
 * @returns
 */
function querylistpartics() {

	if (!(window.confirm("确定要查询我参与的农事任务吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/task/querylist"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQuerylistpartics),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 查询我参与的农事任务 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 查询我发布的农事任务 开始 --------------------------------------- */
// 查询我发布的农事任务的数据
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
 * 查询我发布的农事任务
 * 
 * @returns
 */
function querylistrelease() {

	if (!(window.confirm("确定要查询我发布的农事任务吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/task/querylist"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQuerylistrelease),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 查询我发布的农事任务 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 查询抄送我的农事任务 开始 --------------------------------------- */
// 查询抄送我的农事任务的数据
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
 * 查询抄送我的农事任务
 * 
 * @returns
 */
function querylistcopy() {

	if (!(window.confirm("确定要查询抄送我的农事任务吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/task/querylist"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQuerylistcopy),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 查询抄送我的农事任务 结束 --------------------------------------- */
