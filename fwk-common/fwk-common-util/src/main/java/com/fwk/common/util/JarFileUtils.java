package com.fwk.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * jar 文件处理的工具类
 * 
 * @author wangjunwen
 * @date 2018年2月25日 下午3:13:38
 *
 */
public class JarFileUtils {

	/** 是否是直接运行的 jar 文件 <code>{@link #isRunningJar()}</code> */
	public static final boolean IS_RUNNING_JAR = isRunningJar();

	/** 是否是运行在 <code>{@link #isWindowsOS()}</code> */
	public static final boolean IS_RUNNING_WINDOWS_OS = isWindowsOS();

	/**
	 * 是否是直接从jar执行文件运行的应用
	 * 
	 * @author wangjunwen
	 * @date 2018年2月25日 下午3:17:12
	 *
	 * @return
	 * @throws Exception
	 */
	private static boolean isRunningJar() {

		try {
			java.net.URL url = JarFileUtils.class.getProtectionDomain().getCodeSource().getLocation();

			String filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");

			return (filePath.contains(".jar!"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 获取文件的MD5码
	 * 
	 * @author wangjunwen
	 * @date 2018年2月25日 下午3:55:39
	 *
	 * @param file
	 *            文件对象
	 * @return
	 * @throws Exception
	 */
	public static String fileToMD5(File file) throws Exception {

		try (FileInputStream fileInputStream = new FileInputStream(file);) {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");

			byte[] buffer = new byte[1024];
			int len = -1;

			while ((-1 != (len = fileInputStream.read(buffer)))) {
				messageDigest.update(buffer, 0, len);
			}

			BigInteger bigInt = new BigInteger(1, messageDigest.digest());

			return (bigInt.toString(16).toUpperCase());
		}

	}

	/**
	 * 获取运行的 jar 的文件对象
	 * 
	 * @author wangjunwen
	 * @date 2018年2月25日 下午3:51:43
	 *
	 * @return
	 * @throws Exception
	 */
	public static JarFile getRunningJarFile() throws Exception {
		File file = new File(getRunningJarFilePath());

		JarFile jarFile = new JarFile(file);

		return jarFile;
	}

	/**
	 * 获取 jar 运行的路径
	 * 
	 * @author wangjunwen
	 * @date 2018年2月25日 下午3:51:28
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getRunningJarFilePath() throws Exception {

		java.net.URL url = JarFileUtils.class.getProtectionDomain().getCodeSource().getLocation();

		String filePath = java.net.URLDecoder.decode(url.getFile(), "utf-8");

		if (filePath.contains(".jar!")) {

			filePath = ((filePath.split(".jar!")[0] + ".jar").replaceFirst("file:", ""));
		}

		return filePath;

	}

	/**
	 * 从 jar 包中查找出指定的文件
	 * 
	 * <pre>
	 * 	<b>特别提醒</b>
	 * 		<li>如果需要删除解压目录，请自行在该方法调用结束后调用<code>{@link #deleteDirectory(File)}</code>自行删除</li>
	 * </pre>
	 * 
	 * @author wangjunwen
	 * @date 2018年2月25日 下午3:07:59
	 *
	 * @param jarFile
	 *            从该 jar 包中查找
	 * @param unzipFilePath
	 *            jar 包解压到该目录下
	 * @param findFileList
	 *            要查找的文件列表
	 * @param outFindedFileList
	 *            查找到的文件列表
	 * @throws Exception
	 */
	public static void findFileFromJar(JarFile jarFile, final File unzipFilePath, final List<String> findFileList,
			final List<File> outFindedFileList) throws Exception {

		if ((null == jarFile) || (null == unzipFilePath) || (null == findFileList) || (findFileList.isEmpty())
				|| (null == outFindedFileList)) {
			return;
		} else {
			if (!(unzipFilePath.exists())) {
				unzipFilePath.mkdir();
			}

			// 如果不是 windows 系列的操作系统，那么需要给文件以及文件夹赋予权限
			changePermisions(unzipFilePath);
		}

		try {

			Enumeration<JarEntry> enumeration = jarFile.entries();

			File tempFile = null;// 遍历出来的文件

			File unzipFileDir = null;// 解压到该目录下

			String jarFilePath = null;

			Iterator<String> findFileListIterator = null;

			String findFile = null;

			JarEntry jarEntry = null;
			String jarEntryName = null;
			while (enumeration.hasMoreElements()) {
				jarEntry = enumeration.nextElement();

				jarEntryName = jarEntry.getName();

				if (jarEntry.isDirectory()) {

					unzipFileDir = new File(unzipFilePath + "/" + jarEntryName);

					if (!(unzipFileDir.exists())) {
						unzipFileDir.mkdirs();

						// 如果不是 windows 系列的操作系统，那么需要给文件以及文件夹赋予权限
						changePermisions(unzipFileDir);

						unzipFileDir.deleteOnExit();
					}

					continue;
				} else {

					findFileListIterator = findFileList.iterator();

					while (findFileListIterator.hasNext()) {
						findFile = findFileListIterator.next();

						if (!(jarEntryName.contains(findFile)) && !(jarEntryName.endsWith(".jar"))) {
							continue;
						} else {
							for (File outFindedFile : outFindedFileList) {
								if (outFindedFile.getAbsolutePath().replaceAll("\\\\", "/").endsWith(findFile)) {
									findFile = null;
									break;
								}
							}

							if (null == findFile) {
								break;
							}
						}

						tempFile = new File(unzipFilePath.getAbsolutePath() + "/" + jarEntryName);

						tempFile.deleteOnExit();

						unzipFileDir = tempFile.getParentFile();

						if (!(unzipFileDir.exists())) {
							unzipFileDir.mkdirs();

							// 如果不是 windows 系列的操作系统，那么需要给文件以及文件夹赋予权限
							changePermisions(unzipFileDir);

							unzipFileDir.deleteOnExit();
						}

						try (InputStream inputStream = jarFile.getInputStream(jarEntry);
								ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
								FileOutputStream fileOutputStream = new FileOutputStream(tempFile);) {

							// 如果不是 windows 系列的操作系统，那么需要给文件以及文件夹赋予权限
							changePermisions(tempFile);

							byte[] buffer = new byte[1024];
							int length = -1;

							while ((-1 != (length = inputStream.read(buffer)))) {
								byteArrayOutputStream.write(buffer, 0, length);
							}

							fileOutputStream.write(byteArrayOutputStream.toByteArray());

						}

						// 找到需要查找的文件就直接返回
						if ((jarEntryName.contains(findFile))) {

							outFindedFileList.add(tempFile);

						} else if (tempFile.getAbsolutePath().endsWith(".jar")) {

							jarFilePath = jarEntryName.substring(0, jarEntryName.lastIndexOf(".jar"));

							JarFile otherJarFile = new JarFile(tempFile);

							findFileFromJar(otherJarFile, new File(unzipFilePath.getAbsolutePath() + "/" + jarFilePath),
									findFileList, outFindedFileList);
						}

					}

				} // JarEntry process end
			} // (JarEntry) while loop end

		} finally {
			// colse JarFile
			jarFile.close();
		}

	}

	/**
	 * 删除目录及目录下的文件
	 *
	 * @param dirFile
	 *            要删除的目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(File dirFile) {

		if ((null == dirFile)) {
			return false;
		} else if (!(dirFile.isDirectory())) {
			return false;
		}

		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();

		for (int i = 0; i < files.length; i++) {

			if (files[i].isFile()) {// 删除子文件
				files[i].delete();
			} else if (files[i].isDirectory()) {// 删除子目录
				deleteDirectory(files[i]);
			}

		}

		// 最后再删除目录
		dirFile.delete();

		return true;
	}

	/**
	 * 判断是否是 Windows 操作系统
	 * 
	 * @author wangjunwen
	 * @date 2018年2月24日 下午4:48:11
	 *
	 * @return
	 */
	private static boolean isWindowsOS() {
		// Windows?
		boolean isWindows;
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("windows") != -1) {
			isWindows = true;
		} else {
			isWindows = false;
		}

		return isWindows;
	}

	/**
	 * 变更文件或者文件夹的操作权限
	 * 
	 * @author wangjunwen
	 * @date 2018年2月25日 下午5:34:17
	 *
	 * @param fileOrDir
	 * @throws Exception
	 */
	public static void changePermisions(File fileOrDir) throws Exception {
		// 如果不是 windows 系列的操作系统，那么需要给文件以及文件夹赋予相应的操作权限
		if (!(isWindowsOS())) {
			Runtime runtime = Runtime.getRuntime();

			runtime.exec(new String[] { "/bin/chmod", "777", fileOrDir.getAbsolutePath() });
		}
	}

}
