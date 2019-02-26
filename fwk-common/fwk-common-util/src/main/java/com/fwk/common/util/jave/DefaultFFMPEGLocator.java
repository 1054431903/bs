/*
 * JAVE - A Java Audio/Video Encoder (based on FFMPEG)
 * 
 * Copyright (C) 2008-2009 Carlo Pelliccia (www.sauronsoftware.it)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.fwk.common.util.jave;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.jar.JarFile;

import com.fwk.common.util.JarFileUtils;

/**
 * The default ffmpeg executable locator, which exports on disk the ffmpeg
 * executable bundled with the library distributions. It should work both for
 * windows and many linux distributions. If it doesn't, try compiling your own
 * ffmpeg executable and plug it in JAVE with a custom {@link FFMPEGLocator}.
 * 
 * @author Carlo Pelliccia
 */
public class DefaultFFMPEGLocator extends FFMPEGLocator {

	/**
	 * Trace the version of the bundled ffmpeg executable. It's a counter: every
	 * time the bundled ffmpeg change it is incremented by 1.
	 */
	private static final int myEXEversion = 1;

	/**
	 * The ffmpeg executable file path.
	 */
	private String path;

	/**
	 * It builds the default FFMPEGLocator, exporting the ffmpeg executable on a
	 * temp file.
	 * 
	 * @throws Exception
	 */
	public DefaultFFMPEGLocator() throws Exception {
		// Windows?
		boolean isWindows = JarFileUtils.IS_RUNNING_WINDOWS_OS;

		List<FileExcuteCopy> fileExcuteCopyList = new ArrayList<FileExcuteCopy>();

		// Temp dir?
		File temp = new File(System.getProperty("java.io.tmpdir"), "jave-" + myEXEversion);
		if (!temp.exists()) {
			temp.mkdirs();
		}

		// ffmpeg executable export on disk.
		String suffix = isWindows ? ".exe" : "";
		File exe = new File(temp, "ffmpeg" + suffix);

		// copyFile("ffmpeg" + suffix, exe);

		// exe file
		FileExcuteCopy exeFile = new FileExcuteCopy();
		exeFile.setPath(("jave/ffmpeg" + suffix));
		exeFile.setDest(exe);

		fileExcuteCopyList.add(exeFile);

		// pthreadGC2.dll
		if (isWindows) {
			File dll = new File(temp, "pthreadGC2.dll");
			// copyFile("pthreadGC2.dll", dll);

			// dll file
			FileExcuteCopy dllFile = new FileExcuteCopy();
			dllFile.setPath("jave/pthreadGC2.dll");
			dllFile.setDest(dll);

			fileExcuteCopyList.add(dllFile);
		}

		// copy exe file
		copyExecuteFile(fileExcuteCopyList);

		// Need a chmod?
		if (!(isWindows)) {
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec(new String[] { "/bin/chmod", "777", exe.getAbsolutePath() });
			} catch (IOException e) {
				throw e;
			}
		}

		// Ok.
		this.path = exe.getAbsolutePath();
	}

	protected String getFFMPEGExecutablePath() {
		return path;
	}

	/**
	 * Copies a file bundled in the package to the supplied destination.
	 * 
	 * @param path
	 *            The name of the bundled file.
	 * @param dest
	 *            The destination.
	 * @throws RuntimeException
	 *             If aun unexpected error occurs.
	 */
	private void copyFile(String path, File dest) throws RuntimeException {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = getClass().getResourceAsStream(path);
			output = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int l;
			while ((l = input.read(buffer)) != -1) {
				output.write(buffer, 0, l);
			}
		} catch (IOException e) {
			throw new RuntimeException("Cannot write file " + dest.getAbsolutePath());
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (Throwable t) {
					;
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (Throwable t) {
					;
				}
			}
		}
	}

	/**
	 * 拷贝可执行文件
	 * 
	 * @author wangjunwen
	 * @date 2018年2月26日 上午10:14:30
	 *
	 * @param fileExcuteCopyList
	 *            需要拷贝的文件列表
	 * @throws Exception
	 */
	private void copyExecuteFile(List<FileExcuteCopy> fileExcuteCopyList) throws Exception {

		if ((null == fileExcuteCopyList) || (fileExcuteCopyList.isEmpty())) {
			return;
		}

		final List<String> findFileList = new ArrayList<String>();
		for (FileExcuteCopy fileExcuteCopy : fileExcuteCopyList) {
			findFileList.add(fileExcuteCopy.getPath());
		}

		File findFile = null;// 查找到的文件

		File unzipDir = null;// 将 jar 包解压到该目录下

		List<File> outFindedFileList = new ArrayList<File>();// 查找到的文件列表

		if (JarFileUtils.IS_RUNNING_JAR) {
			unzipDir = new File(fileExcuteCopyList.get(0).getDest().getParentFile(), UUID.randomUUID().toString());
			unzipDir.deleteOnExit();

			JarFile jarFile = JarFileUtils.getRunningJarFile();

			JarFileUtils.findFileFromJar(jarFile, unzipDir, findFileList, outFindedFileList);
		} else {
			for (String findFilePath : findFileList) {
				findFilePath = findFilePath.replaceFirst("jave/", "");

				findFilePath = (Thread.currentThread().getContextClassLoader().getResource("jave/").getFile()
						+ findFilePath);

				findFile = new File(findFilePath);

				outFindedFileList.add(findFile);// 添加到查找到的文件列表中
			}
		}

		Iterator<File> outFindedFileListIterator = null;

		String destFileMD5 = "destFileMD5";// 目标文件的MD5码
		String fileMD5 = "fileMD5";// 源文件的MD5码

		for (FileExcuteCopy fileExcuteCopy : fileExcuteCopyList) {
			destFileMD5 = "destFileMD5";
			fileMD5 = "fileMD5";

			outFindedFileListIterator = outFindedFileList.iterator();

			findFile = null;

			while (outFindedFileListIterator.hasNext()) {
				findFile = outFindedFileListIterator.next();

				if (findFile.getAbsolutePath().replaceAll("\\\\", "/").endsWith(fileExcuteCopy.getPath())) {
					outFindedFileListIterator.remove();
					break;
				}
			}

			if ((null == findFile)) {
				continue;
			}

			// 如果文件已存在，并且文件相同就不再复制
			if (fileExcuteCopy.getDest().exists()) {
				destFileMD5 = JarFileUtils.fileToMD5(fileExcuteCopy.getDest());

				fileMD5 = JarFileUtils.fileToMD5(findFile);

				// 如果文件不相同，那么重新复制新的文件
				if (!(destFileMD5.equals(fileMD5))) {
					fileExcuteCopy.getDest().delete();
				}
			}

			// 两个文件的MD5码不相同的时候需要复制
			if (!(destFileMD5.equals(fileMD5))) {

				try (FileInputStream fileInputStream = new FileInputStream(findFile);
						OutputStream outputStream = new FileOutputStream(fileExcuteCopy.getDest());) {

					byte[] buffer = new byte[1024];

					int readSize = -1;

					while (-1 != (readSize = fileInputStream.read(buffer))) {
						outputStream.write(buffer, 0, readSize);
					}
				}

			}
		}

		// 删除当前解压的目录
		JarFileUtils.deleteDirectory(unzipDir);

	}

	/**
	 * 拷贝文件的扩展实体类
	 * 
	 * @author wangjunwen
	 * @date 2018年2月26日 上午9:35:55
	 *
	 */
	private class FileExcuteCopy {
		// 文件名称
		private String path;
		// 拷贝到的目标路径
		private File dest;

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public File getDest() {
			return dest;
		}

		public void setDest(File dest) {
			this.dest = dest;
		}

	}

}
