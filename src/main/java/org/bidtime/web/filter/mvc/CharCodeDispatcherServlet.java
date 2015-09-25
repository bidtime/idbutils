package org.bidtime.web.filter.mvc;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;

public class CharCodeDispatcherServlet extends DispatcherServlet {
	private static final long serialVersionUID = 1L;
	private String encoding;

	public void init(ServletConfig config) throws ServletException {
		encoding = config.getInitParameter("encoding");
		if (encoding == null || encoding.trim().length() == 0) { // 默认 UTF-8
			encoding = "UTF-8";
		}
		super.init(config);
	}

	protected void doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(encoding);
		super.doService(request, response);
	}
}
