package org.bidtime.dbutils.utils.comm;

public interface IObjectCallBack<R> {
  
  /**
   * 组装数据
   *
   * @param r
   */
  void callback(R r);
  
}
