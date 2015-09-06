/**
 * 
 */
package org.bidtime.utils.basic;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 *
 */
public class MapParamsUtils {
	
	private static final Logger logger = LoggerFactory
			.getLogger(MapParamsUtils.class);

	public static void addToMap(Map<String, Object> map, String key, Object o) {
		logger.debug(key+":"+o);
		if (o != null) {
			if (o instanceof String) {
				if (StringUtils.isEmpty((String)o)) {
					return;
				}
			}
			map.put(key, o);
		}
	}

	public static void addPercentToMap(Map<String, Object> map, String key, Object o) {
		addToMap(map, key, "%", o, "%");
	}

	public static void addLeftPercentToMap(Map<String, Object> map, String key, Object o) {
		addToMap(map, key, "%", o, null);
	}

	public static void addRightPercentToMap(Map<String, Object> map, String key, Object o) {
		addToMap(map, key, null, o, "%");
	}

	public static void addToMap(Map<String, Object> map, String key, String sBef,
			Object o, String sAft) {
		logger.debug(key+":"+o);
		if (o != null) {
			if (o instanceof String) {
				if (StringUtils.isEmpty((String)o)) {
					return;
				}
			}
			if (StringUtils.isEmpty(sBef) && (StringUtils.isEmpty(sAft))) {
				map.put(key, String.valueOf(o));
			} else if (!StringUtils.isEmpty(sBef) && (StringUtils.isEmpty(sAft))) {
				map.put(key, sBef + String.valueOf(o));
			} else if (StringUtils.isEmpty(sBef) && (!StringUtils.isEmpty(sAft))) {
				map.put(key, String.valueOf(o) + sAft);
			} else if (!StringUtils.isEmpty(sBef) && (!StringUtils.isEmpty(sAft))) {
				map.put(key, sBef + String.valueOf(o) + sAft);
			} 
		}
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
	
	public static String addOrderByReplace(String orderby, String replace) {
		if (StringUtils.isNotEmpty(orderby)) {
			String[] list = orderby.split(" ");
			if (list.length>0) {
				StringBuilder sb = new StringBuilder();
				if (!StringUtils.equalsIgnoreCase(list[0], replace)) {
					list[0] = replace;
				}
				sb.append(" order by ");
				for (int i=0; i<list.length; i++) {
					if (i==0) {
						sb.append(replace);
					} else {
						sb.append(" ");
						sb.append(list[i]);
					}
				}
				return sb.toString();
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append(" order by ");
				sb.append(orderby);
				return sb.toString();
			}
		} else {
			return orderby;
		}
	}
	
	public static String orderByReplace(String orderby, String replace) {
		if (StringUtils.isNotEmpty(orderby)) {
			StringBuilder sb = new StringBuilder();
			String[] list = orderby.split(" ");
			if (list.length>0 && !StringUtils.endsWithIgnoreCase(list[0], replace)) {
				list[0] = replace;
				for (int i=0; i<list.length; i++) {
					if (i==0) {
						sb.append(replace);
					} else {
						sb.append(" ");
						sb.append(list[i]);
					}
				}
				return sb.toString();
			} else {
				return orderby;
			}
		} else {
			return orderby;
		}
	}
	
//	public static void main(String[] args) {
////		Map<String, Object> p = new HashMap<String, Object>();
////		p.put("ob1", "ruldId1 asc");
//		//p.put("idOrderBy1", "ruldId1 asc");
//		//p.put("ob2", "ruldId2 asc");
//		
//		String orderby = "kk";
//		System.out.println(addOrderByReplace(orderby, "name"));
//		
////		Pattern temp = Pattern
////					.compile("([^[,|\\{|\\}|\\S]&[\\s]]+?)");
////		
////		String input = "{ob1, ob2}";
////		Matcher matcher = temp.matcher(input);
////		if (matcher.find()) {
////			for (int i=0; i<matcher.groupCount(); i++) {
////				System.out.println(matcher.group(i));
////			}
////		}
//		//int i = 0;
//		//while (matcher.find()) {
//		//	System.err.println(matcher.group(i));
//		//	i++;
//		//}
//	}

}
