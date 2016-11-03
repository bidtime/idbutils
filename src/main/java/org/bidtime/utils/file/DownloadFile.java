package org.bidtime.utils.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadFile {

	private static final Logger logger = LoggerFactory
			.getLogger(DownloadFile.class);
	
	private static final int bufferSize = 10 * 1024 * 1024;

	public static HttpServletResponse download(HttpServletResponse response,
			String path) {
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 取得文件的后缀名。
			// String ext = filename.substring(filename.lastIndexOf(".") +
			// 1).toUpperCase();

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return response;
	}

	public static boolean navigate(HttpServletResponse response, String fileName) {
		boolean bReturn = false;
		try {
			// path是指欲下载的文件的路径。
			File file = new File(fileName);
			if (!file.exists()) {
				return bReturn;
			} else {
				// 取得文件名。
				// String filename = file.getName();
				// 取得文件的后缀名。
				// String ext = filename.substring(filename.lastIndexOf(".") +
				// 1).toUpperCase();

				// 以流的形式下载文件。
				InputStream fis = new BufferedInputStream(new FileInputStream(
						fileName));
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();
				// 清空response
				response.reset();
				OutputStream toClient = new BufferedOutputStream(
						response.getOutputStream());
				response.setContentType("image/jpeg;charset=utf-8");
				toClient.write(buffer);
				toClient.flush();
				toClient.close();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return bReturn;
	}

	public void downloadLocal(HttpServletResponse response)
			throws FileNotFoundException {
		// 下载本地文件
		String fileName = "Operator.doc".toString(); // 文件的默认保存名
		// 读到流中
		InputStream inStream = new FileInputStream("c:/Operator.doc");// 文件的存放路径
		// 设置输出的格式
		response.reset();
		response.setContentType("bin");
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");
		// 循环取出流中的数据
		byte[] b = new byte[100];
		int len;
		try {
			while ((len = inStream.read(b)) > 0)
				response.getOutputStream().write(b, 0, len);
			inStream.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	/*
	 * public void downloadNet(HttpServletResponse response) throws
	 * MalformedURLException { // 下载网络文件 int bytesum = 0; int byteread = 0;
	 * 
	 * URL url = new URL("windine.blogdriver.com/logo.gif");
	 * 
	 * try { URLConnection conn = url.openConnection(); InputStream inStream =
	 * conn.getInputStream(); FileOutputStream fs = new
	 * FileOutputStream("c:/abc.gif");
	 * 
	 * byte[] buffer = new byte[1204]; int length; while ((byteread =
	 * inStream.read(buffer)) != -1) { bytesum += byteread;
	 * System.out.println(bytesum); fs.write(buffer, 0, byteread); } } catch
	 * (FileNotFoundException e) {} catch (IOException e)
	 * {} }
	 */

	/*
	 * //Process the HTTP Get request public static void
	 * downloadFile(HttpServletRequest request, HttpServletResponse response)
	 * throws ServletException, IOException {
	 * response.setContentType(CONTENT_TYPE); //得到下载文件的名字 //String
	 * filename=request.getParameter("filename");
	 * 
	 * //解决中文乱码问题 String filename=new
	 * String(request.getParameter("filename").getBytes("iso-8859-1"),"gbk");
	 * 
	 * //创建file对象 File file=new File("F:\\book\\WebRoot\\"+filename);
	 * 
	 * //设置response的编码方式 response.setContentType("application/x-msdownload");
	 * 
	 * //写明要下载的文件的大小 response.setContentLength((int)file.length());
	 * 
	 * //设置附加文件名 //
	 * response.setHeader("Content-Disposition","attachment;filename="
	 * +filename);
	 * 
	 * //解决中文乱码
	 * response.setHeader("Content-Disposition","attachment;filename="+new
	 * String
	 * 
	 * (filename.getBytes("gbk"),"iso-8859-1"));
	 * 
	 * //读出文件到i/o流 FileInputStream fis=new FileInputStream(file);
	 * BufferedInputStream buff=new BufferedInputStream(fis);
	 * 
	 * byte [] b=new byte[1024];//相当于我们的缓存
	 * 
	 * long k=0;//该值用于计算当前实际下载了多少字节
	 * 
	 * //从response对象中得到输出流,准备下载
	 * 
	 * OutputStream myout=response.getOutputStream();
	 * 
	 * //开始循环下载
	 * 
	 * while(k<file.length()){
	 * 
	 * int j=buff.read(b,0,1024); k+=j;
	 * 
	 * //将b中的数据写到客户端的内存 myout.write(b,0,j);
	 * 
	 * }
	 * 
	 * //将写入到客户端的内存的数据,刷新到磁盘 myout.flush();
	 * 
	 * }
	 */

	/**
	 * @param sFileName
	 * @param response
	 * @throws IOException
	 */
	public static void visitUrl(String sFileName, HttpServletResponse response)
			throws IOException {
		response.setContentType("image/gif;charset=utf-8");
		OutputStream output = response.getOutputStream();
		// ServletOutputStream sops=response.getOutputStream();
		FileInputStream fis = new FileInputStream(sFileName);
		try {
			copyStream(fis, output, true);
		} finally {
			fis.close();
			output.flush();
			output.close();
			fis = null;
			output = null;
		}
		// file=null;
	}

	/*
	 * 复制流 到 前端浏览器
	 * 
	 * @param source 源文件输入流
	 * 
	 * @param dest 输出流
	 * 
	 * @param flush
	 * 
	 * @return
	 */
	private static final long copyStream(InputStream source, OutputStream dest,
			boolean flush) {
		int bytes;
		long total = 0l;
		byte[] buffer = new byte[bufferSize];
		try {
			while ((bytes = source.read(buffer)) != -1) {
				if (bytes == 0) {
					bytes = source.read();
					if (bytes < 0)
						break;
					dest.write(bytes);
					if (flush)
						dest.flush();
					total += bytes;
				}
				dest.write(buffer, 0, bytes);
				if (flush)
					dest.flush();
				total += bytes;
			}

		} catch (IOException e) {
			throw new RuntimeException("IOException caught while copying.", e);
		}
		return total;
	}

}
