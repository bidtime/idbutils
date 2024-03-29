package org.bidtime.test.basic;

import java.sql.SQLException;
import java.util.Date;

import org.bidtime.dbutils.data.dataset.GsonRows;
import org.bidtime.dbutils.utils.basic.DateTimeComm;

/**
 * Created by bidtim on 2015/9/23.
 */
public class GsonUtils {
  
  private static final String FROM_KEY = " from ";
  
  public static void main(String[] args) throws Exception {
    String sql = "select * from ap_duty where duty_id=10";
    String tableName = GsonUtils.getTableName(sql);
    System.out.println(tableName);
  }
  
  public static String getTableName(String sql) throws SQLException {
    String tableName = "";
    int pos = sql.indexOf(FROM_KEY);
    if (pos >= 0) {
      String tab = sql.substring(pos + FROM_KEY.length(), sql.length());
      int pp = tab.indexOf(' ');
      if (pp >=0 ) {
        tableName = tab.substring(0, pp);
      } else {
        tableName = tab;
      }
    }
    return tableName;
  }
  
  public static String toInsertSql(GsonRows rows, String tableName, String insertSql, boolean batch) throws SQLException {
    if (rows == null) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    // insert into table
    sb.append(insertSql);
    sb.append(" ");
    sb.append(tableName);
    sb.append(" ( ");
    // column begin...
    int n = 0;
    for (String head : rows.getHead()) {
      if (n > 0) {
        sb.append(",");
      }
      sb.append(head);
      n ++;
    }
    sb.append(")");
    // column end.    
    if (batch) {
      sb.append(" values ");
      sb.append("\n");
      int j = 0;
      for (Object[] row : rows.getData()) {
        if (j > 0) {
          sb.append(", ");          
        }
        sb.append("(");
        int c = 0;
        for (Object o : row) {
          if (c > 0) {
            sb.append(',');
          }
          sb.append(objectToString(o));
          c ++;
        }
        sb.append(")");
        sb.append("\n");
        j ++;
        break;
      }
    }
    
    return sb.toString();
  }
  
  public static String objectToString(Object o) {
    if (o != null) {
      if (o instanceof String) {
        return "'" + (String) o + "'";
      } else if (o instanceof Date) {
        String dt = DateTimeComm.dateToString((Date)o, "yyyy-MM-dd HH:mm:ss SSS");
        return dt;
      } else {
        return String.valueOf(o);
      }
    } else {
      return null;
    }
  }

}
