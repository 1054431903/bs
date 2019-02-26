package com.fwk.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * 二维码图片处理的工具类
 * 
 * @author wangjunwen
 *
 */
public class QRCodeUtil {
	// 回收站的路径
	private static String recycleFileDir = "/var/qrcodemanager/recycle/";

	/** 生成的二维码图片的临时保存的目录 */
	public static final String TEMP_FILE_DIR = "/var/qrcodemanager/temp/";

	static {
		File recycleFile = new File(recycleFileDir);
		if (!(recycleFile.exists())) {
			// 如果回收站的目录不存在就先创建回收站的目录
			recycleFile.mkdirs();
		}

		File tempFileDir = new File(TEMP_FILE_DIR);
		if (!(tempFileDir.exists())) {
			// 如果临时保存的目录不存在就先创建回收站的目录
			tempFileDir.mkdirs();
		}
	}

	/**
	 * 生成二维码图片
	 * 
	 * <pre>
	 *    生成二维码图片的文件格式，默认为 png
	 *    生成的二维码图片的宽度，默认为 100
	 *    生成的二维码图片的高度，默认为 100
	 *    
	 *    <b>如需要自行设置以上这些参数，请调用 {@link QRCodeUtil#writeQRCodeImage(File, String, String, int, int)}</b>
	 * </pre>
	 * 
	 * @param imageFile
	 *            生成二维码图片的路径（例如：/var/demo/百度-网址-二维码.png）
	 * @param content
	 *            写入到二维码的内容（例如：http://www.baidu.com）
	 * @return 写入成功返回 true，否则返回 false
	 * @throws Exception
	 *             抛出异常
	 */
	public static boolean writeQRCodeImage(File imageFilePath, String content) throws Exception {
		return writeQRCodeImage(imageFilePath, content, "png", 100, 100);
	}

	/**
	 * 生成二维码图片
	 * 
	 * <pre>
	  *    生成的二维码图片的宽度，默认为 100
	  *    生成的二维码图片的高度，默认为 100
	  *    
	  *    <b>如需要自行设置以上这些参数，请调用 {@link QRCodeUtil#writeQRCodeImage(File, String, String, int, int)}</b>
	 * </pre>
	 * 
	 * @param imageFile
	 *            生成二维码图片的路径（例如：/var/demo/百度-网址-二维码.png）
	 * @param content
	 *            写入到二维码的内容（例如：http://www.baidu.com）
	 * @param pictureFormat
	 *            生成二维码图片的文件格式（例如：png、jpg、……，默认值： png）
	 * @return 写入成功返回 true，否则返回 false
	 * @throws Exception
	 *             抛出异常
	 */
	public static boolean writeQRCodeImage(File imageFilePath, String content, String pictureFormat) throws Exception {
		return writeQRCodeImage(imageFilePath, content, pictureFormat, 100, 100);
	}

	/**
	 * 生成二维码图片
	 * 
	 * @param imageFile
	 *            生成二维码图片的路径（例如：/var/demo/百度-网址-二维码.png）
	 * @param content
	 *            写入到二维码的内容（例如：http://www.baidu.com）
	 * @param pictureFormat
	 *            生成二维码图片的文件格式（例如：png、jpg、……，默认值： png）
	 * @param width
	 *            生成的二维码图片的宽度（例如：100，默认值：100）
	 * @param height
	 *            生成的二维码图片的高度（例如：100，默认值：100）
	 * @return 写入成功返回 true，否则返回 false
	 * @throws Exception
	 *             抛出异常
	 */
	public static boolean writeQRCodeImage(File imageFilePath, String content, String pictureFormat, int width,
			int height) throws Exception {

		if (null == imageFilePath) {
			return false;
		} else {
			// 回收重名的文件
			/*
			 * if (!(recycleExistsFile(imageFilePath))) { throw new
			 * Exception("can not recycle file"); }
			 */
		}

		if (width <= 0) {
			width = 100;
		}

		if (height <= 0) {
			height = 100;
		}

		if ((null == pictureFormat) || (pictureFormat = pictureFormat.trim()).isEmpty()) {
			pictureFormat = "png";
		}

		QRCodeWriter qrCodeWriter = new QRCodeWriter();

		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

		MatrixToImageWriter.writeToPath(bitMatrix, pictureFormat, imageFilePath.toPath());

		return (imageFilePath.exists());
	}

	/**
	 * 回收已存在的文件
	 * 
	 * @param file
	 *            需要回收的文件
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private static boolean recycleExistsFile(File file) throws Exception {
		if ((null == file) || !(file.exists())) {
			return false;
		}

		String recycleFilePath = (recycleFileDir + (UUID.randomUUID().toString().replaceAll("[^0-9a-zA-Z]", "")) + "_"
				+ file.getName());

		File recycleFile = new File(recycleFilePath);

		if (recycleFile.createNewFile()) {
			try (FileInputStream fileInputStream = new FileInputStream(file);
					FileOutputStream fileOutputStream = new FileOutputStream(recycleFile);) {
				IOUtils.copy(fileInputStream, fileOutputStream);
			} catch (Exception e) {
				throw e;
			}
		}

		// 如果回收站已经有要回收文件的备份，那么回收成功，否则回收失败
		return (recycleFile.exists());
	}

	/**
	 * 生成溯源码
	 * 
	 * @author wangjunwen
	 * @date 2017-08-18 11:00:34
	 *
	 * @return
	 */
	public static String generateTraceNo() {

		String fakeNo = "";// 自动生成15位的验证码

		while (15 != fakeNo.length()) {
			fakeNo = (String.valueOf((long) (Math.random() * 1000000000000000L)));
		}

		return fakeNo;
	}

	/**
	 * 生成验证码
	 * 
	 * @author wangjunwen
	 * @date 2017-08-18 11:00:34
	 *
	 * @return
	 */
	public static String generateFakeNo() {

		String fakeNo = "";// 自动生成18位的验证码

		while (18 != fakeNo.length()) {
			fakeNo = (String.valueOf((long) (Math.random() * 1000000000000000000L)));
		}

		return fakeNo;
	}

}
