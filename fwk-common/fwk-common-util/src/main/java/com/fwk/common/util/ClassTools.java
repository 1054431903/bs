package com.fwk.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 与类相关的处理的工具类
 * 
 * @author wangjunwen
 *
 */
public class ClassTools {
	/** 类加载器 */
	public static final ClassLoader CLASSLOADER = Thread.currentThread().getContextClassLoader();

	/** 所有类路径包含 jar 包的路径 */
	public static final String ALL_CLASS_PATH = System.getProperty("java.class.path");

	/** 当前的类路径 */
	public static final String CURRENT_CLASS_PATH = CLASSLOADER.getResource("").toString();

	/** 类文件名的后缀 */
	public static final String suffix_class_file = ".class";

	/**
	 * 获取类路径下的所有类
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<Class<?>> getAllClassFromClassPath() {

		final List<Class<?>> clazzList = new ArrayList<Class<?>>();

		String[] classPath = ALL_CLASS_PATH.split(";");

		File tempFile = null;
		List<File> fileList = null;
		String tempStr = null;
		String clazzName = null;
		Class<?> clazz = null;
		for (String str : classPath) {
			tempFile = new File(str);
			if (tempFile.isDirectory()) {// 获取类路径下是所有 Class
				fileList = listClassFileFromDir(tempFile.toPath().toString());
				for (File f : fileList) {
					if (f.toPath().toString().endsWith(suffix_class_file)) {
						clazzList.add(getClass(str, f));
					}
				}
			} else if (str.endsWith(".jar")) {// 获取jar下的所有 Class
				for (Iterator<String> iterator = listClassFileFromJarFile(str).iterator(); iterator.hasNext();) {

					tempStr = iterator.next();

					try {
						if (tempStr.endsWith(suffix_class_file)) {
							clazzName = tempStr.replaceAll("\\\\", ".").replaceAll("/", ".").replaceFirst(".", "");
							clazzName = clazzName.substring(0, (clazzName.length() - suffix_class_file.length()));

							clazz = Class.forName(clazzName, true, CLASSLOADER);

							clazzList.add(clazz);
						}
					} catch (Exception e) {
						// throw new RuntimeException(e);
					}
				}
			}
		}

		return clazzList;
	}

	/**
	 * 在类路径下获取 Class
	 * 
	 * @param classPath
	 *            类路径
	 * @param clazzFile
	 *            类文件
	 * @return
	 * @throws Exception
	 */
	private static Class<?> getClass(String classPath, File clazzFile) {
		try {
			if (!(clazzFile.isDirectory()) && (clazzFile.exists())) {
				String filePath = clazzFile.getPath().toString();
				if (filePath.endsWith(suffix_class_file)) {

					String clazzName = filePath.substring(classPath.length()).replaceAll("\\\\", ".")
							.replaceAll("/", ".").replaceFirst(".", "");

					clazzName = clazzName.substring(0, (clazzName.length() - suffix_class_file.length()));

					return Class.forName(clazzName, true, CLASSLOADER);
				}
			}
		} catch (Exception e) {
			// throw new RuntimeException(e);
		}

		return null;
	}

	/**
	 * 遍历出指定目录下的所有文件
	 * 
	 * @param dir
	 *            指定的目录
	 * @return
	 */
	public static List<File> listClassFileFromDir(String dir) {
		List<File> litFile = new ArrayList<File>();

		File path = new File(dir);
		if (!(path.isDirectory())) {
			return litFile;
		}

		// 遍历出指定目录下的所有文件
		setFileFromDir(litFile, path);

		return litFile;
	}

	/**
	 * 遍历出指定目录下的所有文件
	 * 
	 * @param litFile
	 *            保存遍历出的文件
	 * @param dir
	 *            要遍历的目录
	 */
	public static void setFileFromDir(List<File> litFile, File dir) {
		litFile = ((null == litFile) ? new ArrayList<File>() : litFile);

		File[] files = dir.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				// 递归遍历
				setFileFromDir(litFile, file);
			} else {
				litFile.add(file);
			}
		}
	}

	/**
	 * 遍历出 jar 包中的所有文件
	 * 
	 * @param jarFilePath
	 *            JAR包的文件路径
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static List<String> listClassFileFromJarFile(String jarFilePath) {
		List<String> litFile = new ArrayList<String>();

		try {
			JarFile jarFile = new JarFile(new File(jarFilePath));

			JarEntry jarEntry = null;
			for (Enumeration<JarEntry> enumeration = jarFile.entries(); enumeration.hasMoreElements();) {
				jarEntry = enumeration.nextElement();

				litFile.add(jarEntry.getName());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return litFile;
	}

}
