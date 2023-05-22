package org.bidtime.dbutils.jdbc.rs.handle.ext;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bidtime.dbutils.data.ResultDTO;
import org.bidtime.dbutils.jdbc.rs.BeanProcessorEx;

/**
 * @author jss
 * 
 *         提供对从ResultSet进行预处理的功能,继承自dbutils的ResultSetHandler类
 *
 */
@SuppressWarnings("serial")
public class ResultSetDTOHandler<T> extends ResultSetExHandler<ResultDTO<T>> {

	protected boolean countSql = false;

	@Override
	public ResultDTO<T> handle(ResultSet rs) throws SQLException {
		return new ResultDTO<T>(convertToBean(rs));
	}

	@SuppressWarnings("unchecked")
	protected T convertToBean(ResultSet rs) throws SQLException {
		return rs.next() ? (T) this.convert.toBean(rs, this.type, mapBeanPropColumns) : null;
	}

//  @SuppressWarnings("unchecked")
//  public List<T> toList(ResultSet rs) throws SQLException {
//    List<T> list = new ArrayList<T>();
//    Object[] ar = rs.next() ? row_convert.toArray(rs) : null;
//    if (ar != null) {
//      for (Object o : ar) {
//        list.add((T)o);
//      }
//    }
//    return list;
//  }

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void setProp(Class type) {
		this.setProp(type, false);
	}

	@SuppressWarnings({ "rawtypes" })
	public void setProp(Class type, boolean countSql) {
		super.setProp(type);
		this.countSql = countSql;
	}

	@SuppressWarnings({ "rawtypes" })
	public void setProp(Class type, BeanProcessorEx convert, boolean countSql) {
		super.setProp(type, convert);
		this.countSql = countSql;
	}

//	public void setMapBeanConvert(Map<String, String> mapConvert) {
//		this.mapBeanPropColumns = mapConvert;
//	}

	public boolean isCountSql() {
		return countSql;
	}

	public void setCountSql(boolean countSql) {
		this.countSql = countSql;
	}

}
