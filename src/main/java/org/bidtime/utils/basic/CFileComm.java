package org.bidtime.utils.basic;

import java.io.File;
import java.io.IOException;

public class CFileComm {

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
		if (i>0) {
			int leg = s.length();
			return (i > 0 ? (i + 1) == leg ? null : s.substring(i, s.length()) : null);			
		} else {
			return null;
		}
	}

}
