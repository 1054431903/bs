/*******************************************************************************
 * 
 * 
 * 农事记录的服务调用
 * 
 * 
 * 
 ******************************************************************************/
//
//
//
//
/*---------------------------------- 新增农事记录 开始 --------------------------------------- */
// 新增农事记录的数据
var jsonDataAdd = {
	content : "农事——内容",
	farmtypelist : [ {
		farmtype : 84
	}, {
		farmtype : 85
	}, {
		farmtype : 87
	} ],
	operateid : 134,
	loginorgid : "5ac2f31246e0fb00013b7164",
	begintime : "2018-03-28 08:16",
	endtime : "2018-05-01 21:35",
	cost : 99999999,
	remark : "备注备注。。。。。。。。",
	recordparcellist : [ {
		parcelid : 1
	}, {
		parcelid : 2
	}, {
		parcelid : 3
	} ],
	recordpartuserlist : [ {
		userid : 1
	}, {
		userid : 2
	}, {
		userid : 3
	} ],
	outputlist : [ {
		name : "美嘉特复合肥",
		type : 81,
		stand : "50kg/袋",
		num : 1,
		numunit : 8,
		facturer : "生产厂家不详"
	}, {
		name : "绿田园",
		type : 82,
		stand : "25kg/袋",
		num : 1,
		numunit : 8,
		facturer : "生产厂家不详"
	}, {
		name : "伊斯特",
		type : 83,
		stand : "60kg/袋",
		num : 1,
		numunit : 8,
		facturer : "生产厂家不详"
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
			},
			{
				resourcepath : "http://fwk-oss.oss-cn-shenzhen.aliyuncs.com/fb156a9f-3043-4200-a454-ca42a4088ef1.amr",
				resourcename : "中国人民大学",
				timelength : 25
			} ]
};

/**
 * 新增农事记录
 * 
 * @returns
 */
function add() {

	if (!(window.confirm("确定要新增农事记录吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/record/add"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataAdd),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 新增农事记录 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 查看农事记录 开始 --------------------------------------- */
// 查看农事记录的数据
var jsonDataQuerybyid = {
	id : 32
};

/**
 * 查看农事记录
 * 
 * @returns
 */
function querybyid() {

	if (!(window.confirm("确定要查看农事记录吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/record/querybyid"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQuerybyid),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 查看农事记录 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 修改农事记录 开始 --------------------------------------- */
// 修改农事记录的数据
var jsonDataUpdate = {
	content : "农事记录242424242424242424",
	farmtypelist : [ {
		farmtype : 31
	}, {
		farmtype : 32
	}, {
		farmtype : 33
	} ],
	begintime : "2018-04-17 17:17:00",
	endtime : "2018-04-17 22:17:00",
	cost : 100,
	remark : "备注备注。。。。。。。。",
	id : 445,
	operateid :253,
	loginorgid : "5ac2f31246e0fb00013b7164",
	recordparcellist : [ /*{
		parcelid : 100
	}, {
		parcelid : 200
	}, {
		parcelid : 201
	}, {
		parcelid : 202
	} ],
	recordpartuserlist : [ {
		userid : 123
	}, {
		userid : 124
	}, {
		userid : 678
	}*/ ],
	outputlist : [ /*{
		name : "CCC伊斯特",
		type : 1,
		stand : "360kg/袋",
		num : 1,
		numunit : 8,
		facturer : "CCC生产厂家不详",
		id : 18
	} */],
	recordpartuserlist : [
		{userid : 128}
	],
	resourcehistorylist : [
			/*{
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
			},
			{
				resourcepath : "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517656507439&di=7e83e5dc6e2f052f8a16da26231e23f7&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dpixel_huitu%252C0%252C0%252C294%252C40%2Fsign%3D0553832026f5e0fefa158141351851c4%2F314e251f95cad1c852ca701e743e6709c93d5192.jpg",
				resourcename : "北京理工大学"
			},*/
			{
				resourcepath : "http://fwk-oss.oss-cn-shenzhen.aliyuncs.com/1214231323/xxxxx/df964235-bf19-40e0-ac00-ffebf81bccda.amr",
				resourcename : "录音文件时长测试",
				timelength : 5
			} ]
};

/**
 * 修改农事记录
 * 
 * @returns
 */
function update() {

	if (!(window.confirm("确定要修改农事记录吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/record/update"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataUpdate),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 修改农事记录 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 查询农事记录 开始 --------------------------------------- */
// 查询农事记录的数据
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
 * 查询农事记录
 * 
 * @returns
 */
function querylist() {

	if (!(window.confirm("确定要查询农事记录吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/record/querylist"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataQuerylist),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 查询农事记录 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 删除农事记录 开始 --------------------------------------- */
// 删除农事记录的数据
var jsonDataDeletebyid = {
	id : null
};

/**
 * 删除农事记录
 * 
 * @returns
 */
function deletebyid() {

	if (!(window.confirm("确定要删除农事记录吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/record/deletebyid"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataDeletebyid),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 删除农事记录 结束 --------------------------------------- */
//
//
//
//
/*---------------------------------- 批量删除农事记录 开始 --------------------------------------- */
// 删除农事记录的数据
var jsonDataBatchdeletebyid = {
	id : [ null ]
};

/**
 * 批量删除农事记录
 * 
 * @returns
 */
function batchdeletebyid() {

	if (!(window.confirm("确定要批量删除农事记录吗？"))) {
		return;
	}

	jQuery.ajax({
		url : (api_recode_base_url + "/record/deletebyid"),
		type : 'POST',
		contentType : "application/json;charset=utf-8", // 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : JSON.stringify(jsonDataBatchdeletebyid),
		success : function(data) {
			console.log("response data ====> " + data);
		}
	});
}
/*---------------------------------- 批量删除农事记录 结束 --------------------------------------- */
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
		url : (api_recode_base_url + "/record/queryparcellist"),
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

