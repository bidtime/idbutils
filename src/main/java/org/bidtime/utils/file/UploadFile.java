package org.bidtime.utils.file;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.bidtime.utils.basic.DateTimeComm;
import org.bidtime.utils.basic.FileComm;
import org.bidtime.utils.basic.IdEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadFile {

	private static int MAX_FILE_SIZE = 100 * 1024 * 1024; // 限制文件的上传大小
	private static int MAX_MEM_FILESIZE = 1024; // 放内存的大小

	private static final Logger logger = LoggerFactory
			.getLogger(UploadFile.class);

	public static boolean uploadFileToUUID(String rootFile, String urlFile,
			HttpServletRequest request, Map<String, String> mapFile)
			throws Exception {
		int result = 0;
		long start = System.currentTimeMillis();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(MAX_MEM_FILESIZE);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(MAX_FILE_SIZE);
		List<FileItem> fileItems = upload.parseRequest(request);
		Iterator<FileItem> iterator = fileItems.iterator();
		StringBuilder sb = new StringBuilder();
		try {
			while (iterator.hasNext()) {
				FileItem fileItem = iterator.next();
				if (fileItem != null && !fileItem.isFormField()) {
					if (fileItem.getName() == null) {
						continue;
					}
					if (fileItem.getName().trim().equals("")) {
						continue;
					}
					result ++;
					File fileParent = new File(rootFile);
					if (!fileParent.exists()) {
						fileParent.mkdirs();
					}
					String fileName = IdEntity.getUUID(true)
							+ FileComm.getExtName(fileItem.getName());
					fileItem.write(new File(rootFile + fileName));
					if (mapFile != null) {
						mapFile.put(fileItem.getFieldName(), urlFile + fileName);
					}
					if (logger.isDebugEnabled()) {
						sb.append(result);
						sb.append(":[");
						sb.append(fileItem.getName());
						sb.append("] upload success; New File:");
						sb.append(fileName);
						sb.append("; File Size:");
						sb.append(fileItem.getSize());
						logger.debug(sb.toString());
					}
				}
			}
		} catch(Exception e) {
			logger.error("uploadFileToUUID", e);
			return false;
		} finally {
			long end = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				String lstr = DateTimeComm.getFormatSpanTime(start, end);
				logger.debug(lstr);
			}
			//DateTimeComm.logFormatEndTime(start, end, logger);
			sb.setLength(0);
			sb = null;
		}
		return result>0?true:false;
	}

}
