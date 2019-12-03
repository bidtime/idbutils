package org.bidtime.dbutils.jdbc.rs.handle.ext;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.ResultSetHandler;
import org.bidtime.dbutils.jdbc.rs.BeanProcessorEx;

/**
 * @author jss
 * 
 *         提供对从ResultSet进行预处理的功能,继承自dbutils的ResultSetHandler类
 *
 */
@SuppressWarnings("serial")
public class ResultSetExHandler<T> implements ResultSetHandler<T>, Serializable {

	protected Map<String, String> mapBeanPropColumns = null;

	@SuppressWarnings("rawtypes")
	protected Class type;

	protected static final BeanProcessorEx default_convert = new BeanProcessorEx();

	protected static final BasicRowProcessor row_convert = new BasicRowProcessor();
	
	protected BeanProcessorEx convert;

  @SuppressWarnings("unchecked")
  @Override
  public T handle(ResultSet rs) throws SQLException {
    return rs.next() ? (T) this.convert.toBean(rs, this.type, mapBeanPropColumns) : null;
  }

	@SuppressWarnings({ "rawtypes" })
	public void setProp(Class type) {
		this.type = type;
		this.convert = default_convert;
	}

	@SuppressWarnings({ "rawtypes" })
	public void setProp(Class type, BeanProcessorEx convert) {
		this.type = type;
		this.convert = convert;
	}

	public void setMapBeanConvert(Map<String, String> mapConvert) {
		this.mapBeanPropColumns = mapConvert;
	}

	//
//	private Type getParams(int idx) {
//		Type genType = getClass().getGenericSuperclass();
//		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
//		return params[idx];
//	}

}
