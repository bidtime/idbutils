package org.bidtime.utils.spring;


import javax.sql.DataSource;

import org.bidtime.dbutils.jdbc.sql.xml.TableFieldXmlsParser;
import org.bidtime.dbutils.params.StmtParams;
import org.bidtime.web.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jss
 * 
 * 提供对Spring上下文的操作
 *
 */
public class SpringContextUtils {
	
	private static final Logger logger = LoggerFactory
			.getLogger(RequestUtils.class);

	private static final SpringContextUtils INSTANCE = new SpringContextUtils();

	private DataSource dataSource;

	//private String[] ctxStrArray = null;

//	public static void setContextString(String contextString) {
//		if (StringUtils.isBlank(contextString)) {
//			throw new IllegalArgumentException(
//					"Context String can not be blank!");
//		}
//		INSTANCE.ctxStrArray = new String[] { contextString };
//	}

//	public static void setContextString(String[] ctxStrArray) {
//		if (ctxStrArray == null || ctxStrArray.length <= 0) {
//			throw new IllegalArgumentException("Context has not set!");
//		}
//		INSTANCE.ctxStrArray = ctxStrArray;
//	}

//	public static ApplicationContext getContext() {
//		if (INSTANCE.context == null) {
//			synchronized (INSTANCE) {
//				if (INSTANCE.context != null) {
//					return INSTANCE.context;
//				}
//
//				if (INSTANCE.ctxStrArray == null
//						|| INSTANCE.ctxStrArray.length <= 0) {
//					String msg = "did not set the spring context string";
//					logger.error(msg);
//					throw new RuntimeException(msg);
//				} else {
//					INSTANCE.context = new ClassPathXmlApplicationContext(
//							INSTANCE.ctxStrArray);
//				}
//			}
//		}
//		return INSTANCE.context;
//	}

//	public static void setServletContext(ServletContext context) {
//		INSTANCE.servletContext = context;
//	}
//
//	public static ServletContext getServletContext() {
//		return INSTANCE.servletContext;
//	}

	public static Object getBean(String beanName) {
		return TableFieldXmlsParser.getBean(beanName);
	}

	public static DataSource getDataSourceOfName(String beanName) {
		return (DataSource)TableFieldXmlsParser.getBean(beanName);
	}

	public static DataSource getDataSourceDefault() {
		if (INSTANCE.dataSource == null) {
			synchronized (INSTANCE) {
				if (INSTANCE.dataSource == null) {
					String dsName = StmtParams.getInstance().getDataSource();
					logger.debug("init ds: {}", dsName);
					INSTANCE.dataSource = getDataSourceOfName(dsName);
				}
			}
		}
		return INSTANCE.dataSource;
	}

}
