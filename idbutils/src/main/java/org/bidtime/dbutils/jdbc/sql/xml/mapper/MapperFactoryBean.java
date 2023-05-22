package org.bidtime.dbutils.jdbc.sql.xml.mapper;

import org.bidtime.dbutils.jdbc.sql.xml.JsonFieldXmlsLoader;
import org.springframework.beans.factory.FactoryBean;

public class MapperFactoryBean<T> implements FactoryBean<T> {
	
	protected JsonFieldXmlsLoader jsonFieldXmlsLoader;

	public JsonFieldXmlsLoader getJsonFieldXmlsLoader() {
		return jsonFieldXmlsLoader;
	}

	public void setJsonFieldXmlsLoader(JsonFieldXmlsLoader jsonFieldXmlsLoader) {
		this.jsonFieldXmlsLoader = jsonFieldXmlsLoader;
	}

	private Class<T> mapperInterface;

	private boolean addToConfig = true;

	public MapperFactoryBean() {
		// intentionally empty
	}

	public MapperFactoryBean(Class<T> mapperInterface) {
		this.mapperInterface = mapperInterface;
	}

	/**
	 * {@inheritDoc}
	 */
//	@Override
//	protected void checkDaoConfig() {
//		super.checkDaoConfig();
//
//		notNull(this.mapperInterface, "Property 'mapperInterface' is required");
//
//		Configuration configuration = getSqlSession().getConfiguration();
//		if (this.addToConfig && !configuration.hasMapper(this.mapperInterface)) {
//			try {
//				configuration.addMapper(this.mapperInterface);
//			} catch (Exception e) {
//				logger.error("Error while adding the mapper '" + this.mapperInterface + "' to configuration.", e);
//				throw new IllegalArgumentException(e);
//			} finally {
//				ErrorContext.instance().reset();
//			}
//		}
//	}

	/**
	 * {@inheritDoc}
	 */
//	@Override
//	public T getObject() throws Exception {
//		return getSqlSession().getMapper(this.mapperInterface);
//	}

	@Override
	public T getObject() throws Exception {
		//return getSqlSession().getMapper(this.mapperInterface);
		return this.jsonFieldXmlsLoader.getMapper(this.mapperInterface);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<T> getObjectType() {
		return this.mapperInterface;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSingleton() {
		return true;
	}

	// ------------- mutators --------------

	/**
	 * Sets the mapper interface of the MyBatis mapper
	 *
	 * @param mapperInterface
	 *            class of the interface
	 */
	public void setMapperInterface(Class<T> mapperInterface) {
		this.mapperInterface = mapperInterface;
	}

	/**
	 * Return the mapper interface of the MyBatis mapper
	 *
	 * @return class of the interface
	 */
	public Class<T> getMapperInterface() {
		return mapperInterface;
	}

	/**
	 * If addToConfig is false the mapper will not be added to MyBatis. This
	 * means it must have been included in mybatis-config.xml.
	 * <p/>
	 * If it is true, the mapper will be added to MyBatis in the case it is not
	 * already registered.
	 * <p/>
	 * By default addToCofig is true.
	 *
	 * @param addToConfig
	 */
	public void setAddToConfig(boolean addToConfig) {
		this.addToConfig = addToConfig;
	}

	/**
	 * Return the flag for addition into MyBatis config.
	 *
	 * @return true if the mapper will be added to MyBatis in the case it is not
	 *         already registered.
	 */
	public boolean isAddToConfig() {
		return addToConfig;
	}
}