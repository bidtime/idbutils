package org.bidtime.dbutils.jdbc.sql.xml.binding;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.sql.DataSource;

import org.apache.commons.dbutils.ResultSetHandler;
import org.bidtime.dbutils.jdbc.connection.SqlLoadUtils;
import org.bidtime.dbutils.jdbc.sql.xml.JsonFieldXmlsLoader;
import org.bidtime.dbutils.jdbc.sql.xml.parser.TTableProps;

public class MethodProxy<T> implements InvocationHandler {
	
	private JsonFieldXmlsLoader jsonFieldXmlsLoader;

	protected Class<T> methodInterface;

	public MethodProxy(Class<T> methodInterface) {
		this.methodInterface = methodInterface;
	}

	public MethodProxy(JsonFieldXmlsLoader jsonFieldXmlsLoader, Class<T> methodInterface) {
		this.methodInterface = methodInterface;
		this.jsonFieldXmlsLoader = jsonFieldXmlsLoader;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("=========================");
		System.out.println("类名:" + methodInterface);
		System.out.println("方法名:" + method.getName());
		System.out.println("参数:" + args);
		System.out.println("注解:" + method.getAnnotations());
		try {
		    try {
		        if (Object.class.equals(method.getDeclaringClass())) {
		          return method.invoke(this, args);
		        } else if (isDefaultMethod(method)) {
		          return invokeDefaultMethod(proxy, method, args);
		        }
		      } catch (Throwable t) {
		        throw new Exception(t);
		      }

			// 针对不同的方法进行不同的操作
			//return invokeMethod(MethodInterfaceImpl.class, method.getName(), args);
			//getCurrentDataSource(), this.getClass(), sqlId, rsh, params
		    TTableProps tp = jsonFieldXmlsLoader.getTableProp(methodInterface);
		    if (tp == null) {
		    	throw new Exception("invoke:" + proxy.getClass() + "no xml file");
		    }
		    return tp.execute(jsonFieldXmlsLoader.getDataSource(), method.getName(), args);
			//return invokeMethodDS(method.getName(), args);
		} catch (InvocationTargetException e) {
            throw e.getCause();
        }
	}

//	public Object invokeMethodDS(String sqlId, Object[] args_old) throws Exception {
//		Object[] args = new Object[args_old.length + 3];
//		Class<?>[] args_class = new Class[args.length];
//		//
//		args[0] = (DataSource)JsonFieldXmlsLoader.getInstance().getDataSource();
//		args[1] = methodInterface;
//		args[2] = sqlId;
//		for (int i=0; i<args_old.length; i++) {
//			Object o = args_old[i];
//			args[i+3] = o;
//			//args_class[i+3] = getIt(o);
//		}
//		args_class[0] = DataSource.class;
//		args_class[1] = methodInterface.getClass();
//		args_class[2] = String.class;
//		args_class[3] = ResultSetHandler.class;
//		args_class[4] = Map.class;
//		
//		Method method = SqlLoadUtils.class.getDeclaredMethod("query", args_class);
//		method.setAccessible(true);
//		return method.invoke(null, args);
//	}
	
//	private Object[] mergeArgs(String sqlId, Object[] args_old) throws Exception {
//		if (args_old == null) {
//			Object[] args = new Object[2];
//			args[0] = (DataSource)jsonFieldXmlsLoader.getDataSource();
//			args[1] = methodInterface;
//			return args;
//		} else {
//			Object[] args = new Object[args_old.length + 2];
//			args[0] = (DataSource)jsonFieldXmlsLoader.getDataSource();
//			args[1] = methodInterface;
//			for (int i= 0; i<args_old.length; i++) {
//				args[i+2] = args_old[i];
//			}
//			return args;			
//		}
//	}

	public Object invokeMethodDS(String sqlId, Object[] args_old) throws Exception {
		Object[] args = new Object[args_old.length + 3];
		args[0] = (DataSource)jsonFieldXmlsLoader.getDataSource();
		args[1] = methodInterface;
		args[2] = sqlId;
		for (int i= 0; i<args_old.length; i++) {
			args[i+3] = args_old[i];
		}
		//return invokeStatic(SqlLoadUtils.class, "query", args);
		return invokeMethod(SqlLoadUtils.class, "query", args);
	}
	
	private static Class<?> getIt(Object o) {
		if (o instanceof Class) {
			return o.getClass();
		} else if (o instanceof ResultSetHandler) {
			return ResultSetHandler.class;
		} else if (o instanceof DataSource) {
			return DataSource.class;
		} else if (o instanceof String) {
			return String.class;
		} else {
			Class<?>[] cs = o.getClass().getInterfaces();
			if (cs != null && cs.length > 0) {
              return cs[0];
			} else {
              return o.getClass();
            }
		}
	}
	
	@SuppressWarnings("deprecation")
	public static <R> Object invokeMethod(Class<R> clz, String methodName, Object[] args) throws Exception {
		Object owner = clz.newInstance();
		Class<?>[] args_class = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			args_class[i] = getIt(args[i]);
		}
		Method method = clz.getDeclaredMethod(methodName, args_class);
		return method.invoke(owner, args);
	}
	
	public <R> Object invokeStatic(Class<R> clz, String methodName, Object[] args) throws Exception {
		Class<?>[] args_class = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			args_class[i] = getIt(args[i]);
		}
		Method method = clz.getDeclaredMethod(methodName, args_class);
		method.setAccessible(true);
		return method.invoke(null, args);
	}

  @SuppressWarnings("deprecation")
  private Object invokeDefaultMethod(Object proxy, Method method, Object[] args)
      throws Throwable {
		final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
		    .getDeclaredConstructor(Class.class, int.class);
		if (!constructor.isAccessible()) {
		  constructor.setAccessible(true);
		}
		final Class<?> declaringClass = method.getDeclaringClass();
		return constructor
		    .newInstance(declaringClass,
		        MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED
		            | MethodHandles.Lookup.PACKAGE | MethodHandles.Lookup.PUBLIC)
		    .unreflectSpecial(method, declaringClass).bindTo(proxy).invokeWithArguments(args);
  }

  /**
   * Backport of java.lang.reflect.Method#isDefault()
   */
  private boolean isDefaultMethod(Method method) {
    return ((method.getModifiers()
        & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC)) == Modifier.PUBLIC)
        && method.getDeclaringClass().isInterface();
  }
  
}
