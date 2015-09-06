package org.bidtime.web.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.bidtime.utils.spring.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author jss
 * 
 * 初始化Servlet
 *
 */
public class InitServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory
			.getLogger(InitServlet.class);

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InitServlet() {
		super();
	}

	@Override
	public void destroy() {
		if (logger.isDebugEnabled()) {
			logger.debug("destroy begin...");
		}
		super.destroy();
		if (logger.isDebugEnabled()) {
			logger.info("destroy end.");
		}
	}

	@Override
	public void init() throws ServletException {
		initSysBeansOfServletContext(getServletContext());
	}

	public static void initSysBeansOfServletContext(ServletContext context) {
		initSysBeansOfAppContext(WebApplicationContextUtils
				.getRequiredWebApplicationContext(context));
	}

	public static void initSysBeansOfAppContext(ApplicationContext context) {
		if (logger.isDebugEnabled()) {
			logger.debug("init begin...");
		}
		SpringContextUtils.setContext(context);
		if (logger.isDebugEnabled()) {
			logger.debug("init end.");
		}
	}

}
