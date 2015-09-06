package org.bidtime.web.filter.zip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ZipBaseFilter implements Filter {

	//private static final Logger logger = Logger.getLogger(ZipBaseFilter.class);

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 要想压缩页面 就是当用户访问页面时 在服务器把页面信息打给浏览器的时候进行压缩 所以这时候就是对response进行包装
		ZipBaseResponse myresponse = new ZipBaseResponse((HttpServletResponse) response);
		chain.doFilter(request, (ServletResponse) myresponse);
		// 从包装的myresponse里获得写到容器流里的原始数据bs
		byte[] bs = myresponse.getOut();
		//logger.info("压缩前>>>" + bs.length);
		// 用原始的response响应用户，此response需要转换为HttpServletResponse类型
		HttpServletResponse resp = (HttpServletResponse) response;
		// response解决中文乱码问题
		resp.setContentType("text/html;charset=utf-8");
		// 开始压缩
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		GZIPOutputStream zip = new GZIPOutputStream(out);
//		zip.write(bs);
//		zip.close();
//		// 获取压缩后的数据
//		byte[] dest = out.toByteArray();
		byte[] dest = doZip(bs);
		//logger.info("压缩后>>>" + dest.length);
		// 设置响应头
		resp.setDateHeader("content-length", dest.length);
		resp.setHeader("content-Encoding", "gzip");
		resp.getOutputStream().write(dest);
	}
	
	protected byte[] doZip(byte[] bs) {
		return bs;
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}

class ZipBaseResponse extends HttpServletResponseWrapper {
	private ByteArrayOutputStream out = new ByteArrayOutputStream();// 定义一个容器

	public ZipBaseResponse(HttpServletResponse response) {
		super(response);
	}

	// 重写父类的getwriter方法
	PrintWriter writer;

	@Override
	public PrintWriter getWriter() throws IOException {
		// 写到一个容器流里 ，
		writer = new PrintWriter(new OutputStreamWriter(out, "utf-8"), true);
		return writer;
	}

	// 提供一个获得流里内容的方法 因为用字符流写到容器里的，有缓冲区,所以在取出流里的数据时判断字符流是否关闭
	public byte[] getOut() {
		if (writer != null) {
			writer.close();
		}
		return out.toByteArray();
	}
}