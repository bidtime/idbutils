package org.bidtime.dbutils.data;

import java.io.Serializable;
import java.util.Collection;

@SuppressWarnings("serial")
public class ResultDTO<T> implements Serializable {
	
  //数据状态: sucess, state
  private static final short SUCCESS = 0;		//成功
  private static final short ERROR = 1;			//通用错误

    /**
     * 数据，真正的结果对象
     */
    private T data;

    public T getData() {
        return data;
    }

    @SuppressWarnings({"rawtypes"})
    public void setData(T data) {
        if (data != null) {
            if (data instanceof Collection) {
                this.len = ((Collection) data).size();
            }
        }
        this.data = data;
    }

    public boolean isSuccess() {
        return state == 0 ? true : false;
    }

    /**
     * 状态
     */
    private int state = 0;

    private long len = 0;

    public long getLen() {
        return len;
    }

    public void setLen(long len) {
        this.len = len;
    }

    /**
     * 消息
     */
    private String msg;

    public int getState() {
        return state;
    }

    public void setState(int n) {
        this.state = n;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSuccess(boolean b) {
        setState(b ? 0 : 1);
    }
    
    public void setError(String msg) {
    	setSuccess(false);
    	setMsg(msg);
    }

    public ResultDTO() {
    }

    public ResultDTO(T t) {
      this.setData(t);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <K> ResultDTO success(K data) {
        ResultDTO r = new ResultDTO();
        r.setData(data);
        return r;
    }

    @SuppressWarnings({"rawtypes"})
    public static ResultDTO success() {
        ResultDTO r = new ResultDTO();
        return r;
    }

    @SuppressWarnings({"rawtypes"})
    public static ResultDTO error(int state, String msg) {
        ResultDTO r = new ResultDTO();
        r.setState(state);
        r.setMsg(msg);
        return r;
    }

    @SuppressWarnings({"rawtypes"})
    public static ResultDTO error() {
        ResultDTO r = new ResultDTO();
        r.setSuccess(false);
        return r;
    }

    @SuppressWarnings({"rawtypes"})
    public static ResultDTO error(String msg) {
        ResultDTO r = new ResultDTO();
        r.setSuccess(false);
        r.setMsg(msg);
        return r;
    }

    @SuppressWarnings("rawtypes")
    public static ResultDTO apply(int applies, String msg) {
        short state = (applies > 0) ? SUCCESS : ERROR;
        if (state == SUCCESS) {
            return ResultDTO.success();
        } else {
            return ResultDTO.error(msg);
        }
    }

    @SuppressWarnings("rawtypes")
    public static ResultDTO apply(int applies) {
        short state = (applies > 0) ? SUCCESS : ERROR;
        if (state == SUCCESS) {
            return ResultDTO.success();
        } else {
            return ResultDTO.error();
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <K> ResultDTO error(String msg, K data) {
        ResultDTO r = new ResultDTO();
        r.setSuccess(false);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public ResultDTO(int state, String msg, T data) {
        setState(state);
        setMsg(msg);
        setData(data);
    }

    public ResultDTO(int state, String msg) {
        setState(state);
        setMsg(msg);
    }

    //组装数据用回调接口
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void doResult(ResultDTO<?> dto, ResultDataCallBack cb) throws Exception {
    	if (dto != null) {
    		dto.resultData(cb);
    	}
    }
	    
    //组装数据用回调接口
	public void resultData(ResultDataCallBack<T> cb) throws Exception {
        if (this.isSuccess() && cb != null) {
            T o = this.getData();
            if (o != null) {
              if (o instanceof Collection) {
                @SuppressWarnings({ "unchecked" })
                Collection<T> c = (Collection<T>) o;
                if ( !c.isEmpty() ) {
                  for (T t : c) {
                    cb.callback(t);
                  }
                }
              } else {
                cb.callback((T) o);
              }
            }
          }
    }
    
}
