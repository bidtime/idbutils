package org.bidtime.dbutils.jdbc.sql.xml.binding;

import java.util.HashMap;
import java.util.Map;

import org.bidtime.dbutils.jdbc.sql.xml.JsonFieldXmlsLoader;

public class MethodRegistry {

	private final Map<Class<?>, MethodProxyFactory<?>> knownMappers = new HashMap<>();

//	public <T> void addMapper(Class<T> type) {
//		knownMappers.put(type, new MethodProxy<T>(type));
//	}

	@SuppressWarnings("unchecked")
	public <T> T getMapper(Class<T> type, JsonFieldXmlsLoader jsonFieldXmlsLoader) throws Exception {
		final MethodProxyFactory<T> mapperProxyFactory = (MethodProxyFactory<T>) knownMappers.get(type);
		if (mapperProxyFactory == null) {
			throw new Exception("Type " + type + " is not known to the MapperRegistry.");
		}
		try {
			return mapperProxyFactory.newInstance(jsonFieldXmlsLoader);
		} catch (Exception e) {
			throw new Exception("Error getting mapper instance. Cause: " + e, e);
		}
	}
	
	public <T> boolean hasMapper(Class<T> type) {
	    return knownMappers.containsKey(type);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> boolean hasMapper(String clzName) throws Exception {
		Class clz = (Class<T>)Class.forName(clzName);
		return hasMapper(clz);
	}
	
//	@SuppressWarnings({ "rawtypes" })
//	public boolean isMapper(String clzName) throws Exception {
//		Class clz = Class.forName(clzName);
//		return clz == null ? false : true;
//	}
	
	@SuppressWarnings({ "rawtypes" })
	public Class getMapperByName(String clzName) throws Exception {
		Class clz = Class.forName(clzName);
		return clz;
	}

	public <T> void addMapper(Class<T> type) throws Exception {
		if (type.isInterface()) {
			if (hasMapper(type)) {
				throw new Exception("Type " + type + " is already known to the MapperRegistry.");
			}
			boolean loadCompleted = false;
			try {
				knownMappers.put(type, new MethodProxyFactory<T>(type));
				// It's important that the type is added before the parser is
				// run
				// otherwise the binding may automatically be attempted by the
				// mapper parser. If the type is already known, it won't try.
				//MapperAnnotationBuilder parser = new MapperAnnotationBuilder(config, type);
				//parser.parse();
				loadCompleted = true;
			} finally {
				if (!loadCompleted) {
					knownMappers.remove(type);
				}
			}
		}
	}
  
//	public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
//	  return mapperRegistry.getMapper(type, sqlSession);
//    }

}
