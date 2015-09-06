package org.bidtime.utils.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.bidtime.dbutils.jdbc.dao.BasicDAO;
import org.bidtime.utils.spring.SpringBeanFactoryUtil;
import org.bidtime.web.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jss
 * 
 * 基于单表的Service基类
 *
 */
public class BasicService<E extends BasicDAO> {

	private static final Logger logger = LoggerFactory
			.getLogger(RequestUtils.class);

	protected E dao;

	public BasicService() {
		Type type = this.getClass().getGenericSuperclass();
		if (!(type instanceof ParameterizedType))
			return;
		ParameterizedType pType = (ParameterizedType) type;
		try {
			@SuppressWarnings("unchecked")
			Class<E> clazz = (Class<E>) pType.getActualTypeArguments()[0];
			dao = SpringBeanFactoryUtil.getBean(clazz);
		} catch (BeansException e) {
			logger.error("init class " + this.getClass() + " error.");
		}
	}

//	@Transactional(rollbackFor = Exception.class)
//	public int insert(GsonRow row) throws Exception {
//		return dao.insert(row);
//	}
//
//	@Transactional(rollbackFor = Exception.class)
//	public int update(GsonRow row) throws Exception {
//		return dao.update(row);
//	}

//	@Transactional(rollbackFor = Exception.class)
//	public int applyUpdates(ParserDataSetJson p) throws Exception {
//		return dao.applyUpdates(p);
//	}
//
//	@Transactional(rollbackFor = Exception.class)
//	public int applyUpdates(ParserDataSetJson p, String id) throws Exception {
//		return dao.applyUpdates(p, id);
//	}

	@Transactional(rollbackFor = Exception.class)
	public int delete(Long[] ids) throws Exception {
		return dao.delete(ids);
	}

//	@Transactional(rollbackFor = Exception.class)
//	public int insertJsonOneRow(String sJson) throws Exception {
//		return dao.insertJsonOneRow(sJson);
//	}

//	@Transactional(rollbackFor = Exception.class)
//	public int insertGsonHeadOneRow(GsonRow g) throws Exception {
//		return dao.insert(g);
//	}

//	@Transactional(rollbackFor = Exception.class)
//	public int delete(String[] head, List<Object[]> list)
//			throws Exception {
//		return dao.delete(head, list);
//	}

//	@Transactional(rollbackFor = Exception.class)
//	public int updateJsonHeadOneRow(GsonHeadOneRow g, String tableName)
//			throws Exception {
//		return dao.updateJsonHeadOneRow(g, tableName);
//	}
//
//	@Transactional(rollbackFor = Exception.class)
//	public int updateJsonOneRow(GsonHeadOneRow g, Long id) throws Exception {
//		return dao.updateJsonOneRow(g, id);
//	}

//	@Transactional(rollbackFor = Exception.class)
//	public int deleteListPk(String sJson) throws Exception {
//		return dao.deleteListPk(sJson);
//	}
//
//	// BasicDao 中的方法
//	@Transactional(rollbackFor = Exception.class)
//	public int insertList(String sJson) throws Exception {
//		return dao.insertList(sJson);
//	}
//
//	// BasicDao 中的方法
//	@Transactional(rollbackFor = Exception.class)
//	public int deleteList(String sJson) throws Exception {
//		return dao.deleteList(sJson);
//	}

	// BasicDao 中的方法
//	@Transactional(rollbackFor = Exception.class)
//	public int insertGsonHeadOneRow(GsonHeadOneRow g, Object id)
//			throws Exception {
//		return dao.insertGsonHeadOneRow(g, id);
//	}
//
//	// BasicDao 中的方法
//	@Transactional(rollbackFor = Exception.class)
//	public int updateJsonOneRow(GsonHeadOneRow g) throws Exception {
//		return dao.updateJsonOneRow(g);
//	}

}
