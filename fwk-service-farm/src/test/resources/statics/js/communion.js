/*******************************************************************************
 * 
 * 
 * 农事交流的服务调用
 * 
 * 
 * 
 ******************************************************************************/
//
//
//
//
/*---------------------------------- 新增农事交流 开始 --------------------------------------- */
// 新增农事交流的数据
var jsonDataAdd = {
	operateid : 1,
	communioncontent : "农事交流问题内容rtyjkluhyhuusghfisfhsuhfudfsf",
	/* location : '中国 云南 昆明', */
	communionparcellist : [ {
		parcelid : 1
	}, {
		parcelid : 2
	}, {
		parcelid : 3
	} ],
	communioncategorylist : [ {
		categoryname : "苹果",
		categoryid : 1
	}, {
		categoryname : "茶叶",
		categoryid : 2
	}, {
		categoryname : "三七",
		categoryid : 3
	} ],
	resourcehistorylist : [
			{
				resourcepath : "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517631733682&di=45cdb0baef638632d0af7b264c8cf0d5&imgtype=0&src=http%3A%2F%2Fnews.aweb.com.cn%2Fawebupf%2F5%2F118%2F20091127%2F10403511.jpg",
				resourcename : "北京大学"
			},
			{
				resourcepath : "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517631817831&di=8b1284d519103c0b8918f1e176261b6d&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F0ff41bd5ad6eddc4996e842f33dbb6fd5366335c.jpg",
				resourcename : "清华大学"
			},
			{
				resourcepath : "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517631940018&di=d1ee313eaf01cf1c2c73412d24a89a70&imgtype=0&src=http%3A%2F%2Fimg.edu-hb.com%2FNewsImg%2F201704%2F17%2F170417201149a55kf.jpeg",
				resourcename : "中国人民大学"
			} ]
};

/**
 * 新增农事交流
 * 
 * @returns
 */
function add() {

	if (!(window.confirm("确定要新增农事交流吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/communion/add"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataAdd),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 新增农事交流 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 查看农事交流 开始 --------------------------------------- */
// 查看农事交流的数据
var jsonDataQuerybyid = {
	id : 12
};

/**
 * 查看农事交流
 * 
 * @returns
 */
function querybyid() {

	if (!(window.confirm("确定要查看农事交流吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/communion/querybyid"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQuerybyid),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 查看农事交流 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 修改农事交流 开始 --------------------------------------- */
// 修改农事交流的数据
var jsonDataUpdate = {
	id : 1,
	operateid : 1,
	communioncontent : "农事交流问题内容rtyjkluhyhuusghfisfhsuhfudfsfxxxxxxxxxxxxxx",
	/* location : '中国 云南 昆明', */
	communionparcellist : [ {
		parcelid : 1
	}, {
		parcelid : 2
	}, {
		parcelid : 3
	} ],
	communioncategorylist : [ {
		categoryname : "苹果",
		categoryid : 1
	}, {
		categoryname : "茶叶",
		categoryid : 2
	}, {
		categoryname : "三七",
		categoryid : 3
	} ],
	resourcehistorylist : [
			{
				resourcepath : "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517631733682&di=45cdb0baef638632d0af7b264c8cf0d5&imgtype=0&src=http%3A%2F%2Fnews.aweb.com.cn%2Fawebupf%2F5%2F118%2F20091127%2F10403511.jpg",
				resourcename : "北京大学"
			},
			{
				resourcepath : "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517631817831&di=8b1284d519103c0b8918f1e176261b6d&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F0ff41bd5ad6eddc4996e842f33dbb6fd5366335c.jpg",
				resourcename : "清华大学"
			},
			{
				resourcepath : "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517631940018&di=d1ee313eaf01cf1c2c73412d24a89a70&imgtype=0&src=http%3A%2F%2Fimg.edu-hb.com%2FNewsImg%2F201704%2F17%2F170417201149a55kf.jpeg",
				resourcename : "中国人民大学"
			} ]
};

/**
 * 修改农事交流
 * 
 * @returns
 */
function update() {

	if (!(window.confirm("确定要修改农事交流吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/communion/update"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataUpdate),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 修改农事交流 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 查询农事交流 开始 --------------------------------------- */
// 查询农事交流的数据
var jsonDataQuerylist = {
	parcelid : null,
	parcelid : null,
	keyword : null,
	farmtypelist : [ {
		farmtype : 9
	}, {
		farmtype : 10
	}, {
		farmtype : 11
	} ],
};

/**
 * 查询农事交流
 * 
 * @returns
 */
function querylist() {

	if (!(window.confirm("确定要查询农事交流吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/communion/querylist"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQuerylist),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 查询农事交流 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 删除农事交流 开始 --------------------------------------- */
// 删除农事交流的数据
var jsonDataDeletebyid = {
	id : null
};

/**
 * 删除农事交流
 * 
 * @returns
 */
function deletebyid() {

	if (!(window.confirm("确定要删除农事交流吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/communion/deletebyid"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataDeletebyid),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 删除农事交流 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 批量删除农事交流 开始 --------------------------------------- */
// 删除农事交流的数据
var jsonDataBatchdeletebyid = {
	id : [ null ]
};

/**
 * 批量删除农事交流
 * 
 * @returns
 */
function batchdeletebyid() {

	if (!(window.confirm("确定要批量删除农事交流吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/communion/deletebyid"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataBatchdeletebyid),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 批量删除农事交流 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 获取地块 开始 --------------------------------------- */
// 获取地块
var jsonDataQueryparcellist = {
	operateid : 1
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
		url : (api_recode_base_url + "/communion/queryparcellist"),
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
/*---------------------------------- 新增农事交流 的评论开始 --------------------------------------- */
// 新增农事交流 的评论的数据
var jsonDataAddCommentcontent = {
	operateid : 1,
	communionid : 1,
	commentcontent : "农事交流-1-回复-salfjjlasfhfkjsklfsldfhlsfdjsdf"
};

/**
 * 新增农事交流的评论
 * 
 * @returns
 */
function addCommentcontent() {

	if (!(window.confirm("确定要新增农事交流的评论吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/communion/commentcontent/add"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataAddCommentcontent),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 新增农事交流 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 新增农事交流 的评论开始 --------------------------------------- */
//新增农事交流 的评论的数据
var jsonDataAddCommentcontent = {
	operateid : 1,
	communionid : 1,
	commentcontent : "农事交流-1-回复-salfjjlasfhfkjsklfsldfhlsfdjsdf"
};

/**
* 新增农事交流的评论
* 
* @returns
*/
function addCommentcontent() {

	if (!(window.confirm("确定要新增农事交流的评论吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/communion/commentcontent/add"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataAddCommentcontent),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 新增农事交流 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 删除农事交流 开始 --------------------------------------- */
// 删除农事交流的数据
var jsonDataDeletebyid = {
	id : null
};

/**
 * 删除农事交流
 * 
 * @returns
 */
function deletebyid() {

	if (!(window.confirm("确定要删除农事交流吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/communion/deletebyid"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataDeletebyid),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 删除农事交流 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 批量删除农事交流 开始 --------------------------------------- */
// 删除农事交流的数据
var jsonDataBatchdeletebyid = {
	id : [ null ]
};

/**
 * 批量删除农事交流
 * 
 * @returns
 */
function batchdeletebyid() {

	if (!(window.confirm("确定要批量删除农事交流吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/communion/deletebyid"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataBatchdeletebyid),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 批量删除农事交流 结束 --------------------------------------- */
//
//
//
//