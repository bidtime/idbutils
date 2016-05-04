package org.bidtime.dbutils.gson;

/**
 * @author libing on 2016/2/25.
 */
@Deprecated
public interface AssemblyResultDataCallBack<R> {
    /**
     * 组装数据
     *
     * @param r
     */
    void assembly(R r) throws Exception;
}
