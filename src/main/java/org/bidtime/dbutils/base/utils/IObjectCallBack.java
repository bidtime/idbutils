package org.bidtime.dbutils.base.utils;

public interface IObjectCallBack<R> {
  
  /**
   * 组装数据
   *
   * @param r
   */
  void callback(R r);
  
}
