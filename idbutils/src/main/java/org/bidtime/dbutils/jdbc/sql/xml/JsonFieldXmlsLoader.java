/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bidtime.dbutils.jdbc.sql.xml;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.bidtime.dbutils.jdbc.sql.xml.binding.MethodRegistry;
import org.bidtime.dbutils.jdbc.sql.xml.parser.TTableProps;

/**
 * <code>JsonFieldXmlsLoader</code> is a registry for sets of queries so that multiple
 * copies of the same queries aren't loaded into memory. This implementation
 * loads properties files filled with query name to SQL mappings. This class is
 * thread safe.
 */
public class JsonFieldXmlsLoader extends TableFieldXmlsParser {

	/**
	 * The Singleton instance of this class.
	 */
	private volatile static JsonFieldXmlsLoader instance = null;
	
	private final MethodRegistry methodRegistry = new MethodRegistry();
	
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Return an instance of this class.
	 * 
	 * @return The Singleton instance.
	 */
	public synchronized static JsonFieldXmlsLoader getInstance() {
//		if (instance == null) {
//			synchronized (JsonFieldXmlsLoader.class) {
//				if (instance == null) {
//					instance = (JsonFieldXmlsLoader)SpringContextUtils.getBean("jsonFieldXmlsLoader");
//				}
//			}
//		}
		return instance;
	}

	@Override
	public void init() {
		super.init();
		instance = this;
	}

	public JsonFieldXmlsLoader(String path) {
		super(path);
	}

	public JsonFieldXmlsLoader() {
		super();
	}
	
	public JsonFieldXmlsLoader(String path, String ext) {
		super(path, ext);
	}
	
	public JsonFieldXmlsLoader(String path, String ext, Boolean recu) {
		super(path, ext, recu);
	}
	
	public static TTableProps getTableProps(Object o) throws SQLException {
		return getInstance().get(o.getClass());
	}
	
	@SuppressWarnings("rawtypes")
	public static TTableProps getTableProps(Class cls) throws SQLException {
		return getInstance().get(cls);
	}
	
	@SuppressWarnings("rawtypes")
	public TTableProps getTableProp(Class cls) throws SQLException {
		return get(cls);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected TTableProps load(String sKey, String path) throws Exception {
		TTableProps tp = super.load(sKey, path);
		Class clz = methodRegistry.getMapperByName(sKey);
		if (clz == null) {
			throw new Exception("class " + sKey + ", has not class");
		}
		if (!clz.isInterface()) {
			throw new Exception("class " + sKey + ", is not interface");
		}
		methodRegistry.addMapper(clz);
		return tp;
	}
	
	@SuppressWarnings("rawtypes")
	public static String getSqlOfId(Class clazz, String id, String colId) throws SQLException {
		TTableProps tp = getTableProps(clazz);
		if (tp != null) {
			String sql = null;
			if (colId == null) {
				sql = tp.getSqlOfId(id);
				if (sql == null) {
					throw new SQLException("clz:" + clazz.getSimpleName()
							+ ",id:" + id + ", is none sql cols.");
				}
			} else {
				sql = tp.getSqlOfId(id, colId);
			}
			if (sql == null) {
				throw new SQLException("clz:" + clazz.getSimpleName()
						+ ",id/colId:" + id + "/" + colId + ", is none sql cols.");
			} else {
				return sql;
			}
		} else {
			throw new SQLException("clz:" + clazz.getSimpleName() + " is none sql xml.");
		}
	}
	  
	public <T> T getMapper(Class<T> type) throws Exception {
	  return methodRegistry.getMapper(type, this);
    }

}
