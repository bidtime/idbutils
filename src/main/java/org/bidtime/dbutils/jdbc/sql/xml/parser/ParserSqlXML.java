package org.bidtime.dbutils.jdbc.sql.xml.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bidtime.dbutils.jdbc.sql.ArrayUtils;
import org.bidtime.dbutils.utils.comm.CaseInsensitiveHashMap;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserSqlXML {

	private static final Logger logger = LoggerFactory
			.getLogger(ParserSqlXML.class);

	public ParserSqlXML() {
	}

//	private static final Pattern REPLACE_PATN = Pattern
//			.compile("\\{[\\sorder by\\s]*\\}");
	
	private static Integer str2int(String s) {
		if (s == null) {
			return null;
		} else {
			return Integer.valueOf(s);
		}
	}

	private static void elementToColumnPro(Element e, ColumnPro p) {
		p.setName(getAttrValue(e, "name"));
		p.setColumn(getAttrValue(e, "column"));
		p.setType(getAttrValue(e, "type"));
		p.setLength(str2int(getAttrValue(e, "length")));
		p.setGenerator(getAttrValue(e, "generator"));
		p.setNotNull( Boolean.valueOf(getAttrValue(e, "not-null")) );
		if (p.getNotNull()) {
			p.setDefaultValue(getAttrValue(e, "default"));
		}
	}

	private static void visitClassElementChild(Element pElement, TTableProps tp) {
		List<String> listPkField = new ArrayList<String>();
		for (Iterator<?> iter = pElement.elementIterator(); iter.hasNext();) {
			Element e = (Element) iter.next();
			if (e.getName().equals("id")) {
				ColumnPro p = new ColumnPro(true);
				elementToColumnPro(e, p);
				listPkField.add(p.getColumn());
				tp.addColsPkPro(p.getName(), p);
			} else if (e.getName().equals("property")) {
				ColumnPro p = new ColumnPro(false);
				elementToColumnPro(e, p);
				tp.addColsCommonPro(p.getName(), p);
			}
		}
		tp.setFieldPK(ArrayUtils.listToStringArray(listPkField));
	}

	private static Map<String, String> getElementToMapConvert(Element pElement) {
		Map<String, String> map = new CaseInsensitiveHashMap<String>();
		for (Iterator<?> iter = pElement.elementIterator(); iter.hasNext();) {
			Element e = (Element) iter.next();
			if (e.getName().equals("id") || e.getName().equals("property")) {
				//p.setType(getAttrValue(e, "type"));
				String key = getAttrValue(e, "column");
				String value = getAttrValue(e, "name");
				map.put(key, value);
			}
		}
		return map;
	}

//	public static String removeSpaces(String s) {
//		StringTokenizer st = new StringTokenizer(s, " ", false);
//		String t = "";
//		while (st.hasMoreElements()) {
//			t += st.nextElement();
//		}
//		return t;
//	}
	
//	private static final Pattern SQL_STANDARD_PATN = Pattern.compile(
//			"^*(insert|delete|update|if exists|select|call)\\s", Pattern.CASE_INSENSITIVE);
//
//	private static boolean isExistsSql(String sql) {
//		Matcher matcher = SQL_STANDARD_PATN.matcher(sql);
//		return matcher.find();
//	}
//	
//	private static final Pattern COMMA_CHAR_PATN = Pattern.compile(
//			",", Pattern.CASE_INSENSITIVE);
//
//	private static boolean isExistsComma(String s) {
//		Matcher matcher = COMMA_CHAR_PATN.matcher(s);
//		return matcher.find();
//	}
	
//	public static void main(String[] args) {
//		//String sSql = "\\n\\t  \\t\\n\\t  \\t\\tselect \\n\\t  \\t\\t\\tca.ActID,\\n\\t  \\t\\t\\tca.ActSmallID,\\n\\t  \\t\\t\\tst.ActSmallName,\\n\\t  \\t\\t\\tca.ActName,\\n\\t  \\t\\t\\tca.ActContent,\\n\\t  \\t\\t\\tca.ShopID,\\n\\t  \\t\\t\\tca.ShopName,\\n\\t  \\t\\t\\tca.Longitude,\\n\\t\\t\\t\\tca.Latitude,\\n\\t\\t\\t\\tca.SubmitMan,\\n\\t\\t\\t\\tca.SubmitDate,\\n\\t\\t\\t\\tca.Auditor,\\n\\t\\t\\t\\tca.AuditDate,\\n\\t\\t\\t\\tca.CancelDate,\\n\\t\\t\\t\\tca.CancelMan,\\n\\t\\t\\t\\tca.Status,\\n\\t\\t\\t\\t(case \\n\\t\\t             when ca.Status =0 then\\n\\t\\t             '未审'\\n\\t\\t             when ca.Status = 1 then\\n\\t\\t             '已审'\\n\\t\\t             when ca.Status = 2 then\\n\\t\\t             '取消'\\n\\t\\t             when ca.Status = 3 then\\n\\t\\t             '完成'\\n\\t\\t         end) staName,\\n\\t\\t\\t\\tca.ActBeginDate,\\n\\t\\t\\t\\tca.ActEndDate,\\n\\t\\t\\t\\tca.RptBeginDate,\\n\\t\\t\\t\\tca.RptEndDate,\\n\\t\\t\\t\\tca.IndexURL,\\n\\t\\t\\t\\tca.ItemType,\\n\\t\\t\\t\\tca.ItemPath,\\n\\t\\t\\t\\tca.OldPriceDes,\\n\\t\\t\\t\\tca.PriceDes,\\n\\t\\t\\t\\tca.OffDes,\\n\\t\\t\\t\\tca.ImageURL,\\n\\t\\t\\t\\tca.Type,\\n\\t\\t\\t\\t(case \\n\\t\\t             when ca.Type =0 then\\n\\t\\t             '报名'\\n\\t\\t             when ca.Type = 1 then\\n\\t\\t             '购买'\\n\\t\\t         end) typeName,\\n\\t\\t         ca.Price\\n\\t\\t\\tfrom crm_activity ca\\n\\t\\t\\tleft join crm_actsmalltype st on st.ActSmallID=ca.ActSmallID\\n\\t\\t\\t<<where ca.Status=#Status#>>\\n\\t\\t\\t<<and ca.ActName like #name#>>\\n\\t\\t\\t<<and ca.ActId =#id#>>\\n\\t\\t\\t<<and ca.ShopId = #shopId#>>\\n\\t\\t\\t<<and ca.ShopId in (select suser.ShopId from crm_shop_user suser where suser.CustomerID=#customerID#)>>\\n\\t\\t\\t<<and ca.SubmitDate between #dtOrderStart# and #dtOrderEnd#>>\\n\\t\\t\\t\\n\\t\\t\\torder by ca.SubmitDate desc\\n\\t  \\t\\n\\t\\t";
//		String sSql = "\n\t  \t\n\t  \t\tinsert into t_user_oper_log (UserID,UserName,funCode,funOperCode,tmCreate,EnablePower,UserIP,ShopID) values (?,?,?,?,Now(),?,?,?)\n\t  \t\n\t\t";
//		boolean b = isExistsSql(sSql);
//		System.out.println(b);
//		b = isExistsComma(sSql);
//		System.out.println(b);
//	}
	
	@SuppressWarnings({ "rawtypes" })
	private static void visitSqlIdCol(Element pele, SqlHeadCountPro p) {
		for (Iterator it1 = pele.elementIterator(); it1.hasNext();) {
			Element pe = (Element) it1.next();
			if (pe.getName().equals("cols")) {
				for (Iterator iter1 = pe.elementIterator(); iter1.hasNext();) {
					Element e = (Element) iter1.next();
					if (e.getName().equals("col")) {
						Attribute atName = e.attribute("name");
						if (atName != null) {
							Attribute atProp = e.attribute("prop");
							String prop = null;
							if (atProp != null) {
								prop = atProp.getText();
							}
							p.setCol(atName.getValue(), e.getText(), prop);
						}
					}
				}
			}
		}
	}
	
	private static String getAttrValue(Element e, String attrName) {
		Attribute eleAttr = e.attribute(attrName);
		if (eleAttr != null) {
			return eleAttr.getValue();
		} else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	private static void visitSqlElementChild_tp(Element pElement, TTableProps tp)
			throws DocumentException {
		for (Iterator iter1 = pElement.elementIterator(); iter1.hasNext();) {
			Element e = (Element) iter1.next();
			if (StringUtils.equals(e.getName(), "id")) {
				// attribute name
				String id = getAttrValue(e, "name");
				if (StringUtils.isEmpty(id)) {
					throw new DocumentException("sql id is null");
				}
				String sql = e.getText();
				if (StringUtils.isEmpty(sql)) {
					throw new DocumentException("sql context is null");
				}
				SqlHeadCountPro p = new SqlHeadCountPro(id, sql);
				// attribute type
				int sqlCmdType = SQLCmdType.getSqlCmdType(sql);
				p.setSqlCmdType(sqlCmdType);
				// mapConvert
				p.setMapConvert(getAttrValue(e, "mapConvert"));
//				Attribute attrCountIdSql = e.attribute("countSqlId");
//				if (attrCountIdSql != null) {
//					p.setCountSqlId(attrCountIdSql.getValue());
//				} else {
//					p.setCountSqlId(null);
//				}
//				Attribute attrHeadId = e.attribute("headId");
//				if (attrHeadId != null) {
//					p.setHeadId(attrHeadId.getValue());
//				} else {
//					p.setHeadId(null);
//				}
				visitSqlIdCol(e, p);
				tp.addSqlHeadPro(p);
			} else if (StringUtils.equalsIgnoreCase(e.getName(), "mapConvert")) {
				String id = getAttrValue(e, "id");
				if (StringUtils.isEmpty(id)) {
					throw new DocumentException("mapConvert id is null");
				}
				Map<String, String> map = getElementToMapConvert(e);
				tp.addToMapBeanConvert(id, map);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static TTableProps parserTable(Class cls, String path)
			throws DocumentException, IOException {
		return parserTables(cls, path);
	}

	@SuppressWarnings({ "rawtypes" })
	public static TTableProps parserTables(Class cls, String path)
			throws DocumentException, IOException {
		SAXReader saxReader = new SAXReader();
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource(path);
		InputStream is = null;
		//InputStream is = cls.getResourceAsStream(path);
		TTableProps tp = null;
		try {
			is = url.openStream();
			Document document = saxReader.read(is);
			Element root = document.getRootElement();
			tp = new TTableProps();
			//class
			Element eleClass = root.element("class");
			if (eleClass != null) {
				//String packageName = getAttrValue(eleClass, "package");
				String beanName = getAttrValue(eleClass, "name");
				if (beanName == null) {
					throw new DocumentException("class name is null");
				}
				String tableName = getAttrValue(eleClass, "table");
				if (StringUtils.isEmpty(tableName)) {
					throw new DocumentException("table name is null");
				}
				tp.setTableName(tableName);
				tp.setClassName(beanName);
				//
				visitClassElementChild(eleClass, tp);
			}
			//sql
			Element elementSql = root.element("sql");
			if (elementSql != null) {
				visitSqlElementChild_tp(elementSql, tp);
			} else {	//
				
			}
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				logger.error("parserTables->" + path + ":", e);
			}				
		}
		return tp;
	}
}
