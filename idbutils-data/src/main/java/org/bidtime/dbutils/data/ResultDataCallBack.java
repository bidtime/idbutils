package org.bidtime.dbutils.data;

/**
 * @author libing on 2016/2/25.
 */
public interface ResultDataCallBack<R> {
    /**
     * 组装数据
     *
     * @param r
     */
    void callback(R r);
}
