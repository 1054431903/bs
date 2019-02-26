package com.fwk.service.common.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.zip.ZipOutputStream;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;
import com.fwk.common.util.BaseAccess;
import com.fwk.common.util.ConstantUtils;
import com.fwk.common.util.DateTimeUtil;
import com.fwk.common.util.FileUtils;
import com.fwk.common.util.Files;
import com.fwk.common.util.HTMLUtil;
import com.fwk.common.util.HttpClientUtils;
import com.fwk.common.util.ImageBase64Utility;
import com.fwk.common.util.ObjectUtil;
import com.fwk.common.util.RequestUploadBase64Image;
import com.fwk.common.util.ResponseUploadBase64Image;
import com.fwk.common.util.ResultWrap;
import com.fwk.common.util.ZipUtils;
import com.fwk.common.util.constants.EImageFormat;
import com.fwk.common.util.constants.EResourceType;
import com.fwk.common.util.jave.Encoder;
import com.fwk.common.util.jave.MultimediaInfo;

/**
 * 文件上传或者下载的处理工具类
 * 
 * @author wangjunwen
 * @date 2017-08-20 13:42:22
 *
 */
@RestController
public class FileUploadDownloadService {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadDownloadService.class);

	@Autowired
	private AccessService accessService;

	/** 访问图片资源的URL的前缀 */
	public String RESOURCE_URL_PREFIX;

	/** 文件上传的临时路径 */
	public String TEMP_FILE_UPLOAD_PATH = (System.getProperty("java.io.tmpdir") + "/var/temp/fileupload/fwk/");

	/** 临时保存上传文件的目录 */
	private File TEMP_DIRECTRY = new File(TEMP_FILE_UPLOAD_PATH);

	/** Base64传输的图片的头部 */
	private Map<String, String> BASE64_IMAGE_HEADER_DATA_MAP = new HashMap<String, String>();

	@PostConstruct
	public void init() {
		if (!(TEMP_DIRECTRY.exists())) {
			TEMP_DIRECTRY.mkdirs();
		}

		// png 格式的
		BASE64_IMAGE_HEADER_DATA_MAP.put("data:image/png;base64,", EImageFormat.png.name());
		// jpg格式的
		BASE64_IMAGE_HEADER_DATA_MAP.put("data:image/jpeg;base64,", EImageFormat.jpg.name());
		BASE64_IMAGE_HEADER_DATA_MAP.put("data:image/jpg;base64,", EImageFormat.jpg.name());
		// gif格式的
		BASE64_IMAGE_HEADER_DATA_MAP.put("data:image/gif;base64,", EImageFormat.gif.name());

		// 不允许在其他地方对其修改
		BASE64_IMAGE_HEADER_DATA_MAP = Collections.unmodifiableMap(BASE64_IMAGE_HEADER_DATA_MAP);

		RESOURCE_URL_PREFIX = ("http://" + accessService.RESOURCE_PUBLIC_HOST + ":" + accessService.SERVICE_PUBLIC_PORT
				+ "/");
	}

	/**
	 * 将 java.io.File 对象转换为 com.trace.common.util.Files 对象
	 * 
	 * @author wangjunwen
	 * @date 2017-08-20 14:01:04
	 *
	 * @param resource
	 *            File对象{@link java.io.File}
	 * @return
	 * @see com.trace.common.util.Files
	 * @throws Exception
	 */
	public Files convertFileToFiles(File file) throws Exception {

		// 初始化文件的字节数组
		byte[] data = {};

		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				FileInputStream fileInputStream = new FileInputStream(file);) {

			// 定义每次读取的字节数量
			final int byteSize = 1024;
			byte[] bytes = new byte[byteSize];

			// 当前读取到的字节数量
			int readSize = 0;
			while ((readSize = fileInputStream.read(bytes)) > 0) {
				// 如果当前读取到的字节数量与定义每次读取的字节数量相等，那么直接写入到字节数组缓冲区，否则只写入当前读取到的字节数到字节数组缓冲区
				if (byteSize == readSize) {
					byteArrayOutputStream.write(bytes);
				} else {
					byteArrayOutputStream.write(Arrays.copyOf(bytes, readSize));
				}
			}

			data = byteArrayOutputStream.toByteArray();

		} catch (Exception e) {
			throw e;
		}

		// 文件扩展名（不包含圆点）
		String extionName = file.getName().substring((file.getName().lastIndexOf(".") + 1));

		// 创建 Files 对象
		Files resource = new Files();
		resource.setExtName(extionName);
		resource.setData(data);
		resource.setUploadDate(DateTimeUtil.getNow());
		resource.setFormat(extionName);
		resource.setResName(file.getName());

		return resource;
	}

	/**
	 * 上传文件
	 * 
	 * @author wangjunwen
	 * @date 2017-08-20 14:02:35
	 *
	 * @param file
	 *            File对象{@link java.io.File}
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(File file) throws Exception {

		// 上传到阿里云的 OSS 上
		return uploadToAliyunOSS(file);

		// 上传到 FastDFS 上
		// return uploadToFastDFS(file);
	}

	/**
	 * 上传到 FastDFS 上
	 * 
	 * @author wangjunwen
	 * @date 2018年4月3日 上午9:21:39
	 *
	 * @param file
	 *            File对象{@link java.io.File}
	 * @return
	 * @throws Exception
	 */
	public String uploadToFastDFS(File file) throws Exception {
		String url = null;// 图片上传后访问的URL地址

		try {
			// 创建 Files 对象
			Files resource = convertFileToFiles(file);

			// 将 Files 对象转换为 json 串
			final String jsonParam = JSONObject.toJSONString(resource);

			logger.info("[资源服务器地址]=======> " + accessService.SERVICE_DB_UPLOADRESOURCE);

			final ResultWrap resultWrap = HttpClientUtils.doPost(accessService.SERVICE_DB_UPLOADRESOURCE, jsonParam);

			if ((null != resultWrap)) {

				ResultWrap dataResultWrap = JSONObject.toJavaObject(
						(JSONObject) JSONObject.parse(ObjectUtil.toString(resultWrap.getData())), ResultWrap.class);
				try {

					if (ConstantUtils.RESULTWRAP_FLAG_OK.equals(dataResultWrap.getFlag())) {
						url = ((null == dataResultWrap.getData()) ? "" : String.valueOf(dataResultWrap.getData()));
					}

				} catch (Exception e) {
					// 原来上传成功后返回的 data 中的数据为资源的 URL
					url = ((null == resultWrap.getData()) ? "" : String.valueOf(resultWrap.getData()));
				}
			}

		} catch (Exception e) {
			throw e;
		}

		return (RESOURCE_URL_PREFIX + url);
	}

	/**
	 * 上传到阿里云的 OSS 上
	 * 
	 * @author wangjunwen
	 * @date 2018年4月3日 上午9:21:39
	 *
	 * @param file
	 *            File对象{@link java.io.File}
	 * @return
	 * @throws Throwable
	 */
	public String uploadToAliyunOSS(File file) {

		// endpoint以杭州为例，其它region请按实际情况填写
		String endpoint = accessService.ALIYUN_OSS_ENDPOINT;// "http://oss-cn-hangzhou.aliyuncs.com";
		// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录
		// https://ram.console.aliyun.com 创建
		String accessKeyId = accessService.ALIYUN_OSS_ACCESSKEYID;// "<yourAccessKeyId>";
		String accessKeySecret = accessService.ALIYUN_OSS_ACCESSKEYSECRET;// "<yourAccessKeySecret>";
		// 创建OSSClient实例
		DefaultCredentialProvider defaultCredentialProvider = new DefaultCredentialProvider(accessKeyId,
				accessKeySecret);

		OSSClient ossClient = null;
		try (// 上传文件流
				InputStream inputStream = new FileInputStream(file);) {

			ossClient = new OSSClient(endpoint, defaultCredentialProvider, null);

			// 文件名作为 key（必须保证在该 Bukker 中的唯一性质）
			final String key = (UUID.randomUUID().toString() + (FileUtils.getFileExtentionName(file)));

			UploadFileRequest uploadFileRequest = new UploadFileRequest(accessService.ALIYUN_OSS_BUKKER, key);

			uploadFileRequest.setUploadFile(file.getAbsolutePath());

			UploadFileResult uploadFileResult = ossClient.uploadFile(uploadFileRequest);

			// 返回成功上传后的文件地址
			return uploadFileResult.getMultipartUploadResult().getLocation();
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			if (null != ossClient) {
				// 关闭client
				ossClient.shutdown();
			}
		}

		return null;
	}

	/**
	 * 上传文件
	 * 
	 * @author wangjunwen
	 * @date 2017-08-21 13:29:54
	 *
	 * @param request
	 * @param response
	 */
	public void upload(HttpServletRequest request, HttpServletResponse response) {
		ResultWrap resultWrap = new ResultWrap();

		// 上传到服务器之后可访问的URL地址
		final List<String> urlList = new ArrayList<String>();

		// 上传到本地的文件，上传到服务器之后需要把本地的文件删除掉
		final List<File> uploadFileList = new ArrayList<File>();

		try {
			final Collection<Part> parts = request.getParts();
			Iterator<Part> iterator = parts.iterator();

			Part part = null;
			File uploadFile = null;

			final int everyTimeReadSize = 1024;// 每次读取多少个字节
			int readSize = 0;// 当前读取到的字节数量
			byte[] bytes = new byte[everyTimeReadSize];

			String url = "";// 上传到服务器之后的URL访问地址
			String filePath = "";// 上传的文件路径

			// 遍历上传的每一个文件
			while (iterator.hasNext()) {
				part = iterator.next();

				if (!(TEMP_DIRECTRY.exists())) {
					TEMP_DIRECTRY.mkdirs();
				}

				// 文件路径（避免发生文件名重名）
				filePath = (TEMP_DIRECTRY.getPath() + "/"
						+ (UUID.randomUUID().toString() + "_" + part.getSubmittedFileName()));

				uploadFile = new File(filePath);

				try (InputStream inputStream = part.getInputStream();
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
						FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);) {

					while ((readSize = inputStream.read(bytes)) > 0) {
						if (everyTimeReadSize == readSize) {
							byteArrayOutputStream.write(bytes);
						} else {
							byteArrayOutputStream.write(Arrays.copyOf(bytes, readSize));
						}
					}

					fileOutputStream.write(byteArrayOutputStream.toByteArray());

					uploadFileList.add(uploadFile);

					// 上传到服务器，并获取可访问的URL地址
					url = uploadFile(uploadFile);

					urlList.add(url);
				} catch (Exception e) {
					throw e;
				}
			}

			// 设置响应客户端的数据
			resultWrap.setData(urlList);
		} catch (Exception e) {
			resultWrap.error(e.getMessage());
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);

			if ((null != uploadFileList) && (uploadFileList.size() > 0)) {
				for (File file : uploadFileList) {
					file.delete();// 删除文件
				}
			}
		}
	}

	/**
	 * 上传原始文件
	 * 
	 * @author wangjunwen
	 * @date 2018年1月27日 上午9:50:35
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public List<FileUploadEXT> uploadProtogenesis(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return (uploadProtogenesis(request, response, true));
	}

	/**
	 * 上传原始文件
	 * 
	 * @author wangjunwen
	 * @date 2018年1月27日 上午9:50:35
	 *
	 * @param request
	 * @param response
	 * @param isDeleteLocalFile
	 *            是否删除上传在本地的文件(true：删除；false：不删除)
	 * @return
	 * @throws Exception
	 */
	public List<FileUploadEXT> uploadProtogenesis(HttpServletRequest request, HttpServletResponse response,
			boolean isDeleteLocalFile) throws Exception {

		// 上传之后的文件
		List<FileUploadEXT> fileUploadEXTList = new ArrayList<FileUploadEXT>();

		// 上传到本地的文件，上传到服务器之后需要把本地的文件删除掉
		final List<File> uploadFileList = new ArrayList<File>();

		final Collection<Part> parts = request.getParts();
		Iterator<Part> iterator = parts.iterator();

		Part part = null;
		File uploadFile = null;

		final int everyTimeReadSize = 1024;// 每次读取多少个字节
		int readSize = 0;// 当前读取到的字节数量
		byte[] bytes = new byte[everyTimeReadSize];

		String url = "";// 上传到服务器之后的URL访问地址
		String filePath = "";// 上传的文件路径

		FileUploadEXT tempFileUploadEXT = null;

		// Header 中的 Content-Disposition 的内容
		String contentDisposition = null;
		String fileName = null;// 文件名

		// 遍历上传的每一个文件
		while (iterator.hasNext()) {
			part = iterator.next();

			if (!(TEMP_DIRECTRY.exists())) {
				TEMP_DIRECTRY.mkdirs();
			}

			// Returns The header value or null if the header is not present
			contentDisposition = part.getHeader("Content-Disposition");

			if ((null == contentDisposition) || (-1 == contentDisposition.indexOf("filename="))) {
				continue;
			}

			// 例如：form-data; name="uploadfile"; filename="task.js"
			fileName = (contentDisposition.split(";")[2].replaceFirst("filename=", "").replaceAll("\"", "")).trim();

			if (fileName.lastIndexOf("\\") > 0) {
				fileName = fileName.substring(fileName.lastIndexOf("\\"));
			}

			// 文件路径（避免发生文件名重名）
			filePath = (TEMP_DIRECTRY.getPath() + "/" + (UUID.randomUUID().toString() + "_" + fileName));

			uploadFile = new File(filePath);

			try (InputStream inputStream = part.getInputStream();
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);) {

				while ((readSize = inputStream.read(bytes)) > 0) {
					if (everyTimeReadSize == readSize) {
						byteArrayOutputStream.write(bytes);
					} else {
						byteArrayOutputStream.write(Arrays.copyOf(bytes, readSize));
					}
				}

				fileOutputStream.write(byteArrayOutputStream.toByteArray());

				uploadFileList.add(uploadFile);

				// 上传到服务器，并获取可访问的URL地址
				url = uploadFile(uploadFile);

				// 获取音、视频文件的播放时长
				final Integer timelength = getTimeLength(uploadFile.getAbsolutePath());

				tempFileUploadEXT = new FileUploadDownloadService().new FileUploadEXT();
				tempFileUploadEXT.setResourcename(fileName);// 文件名称
				tempFileUploadEXT.setResourcepath(url);// 文件的访问URL地址
				tempFileUploadEXT.setTimelength(timelength);// 设置音、视频文件的播放时长
				tempFileUploadEXT.setLocalpath(uploadFile.getAbsolutePath());// 设置上传在本地的路径

				fileUploadEXTList.add(tempFileUploadEXT);

			} finally {
				if (isDeleteLocalFile && (null != uploadFileList) && (uploadFileList.size() > 0)) {
					for (File file : uploadFileList) {
						file.delete();// 删除文件
					}
				}
			}
		}

		return fileUploadEXTList;
	}

	/**
	 * 以 Base64 的方式上传图片
	 * 
	 * @author wangjunwen
	 * @date 2017年9月24日 下午4:36:49
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public List<FileUploadEXT> uploadImageByBase64EXT(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> requestMap = HTMLUtil.getPageParams(request);//xx
		// 上传之后的文件
		List<FileUploadEXT> fileUploadEXTList = new ArrayList<FileUploadEXT>();

		// 上传到本地的文件，上传到服务器之后需要把本地的文件删除掉
		final List<File> uploadFileList = new ArrayList<File>();

		try {
			BaseAccess baseAccess = new BaseAccess();
			Map<String, Object> paramMap = baseAccess.getParamMap(request);

			// 去掉字符串两端的空格
			HTMLUtil.trimByString(paramMap);
			HTMLUtil.trimByString(requestMap);//xx

			// 将 null 值或者空字符串的元素过滤掉
			ObjectUtil.filterNullValue(paramMap);
			ObjectUtil.filterNullValue(requestMap);//xx

			RequestUploadBase64Image requestUploadBase64Image = null;

			if ((null != paramMap) && (paramMap.size() > 0)) {
				requestUploadBase64Image = JSONObject.toJavaObject((JSONObject) JSONObject.toJSON(paramMap),
						RequestUploadBase64Image.class);
			}
			
			//xx
			if ((null != requestMap) && (requestMap.size() > 0)) {
				requestUploadBase64Image = JSONObject.toJavaObject((JSONObject) JSONObject.toJSON(requestMap),
						RequestUploadBase64Image.class);
			}

			if ((null != requestUploadBase64Image) && (null != requestUploadBase64Image.getBase64Data())) {

				// 头部信息
				// final String headerData = "data:image/png;base64,";

				// 是否是支持的图片格式
				boolean isSupportedFormat = false;

				FileUploadEXT tempFileUploadEXT = null;

				Entry<String, String> entry = null;
				for (Iterator<Entry<String, String>> iterator = BASE64_IMAGE_HEADER_DATA_MAP.entrySet()
						.iterator(); iterator.hasNext();) {

					entry = iterator.next();

					if (requestUploadBase64Image.getBase64Data().startsWith(entry.getKey())) {
						isSupportedFormat = true;

						// 把头部信息替换掉
						final String base64Data = requestUploadBase64Image.getBase64Data().replaceFirst(entry.getKey(),
								"");

						// 重新设置Base64的数据
						requestUploadBase64Image.setBase64Data(base64Data);

						if ((null == requestUploadBase64Image.getFormat())
								|| (requestUploadBase64Image.getFormat().trim().isEmpty())) {
							requestUploadBase64Image.setFormat(entry.getValue());
						}

						break;
					}
				}

				// 遇到不支持的格式图片上传时抛出异常（支持直接上传Base64编码的图像，可以不用加头部信息）
				if (!(isSupportedFormat)) {
					int subIndex = requestUploadBase64Image.getBase64Data().indexOf(",");

					// 去掉 Base64编码的头部信息
					if (-1 != subIndex) {
						subIndex = (subIndex + 1);

						// 去掉图片的 Base64 编码的头部
						requestUploadBase64Image
								.setBase64Data(requestUploadBase64Image.getBase64Data().substring(subIndex));
					}

					// 设置图片的格式
					if ((null == requestUploadBase64Image.getFormat())
							|| (requestUploadBase64Image.getFormat().trim().isEmpty())) {
						// 将不支持的图片格式默认设置为 jpg 格式的图片
						requestUploadBase64Image.setFormat(EImageFormat.jpg.name());
					}
				}

				// 先生成图片文件再上传到资源服务器
				File uploadFile = ImageBase64Utility.generateImageByBase64(requestUploadBase64Image.getBase64Data(),
						requestUploadBase64Image.getFormat());
				uploadFileList.add(uploadFile);

				// 上传到服务器，并获取可访问的URL地址
				String resouceURL = uploadFile(uploadFile);

				tempFileUploadEXT = new FileUploadDownloadService().new FileUploadEXT();
				tempFileUploadEXT.setResourcepath(resouceURL);
				tempFileUploadEXT.setResourcename(requestUploadBase64Image.getResouceName());

				// 上传之后的文件
				fileUploadEXTList.add(tempFileUploadEXT);
			}

		} finally {
			if ((null != uploadFileList) && (uploadFileList.size() > 0)) {
				for (File file : uploadFileList) {
					file.delete();// 删除文件
				}
			}
		}

		return fileUploadEXTList;
	}

	/**
	 * 以 Base64 的方式上传图片
	 * 
	 * @author wangjunwen
	 * @date 2017年9月24日 下午4:36:49
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void uploadImageByBase64(HttpServletRequest request, HttpServletResponse response) {
		ResultWrap resultWrap = new ResultWrap();

		final List<ResponseUploadBase64Image> urlList = new ArrayList<ResponseUploadBase64Image>();
		final List<File> uploadFileList = new ArrayList<File>();

		try {
			BaseAccess baseAccess = new BaseAccess();
			Map<String, Object> paramMap = baseAccess.getParamMap(request);

			// 去掉字符串两端的空格
			HTMLUtil.trimByString(paramMap);

			// 将 null 值或者空字符串的元素过滤掉
			ObjectUtil.filterNullValue(paramMap);

			RequestUploadBase64Image requestUploadBase64Image = null;

			if ((null != paramMap) && (paramMap.size() > 0)) {
				requestUploadBase64Image = JSONObject.toJavaObject((JSONObject) JSONObject.toJSON(paramMap),
						RequestUploadBase64Image.class);
			}

			if ((null != requestUploadBase64Image) && (null != requestUploadBase64Image.getBase64Data())) {

				// 头部信息
				// final String headerData = "data:image/png;base64,";

				// 是否是支持的图片格式
				boolean isSupportedFormat = false;

				Entry<String, String> entry = null;
				for (Iterator<Entry<String, String>> iterator = BASE64_IMAGE_HEADER_DATA_MAP.entrySet()
						.iterator(); iterator.hasNext();) {

					entry = iterator.next();

					if (requestUploadBase64Image.getBase64Data().startsWith(entry.getKey())) {
						isSupportedFormat = true;

						// 把头部信息替换掉
						final String base64Data = requestUploadBase64Image.getBase64Data().replaceFirst(entry.getKey(),
								"");

						// 重新设置Base64的数据
						requestUploadBase64Image.setBase64Data(base64Data);

						if ((null == requestUploadBase64Image.getFormat())
								|| (requestUploadBase64Image.getFormat().trim().isEmpty())) {
							requestUploadBase64Image.setFormat(entry.getValue());
						}

						break;
					}
				}

				// 遇到不支持的格式图片上传时抛出异常（支持直接上传Base64编码的图像，可以不用加头部信息）
				if (!(isSupportedFormat)) {
					int subIndex = requestUploadBase64Image.getBase64Data().indexOf(",");

					// 去掉 Base64编码的头部信息
					if (-1 != subIndex) {
						subIndex = (subIndex + 1);

						// 去掉图片的 Base64 编码的头部
						requestUploadBase64Image
								.setBase64Data(requestUploadBase64Image.getBase64Data().substring(subIndex));
					}

					// 设置图片的格式
					if ((null == requestUploadBase64Image.getFormat())
							|| (requestUploadBase64Image.getFormat().trim().isEmpty())) {
						// 将不支持的图片格式默认设置为 jpg 格式的图片
						requestUploadBase64Image.setFormat(EImageFormat.jpg.name());
					}
				}

				// 先生成图片文件再上传到资源服务器
				File uploadFile = ImageBase64Utility.generateImageByBase64(requestUploadBase64Image.getBase64Data(),
						requestUploadBase64Image.getFormat());
				uploadFileList.add(uploadFile);

				// 上传到服务器，并获取可访问的URL地址
				String resouceURL = uploadFile(uploadFile);

				ResponseUploadBase64Image responseUploadBase64Image = new ResponseUploadBase64Image();
				responseUploadBase64Image.setResouceName(requestUploadBase64Image.getResouceName());
				responseUploadBase64Image.setResouceURL(resouceURL);

				urlList.add(responseUploadBase64Image);

				// 设置响应客户端的数据
				resultWrap.setData(urlList);
			} else {
				// 图片上传失败
				resultWrap.error();
			}

		} catch (Exception e) {
			resultWrap.error(e.getMessage());
		} finally {
			HTMLUtil.sendWrap(response, resultWrap, request);

			if ((null != uploadFileList) && (uploadFileList.size() > 0)) {
				for (File file : uploadFileList) {
					file.delete();// 删除文件
				}
			}
		}
	}

	/**
	 * 从图片的HTTP链接地址下载图片
	 * 
	 * @author wangjunwen
	 * @date 2017年11月1日 上午9:20:56
	 *
	 * @param httpURL
	 *            图片资源的访问地址
	 * @return
	 * @throws Exception
	 */
	public File dowloadByHttpURL(String httpURL) throws Exception {
		return dowloadByHttpURL(httpURL, null, null, false);
	}

	/**
	 * 从图片的HTTP链接地址下载图片
	 * 
	 * @author wangjunwen
	 * @date 2017年11月1日 上午9:20:56
	 *
	 * @param httpURL
	 *            图片资源的访问地址
	 * @param saveToDir
	 *            图片下载之后保存在哪个目录下
	 * @return
	 * @throws Exception
	 */
	public File dowloadByHttpURL(String httpURL, String saveToDir) throws Exception {
		return dowloadByHttpURL(httpURL, saveToDir, null, false);
	}

	/**
	 * 从图片的HTTP链接地址下载图片
	 * 
	 * @author wangjunwen
	 * @date 2017年11月1日 上午9:20:56
	 *
	 * @param httpURL
	 *            图片资源的访问地址
	 * @param saveToDir
	 *            图片下载之后保存在哪个目录下
	 * @param fileName
	 *            图片下载完之后保存的文件名称
	 * @return
	 * @throws Exception
	 */
	public File dowloadByHttpURL(String httpURL, String saveToDir, String fileName) throws Exception {
		return dowloadByHttpURL(httpURL, saveToDir, fileName, false);
	}

	/**
	 * 从图片的HTTP链接地址下载图片
	 * 
	 * @author wangjunwen
	 * @date 2017年11月1日 上午9:15:34
	 *
	 * @param httpURL
	 *            图片资源的访问地址
	 * @param saveToDir
	 *            图片下载之后保存在哪个目录下
	 * @param fileName
	 *            图片下载完之后保存的文件名称
	 * @param fileNameIsContainsExtensionName
	 *            以上指定的文件名称是否已经包含文件的扩展名。例如：***.jpg、***.png、……（如果该值为
	 *            false，那么默认使用链接地址中的文件的扩展名）
	 * @return
	 * @throws Exception
	 */
	public File dowloadByHttpURL(String httpURL, String saveToDir, String fileName,
			boolean fileNameIsContainsExtensionName) throws Exception {

		// 保存的目录
		saveToDir = (((null == saveToDir) || (saveToDir = saveToDir.trim()).isEmpty()) ? ""
				: (saveToDir.endsWith("\\") || saveToDir.endsWith("/") ? saveToDir : (saveToDir + "/")));

		// 文件名
		fileName = (((null == fileName) || (fileName = fileName.trim()).isEmpty())
				? (UUID.randomUUID().toString()) + (httpURL.substring((httpURL.lastIndexOf(".") - 1)))
				: (fileNameIsContainsExtensionName ? fileName
						: (fileName + (httpURL.substring((httpURL.lastIndexOf(".") - 1))))));

		final File file = new File(saveToDir, fileName);

		URL url = new URL(httpURL);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

		// 设置用户代理（防止报403错误）
		httpURLConnection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");

		final byte[] byteArray = new byte[1024];// 每次最多读取的字节数
		int readedSize = 0;// 本次读取到的字节个数

		try (InputStream inputStream = httpURLConnection.getInputStream();
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				// 将读取到的数据写入到字节流中
				FileOutputStream fileOutputStream = new FileOutputStream(file);) {

			while ((readedSize = inputStream.read(byteArray)) > 0) {
				byteArrayOutputStream.write(byteArray, 0, readedSize);
			}

			byteArrayOutputStream.writeTo(fileOutputStream);
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != httpURLConnection) {
				httpURLConnection.disconnect();
			}

			// 程序退出时删除文件
			file.deleteOnExit();
		}

		return file;
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @author wangjunwen
	 * @date 2017年11月1日 上午10:03:26
	 *
	 * @param dir
	 *            将要删除的文件目录
	 * @return 删除成功返回 true，否则返回 false
	 */
	public boolean deleteDir(File dir) {
		if ((null == dir)) {
			return false;
		}

		if (dir.isDirectory()) {
			String[] children = dir.list();

			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	/**
	 * 将文件打包到 zip 压缩文件中下载
	 * 
	 * @author wangjunwen
	 * @date 2017年11月1日 上午11:30:54
	 *
	 * @param response
	 *            HttpServletResponse对象{@link javax.servlet.http.HttpServletResponse}
	 * @param zipFile
	 *            zip文件
	 * @param files
	 *            需要打包的文件
	 * @throws Exception
	 */
	public void downloadZipFile(HttpServletResponse response, File zipFile, File... files) throws Exception {
		try (FileOutputStream zipFileOutputStream = new FileOutputStream(zipFile);
				ZipOutputStream zipOutputStream = new ZipOutputStream(zipFileOutputStream);
				InputStream zipFileInputStream = new BufferedInputStream(new FileInputStream(zipFile));
				OutputStream responseOutputStream = new BufferedOutputStream(response.getOutputStream());) {

			for (File file : files) {
				ZipUtils.doCompress(file, zipOutputStream);
			}

			byte[] buffer = new byte[zipFileInputStream.available()];
			zipFileInputStream.read(buffer);

			response.setContentType("application/octet-stream");

			// 在此处对文件名重新编码，避免出现中文乱码
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(zipFile.getName().getBytes("UTF-8"), "ISO-8859-1"));

			responseOutputStream.write(buffer);
			responseOutputStream.flush();

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 获取音、视频文件的播放时长（单位：秒）
	 * 
	 * @author wangjunwen
	 * @date 2018年2月24日 下午2:24:15
	 *
	 * @param filePath
	 *            文件路径
	 * @return 当文件为非音、视频文件时返回 null
	 * @throws Exception
	 */
	public Integer getTimeLength(String filePath) throws Exception {

		// 获取文件类型
		EResourceType eResourceType = EResourceType.getResourceTypeByFileExtentionName(filePath);

		// 只有音、视频文件才有播放时长，该文件如果不是音、视频文件则直接返回 null
		if (!(EResourceType.AUDIO.equals(eResourceType)) && !(EResourceType.VIDEO.equals(eResourceType))) {
			return null;
		}

		return 0;
	}

	/**
	 * 获取音、视频文件的播放时长（单位：秒）
	 * 
	 * @author wangjunwen
	 * @date 2018年4月1日 下午5:24:32
	 *
	 * @param filePath
	 *            文件路径
	 * @return
	 * @throws Exception
	 */
	public Integer getTimeLengthByJave(String filePath) throws Exception {

		// 音、视频文件的播放时长（单位：秒）
		Integer timelength = null;

		File source = new File(filePath);
		Encoder encoder = new Encoder();
		MultimediaInfo multimediaInfo = encoder.getInfo(source);
		long duration = multimediaInfo.getDuration();

		timelength = (int) (duration / 1000);

		return timelength;
	}

	/**
	 * 上传文件的扩展类
	 * 
	 * @author wangjunwen
	 * @date 2018年1月27日 上午9:44:08
	 *
	 */
	public class FileUploadEXT {
		// 资源的访问地址
		private String resourcepath;
		// 资源的名称
		private String resourcename;
		// 音、视频的时长
		private Integer timelength;
		// 文件上传在本地的路径
		private String localpath;

		public String getResourcepath() {
			return resourcepath;
		}

		public void setResourcepath(String resourcepath) {
			this.resourcepath = resourcepath;
		}

		public String getResourcename() {
			return resourcename;
		}

		public void setResourcename(String resourcename) {
			this.resourcename = resourcename;
		}

		public Integer getTimelength() {
			return timelength;
		}

		public void setTimelength(Integer timelength) {
			this.timelength = timelength;
		}

		public String getLocalpath() {
			return localpath;
		}

		public void setLocalpath(String localpath) {
			this.localpath = localpath;
		}

	}

}
