package org.bidtime.web.filter.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZIPFilter implements Filter {

	private static final Logger logger = LoggerFactory
			.getLogger(ZIPFilter.class);

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 要想压缩页面 就是当用户访问页面时 在服务器把页面信息打给浏览器的时候进行压缩 所以这时候就是对response进行包装
		ZipBaseResponse myresponse = new ZipBaseResponse(
				(HttpServletResponse) response);
		chain.doFilter(request, (ServletResponse) myresponse);
		// 从包装的myresponse里获得写到容器流里的原始数据bs
		byte[] bs = myresponse.getOut();
		// logger.info("压缩前>>>" + bs.length);
		// 用原始的response响应用户，此response需要转换为HttpServletResponse类型
		HttpServletResponse resp = (HttpServletResponse) response;
		// response解决中文乱码问题
		resp.setContentType("text/html;charset=utf-8");
		// 开始压缩
		byte[] dest = doZip(bs);
		// logger.info("压缩后>>>" + dest.length);
		// 设置响应头
		resp.setDateHeader("content-length", dest.length);
		resp.setHeader("content-Encoding", "gzip");
		resp.getOutputStream().write(dest);
	}

	protected byte[] doZip(byte[] bs) {
		// 开始压缩
		// // 获取压缩后的数据
		// byte[] dest = out.toByteArray();
		return zip(bs);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

	/***
	 * 压缩Zip
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] zip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ZipOutputStream zip = new ZipOutputStream(bos);
			ZipEntry entry = new ZipEntry("zip");
			entry.setSize(data.length);
			zip.putNextEntry(entry);
			zip.write(data);
			zip.closeEntry();
			zip.close();
			b = bos.toByteArray();
			bos.close();
		} catch (Exception e) {
			logger.error("zip", e);
		}
		return b;
	}
	
	 /**
	  * 压缩数据
	  * 
	  * @param object
	  * @return
	  * @throws IOException
	  */
//	public static byte[] jzlib(byte[] object) {
//
//		byte[] data = null;
//		try {
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			GZIPOutputStream zOut = new GZIPOutputStream(out,
//					JZlib.Z_DEFAULT_COMPRESSION);
//			DataOutputStream objOut = new DataOutputStream(zOut);
//			objOut.write(object);
//			objOut.flush();
//			zOut.close();
//			data = out.toByteArray();
//			out.close();
//
//		} catch (IOException e) {
//		}
//		return data;
//	}
	
	/***
	 * 解压Zip
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] unZip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			ZipInputStream zip = new ZipInputStream(bis);
			while (zip.getNextEntry() != null) {
				byte[] buf = new byte[1024];
				int num = -1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ((num = zip.read(buf, 0, buf.length)) != -1) {
					baos.write(buf, 0, num);
				}
				b = baos.toByteArray();
				baos.flush();
				baos.close();
			}
			zip.close();
			bis.close();
		} catch (Exception e) {
			logger.error("http:{}", e.getMessage());
		}
		return b;
	}

}