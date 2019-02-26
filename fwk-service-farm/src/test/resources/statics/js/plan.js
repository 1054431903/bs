/*******************************************************************************
 * 
 * 
 * 农事计划的服务调用
 * 
 * 
 * 
 ******************************************************************************/
//
//
//
//
/*---------------------------------- 新增农事计划 开始 --------------------------------------- */
// 新增农事计划的数据
var jsonDataAdd = {
	describe : "农事计划 — 施肥",
	begintime : "2018-02-05 08:00:00",
	endtime : "2018-02-06 08:00:00",
	remark : "备注备注。。。。。。。。",
	operateid : 1,
	planparcellist : [ {
		parcelid : 1
	}, {
		parcelid : 2
	}, {
		parcelid : 3
	} ],
	planparticslist : [ {
		userid : 1
	}, {
		userid : 7
	}, {
		userid : 8
	} ],
	planwarnlist : [ {
		type : "remind-note",
		warntimes : 45,
		timeunit : "farm-time-unit-minutes"
	} ]
};

/**
 * 新增农事计划
 * 
 * @returns
 */
function add() {

	if (!(window.confirm("确定要新增农事计划吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/plan/add"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataAdd),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 新增农事计划 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 查看农事计划 开始 --------------------------------------- */
// 查看农事计划的数据
var jsonDataQuerybyid = {
	id : 1
};

/**
 * 查看农事计划
 * 
 * @returns
 */
function querybyid() {

	if (!(window.confirm("确定要查看农事计划吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/plan/querybyid"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQuerybyid),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 查看农事计划 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 修改农事计划 开始 --------------------------------------- */
// 修改农事计划的数据
var jsonDataUpdate = {
	id : 3,
	describe : "农事计划 — 施肥333333-hsdgfdsdjhfjdshf",
	begintime : "2018-02-05 08:00:00",
	endtime : "2018-02-06 08:00:00",
	remark : "备注备注。。。。。。。。",
	operateid : 1,
	planparcellist : [ {
		parcelid : 1
	}, {
		parcelid : 12
	}, {
		parcelid : 13
	} ],
	planparticslist : [ {
		userid : 1
	}, {
		userid : 17
	}, {
		userid : 18
	} ],
	planwarnlist : [ {
		type : "remind-note",
		warntimes : 5,
		timeunit : "farm-time-unit-hour"
	} ]
};

/**
 * 修改农事计划
 * 
 * @returns
 */
function update() {

	if (!(window.confirm("确定要修改农事计划吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/plan/update"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataUpdate),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 修改农事计划 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 查询农事计划 开始 --------------------------------------- */
// 查询农事计划的数据
var jsonDataQuerylist = {
	begintime : '2018-02-05',
	endtime : '2018-02-06'
};

/**
 * 查询农事计划
 * 
 * @returns
 */
function querylist() {

	if (!(window.confirm("确定要查询农事计划吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/plan/querylist"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQuerylist),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 查询农事计划 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 删除农事计划 开始 --------------------------------------- */
// 删除农事计划的数据
var jsonDataDeletebyid = {
	id : null
};

/**
 * 删除农事计划
 * 
 * @returns
 */
function deletebyid() {

	if (!(window.confirm("确定要删除农事计划吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/plan/deletebyid"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataDeletebyid),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 删除农事计划 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 批量删除农事计划 开始 --------------------------------------- */
// 删除农事计划的数据
var jsonDataBatchdeletebyid = {
	id : [ null ]
};

/**
 * 批量删除农事计划
 * 
 * @returns
 */
function batchdeletebyid() {

	if (!(window.confirm("确定要批量删除农事计划吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/plan/deletebyid"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataBatchdeletebyid),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 批量删除农事计划 结束 --------------------------------------- */
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
		url : (api_recode_base_url + "/plan/queryparcellist"),
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
/*---------------------------------- 按月查询农事计划 开始 --------------------------------------- */
// 按月查询农事计划
var jsonDataQuerymonth = {
	begintime : '2018-02-05',
	endtime : '2018-02-05'
};

/**
 * 按月查询农事计划
 * 
 * @returns
 */
function querymonth() {

	if (!(window.confirm("确定要按月查询农事计划吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/plan/querylist"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQuerymonth),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 按月查询农事计划 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 按周查询农事计划 开始 --------------------------------------- */
// 按周查询农事计划
var jsonDataQueryweek = {
	begintime : '2018-02-05',
	endtime : '2018-02-05'
};

/**
 * 按周查询农事计划
 * 
 * @returns
 */
function queryweek() {

	if (!(window.confirm("确定要按周查询农事计划吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/plan/querylist"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQueryweek),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 按周查询农事计划 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 按日查询农事计划 开始 --------------------------------------- */
// 按日查询农事计划
var jsonDataQueryday = {
	begintime : '2018-02-05',
	endtime : '2018-02-05'
};

/**
 * 按日查询农事计划
 * 
 * @returns
 */
function queryday() {

	if (!(window.confirm("确定要按日查询农事计划吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/plan/querylist"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQueryday),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 按日查询农事计划 结束 --------------------------------------- */
