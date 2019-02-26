package com.fwk.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 图片的 Base64 处理工具类
 * 
 * @author wangjunwen
 * @date 2017年9月24日 下午3:11:32
 *
 */
public class ImageBase64Utility {

	/**
	 * 图片转化成base64字符串
	 * 
	 * @author wangjunwen
	 * @date 2017年9月24日 下午3:57:20
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String getBase64ByImage(File file) throws Exception {
		final byte[] bytes = new byte[1024];
		int readCount = 0;

		try (FileInputStream fileInputStream = new FileInputStream(file);
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {

			while ((readCount = fileInputStream.read(bytes)) > 0) {
				byteArrayOutputStream.write(bytes, 0, readCount);
			}

			// 对字节数组Base64编码后返回Base64编码过的字符串
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(byteArrayOutputStream.toByteArray());
		}

	}

	/**
	 * base64字符串转化成图片
	 * 
	 * @author wangjunwen
	 * @date 2017年9月24日 下午5:01:32
	 *
	 * @param imgSrc
	 *            图片的Base64字符串
	 * @return
	 * @throws Exception
	 */
	public static File generateImageByBase64(String imgSrc) throws Exception {
		return generateImageByBase64(imgSrc, null, null);
	}

	/**
	 * base64字符串转化成图片
	 * 
	 * @author wangjunwen
	 * @date 2017年9月24日 下午5:00:54
	 *
	 * @param imgSrc
	 *            图片的Base64字符串
	 * @param format
	 *            图片格式。例如：jpg、jpeg、png、gif
	 * @return
	 * @throws Exception
	 */
	public static File generateImageByBase64(String imgSrc, String format) throws Exception {
		return generateImageByBase64(imgSrc, format, null);
	}

	/**
	 * base64字符串转化成图片
	 * 
	 * @author wangjunwen
	 * @date 2017年9月24日 下午3:35:38
	 *
	 * @param imgSrc
	 *            图片的Base64字符串
	 * @param format
	 *            图片格式。例如：jpg、jpeg、png、gif
	 * @param output
	 *            文件输出的路径
	 * @return
	 * @throws Exception
	 */
	public static File generateImageByBase64(String imgSrc, String format, File output) throws Exception {
		if (null == imgSrc) {
			// 图像数据为空
			return null;
		}

		if ((null == format) || (format = format.trim()).isEmpty()) {
			// 默认为 jpg 文件
			format = "jpg";
		} else if (format.startsWith(".")) {
			format = (format.replaceFirst(".", ""));
		}

		if (null == output) {
			output = new File((UUID.randomUUID().toString() + "." + format));
		}

		try (FileOutputStream fileOutputStream = new FileOutputStream(output)) {
			BASE64Decoder decoder = new BASE64Decoder();

			// Base64解码
			byte[] bytes = decoder.decodeBuffer(imgSrc);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}

			// 生成图片
			fileOutputStream.write(bytes);
			fileOutputStream.flush();

			return output;
		}
	}

}
