package org.bidtime.utils.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileComm {

	private static final Logger logger = LoggerFactory
			.getLogger(FileComm.class);

	/**
	 * Sets the executable Unix permission (0777) on a file or folder.
	 * <p/>
	 * This invokes a chmod exec, so there is no guarantee of it being fast.
	 * Caller must make sure to not invoke this under Windows.
	 * 
	 * @param file
	 *            The file to set permissions on.
	 * @throws IOException
	 *             If an I/O error occurs
	 */
	public static void setExecutablePermission(File file) throws IOException {
		if (isLinux() && file != null) {
			if (file.isDirectory()) {
				Runtime.getRuntime()
						.exec(new String[] { "chmod", "777",
								file.getAbsolutePath() });
			} else {
				Runtime.getRuntime()
						.exec(new String[] { "chmod", "644",
								file.getAbsolutePath() });
			}
		}
	}

	public static boolean isLinux() {
		if ("\\".equals(File.separator)) {
			return false;
			// is WINDOWS
		} else if ("/".equals(File.separator)) {
			// is LINUX
			return true;
		} else {
			return true;
		}
	}

	public static String getExtName(String s) {
		return getExtNameOfSplit(s, '.');
	}

	public static String getExtNameOfSplit(String s, char split) {
		int i = s.lastIndexOf(split);
		if (i > 0) {
			int leg = s.length();
			return (i > 0 ? (i + 1) == leg ? null : s.substring(i, s.length())
					: null);
		} else {
			return null;
		}
	}

	public static boolean copyFile(String s, String t) {
		return copyFile(new File(s), new File(t));
	}

	public static boolean copyFile(File s, File t) {
		boolean result = false;
		FileInputStream fin = null;
		FileOutputStream fout = null;
		try {
			fin = new FileInputStream(s);
			fout = new FileOutputStream(t);
			result = copyFile(fin, fout);
		} catch (IOException e) {
			logger.error("fileChannelCopy", e);
		} finally {
			try {
				if (fin != null) {
					fin.close();
				}
				if (fout != null) {
					fout.close();
				}
			} catch (IOException e) {
				logger.error("fileChannelCopy", e);
			}
		}
		return result;
	}

	public static boolean copyFile(FileInputStream fin,
			FileOutputStream fout) {
		boolean result = false;
		FileChannel cin = null;
		FileChannel cout = null;
		try {
			cin = fin.getChannel();// 得到对应的文件通道
			cout = fout.getChannel();// 得到对应的文件通道
			cin.transferTo(0, cin.size(), cout);// 连接两个通道，并且从in通道读取，然后写入out通道
			result = true;
		} catch (IOException e) {
			logger.error("fileChannelCopy", e);
		} finally {
			try {
				if (cin != null) {
					cin.close();
				}
				if (cout != null) {
					cout.close();
				}
			} catch (IOException e) {
				logger.error("fileChannelCopy", e);
			}
		}
		return result;
	}
	
//	public static boolean copyFile(File s, File t) {
//		boolean result = false;
//		FileInputStream fin = null;
//		FileOutputStream fout = null;
//		FileChannel cin = null;
//		FileChannel cout = null;
//		try {
//			fin = new FileInputStream(s);
//			fout = new FileOutputStream(t);
//			cin = fin.getChannel();// 得到对应的文件通道
//			cout = fout.getChannel();// 得到对应的文件通道
//			cin.transferTo(0, cin.size(), cout);// 连接两个通道，并且从in通道读取，然后写入out通道
//			result = true;
//		} catch (IOException e) {
//			logger.error("fileChannelCopy", e);
//		} finally {
//			try {
//				fin.close();
//				cin.close();
//				fout.close();
//				cout.close();
//			} catch (IOException e) {
//				logger.error("fileChannelCopy", e);
//			}
//		}
//		return result;
//	}

	public static boolean deleteFile(String s) {
		try {
			File file = new File(s);
			return file.delete();
		} catch (Exception e) {
			logger.error("deleteFile", e);
			return false;
		}
	}

	public static boolean deleteFile(File file) {
		try {
			return file.delete();
		} catch (Exception e) {
			logger.error("deleteFile", e);
			return false;
		}
	}
	
	public static boolean moveFile(String s, String t) {
		return moveFile(new File(s), new File(t));
	}

	public static boolean moveFile(File s, File t) {
		try {
			return s.renameTo(t);
		} catch (Exception e) {
			logger.error("moveFile", e);
			return false;
		}
	}
	
	public static String getFileNameOfURL(String s) {
		int n = s.lastIndexOf("/");
		return s.substring(n + 1);
	}
}
