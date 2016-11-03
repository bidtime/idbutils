/*
 * 解析spring配置xml文件中所有的datasource bean id
 * 以方便connection访问
 */
package org.bidtime.dbutils.jdbc.connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ParserSpringDsXml {

	private String xmlFile;

	public String getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// try {
//		// List<String> list = ParserSpringDsXml.parserDsBeans(
//		// ParserSpringDsXml.class, "WEB-INF/spring-config.xml",
//		// "com.mchange.v2.c3p0.ComboPooledDataSource");
//		// System.out.println(list.size());
//		// } catch (DocumentException e) {
//		// }
//	}

	public List<String> parserDsBeansOfPath(String filePath)
			throws DocumentException {
		// String sCompareClassName =
		// "com.mchange.v2.c3p0.ComboPooledDataSource";
		// String sCompareClassName =
		// "com.mchange.v2.c3p0.ComboPooledDataSource";
		return parserDsBeansOfFile(filePath + xmlFile);
	}

	public List<String> parserDsBeansOfFile(String filePath)
			throws DocumentException {
		List<String> list = new ArrayList<String>();
		// InputStream is = cls.getResourceAsStream(path);
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
		} catch (FileNotFoundException e1) {
		}
		try {
			list = parserDsBeansOfStream(is);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<String> parserDsBeansOfStream(InputStream is)
			throws DocumentException {
		List<String> list = new ArrayList<String>();
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(is);
		Element root = document.getRootElement();
		for (Iterator iter = root.elementIterator(); iter.hasNext();) {
			Element element = (Element) iter.next();
			//System.out.println("name:"+element.getName());
			// class
			if (element.getName().trim().equalsIgnoreCase("bean")) {
				Attribute attrId = element.attribute("id");
				if (attrId == null || attrId.getValue() == null
						|| attrId.getValue().trim().equalsIgnoreCase("")) {
					throw new DocumentException("id is null");
				}
				Attribute attrClassName = element.attribute("class");
				if (attrClassName == null || attrClassName.getValue() == null
						|| attrClassName.getValue().trim().equalsIgnoreCase("")) {
					throw new DocumentException("class name is null");
				}
				//System.out.println(attrClassName.getName() + ":"
				//		+ attrClassName.getValue());
				if (element.elementIterator()!=null) {
					boolean bHappenDriverClass=false;
					boolean bHappenJdbcUrl=false;
					for (Iterator iterProperty = element.elementIterator(); iterProperty.hasNext();) {
						Element elementBeanChild = (Element) iterProperty.next();
						//System.out.println("childBean:"+elementBeanChild.getName());
						Attribute attrBeanChildName = elementBeanChild.attribute("name");
						if (attrBeanChildName!=null) {
							//System.out.println(attrBeanChildName.getName()+":"+attrBeanChildName.getValue());
							if (attrBeanChildName.getValue().trim().equalsIgnoreCase("driverClass")) {
								bHappenDriverClass = true;
							} else if (attrBeanChildName.getValue().trim().equalsIgnoreCase("jdbcUrl")) {
								bHappenJdbcUrl = true;
							}
						}
						if (bHappenDriverClass && bHappenJdbcUrl) {
							list.add(attrId.getText());
							break;
						}
					}
				}
			}
		}
		return list;
	}

}
