package org.bidtime.dbutils.jdbc.sql.xml.binding;

import java.lang.reflect.Proxy;

import org.bidtime.dbutils.jdbc.sql.xml.JsonFieldXmlsLoader;

public class MethodProxyFactory<T> {

//	@SuppressWarnings("unchecked")
//	public static <T> T newInstance(Class<T> methodInterface) {
//
//		final MethodProxy<T> methodProxy = new MethodProxy<T>(methodInterface);
//
//		return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
//				new Class[] { methodInterface }, methodProxy);
//
//	}

	private Class<T> mapperInterface;
	//private final Map<Method, MapperMethod> methodCache = new ConcurrentHashMap<Method, MapperMethod>();

	public MethodProxyFactory(Class<T> mapperInterface) {
	    this.mapperInterface = mapperInterface;
	}

	public Class<T> getMapperInterface() {
		return mapperInterface;
	}

//	public Map<Method, MapperMethod> getMethodCache() {
//		return methodCache;
//	}

	@SuppressWarnings("unchecked")
	protected T newInstance(MethodProxy<T> mapperProxy) {
		return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface },
				mapperProxy);
	}

	public T newInstance(JsonFieldXmlsLoader jsonFieldXmlsLoader) {
		final MethodProxy<T> mapperProxy = new MethodProxy<T>(jsonFieldXmlsLoader, mapperInterface);
		return newInstance(mapperProxy);
	}

}
