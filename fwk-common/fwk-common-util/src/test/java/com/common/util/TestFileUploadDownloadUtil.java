package com.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 文件上传和下载工具类的测试类
 * 
 * @author wangjunwen
 * @date 2017-08-20 15:10:42
 *
 */
public class TestFileUploadDownloadUtil {

	private static File upload_file_url = null;

	static {
		try {
			upload_file_url = new File(
					(new File("").getCanonicalPath() + "/src/test/java/com/common/util/upload_url.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 以追加并换行的方式写入文件
	 * 
	 * @author wangjunwen
	 * @date 2017年9月18日 下午2:22:27
	 *
	 * @param content
	 *            写入文件的内容
	 */
	public static void appendToFile(String content) {
		try (FileOutputStream fileOutputStream = new FileOutputStream(upload_file_url, true);) {
			fileOutputStream.write(content.getBytes("UTF-8"));
			fileOutputStream.write(("\r\n".getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		try {
			// 上传产品认证证书
			// uploadProductionAuthenticationImageFiles();

			// 上传产品155的图片
			// uploadProduction155ImageFile();

			// 上传产品156的图片
			uploadProduction156ImageFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 上传产品认证证书
	 * 
	 * @author wangjunwen
	 * @date 2017年9月18日 下午2:01:45
	 *
	 * @throws Exception
	 */
	public static void uploadProductionAuthenticationImageFiles() throws Exception {
		File file = new File("E:/神谷科技/同事/谢祥/数据伪造/附件1/产品认证/");

		File[] files = file.listFiles();
		String content = "";
		for (File f : files) {
			// content = (f.getName() + "：" + FileUploadDownloadService.uploadFile(f));
			System.out.println(content);

			appendToFile(content);
		}
	}

	/**
	 * 上传产品155的图片
	 * 
	 * @author wangjunwen
	 * @date 2017年9月18日 下午3:10:31
	 *
	 * @throws Exception
	 */
	public static void uploadProduction155ImageFile() throws Exception {

		appendToFile("=====================<开始写入产品155的图片的URL>========================");

		Map<String, File> fileMap = new HashMap<String, File>();

		// 产品图片
		File productionImageFile = new File("E:/神谷科技/同事/谢祥/数据伪造/产品图片—巴西蘑菇.png");
		fileMap.put("产品图片_155_巴西蘑菇", productionImageFile);

		// ======================= 产销过程 =======================================
		// 产销过程：测土
		File marcketProcessImageFile_1 = new File("E:/神谷科技/同事/谢祥/数据伪造/附件1/产销过程/产销过程7/测土.jpg");
		fileMap.put("产品图片_155_产销过程_" + marcketProcessImageFile_1.getName(), marcketProcessImageFile_1);

		// 产销过程：选种
		File marcketProcessImageFile_2 = new File("E:/神谷科技/同事/谢祥/数据伪造/附件1/产销过程/产销过程7/选种.jpg");
		fileMap.put("产品图片_155_产销过程_" + marcketProcessImageFile_2.getName(), marcketProcessImageFile_2);

		// 产销过程：育苗
		File marcketProcessImageFile_3 = new File("E:/神谷科技/同事/谢祥/数据伪造/附件1/产销过程/产销过程7/育苗.jpg");
		fileMap.put("产品图片_155_产销过程_" + marcketProcessImageFile_3.getName(), marcketProcessImageFile_3);

		// 产销过程：采收
		File marcketProcessImageFile_4 = new File("E:/神谷科技/同事/谢祥/数据伪造/附件1/产销过程/产销过程7/采收.jpg");
		fileMap.put("产品图片_155_产销过程_" + marcketProcessImageFile_4.getName(), marcketProcessImageFile_4);

		// 产销过程：清洗
		File marcketProcessImageFile_5 = new File("E:/神谷科技/同事/谢祥/数据伪造/附件1/产销过程/产销过程7/清洗.jpg");
		fileMap.put("产品图片_155_产销过程_" + marcketProcessImageFile_5.getName(), marcketProcessImageFile_5);

		// 产销过程：包装
		File marcketProcessImageFile_6 = new File("E:/神谷科技/同事/谢祥/数据伪造/附件1/产销过程/产销过程7/包装.jpg");
		fileMap.put("产品图片_155_产销过程_" + marcketProcessImageFile_6.getName(), marcketProcessImageFile_6);

		// 产销过程：分销
		File marcketProcessImageFile_7 = new File("E:/神谷科技/同事/谢祥/数据伪造/附件1/产销过程/产销过程7/分销.jpg");
		fileMap.put("产品图片_155_产销过程_" + marcketProcessImageFile_7.getName(), marcketProcessImageFile_7);

		// 产销过程：出售
		File marcketProcessImageFile_8 = new File("E:/神谷科技/同事/谢祥/数据伪造/附件1/产销过程/产销过程7/出售.jpg");
		fileMap.put("产品图片_155_产销过程_" + marcketProcessImageFile_8.getName(), marcketProcessImageFile_8);

		// ===========产品描述 ==========
		// 产品描述图片一
		File productionDescriptionImageFile_1 = new File("E:/神谷科技/同事/谢祥/数据伪造/附件1/商品详情/A/1.jpg");
		fileMap.put("产品图片_155_产品描述一", productionDescriptionImageFile_1);

		Entry<String, File> tempEntry = null;
		String content = "";
		for (Iterator<Entry<String, File>> Iterator = fileMap.entrySet().iterator(); Iterator.hasNext();) {
			tempEntry = Iterator.next();

			// content = (tempEntry.getKey() + "：" +
			// FileUploadDownloadService.uploadFile(tempEntry.getValue()));
			System.out.println(content);

			appendToFile(content);
		}
	}

	/**
	 * 上传产品156的图片
	 * 
	 * @author wangjunwen
	 * @date 2017年9月18日 下午3:10:31
	 *
	 * @throws Exception
	 */
	public static void uploadProduction156ImageFile() throws Exception {

		appendToFile("=====================<开始写入产品156的图片的URL>========================");

		Map<String, File> fileMap = new HashMap<String, File>();

		// 产品图片
		File productionImageFile = new File("E:/神谷科技/同事/谢祥/数据伪造/产品图片—辣椒干.png");
		fileMap.put("产品图片_156_辣椒干", productionImageFile);

		// ======================= 产销过程 =======================================
		// // 产销过程：测土
		// File marcketProcessImageFile_1 = new
		// File("E:/神谷科技/同事/谢祥/数据伪造/附件1/产销过程/产销过程7/测土.jpg");
		// fileMap.put("产品图片_156_产销过程_" + marcketProcessImageFile_1.getName(),
		// marcketProcessImageFile_1);
		//
		// // 产销过程：选种
		// File marcketProcessImageFile_2 = new
		// File("E:/神谷科技/同事/谢祥/数据伪造/附件1/产销过程/产销过程7/选种.jpg");
		// fileMap.put("产品图片_156_产销过程_" + marcketProcessImageFile_2.getName(),
		// marcketProcessImageFile_2);
		//
		// // 产销过程：育苗
		// File marcketProcessImageFile_3 = new
		// File("E:/神谷科技/同事/谢祥/数据伪造/附件1/产销过程/产销过程7/育苗.jpg");
		// fileMap.put("产品图片_156_产销过程_" + marcketProcessImageFile_3.getName(),
		// marcketProcessImageFile_3);
		//
		// // 产销过程：采收
		// File marcketProcessImageFile_4 = new
		// File("E:/神谷科技/同事/谢祥/数据伪造/附件1/产销过程/产销过程7/采收.jpg");
		// fileMap.put("产品图片_156_产销过程_" + marcketProcessImageFile_4.getName(),
		// marcketProcessImageFile_4);
		//
		// // 产销过程：清洗
		// File marcketProcessImageFile_5 = new
		// File("E:/神谷科技/同事/谢祥/数据伪造/附件1/产销过程/产销过程7/清洗.jpg");
		// fileMap.put("产品图片_156_产销过程_" + marcketProcessImageFile_5.getName(),
		// marcketProcessImageFile_5);
		//
		// // 产销过程：包装
		// File marcketProcessImageFile_6 = new
		// File("E:/神谷科技/同事/谢祥/数据伪造/附件1/产销过程/产销过程7/包装.jpg");
		// fileMap.put("产品图片_156_产销过程_" + marcketProcessImageFile_6.getName(),
		// marcketProcessImageFile_6);
		//
		// // 产销过程：分销
		// File marcketProcessImageFile_7 = new
		// File("E:/神谷科技/同事/谢祥/数据伪造/附件1/产销过程/产销过程7/分销.jpg");
		// fileMap.put("产品图片_156_产销过程_" + marcketProcessImageFile_7.getName(),
		// marcketProcessImageFile_7);
		//
		// // 产销过程：出售
		// File marcketProcessImageFile_8 = new
		// File("E:/神谷科技/同事/谢祥/数据伪造/附件1/产销过程/产销过程7/出售.jpg");
		// fileMap.put("产品图片_156_产销过程_" + marcketProcessImageFile_8.getName(),
		// marcketProcessImageFile_8);

		// ===========产品描述 ==========
		// 产品描述图片一
		File productionDescriptionImageFile = new File("E:/神谷科技/同事/谢祥/数据伪造/附件1/商品详情/B/");
		File[] files = productionDescriptionImageFile.listFiles();
		for (File file : files) {
			fileMap.put("产品图片_156_产品描述_" + file.getName(), file);
		}

		Entry<String, File> tempEntry = null;
		String content = "";
		for (Iterator<Entry<String, File>> Iterator = fileMap.entrySet().iterator(); Iterator.hasNext();) {
			tempEntry = Iterator.next();

			// content = (tempEntry.getKey() + "：" +
			// FileUploadDownloadService.uploadFile(tempEntry.getValue()));
			System.out.println(content);

			appendToFile(content);
		}
	}

	/**
	 * 上传企业资质
	 * 
	 * @author wangjunwen
	 * @date 2017年9月18日 下午4:27:47
	 *
	 * @throws Exception
	 */
	public static void uploadEnterpriseQualificationsImageFile() throws Exception {

		appendToFile("=====================<开始写入企业资质的图片的URL>========================");

		Map<String, File> fileMap = new HashMap<String, File>();

		// 产品图片
		File productionImageFile = new File("E:/神谷科技/同事/谢祥/数据伪造/产品图片—辣椒干.png");
		fileMap.put("产品图片_156_辣椒干", productionImageFile);
	}

}
