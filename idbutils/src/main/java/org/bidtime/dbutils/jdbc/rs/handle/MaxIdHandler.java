package org.bidtime.dbutils.jdbc.rs.handle;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bidtime.dbutils.jdbc.rs.handle.ext.ResultSetExHandler;

/**
 * @author jss
 * 
 *         提供对从ResultSet进行预处理的功能,继承自dbutils的ResultSetHandler类
 *
 */
@SuppressWarnings("serial")
public class MaxIdHandler<T> extends ResultSetExHandler<T> {

	public MaxIdHandler(Class<T> type) {
		super.setProp(type);
	}

  @SuppressWarnings({ "unchecked" })
  @Override
  public T handle(ResultSet rs) throws SQLException {
	  if (rs.next()) {
          //刚插入数据的自增ID
          if (type.isAssignableFrom(Long.class)) {
            return (T)(Long.valueOf(rs.getLong(1)));
          } else if (type.isAssignableFrom(BigDecimal.class)) {
              return (T)rs.getBigDecimal(1);
          } else if (type.isAssignableFrom(Short.class)) {
              return (T)(Short.valueOf(rs.getShort(1)));
          } else {
            return (T)(Integer.valueOf(rs.getInt(1)));
          }
      } else {
    	  return null;
      }
	  //return rs.next() ? (T) this.convert.toBean(rs, this.type, mapBeanPropColumns) : null;
  }

}
