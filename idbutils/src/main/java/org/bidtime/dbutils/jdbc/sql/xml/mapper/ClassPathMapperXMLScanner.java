package org.bidtime.dbutils.jdbc.sql.xml.mapper;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

import org.bidtime.dbutils.jdbc.sql.xml.JsonFieldXmlsLoader;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.StringUtils;

public class ClassPathMapperXMLScanner extends ClassPathBeanDefinitionScanner {

	private JsonFieldXmlsLoader jsonFieldXmlsLoader;
	
	private String jsonFieldXmlsLoaderName;

	public String getJsonFieldXmlsLoaderName() {
		return jsonFieldXmlsLoaderName;
	}

	public void setJsonFieldXmlsLoaderName(String jsonFieldXmlsLoaderName) {
		this.jsonFieldXmlsLoaderName = jsonFieldXmlsLoaderName;
	}

	private Class<? extends Annotation> annotationClass;

	private Class<?> markerInterface;

	private MapperFactoryBean<?> mapperFactoryBean = new MapperFactoryBean<Object>();

	public ClassPathMapperXMLScanner(BeanDefinitionRegistry registry) {
		super(registry, false);
	}

	// public void setAddToConfig(boolean addToConfig) {
	// this.addToConfig = addToConfig;
	// }

	public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
		this.annotationClass = annotationClass;
	}

	public void setMarkerInterface(Class<?> markerInterface) {
		this.markerInterface = markerInterface;
	}

	public void setSqlSessionFactory(JsonFieldXmlsLoader jsonFieldXmlsLoader) {
		this.jsonFieldXmlsLoader = jsonFieldXmlsLoader;
		mapperFactoryBean.setJsonFieldXmlsLoader(jsonFieldXmlsLoader);
	}

	public void setMapperFactoryBean(MapperFactoryBean<?> mapperFactoryBean) {
		this.mapperFactoryBean = mapperFactoryBean != null ? mapperFactoryBean : new MapperFactoryBean<Object>();
	}

	/**
	 * Configures parent scanner to search for the right interfaces. It can
	 * search for all interfaces or just for those that extends a
	 * markerInterface or/and those annotated with the annotationClass
	 */
	public void registerFilters() {
		boolean acceptAllInterfaces = true;

		// if specified, use the given annotation and / or marker interface
		if (this.annotationClass != null) {
			addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
			acceptAllInterfaces = false;
		}

		// override AssignableTypeFilter to ignore matches on the actual marker
		// interface
		if (this.markerInterface != null) {
			addIncludeFilter(new AssignableTypeFilter(this.markerInterface) {
				@Override
				protected boolean matchClassName(String className) {
					return false;
				}
			});
			acceptAllInterfaces = false;
		}

		if (acceptAllInterfaces) {
			// default include filter that accepts all classes
			addIncludeFilter(new TypeFilter() {
				@Override
				public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
						throws IOException {
					return true;
				}
			});
		}

		// exclude package-info.java
		addExcludeFilter(new TypeFilter() {
			@Override
			public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
					throws IOException {
				String className = metadataReader.getClassMetadata().getClassName();
				return className.endsWith("package-info");
			}
		});
	}

	/**
	 * Calls the parent search that will search and register all the candidates.
	 * Then the registered objects are post processed to set them as
	 * MapperFactoryBeans
	 */
	@Override
	public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

		if (beanDefinitions.isEmpty()) {
			logger.warn("No MyBatis mapper was found in '" + Arrays.toString(basePackages)
					+ "' package. Please check your configuration.");
		} else {
			processBeanDefinitions(beanDefinitions);
		}

		return beanDefinitions;
	}

	private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
		GenericBeanDefinition definition;
		for (BeanDefinitionHolder holder : beanDefinitions) {
			definition = (GenericBeanDefinition) holder.getBeanDefinition();

			if (logger.isDebugEnabled()) {
				logger.debug("Creating MapperFactoryBean with name '" + holder.getBeanName() + "' and '"
						+ definition.getBeanClassName() + "' mapperInterface");
			}

			// the mapper interface is the original class of the bean
			// but, the actual class of the bean is MapperFactoryBean
			definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName()); // issue
																												// #59
			definition.setBeanClass(this.mapperFactoryBean.getClass());

			// definition.getPropertyValues().add("addToConfig",
			// this.addToConfig);

			boolean explicitFactoryUsed = false;
			if (StringUtils.hasText(this.jsonFieldXmlsLoaderName)) {
				definition.getPropertyValues().add("jsonFieldXmlsLoader",
						new RuntimeBeanReference(this.jsonFieldXmlsLoaderName));
				explicitFactoryUsed = true;
			} else if (this.jsonFieldXmlsLoader != null) {
				definition.getPropertyValues().add("jsonFieldXmlsLoader", this.jsonFieldXmlsLoader);
				explicitFactoryUsed = true;
			}
			if (!explicitFactoryUsed) {
				if (logger.isDebugEnabled()) {
					logger.debug("Enabling autowire by type for MapperFactoryBean with name '" + holder.getBeanName()
							+ "'.");
				}
				definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) {
		if (super.checkCandidate(beanName, beanDefinition)) {
			return true;
		} else {
			logger.warn(
					"Skipping MapperFactoryBean with name '" + beanName + "' and '" + beanDefinition.getBeanClassName()
							+ "' mapperInterface" + ". Bean already defined with the same name!");
			return false;
		}
	}

	public JsonFieldXmlsLoader getJsonFieldXmlsLoader() {
		return jsonFieldXmlsLoader;
	}

	public void setJsonFieldXmlsLoader(JsonFieldXmlsLoader jsonFieldXmlsLoader) {
		this.jsonFieldXmlsLoader = jsonFieldXmlsLoader;
	}

}
