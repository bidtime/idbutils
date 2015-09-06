package org.bidtime.utils.file;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.bidtime.utils.basic.CFileComm;

public class UploadFile {

	private static int MAXFILESIZE = 100 * 1024 * 1024; // 限制文件的上传大小

	private static final Logger logger = LoggerFactory
			.getLogger(UploadFile.class);

	public static void uploadFileOfRequest(HttpServletRequest request,
			HttpServletResponse response, String sDir) throws ServletException,
			IOException {
		uploadFileOfRequest(request, response, sDir, null);
	}

	@SuppressWarnings("rawtypes")
	public static void uploadFileOfRequest(HttpServletRequest request,
			HttpServletResponse response, String sDir, List<String> listFileName)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		// PrintWriter out = response.getWriter(); // 保存文件到服务器中 |Create a
		// factory
		// for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024); // 超出这个大小放磁盘,否则放在内存中
		ServletFileUpload upload = new ServletFileUpload(factory); // Create a
		// new file
		// upload
		// handler
		upload.setSizeMax(MAXFILESIZE);
		try {
			List fileItems = upload.parseRequest(request); // Parse the
			// request,have a
			// List of file
			// items that you
			// need to process
			Iterator iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next(); // 如果 item.isFormField 为
				// true,则代表简单域,否则为 file
				// 域.Field 有 2
				// 种,一种是简单域,一种是 file 域.
				if (!item.isFormField()) {
					// 获得文件名,包括文件的扩展名
					String name = item.getName();
					if (logger.isDebugEnabled()) {
						logger.debug("item: " + name);
					}
					if (listFileName != null) {
						listFileName.add(name);
					}
					try {
						// "factory.setRepository(new File(path));"可以设置临时目录
						if (logger.isDebugEnabled()) {
							logger.debug("upload dir:" + sDir);
						}
						File filePath = new File(sDir);
						if (!filePath.exists()) {
							if (logger.isDebugEnabled()) {
								logger.debug("dir isn't exists:" + sDir);
							}
							filePath.mkdirs();
							// filePath.setWritable(true, false);
							//CFileComm.setExecutablePermission(filePath);
						} else {
							if (logger.isDebugEnabled()) {
								logger.debug("dir is exists:" + sDir);
							}
						}
						File fileItem = new File(sDir + name);
						if (logger.isDebugEnabled()) {
							logger.debug("dir_item: " + sDir + name);
						}
						item.write(fileItem); // 写入磁盘.
						// fileItem.setWritable(true, false);
						//CFileComm.setExecutablePermission(fileItem);
					} catch (Exception e) {
						logger.error("uploadFileOfRequest", e);
					}
				}
			}
		} catch (FileUploadException e) {
			logger.error("uploadFileOfRequest", e);
		}
	}

	@SuppressWarnings("rawtypes")
	public static void uploadFileMapOfRequest(HttpServletRequest request,
			HttpServletResponse response, String sDir,
			Map<String, String> mapFileName, boolean bIncludeFldName)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		// PrintWriter out = response.getWriter(); // 保存文件到服务器中 |Create a
		// factory
		// for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024); // 超出这个大小放磁盘,否则放在内存中
		ServletFileUpload upload = new ServletFileUpload(factory); // Create a
		// new file
		// upload
		// handler
		upload.setSizeMax(MAXFILESIZE);
		try {
			List fileItems = upload.parseRequest(request); // Parse the
			// request,have a
			// List of file
			// items that you
			// need to process
			Iterator iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next(); // 如果 item.isFormField 为
				// true,则代表简单域,否则为 file
				// 域.Field 有 2
				if (!item.isFormField()) {
					// 获得文件名,包括文件的扩展名
					String name = item.getName();
					String fileName = item.getFieldName();
					if (logger.isDebugEnabled()) {
						logger.debug("item: " + name);
					}
					if (mapFileName != null) {
						mapFileName.put(fileName, name);
					}
					try {
						// "factory.setRepository(new File(path));"可以设置临时目录
						if (logger.isDebugEnabled()) {
							logger.debug("upload dir:" + sDir);
						}
						File filePath = null;
						if (!bIncludeFldName) {
							filePath = new File(sDir);
						} else {
							filePath = new File(sDir + fileName + "/");
						}
						if (!filePath.exists()) {
							if (logger.isDebugEnabled()) {
								logger.debug("dir isn't exists:" + sDir);
							}
							filePath.mkdirs();
							// filePath.setWritable(true, false);
							//CFileComm.setExecutablePermission(filePath);
						} else {
							if (logger.isDebugEnabled()) {
								logger.debug("dir is exists:" + sDir);
							}
						}
						File fileItem = null;
						if (!bIncludeFldName) {
							fileItem = new File(sDir + name);
						} else {
							fileItem = new File(sDir + fileName + "/" + name);
						}
						if (logger.isDebugEnabled()) {
							logger.debug("dir_item: " + sDir + name);
						}
						item.write(fileItem); // 写入磁盘.
						// fileItem.setWritable(true, false);
						//CFileComm.setExecutablePermission(fileItem);
					} catch (Exception e) {
						logger.error("uploadFileMapOfRequest", e);
					}
				}
			}
		} catch (FileUploadException e) {
			logger.error("uploadFileMapOfRequest", e);
		}
	}

	@SuppressWarnings("rawtypes")
	public static void uploadFileMapOfRequest(HttpServletRequest request,
			HttpServletResponse response, String sDir,
			Map<String, String> mapFileName, boolean bIncludeFldName,
			boolean bChangeFileName) throws ServletException, IOException {
		// System.out.println("Access !");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		// PrintWriter out = response.getWriter(); // 保存文件到服务器中 |Create a
		// factory
		// for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024); // 超出这个大小放磁盘,否则放在内存中
		ServletFileUpload upload = new ServletFileUpload(factory); // Create a
		// new file
		// upload
		// handler
		upload.setSizeMax(MAXFILESIZE);
		try {
			List fileItems = upload.parseRequest(request); // Parse the
			// request,have a
			// List of file
			// items that you
			// need to process
			Iterator iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next(); // 如果 item.isFormField 为
				// true,则代表简单域,否则为 file
				// 域.Field 有 2
				if (!item.isFormField()) {
					// 获得文件名,包括文件的扩展名
					String fileName = item.getFieldName();
					String name = null;
					if (bChangeFileName) {
						name = fileName + CFileComm.getExtName(item.getName());
					} else {
						name = item.getName();
					}

					logger.debug("item: " + name);
					if (mapFileName != null) {
						mapFileName.put(fileName, name);
					}
					try {
						// "factory.setRepository(new File(path));"可以设置临时目录
						if (logger.isDebugEnabled()) {
							logger.debug("upload dir:" + sDir);
						}
						File filePath = null;
						if (!bIncludeFldName) {
							filePath = new File(sDir);
						} else {
							filePath = new File(sDir + fileName + "/");
						}
						if (!filePath.exists()) {
							if (logger.isDebugEnabled()) {
								logger.debug("dir isn't exists:" + sDir);
							}
							filePath.mkdirs();
							// filePath.setWritable(true, false);
							//CFileComm.setExecutablePermission(filePath);
						} else {
							if (logger.isDebugEnabled()) {
								logger.debug("dir is exists:" + sDir);
							}
						}
						File fileItem = null;
						if (!bIncludeFldName) {
							fileItem = new File(sDir + name);
						} else {
							fileItem = new File(sDir + fileName + "/" + name);
						}
						if (logger.isDebugEnabled()) {
							logger.debug("dir_item: " + sDir + name);
						}
						item.write(fileItem); // 写入磁盘.
						// fileItem.setWritable(true, false);
						//CFileComm.setExecutablePermission(fileItem);
					} catch (Exception e) {
						logger.error("uploadFileMapOfRequest", e);
					}
				}
			}
		} catch (FileUploadException e) {
			logger.error("uploadFileMapOfRequest", e);
		}
	}

}
