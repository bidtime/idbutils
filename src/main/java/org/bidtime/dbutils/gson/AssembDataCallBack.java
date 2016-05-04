package org.bidtime.dbutils.gson;

/**
 * @author libing on 2016/2/25.
 */
public interface AssembDataCallBack<R> {
    /**
     * 组装数据
     *
     * @param r
     */
    void assemb(R r) throws Exception;
}
