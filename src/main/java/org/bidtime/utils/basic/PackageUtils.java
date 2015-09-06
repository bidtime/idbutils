package org.bidtime.utils.basic;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PackageUtils {
	
	private static final Logger logger = LoggerFactory
			.getLogger(PackageUtils.class);

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		List<String> list = PackageUtils.getRelationFilesRoot("com.eb", ".props", true);
//		try {
//			for (int i = 0; i < list.size(); i++) {
//				System.out.println(list.get(i));
//			}
//		} finally {
//			list = null;
//		}
//	}

	private static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive, List<String> list,
			final String sSufix) {
		// 获取此包的目录 建立一个File
		String packageDirName = packageName.replace('.', '/');
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(sSufix));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "."
						+ file.getName(), file.getAbsolutePath(), recursive,
						list, sSufix);
			} else {
				list.add(packageDirName + '/' + file.getName());
			}
		}
	}

	public static List<String> getRelationFilesRoot(String pack, String sSufix,
			boolean recursive) {
		return getRelationFilesRoot_Jar(pack, sSufix, recursive, true, false);
	}
	
	public static List<String> getRelationFilesJar(String pack, String sSufix,
			boolean recursive) {
		return getRelationFilesRoot_Jar(pack, sSufix, recursive, false, true);
	}
	
	public static List<String> autoGetRelationFilesRootOrJar(String pack, String sSufix,
			boolean recursive) {
		return getRelationFilesRoot_Jar(pack, sSufix, recursive, true, true);
	}
	
	public static List<String> getRelationFilesRoot_Jar(String pack, String sSufix,
			boolean recursive, boolean bIncludeRoot, boolean bIncludeJar) {
		// 第一个class类的集合
		List<String> list = new ArrayList<String>();
		// 是否循环迭代
		// 获取包的名字 并进行替换
		String packageName = pack;
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(
					packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol) && bIncludeRoot) {
					// System.err.println("file类型的扫描");
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					// System.out.println(filePath);
					findAndAddClassesInPackageByFile(packageName, filePath,
							recursive, list, sSufix);
				} else if ("jar".equals(protocol) && bIncludeJar) {
					// 如果是jar包文件
					// 定义一个JarFile
					// System.err.println("jar类型的扫描");
					JarFile jar;
					try {
						// 获取jar
						jar = ((JarURLConnection) url.openConnection())
								.getJarFile();
						// 从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						// 同样的进行循环迭代
						while (entries.hasMoreElements()) {
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							// 如果是以/开头的
							if (name.charAt(0) == '/') {
								// 获取后面的字符串
								name = name.substring(1);
							}
							// 如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一个包
								if (idx != -1) {
									// 获取包名 把"/"替换成"."
									packageName = name.substring(0, idx)
											.replace('/', '.');
								}
								// 如果可以迭代下去 并且是一个包
								if ((idx != -1) || recursive) {
									// 如果是一个.class文件 而且不是目录
									if (name.endsWith(sSufix)
											&& !entry.isDirectory()) {
										list.add(name);
									}
								}
							}
						}
					} catch (IOException e) {
						logger.error("在扫描用户定义视图时从jar包获取文件出错", e);
					}
				}
			}
		} catch (IOException e) {
			logger.error("getRelationFilesRoot_Jar", e);
		}
		return list;
	}

}
