package org.bidtime.dbutils.jdbc.sql;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.bidtime.utils.comm.CaseInsensitiveHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jss
 * 
 * 提供对从xxdao.xml中的sql语法进行正则解析的功能
 *
 */
public class SqlParser {
	
	private static final Logger logger = LoggerFactory
			.getLogger(SqlParser.class);

	// 动态语句 匹配正则表达式  << sql >>
	private static final Pattern D_SQL_PATT = Pattern.compile("<<.*?>>");	//"\\s<<.*?>>\\s"
	
	// where sub sql 匹配正则表达式
	//private static final Pattern WHERE_SQL_PATN = Pattern.compile(
	//		"\\s*(where)\\s", Pattern.CASE_INSENSITIVE);

	// order by sub sql 匹配正则表达式
	private static final Pattern ORDER_BY_PATT = Pattern.compile(
			"\\{.*?order by.*?\\}", Pattern.CASE_INSENSITIVE);

	// and sub sql 匹配正则表达式
	//private static final Pattern COLON_SQL_PATT = Pattern.compile(".*?,.*?",
	//		Pattern.CASE_INSENSITIVE);

	// and sub sql 匹配正则表达式
	//private static final Pattern AND_SQL_PATN = Pattern.compile("\\s*(and)\\s",
	//		Pattern.CASE_INSENSITIVE);

	// 动态变量 匹配正则表达式  #name#
	private static final Pattern PARAM_PATT = Pattern.compile("#[\\s|\\w]*#");

	//当只获取主键的数据时，取到主键的正则
	private static final Pattern PRIMARY_KEY_PATT = Pattern.compile("[\\w]*\\s*=\\s*#[\\s|\\w]*#");

	// 动态替换 匹配正则表达式  {name} -> [idsort]
	//private static final Pattern REPL_PATT = Pattern.compile("\\[[\\s|\\w]*\\]");
	private static final Pattern REPL_PATT = Pattern.compile("\\{.*?\\}");
	
	public static String parse(String configSql, Map<String, ?> inputParams
			, List<Object> opParamList) throws SQLException {
		return parse(configSql, inputParams, opParamList, true);
	}

//	public static void main(String[] args) {
//		String sql = "select " + 
//	  			"id, " +
//	  			"code, " +
//	  			"name  " +
//	  		"from t_duty " +
//	  		"<<where id=#id_#>> " +
//	  		"<<and code=#code#>> " +
//	  		"<<and name like #name#>> "
//			;
//		Set<String> set = new SimpleHashSet();
//		set.add("id");
//		try {
//			Map<String, Object> map = getMapOfFieldPK(sql, set);
//			System.out.println(map);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	public static Map<String, Object> getMapOfFieldPK(String nameSql,
			CaseInsensitiveHashSet fieldPk) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder result = new StringBuilder(nameSql.length());
		for (Matcher matcher = PRIMARY_KEY_PATT.matcher(nameSql); matcher.find();
				matcher = PRIMARY_KEY_PATT.matcher(nameSql)) {
			result.append(nameSql.substring(0, matcher.start()));
			String group = matcher.group();
			String name = getPatternName(group, PARAM_PATT);
			String[] arGroup = group.split("=");
			if (fieldPk.contains(arGroup[0].trim())) {
				map.put(name, null);
			}
			nameSql = nameSql.substring(matcher.end());
		}
		return map;
	}
	
	/**
	 * 解析普通SQL语句
	 * 
	 * @param e
	 * @param inputParams
	 * @return
	 */
	public static String parse(String configSql, Map<String, ?> inputParams
			, List<Object> opParamList, boolean compareKey) throws SQLException {
		// 配置在XML中的SQL语句
//		boolean debug = logger.isDebugEnabled();
//		if (debug) {
//			logger.debug("\nconfig sql :" + configSql);
//			logger.debug("\nparams map :\n" + inputParams);
//		}

		// 替换 order by
		String repl = SqlParser.replaceOrderBy(configSql, inputParams);
		
		// 替换SQL语句
		String replaceSql = replace(repl, inputParams);
		
		if (compareKey && !containsAllParams(replaceSql, inputParams)) {
			SQLException e = new SQLException("sql params is not match.");
			logger.error("parse", e);
			throw e;
		}

		// 根据参数 处理 动态SQL语句,得到带#name#的SQL
		String nameSql = handleDynamic(replaceSql, inputParams);

		List<Object> opList = new ArrayList<Object>();

		// 处理#name# 参数,得到最终可执行的SQL
		String finalSql = getFinalSql(nameSql, inputParams, opList);
//		if (debug) {
//			logger.debug("\nfinal sql :" + finalSql);
//			logger.debug("\nparam List:\n" + outputParamList);
//		}

		//SqlHolder holder = new SqlHolder();
		//holder.setSql(finalSql);
		//holder.setParamList(opList);
		//return holder;
		if (opParamList != null) {
			opParamList.addAll(opList);
		}
		return finalSql;
	}

	/**
	 * 动态SQL替换,把{name} 部分 替换成params传进来的值
	 * 
	 * @param inSql
	 * @param params
	 * @return
	 */
	private static String replace(String inSql, Map<String, ?> params) {
		StringBuilder result = new StringBuilder(inSql.length());
		for (Matcher matcher = REPL_PATT.matcher(inSql); matcher.find(); 
				matcher = REPL_PATT.matcher(inSql)) {
			int start = matcher.start();
			int end = matcher.end();
			result.append(inSql.substring(0, start));
			String group = matcher.group().substring(1, matcher.group().length()-1);
			String name = getPatternName(group, PARAM_PATT);
			Object replace = null;
			if (params != null && !params.isEmpty()) {
				replace = params.get(name);
			}
			//String replace = (String) params.get(group
			//		.replaceAll("\\{|\\}", "").trim());
			if (replace == null) {
				result.append(" ");
			} else {
				result.append(" ");
				result.append(replace);
				result.append(" ");
			}
			inSql = inSql.substring(end);
		}
		result.append(inSql);
		return result.toString();
	}

	private static String replaceOrderByFinalSql(String nameSql,
			Map<String, ?> inputParams) {
		StringBuilder result = new StringBuilder(nameSql.length());
		for (Matcher matcher = PARAM_PATT.matcher(nameSql); matcher.find(); matcher = PARAM_PATT
				.matcher(nameSql)) {
			String sPreview = nameSql.substring(0, matcher.start());
			result.append(sPreview);
			String group = matcher.group();
			String name = getPatternName(group, PARAM_PATT);
			Object value = inputParams.get(name);
			if (value != null) {
				result.append(value);
			}
			nameSql = nameSql.substring(matcher.end());
		}
		result.append(nameSql);
		return result.toString();
	}
	
	private static String replaceOrderBy(String inSql, Map<String, ?> params) {
		StringBuilder result = new StringBuilder(inSql.length());
		boolean bAddOrderBy = false;
		for (Matcher matcher = ORDER_BY_PATT.matcher(inSql); matcher.find(); 
				matcher = ORDER_BY_PATT.matcher(inSql)) {
			int start = matcher.start();
			int end = matcher.end();
			result.append(inSql.substring(0, start));
			String group = matcher.group().substring(1, matcher.group().length() - 1);
			// handleDynamic
			String dynAfterStr = handleDynamic(group, params);
			// replaceOrderByFinalSql
			String finalReplOrderBy = replaceOrderByFinalSql(dynAfterStr, params);
			String[] arrColon = finalReplOrderBy.split(",");
			if (arrColon != null && arrColon.length > 0) {
				for (int m = 0; m < arrColon.length; m ++) {
					String[] sSplit = arrColon[m].split("=");
					if (sSplit != null && sSplit.length > 1) {
						String key = null;
						if (!bAddOrderBy) {
							key = sSplit[0].trim();
							String[] keyAttr = key.split(" ");
							if (keyAttr != null) {
								if (keyAttr.length>0) {
									key = keyAttr[keyAttr.length - 1];
								} else{
									key = keyAttr[0];									
								}
							}
						} else {
							key = sSplit[0].trim();
						}
						String value = sSplit[1].trim();
						String[] valueArr = value.split(" ");
						if (key.equalsIgnoreCase(valueArr[0])) {
							if (!bAddOrderBy) {
								bAddOrderBy = true;
								result.append(" order by ");
							} else {
								result.append(", ");
							}
							result.append(value);
						//} else {
							//log.info("err: " + key + " - " + valueArr[0]);
						}
					}
				}
			}
			inSql = inSql.substring(end);
		}
		result.append(inSql);
		return result.toString();
	}
	
//	private static void doIt2() {
//		String sql = "select id, code, name from xx "
//			// + " {idsort} " + " \n\r "
//			+ "{order by id=#idSort#, <<code=#codeSort#>>, name=#nameSort#}"
//			//+ " [ <<order by id2=#id2Sort#>> <<, code2=#code2Sort#>> <<, name2=#name2Sort#>>] "
//			// + " [ # idSort # ] "
//			;
//		
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("idSort", "id asc");
//		params.put("codeSort", "code desc");
//		
//		String repl = SqlParser.replaceOrderBy(sql, params);
//		//String repl = SqlParser.replace(sql, params);
//		System.out.println(repl);
//	}

	private static String getFinalSql(String nameSql,
			Map<String, ?> inputParams, List<Object> list) {
		StringBuilder result = new StringBuilder(nameSql.length());
		for (Matcher matcher = PARAM_PATT.matcher(nameSql); matcher.find(); matcher = PARAM_PATT
				.matcher(nameSql)) {
			result.append(nameSql.substring(0, matcher.start()));
			String group = matcher.group();
			String name = getPatternName(group, PARAM_PATT);
			Object value = null;
			if (inputParams != null && !inputParams.isEmpty()) {
				value = inputParams.get(name);
			}
			int sqlType = SqlUtils.getObjectType(value);
			result.append(buildSpaceHolder(value, sqlType, list));
			nameSql = nameSql.substring(matcher.end());
		}
		result.append(nameSql);
		return result.toString();
	}

	private static boolean containsAllParams(String nameSql,
			Map<String, ?> inputParams) throws SQLException {
		if (inputParams == null || inputParams.isEmpty()) {
			return true;
		}
		Set<String> set = new HashSet<String>();
		StringBuilder result = new StringBuilder(nameSql.length());
		for (Matcher matcher = PARAM_PATT.matcher(nameSql); matcher.find(); matcher = PARAM_PATT
				.matcher(nameSql)) {
			result.append(nameSql.substring(0, matcher.start()));
			String group = matcher.group();
			String name = getPatternName(group, PARAM_PATT);
			set.add(name);
			nameSql = nameSql.substring(matcher.end());
		}
		boolean b = set.containsAll(inputParams.keySet());
		if (logger.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("contains->");
			sb.append(b);
			sb.append(" inp:");
			sb.append(inputParams);
			sb.append("alp:");
			sb.append(set);
			logger.debug(sb.toString());
		}
		return b;
	}
	
	@SuppressWarnings("rawtypes")
	private static StringBuilder buildSpaceHolder(Object value, int sqlType,
			List<Object> paramList) {
		StringBuilder spaceHolder = new StringBuilder();
		if (value == null) {
			spaceHolder.append("?");
			paramList.add(null);
			return spaceHolder;
		}

		// 如果是byte数组,且数据类型为byte[],则只算一个参数
		if (value instanceof byte[]
				&& (sqlType == Types.BINARY || sqlType == Types.VARBINARY)) {
			spaceHolder.append("?");
			paramList.add(value);
			return spaceHolder;
		}

		if (value.getClass().isArray()) {
			for (int i = 0, length = Array.getLength(value); i < length; i++) {
				spaceHolder.append(",?");
				Object v = Array.get(value, i);
				if (v == null) {
					paramList.add(null);
				} else {
					paramList.add(v);
				}
			}
			if (spaceHolder.length() > 0) {
				spaceHolder.deleteCharAt(0);
			}
		} else if (value instanceof Collection) {
			Collection cv = (Collection) value;
			for (Object o : cv) {
				spaceHolder.append(",?");
				paramList.add(o);
			}
			if (spaceHolder.length() > 0) {
				spaceHolder.deleteCharAt(0);
			}
		} else {
			spaceHolder.append("?");
			paramList.add(value);
		}
		return spaceHolder;
	}

	/**
	 * 动态参数处理
	 *   增加，处理<<where>>参数
	 * @param sSql
	 * @param params
	 * @return
	 */
	private static String handleDynamic(String sSql, Map<String, ?> params) {
		String sql = sSql;
		StringBuilder result = new StringBuilder(sql.length());
		for (Matcher match = D_SQL_PATT.matcher(sql); match.find(); match = D_SQL_PATT
				.matcher(sql)) {
			int start = match.start();
			int end = match.end();
			String sMatcherSql = sql.substring(0, start);
			result.append(sMatcherSql);
			String group = match.group();
			String name = getPatternName(group, PARAM_PATT);
			Object value = null;
			if (params != null && !params.isEmpty()) {
				value = params.get(name);
				if (isNotEmpty(value)) {		// <<>>需要减除
					result.append(group.substring(2, group.length() - 2));
				}
			}
			sql = sql.substring(end);
		}
		result.append(sql);
		return result.toString();
	}

	@SuppressWarnings("rawtypes")
	private static boolean isNotEmpty(Object value) {
		if (value == null) {
			return false;
		}
		Class clazz = value.getClass();
		if (clazz == String.class) {
			return ((String) value).length() > 0;
		} else if (clazz.isArray()) {
			return Array.getLength(value) > 0;
		} else if (Collection.class.isAssignableFrom(clazz)) {
			return !((Collection) value).isEmpty();
		}
		return true;
	}

//	private static String getParaName(String input) {
//		return getParaName(input, PARAM_PATT);
//		Matcher m = PARAM_PATT.matcher(input);
//		if (m.find()) {
//			String p = m.group();
//			return p.substring(1, p.length() - 1).trim();
//		} else {
//			return StringUtils.EMPTY;
//		}
//	}

	private static String getPatternName(String input, Pattern pattern) {
		Matcher m = pattern.matcher(input);
		if (m.find()) {
			String p = m.group();
			return p.substring(1, p.length() - 1).trim();
		} else {
			return StringUtils.EMPTY;
		}
	}
	
}
