/*******************************************************************************
 * 
 * 
 * 文件上传的服务调用
 * 
 * 
 * 
 ******************************************************************************/
//
//
//
/**
 * 普通的文件上传
 * 
 * @returns
 */
function upload() {

	if (!(window.confirm("确定要上传吗？"))) {
		return;
	}

	/*var formData = new FormData();
	formData.append("fileupload", $('#upload_form').get(0).files[0]);*/
	
	var formData = new FormData();
    formData.append("fileupload",document.getElementById("upload_form").files[0]);

	$.ajax({
		url : (api_recode_base_url + "/common/fileservice/upload"),
		type : 'POST',
		/* contentType : "application/json;charset=utf-8", */// 必须有
		dataType : "json", // 表示返回值类型，不必须
		data : formData,
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
