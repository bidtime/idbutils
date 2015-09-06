package org.bidtime.dbutils.jdbc.sql;

import java.lang.reflect.Array;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author jss
 * 
 * 提供对从xxdao.xml中的sql语法进行正则解析的功能
 *
 */
public class SqlParser {
	
//	private static final Logger logger = LoggerFactory
//			.getLogger(SqlParser.class);

	// 动态语句 匹配正则表达式 << sql >>
	private static final Pattern D_SQL_PATN = Pattern.compile("<<.*?>>");	//"\\s<<.*?>>\\s"
	
	// where sub sql 匹配正则表达式
	private static final Pattern WHERE_SQL_PATN = Pattern.compile(
			"\\s*(where)\\s", Pattern.CASE_INSENSITIVE);

	// and sub sql 匹配正则表达式
	private static final Pattern AND_SQL_PATN = Pattern.compile("\\s*(and)\\s",
			Pattern.CASE_INSENSITIVE);

	// 动态变量 匹配正则表达式 #name#
	private static final Pattern PARAM_PATN = Pattern.compile("#[\\s|\\w]*#");

	// 动态替换 匹配正则表达式 {name}
	private static final Pattern REPLACE_PATN = Pattern
			.compile("\\{[\\s|\\w]*\\}");

	/**
	 * 解析普通SQL语句
	 * 
	 * @param e
	 * @param inputParams
	 * @return
	 */
	public static SqlHolder parse(String configSql, Map<String, ?> inputParams) {
		// 配置在XML中的SQL语句
//		boolean debug = logger.isDebugEnabled();
//		if (debug) {
//			logger.debug("\nconfig sql :" + configSql);
//			logger.debug("\nparams map :\n" + inputParams);
//		}

		// 替换SQL语句
		String replaceSql = replace(configSql, inputParams);

		// 根据参数 处理 动态SQL语句,得到带#name#的SQL
		String nameSql = handleDynamic(replaceSql, inputParams);

		List<Object> outputParamList = new ArrayList<Object>();

		// 处理#name# 参数,得到最终可执行的SQL
		String finalSql = getFinalSql(nameSql, inputParams, outputParamList);
//		if (debug) {
//			logger.debug("\nfinal sql :" + finalSql);
//			logger.debug("\nparam List:\n" + outputParamList);
//		}

		SqlHolder holder = new SqlHolder();
		holder.setSql(finalSql);
		holder.setParamList(outputParamList);
		return holder;
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
		for (Matcher matcher = REPLACE_PATN.matcher(inSql); matcher.find(); matcher = REPLACE_PATN
				.matcher(inSql)) {
			int start = matcher.start();
			int end = matcher.end();
			result.append(inSql.substring(0, start));
			String group = matcher.group();
			String replace = (String) params.get(group
					.replaceAll("\\{|\\}", "").trim());
			replace = replace == null ? " " : " " + replace + " ";
			result.append(replace);
			inSql = inSql.substring(end);
		}
		result.append(inSql);
		return result.toString();
	}

	private static String getFinalSql(String nameSql,
			Map<String, ?> inputParams, List<Object> list) {
		StringBuilder result = new StringBuilder(nameSql.length());
		//boolean debug = logger.isDebugEnabled();
		for (Matcher matcher = PARAM_PATN.matcher(nameSql); matcher.find(); matcher = PARAM_PATN
				.matcher(nameSql)) {
			result.append(nameSql.substring(0, matcher.start()));
			String group = matcher.group();
			String name = getParaName(group);
			Object value = inputParams.get(name);
//			if (debug) {
//				logger.debug(name + ":" + value);
//			}
			int sqlType = SqlUtils.getObjectType(value);
			result.append(buildSpaceHolder(value, sqlType, list));
			nameSql = nameSql.substring(matcher.end());
		}
		result.append(nameSql);
		return result.toString();
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

	private static boolean isExistsWhere(String sql) {
		Matcher matcher = WHERE_SQL_PATN.matcher(sql);
		return matcher.find();
	}

	private static StringBuilder isExistsAndReplaceWhere(String sql) {
		Matcher matcher = AND_SQL_PATN.matcher(sql);
		if (matcher.find()) {
			String sRelpace = matcher.replaceFirst(" where ");
			return new StringBuilder(sRelpace);
		} else {
			return null;
		}
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
		int nExistsWhere = -1;
		boolean bAddDynamic = false;
		for (Matcher matcher = D_SQL_PATN.matcher(sql); matcher.find(); matcher = D_SQL_PATN
				.matcher(sql)) {
			int start = matcher.start();
			int end = matcher.end();
			String sMatcherSql = sql.substring(0, start);
			result.append(sMatcherSql);
			String group = matcher.group();
			if (nExistsWhere == -1) {
				nExistsWhere = isExistsWhere(matcher.group()) ? 1 : 0;
			}
			String name = getParaName(group);
			Object value = params.get(name);
			if (isNotEmpty(value)) {
				String sDynamicStr2 = group.substring(2, group.length() - 2); // <<
																				// >>需要减除
				if (nExistsWhere == 1 && !bAddDynamic) {
					bAddDynamic = true;
					StringBuilder sbReturn = isExistsAndReplaceWhere(sDynamicStr2);
					if (sbReturn != null) {
						result.append(sbReturn.toString());
					} else {
						result.append(sDynamicStr2);
					}
				} else {
					result.append(sDynamicStr2);
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

	private static String getParaName(String input) {
		Matcher m = PARAM_PATN.matcher(input);
		if (m.find()) {
			String p = m.group();
			return p.substring(1, p.length() - 1).trim();
		} else {
			return StringUtils.EMPTY;
		}
	}
	
	/*
        // 在src串中查找并返回符合regEx指定模式或子串的结果集
                public static String invokeRegx(String src, String regEx) {
                Pattern pat = Pattern.compile(regEx);
                Matcher matcher = pat.matcher(src);
                if (matcher.find()) {
                        return matcher.group();
                } else {
                        return null;
                }

        }

                // 在src中查找符合regEx指定的模式或子串，并替换为rep指定的子串
                // 返回替换后的结果
                public static String invokeRegx(String src, String regEx, String rep) {
                Pattern pat = Pattern.compile(regEx);
                Matcher matcher = pat.matcher(src);
                if (matcher.find()) {
                        return matcher.replaceAll(rep);
                }

                else {
                        return null;
                }

        }	 */
}
