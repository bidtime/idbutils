package org.bidtime.dbutils.jdbc.sql.xml.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.bidtime.dbutils.jdbc.sql.ArrayUtils;
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
//	
//	private static String replace(String inSql, Map<String, ?> params) {
//		StringBuilder result = new StringBuilder(inSql.length());
//		for (Matcher matcher = REPLACE_PATN.matcher(inSql); matcher.find(); matcher = REPLACE_PATN
//				.matcher(inSql)) {
//			int start = matcher.start();
//			int end = matcher.end();
//			result.append(inSql.substring(0, start));
//			String group = matcher.group();
//			String replace = (String) params.get(group
//					.replaceAll("\\{|\\}", "").trim());
//			replace = replace == null ? " " : " " + replace + " ";
//			result.append(replace);
//			inSql = inSql.substring(end);
//		}
//		result.append(inSql);
//		return result.toString();
//	}
//	
//	public static void main(String[] args) {
////		Map<String, Object> p = new HashMap<String, Object>();
////		p.put("ob1", "ruldId1 asc");
//		//p.put("idOrderBy1", "ruldId1 asc");
//		//p.put("ob2", "ruldId2 asc");
//		
//		Pattern temp = Pattern
//					.compile("([^[,|\\{|\\}|\\S]&[\\s]]+?)");
//		
//		String input = "{ob1, ob2}";
//		Matcher matcher = temp.matcher(input);
//		int i = 0;
//		while(matcher.find()){
//			System.err.println(matcher.group(i));
//			i++;
//		}
//	}

	/**
	 * @param args
	 * 
	 */
	// public static void main(String[] args) {
	// try {
	// //List<TTableProps> list =
	// ParserSqlXML.parserTables(ParserSqlXML.class,"/com/eb/sql/db.xml");
	// //visitListTable(list);
	// ParserSqlXML.parserSql(ParserSqlXML.class,"/com/qmsk/sql/db.xml");
	// } catch (DocumentException e) {
	// e.printStackTrace();
	// } finally {
	// }
	// }

	// private static <T> getAttributeValue(Element e, String name,Class<T>
	// targetType) {
	// if (e.attribute(name)!=null) {
	// return (T) e.attribute(name);
	// } else return null;
	// }

	private static void elementToColumnPro(Element e, ColumnPro p) {
		if (e.attribute("name") != null) {
			p.setName(e.attribute("name").getValue().trim().toLowerCase());
		}
		if (e.attribute("column") != null) {
			p.setColumn(e.attribute("column").getValue().trim().toLowerCase());
		}
		if (e.attribute("type") != null) {
			p.setType(e.attribute("type").getValue().trim().toLowerCase());
		}
		if (e.attribute("length") != null) {
			p.setLength(Integer
					.valueOf(e.attribute("length").getValue().trim()));
		}
		if (e.attribute("generator") != null) {
			p.setGenerator(e.attribute("generator").getValue().trim());
		}
		if (e.attribute("not-null") != null) {
			Boolean b = Boolean.valueOf(e.attribute("not-null").getValue()
					.trim());
			p.setNotNull(b);
		}
		if (p.getNotNull()) {
			if (e.attribute("default") != null) {
				p.setDefaultValue(e.attribute("default").getValue());
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private static void visitClassElementChild(Element pElement, TTableProps tp) {
		List<String> listPkField = new ArrayList<String>();
		for (Iterator iter1 = pElement.elementIterator(); iter1.hasNext();) {
			Element e = (Element) iter1.next();
			// logger.info(e.getName() + ": " + e.getText());
			if (e.getName().trim().equalsIgnoreCase("id")) {
				ColumnPro p = new ColumnPro(true);
				elementToColumnPro(e, p);
				listPkField.add(p.getColumn());
				tp.addColsPkPro(p.getName(), p);
			} else if (e.getName().trim().equalsIgnoreCase("property")) {
				ColumnPro p = new ColumnPro(false);
				elementToColumnPro(e, p);
				tp.addColsCommonPro(p.getName(), p);
			}
		}
		tp.setFieldPK(ArrayUtils.listToStringArray(listPkField));
	}

	private static String trimAll(String str) {
		StringBuilder sb = new StringBuilder();
		char c = ' ';
		for (int i = 0; i < str.length(); i++) {
			char s = str.charAt(i);
			if (s != c) {
				sb.append(s);
			}
		}
		return sb.toString();
	}

//	private static String removeDoubleSpace(String str) {
//		StringBuilder sb = new StringBuilder();
//		char c = ' ';
//		int nSpaceCounts = 0;
//		for (int i = 0; i < str.length(); i++) {
//			char s = str.charAt(i);
//			if (s != c) {
//				if (nSpaceCounts > 0) {
//					sb.append(c);
//				}
//				sb.append(s);
//				nSpaceCounts = 0;
//			} else {
//				nSpaceCounts++;
//			}
//		}
//		return sb.toString();
//	}

	public static String removeSpaces(String s) {
		StringTokenizer st = new StringTokenizer(s, " ", false);
		String t = "";
		while (st.hasMoreElements()) {
			t += st.nextElement();
		}
		return t;
	}
	
	private static final Pattern SQL_STANDARD_PATN = Pattern.compile(
			"^*(insert|delete|update|if exists|select|call)\\s", Pattern.CASE_INSENSITIVE);

	private static boolean isExistsSql(String sql) {
		Matcher matcher = SQL_STANDARD_PATN.matcher(sql);
		return matcher.find();
	}
	
	private static final Pattern COMMA_CHAR_PATN = Pattern.compile(
			",", Pattern.CASE_INSENSITIVE);

	private static boolean isExistsComma(String s) {
		Matcher matcher = COMMA_CHAR_PATN.matcher(s);
		return matcher.find();
	}
	
//	public static void main(String[] args) {
//		//String sSql = "\\n\\t  \\t\\n\\t  \\t\\tselect \\n\\t  \\t\\t\\tca.ActID,\\n\\t  \\t\\t\\tca.ActSmallID,\\n\\t  \\t\\t\\tst.ActSmallName,\\n\\t  \\t\\t\\tca.ActName,\\n\\t  \\t\\t\\tca.ActContent,\\n\\t  \\t\\t\\tca.ShopID,\\n\\t  \\t\\t\\tca.ShopName,\\n\\t  \\t\\t\\tca.Longitude,\\n\\t\\t\\t\\tca.Latitude,\\n\\t\\t\\t\\tca.SubmitMan,\\n\\t\\t\\t\\tca.SubmitDate,\\n\\t\\t\\t\\tca.Auditor,\\n\\t\\t\\t\\tca.AuditDate,\\n\\t\\t\\t\\tca.CancelDate,\\n\\t\\t\\t\\tca.CancelMan,\\n\\t\\t\\t\\tca.Status,\\n\\t\\t\\t\\t(case \\n\\t\\t             when ca.Status =0 then\\n\\t\\t             '未审'\\n\\t\\t             when ca.Status = 1 then\\n\\t\\t             '已审'\\n\\t\\t             when ca.Status = 2 then\\n\\t\\t             '取消'\\n\\t\\t             when ca.Status = 3 then\\n\\t\\t             '完成'\\n\\t\\t         end) staName,\\n\\t\\t\\t\\tca.ActBeginDate,\\n\\t\\t\\t\\tca.ActEndDate,\\n\\t\\t\\t\\tca.RptBeginDate,\\n\\t\\t\\t\\tca.RptEndDate,\\n\\t\\t\\t\\tca.IndexURL,\\n\\t\\t\\t\\tca.ItemType,\\n\\t\\t\\t\\tca.ItemPath,\\n\\t\\t\\t\\tca.OldPriceDes,\\n\\t\\t\\t\\tca.PriceDes,\\n\\t\\t\\t\\tca.OffDes,\\n\\t\\t\\t\\tca.ImageURL,\\n\\t\\t\\t\\tca.Type,\\n\\t\\t\\t\\t(case \\n\\t\\t             when ca.Type =0 then\\n\\t\\t             '报名'\\n\\t\\t             when ca.Type = 1 then\\n\\t\\t             '购买'\\n\\t\\t         end) typeName,\\n\\t\\t         ca.Price\\n\\t\\t\\tfrom crm_activity ca\\n\\t\\t\\tleft join crm_actsmalltype st on st.ActSmallID=ca.ActSmallID\\n\\t\\t\\t<<where ca.Status=#Status#>>\\n\\t\\t\\t<<and ca.ActName like #name#>>\\n\\t\\t\\t<<and ca.ActId =#id#>>\\n\\t\\t\\t<<and ca.ShopId = #shopId#>>\\n\\t\\t\\t<<and ca.ShopId in (select suser.ShopId from crm_shop_user suser where suser.CustomerID=#customerID#)>>\\n\\t\\t\\t<<and ca.SubmitDate between #dtOrderStart# and #dtOrderEnd#>>\\n\\t\\t\\t\\n\\t\\t\\torder by ca.SubmitDate desc\\n\\t  \\t\\n\\t\\t";
//		String sSql = "\n\t  \t\n\t  \t\tinsert into t_user_oper_log (UserID,UserName,funCode,funOperCode,tmCreate,EnablePower,UserIP,ShopID) values (?,?,?,?,Now(),?,?,?)\n\t  \t\n\t\t";
//		boolean b = isExistsSql(sSql);
//		System.out.println(b);
//		b = isExistsComma(sSql);
//		System.out.println(b);
//	}

	@SuppressWarnings("rawtypes")
	private static void visitSqlElementChild_tp(Element pElement, TTableProps tp)
			throws DocumentException {
		try {
		for (Iterator iter1 = pElement.elementIterator(); iter1.hasNext();) {
			Element e = (Element) iter1.next();
			if (StringUtils.equalsIgnoreCase(e.getName(), "id")) {
				// attribute name
				Attribute attrClassName = e.attribute("name");
				if (attrClassName == null
						|| StringUtils.isEmpty(attrClassName.getValue())) {
					throw new DocumentException("sql id is null");
				}
				if (StringUtils.isEmpty(e.getText())) {
					throw new DocumentException("sql context is null");
				}
				String sSql = e.getText();
				// attribute type
				Attribute attrTypeName = e.attribute("type");
				String typeNameTag = null;
				if (attrTypeName != null) {
					typeNameTag = attrTypeName.getValue();
				} else {
					if (!isExistsSql(sSql) && isExistsComma(sSql)) {
						typeNameTag = "head";
					}
				}
				if (StringUtils.equalsIgnoreCase(typeNameTag, "head")) {
					sSql = sSql.replaceAll("\t", "");
					sSql = sSql.replaceAll("\r\n", " ");
					sSql = sSql.replaceAll("\r", " ");
					sSql = sSql.replaceAll("\n", " ");
					sSql = sSql.trim();
					sSql = trimAll(sSql);
					JsonHeadPro p = new JsonHeadPro();
					p.setId(attrClassName.getValue());
					p.setType(typeNameTag);
					p.setHeadFlds(sSql);
					Attribute attrDeryFlds = e.attribute("decryFlds");
					if (attrDeryFlds != null) {
						p.setDecryFlds(attrDeryFlds.getValue());
					}
					tp.addJsonHeadPro(p);
				} else {
					// sSql = removeDoubleSpace(sSql);
					SqlHeadCountPro p = new SqlHeadCountPro();
					p.setId(attrClassName.getValue());
					p.setType(typeNameTag);
					p.setSql(sSql);

					Attribute attrCountIdSql = e.attribute("countSqlId");
					if (attrCountIdSql != null) {
						p.setCountSqlId(attrCountIdSql.getValue());
					} else {
						p.setCountSqlId(null);
					}
					Attribute attrHeadId = e.attribute("headId");
					if (attrHeadId != null) {
						p.setHeadId(attrHeadId.getValue());
					} else {
						p.setHeadId(null);
					}
					tp.addSqlHeadPro(p);
				}
				// String sSqlLower = sSql.toLowerCase();
				// if (sSqlLower.indexOf("select ") == 0
				// || sSqlLower.indexOf("delete ") == 0
				// || sSqlLower.indexOf("update ") == 0
				// || sSqlLower.indexOf("insert into ") == 0
				// || sSqlLower.indexOf("if exists") == 0
				// || sSqlLower.indexOf("call ") >= 0
				// || sSqlLower.indexOf(" call") >= 0) {
				// sSql = removeDoubleSpace(sSql);
				// } else {
				// sSql = trimAll(sSql);
				// }
				// hashMap.put(attrClassName.getValue(), sSql);
				//logger.info(attrClassName.getValue() + ": " + sSql);
			}
		}
		} catch (Exception e) {
			logger.error("visitSqlElementChild_tp:", e);
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
		//logger.info("");
		//logger.info("table cols:" + path);
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource(path);
		InputStream is = null;
		//InputStream is = cls.getResourceAsStream(path);
		TTableProps tp = null;
		try {
			is = url.openStream();		
//			String[] arClassName = path.split("/");
//			if (arClassName != null && arClassName.length > 0) {
//				String sClassName = arClassName[arClassName.length-1];
//				if (StringUtils.equalsIgnoreCase(sClassName,
//						"CountryDAO.xml")) {
//					logger.info(path);
//				}
//			}
			Document document = saxReader.read(is);
			Element root = document.getRootElement();
			tp = new TTableProps();
			Element elementClass = root.element("class");
			if (elementClass != null) {
				Attribute attrClassName = elementClass.attribute("name");
				Attribute attrTableName = elementClass.attribute("table");
				if (attrClassName == null || attrClassName.getValue() == null
						|| attrClassName.getValue().trim().equalsIgnoreCase("")) {
					throw new DocumentException("class name is null");
				}
				if (attrTableName == null || attrTableName.getValue() == null
						|| attrTableName.getValue().trim().equalsIgnoreCase("")) {
					throw new DocumentException("table name is null");
				}
				tp.setTableName(attrTableName.getValue());
				tp.setClassName(attrClassName.getValue());
				visitClassElementChild(elementClass, tp);
			}
			Element elementSql = root.element("sql");
			if (elementSql != null) {
				visitSqlElementChild_tp(elementSql, tp);
			}
			//
			tp.finished();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				logger.error("parserTables->" + path + ":", e);
			}				
		}
		//logger.info("parserTables: end");
		//logger.info("");
		return tp;
	}
}
