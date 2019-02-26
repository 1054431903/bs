package com.fwk.service.common.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.fwk.common.util.Files;
import com.fwk.common.util.HttpClientUtils;
import com.fwk.common.util.ResultWrap;

/**
 * 图片存储方法对象
 * 
 * @author xx
 *
 */
@Service
public class ResourceService {
	
	@Autowired
	private AccessService accessService;

	// 图片改为直接从图片服务器上获取，此方法废弃
	/*
	 * public Object getImgByUUId(String uuid) throws IOException {
	 * String url = "http://10.88.20.95/image/" + uuid; ResultWrap resultWrap =
	 * new ResultWrap(); resultWrap = HttpClientUtils.doGet(url); //
	 * 返回图片字节流转文件验证测试代码
	 * 
	 * byte[] bts = (new
	 * BASE64Decoder()).decodeBuffer(resultWrap.getData().toString()); File
	 * imageFile = new File("D:/BeautyGirl.png"); //创建输出流
	 * 
	 * FileOutputStream outStream = new FileOutputStream(imageFile); //写入数据 try
	 * {
	 * 
	 * outStream.write(bts); outStream.close(); } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * return resultWrap.getData(); }
	 */
	// 存储资源
	public ResultWrap saveResByUUId(List<Files> resList) {
		String url = accessService.SERVICE_DB_UPLOADRESOURCE;
		//String url = "http://10.88.20.25:8085/acp-service-db/db/resource/upload/";
		ResultWrap resultWrap = new ResultWrap();
		try {
			resultWrap = HttpClientUtils.doPost(url, JSON.toJSONString(resList.get(0)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultWrap;

	}

	/**
	 * 重载 saveResByUUId 
	 * 
	 * @param resource
	 *            2016-05-20
	 * @return
	 */
	public ResultWrap saveResByUUId(Files resource) {
		return saveResByUUId(Arrays.asList(resource));
	}

	/**
	 * 重载资源上传 
	 * @param inputStream
	 * @param filename
	 * @return
	 */
	public ResultWrap saveRes(InputStream inputStream, String filename) {
		ResultWrap resultWrap = new ResultWrap();
		if (null != inputStream) {
			Files res = new Files();
			try {
				res.setData(IOUtils.toByteArray(inputStream));
			} catch (IOException e) {
				resultWrap.error(e.getMessage());
				e.printStackTrace();
			}
			//res.setFilename(filename);
			res.setResName(filename);
			return saveResByUUId(res);
		} else {
			resultWrap.error("资源为空");
		}
		return resultWrap;
	}

	// 删除资源
	public ResultWrap deleteResByUUId(List<String> resList) {
		String url = accessService.SERVICE_DB_DELETERESOURCE;
		ResultWrap resultWrap = new ResultWrap();
		try {
			resultWrap = HttpClientUtils.doPost(url, JSON.toJSONString(resList));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultWrap;
	}

	public byte[] readInputStream(InputStream inStream) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		try {
			while ((len = inStream.read(buffer)) != -1) {
				// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
				outStream.write(buffer, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 关闭输入流
		try {
			inStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}
}
