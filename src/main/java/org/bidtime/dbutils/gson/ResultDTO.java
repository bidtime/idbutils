package org.bidtime.dbutils.gson;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.bidtime.web.utils.UserHeadState;

@SuppressWarnings("serial")
public class ResultDTO<T> implements Serializable {

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
            //this.setType(data.getClass());
            if (data instanceof List) {
                this.len = ((List) data).size();
            } else if (data instanceof ResultDTO) {
                // ResultDTO resultDTO = (ResultDTO) data;
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

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static ResultDTO success(Object data) {
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
        short state = (applies > 0) ? UserHeadState.SUCCESS : UserHeadState.ERROR;
        if (state == UserHeadState.SUCCESS) {
            return ResultDTO.success();
        } else {
            return ResultDTO.error(msg);
        }
    }

    @SuppressWarnings("rawtypes")
    public static ResultDTO apply(int applies) {
        short state = (applies > 0) ? UserHeadState.SUCCESS : UserHeadState.ERROR;
        if (state == UserHeadState.SUCCESS) {
            return ResultDTO.success();
        } else {
            return ResultDTO.error();
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static ResultDTO error(String msg, Object data) {
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
    @SuppressWarnings({ "rawtypes" })
	public static void doResult(ResultDTO dto, AssembDataCallBack cb) throws Exception {
    	if (dto != null) {
    		dto.assemb(cb);
    	}
    }
    
    //组装数据用回调接口
    @Deprecated
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void assemb(AssembDataCallBack cb) throws Exception {
        if (isSuccess() && cb != null) {
            Object o = this.getData();
            if ( o != null ) {
	            if (o instanceof List) {
	                for (T t : (List<T>) o) {
	                	cb.assemb(t);
	                }
	            } else {
	           		cb.assemb((T)o);
	            }
            }
        }
    }

    //组装数据用回调接口
    @SuppressWarnings({ "rawtypes" })
	public static void doResult(ResultDTO dto, ResultDataCallBack cb) throws SQLException {
    	if (dto != null) {
    		dto.resultData(cb);
    	}
    }
	    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void resultData(ResultDataCallBack cb) throws SQLException {
        if (isSuccess() && cb != null) {
            Object o = this.getData();
            if ( o != null ) {
	            if (o instanceof List) {
	                for (T t : (List<T>) o) {
	                	cb.callback(t);
	                }
	            } else {
	           		cb.callback((T)o);
	            }
            }
        }
    }

}
