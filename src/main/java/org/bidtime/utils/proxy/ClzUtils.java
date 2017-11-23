package org.bidtime.utils.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.bidtime.utils.proxy.OperDataParam;
import org.bidtime.utils.proxy.OperExistsParam;

public class ClzUtils {

	public static final String INSERT_DATA_ERROR = "增加数据错误";

	public static final String UPDATE_DATA_ERROR = "更新数据错误";

	public static final String DELETE_DATA_ERROR = "删除数据错误";

	// public static final String CODE_EXIST_ERROR = "名称已经存在";

	public static final String CODE_NAME_EXIST_ERROR = "名称或编码已经存在";

	// public static final String NAME_EXIST_ERROR = "编码已经存在";

	@SuppressWarnings("unchecked")
	public static <T, R> String getClzAnnotation(Class<R> clzz, Class<T> t, IObjectCallBack<T> cb) {
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		String msg = null;
		Method method;
		for (StackTraceElement stack : stacks) {
			try {
				String clzName = stack.getClassName();
				Class<?> clz = Class.forName(clzName);
				boolean mustdo = false;
				if (clzz == null) {
					mustdo = true;
				} else {
					if (clz.equals(clzz)) {
						mustdo = true;
					}
				}
				if (mustdo) {
					method = clz.getMethod(stack.getMethodName());
					T anno = (T) method.getAnnotation((Class<Annotation>) t);
					if (anno != null) {
						if (cb != null) {
							return cb.callback(anno);
						}
						break;
					}
				}
			} catch (Exception e) {
				// log.error("getClzAnnotation error, {}", e.getMessage());
			}
		}
		return msg;
	}

	public static <T> String getClzAnnotation(Class<T> t, IObjectCallBack<T> cb) {
		return getClzAnnotation(null, t, cb);
	}

	private interface IObjectCallBack<R> {
		String callback(R r) throws Exception;
	}

	public static String getOperExistsParamValue() {
		return getOperExistsParamValue(null);
	}

	public static <R> String getOperExistsParamValue(Class<R> clz) {
		return getClzAnnotation(clz, OperExistsParam.class, new IObjectCallBack<OperExistsParam>() {
			@Override
			public String callback(OperExistsParam r) throws Exception {
				return r.value();
			}
		});
	}

	public static String getOperDataParamValue() {
		return getOperDataParamValue(null);
	}

	public static <R> String getOperDataParamValue(Class<R> clz) {
		return getClzAnnotation(clz, OperDataParam.class, new IObjectCallBack<OperDataParam>() {
			@Override
			public String callback(OperDataParam r) throws Exception {
				return r.value();
			}
		});
	}

	public static <R> String getOperExistsParamDef(Class<R> clz, String defValue) {
		String msg = getOperExistsParamValue(clz);
		return (msg) != null ? msg : defValue;
	}

	public static <R> String getOperExistsParamDef(String defValue) {
		String msg = getOperExistsParamValue();
		return (msg) != null ? msg : defValue;
	}

	public static <R> String getOperDataParamDef(Class<R> clz, String defValue) {
		String msg = getOperDataParamValue(clz);
		return (msg) != null ? msg : defValue;
	}

	public static <R> String getOperDataParamDef(String defValue) {
		String msg = getOperDataParamValue();
		return (msg) != null ? msg : defValue;
	}

}
