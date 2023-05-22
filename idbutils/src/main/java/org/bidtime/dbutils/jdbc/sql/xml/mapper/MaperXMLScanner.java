package org.bidtime.dbutils.jdbc.sql.xml.mapper;

import static org.springframework.util.Assert.notNull;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.bidtime.dbutils.jdbc.sql.xml.JsonFieldXmlsLoader;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

public class MaperXMLScanner
		implements BeanDefinitionRegistryPostProcessor, InitializingBean, BeanNameAware {

	private String basePackage;

	private JsonFieldXmlsLoader jsonFieldXmlsLoader;
	
	private String jsonFieldXmlsLoaderName;

	public String getJsonFieldXmlsLoaderName() {
		return jsonFieldXmlsLoaderName;
	}

	public void setJsonFieldXmlsLoaderName(String jsonFieldXmlsLoaderName) {
		this.jsonFieldXmlsLoaderName = jsonFieldXmlsLoaderName;
	}

	public JsonFieldXmlsLoader getJsonFieldXmlsLoader() {
		return jsonFieldXmlsLoader;
	}

	public void setJsonFieldXmlsLoader(JsonFieldXmlsLoader jsonFieldXmlsLoader) {
		this.jsonFieldXmlsLoader = jsonFieldXmlsLoader;
	}

	private Class<? extends Annotation> annotationClass;

	private Class<?> markerInterface;

	private ApplicationContext applicationContext;

	private String beanName;

	private boolean processPropertyPlaceHolders;

	private BeanNameGenerator nameGenerator;

	/**
	 * This property lets you set the base package for your mapper interface
	 * files.
	 * <p>
	 * You can set more than one package by using a semicolon or comma as a
	 * separator.
	 * <p>
	 * Mappers will be searched for recursively starting in the specified
	 * package(s).
	 *
	 * @param basePackage
	 *            base package name
	 */
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	/**
	 * This property specifies the annotation that the scanner will search for.
	 * <p>
	 * The scanner will register all interfaces in the base package that also
	 * have the specified annotation.
	 * <p>
	 * Note this can be combined with markerInterface.
	 *
	 * @param annotationClass
	 *            annotation class
	 */
	public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
		this.annotationClass = annotationClass;
	}

	/**
	 * This property specifies the parent that the scanner will search for.
	 * <p>
	 * The scanner will register all interfaces in the base package that also
	 * have the specified interface class as a parent.
	 * <p>
	 * Note this can be combined with annotationClass.
	 *
	 * @param superClass
	 *            parent class
	 */
	public void setMarkerInterface(Class<?> superClass) {
		this.markerInterface = superClass;
	}

	/**
	 *
	 * @since 1.1.1
	 *
	 * @param processPropertyPlaceHolders
	 */
	public void setProcessPropertyPlaceHolders(boolean processPropertyPlaceHolders) {
		this.processPropertyPlaceHolders = processPropertyPlaceHolders;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;		
	}
	
	/**
	 * Gets beanNameGenerator to be used while running the scanner.
	 *
	 * @return the beanNameGenerator BeanNameGenerator that has been configured
	 * @since 1.2.0
	 */
	public BeanNameGenerator getNameGenerator() {
		return nameGenerator;
	}

	/**
	 * Sets beanNameGenerator to be used while running the scanner.
	 *
	 * @param nameGenerator
	 *            the beanNameGenerator to set
	 * @since 1.2.0
	 */
	public void setNameGenerator(BeanNameGenerator nameGenerator) {
		this.nameGenerator = nameGenerator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		notNull(this.basePackage, "Property 'basePackage' is required");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
		// left intentionally blank
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @since 1.0.2
	 */
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
		if (this.processPropertyPlaceHolders) {
			processPropertyPlaceHolders();
		}

		ClassPathMapperXMLScanner scanner = new ClassPathMapperXMLScanner(registry);
		//scanner.setAddToConfig(this.addToConfig);
		scanner.setAnnotationClass(this.annotationClass);
		scanner.setMarkerInterface(this.markerInterface);
		//scanner.setSqlSessionFactory(this.sqlSessionFactory);
		//scanner.setSqlSessionTemplate(this.sqlSessionTemplate);
		//scanner.setSqlSessionFactoryBeanName(this.sqlSessionFactoryBeanName);
		//scanner.setSqlSessionTemplateBeanName(this.sqlSessionTemplateBeanName);
		scanner.setJsonFieldXmlsLoader(this.jsonFieldXmlsLoader);
		scanner.setJsonFieldXmlsLoaderName(this.jsonFieldXmlsLoaderName);
		scanner.setResourceLoader(this.applicationContext);
		scanner.setBeanNameGenerator(this.nameGenerator);
		scanner.registerFilters();
		scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage,
				ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
	}

	/*
	 * BeanDefinitionRegistries are called early in application startup, before
	 * BeanFactoryPostProcessors. This means that PropertyResourceConfigurers
	 * will not have been loaded and any property substitution of this class'
	 * properties will fail. To avoid this, find any PropertyResourceConfigurers
	 * defined in the context and run them on this class' bean definition. Then
	 * update the values.
	 */
	private void processPropertyPlaceHolders() {
		Map<String, PropertyResourceConfigurer> prcs = applicationContext
				.getBeansOfType(PropertyResourceConfigurer.class);

		if (!prcs.isEmpty() && applicationContext instanceof ConfigurableApplicationContext) {
			BeanDefinition mapperScannerBean = ((ConfigurableApplicationContext) applicationContext).getBeanFactory()
					.getBeanDefinition(beanName);

			// PropertyResourceConfigurer does not expose any methods to
			// explicitly perform
			// property placeholder substitution. Instead, create a BeanFactory
			// that just
			// contains this mapper scanner and post process the factory.
			DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
			factory.registerBeanDefinition(beanName, mapperScannerBean);

			for (PropertyResourceConfigurer prc : prcs.values()) {
				prc.postProcessBeanFactory(factory);
			}

			PropertyValues values = mapperScannerBean.getPropertyValues();

			this.basePackage = updatePropertyValue("basePackage", values);
			this.jsonFieldXmlsLoaderName = updatePropertyValue("jsonFieldXmlsLoaderName", values);
			//this.sqlSessionFactoryBeanName = updatePropertyValue("sqlSessionFactoryBeanName", values);
			//this.sqlSessionTemplateBeanName = updatePropertyValue("sqlSessionTemplateBeanName", values);
		}
	}

	private String updatePropertyValue(String propertyName, PropertyValues values) {
		PropertyValue property = values.getPropertyValue(propertyName);

		if (property == null) {
			return null;
		}

		Object value = property.getValue();

		if (value == null) {
			return null;
		} else if (value instanceof String) {
			return value.toString();
		} else if (value instanceof TypedStringValue) {
			return ((TypedStringValue) value).getValue();
		} else {
			return null;
		}
	}

}
