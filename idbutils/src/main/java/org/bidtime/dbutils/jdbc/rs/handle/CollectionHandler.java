package org.bidtime.dbutils.jdbc.rs.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author jss
 * 
 *         提供对从ResultSet进行预处理的功能,继承ResultSetDTOHandler类
 *
 */
@SuppressWarnings("serial")
public class CollectionHandler<T> extends AbstractListHandler<T> {
  
  /**
   * The column number to retrieve.
   */
  private int columnIndex;

  /**
   * The column name to retrieve.  Either columnName or columnIndex
   * will be used but never both.
   */
  //private String columnName;

  public CollectionHandler(Class<T> type) {
    this(type, 1);
  }

  public CollectionHandler(Class<T> type, int col) {
    super.setProp(type);
    this.columnIndex = col;
  }

	/**
	 * Row handler. Method converts current row into some Java object.
	 * 
	 * @param rs
	 *            <code>ResultSet</code> to process.
	 * @return row processing result
	 * @throws SQLException
	 *             error occurs
	 */
  @SuppressWarnings("unchecked")
  @Override
	public void handleRow(ResultSet rs, Collection<T> c) throws SQLException {
    do {
      c.add((T)rs.getObject(columnIndex));
    } while (rs.next());
  }

}
