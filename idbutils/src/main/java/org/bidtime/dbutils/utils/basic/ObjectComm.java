package org.bidtime.dbutils.utils.basic;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectComm {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ObjectComm.class);

	private static final byte byte_one = 1;
	//private static final byte byte_zero = 0;

	public static Double objectToDouble(Object o, Object objDefault) {
		Object obj = objectToDouble(o);
		if (obj != null) {
			return (Double) obj;
		} else {
			//return objDefault == null ? null : (Double) objDefault;
			return objectToDouble(objDefault);
		}
	}

	public static Double objectToDouble(Object o) {
		if (o != null) {
			if (o instanceof Double) {
				return ((Double) o).doubleValue();
			} else if (o instanceof Float) {
				return ((Float) o).doubleValue();
			} else if (o instanceof BigDecimal) {
				return ((BigDecimal) o).doubleValue();
			} else if (o instanceof Integer) {
				return ((Integer) o).doubleValue();
			} else if (o instanceof Long) {
				return ((Long) o).doubleValue();
			} else if (o instanceof Short) {
				return ((Short) o).doubleValue();
			} else if (o instanceof Byte) {
				return ((Byte) o).doubleValue();
			} else if (o instanceof String) {
				if (StringUtils.isNotEmpty((String)o)) {
					return Double.parseDouble((String)o);
				} else {
					return null;
				}
			} else if (o instanceof BigInteger) {
				return ((BigInteger) o).doubleValue();
			} else {
				return Double.valueOf(String.valueOf(o));
			}
		} else {
			return null;
		}
	}

	public static Float objectToFloat(Object o) {
		if (o != null) {
			if (o instanceof Float) {
				return (Float) o;
			} else if (o instanceof Double) {
				return ((Double) o).floatValue();
			} else if (o instanceof BigDecimal) {
				return ((BigDecimal) o).floatValue();
			} else if (o instanceof Integer) {
				return ((Integer) o).floatValue();
			} else if (o instanceof Long) {
				return ((Long) o).floatValue();
			} else if (o instanceof Short) {
				return ((Short) o).floatValue();
			} else if (o instanceof Byte) {
				return ((Byte) o).floatValue();
			} else if (o instanceof String) {
				if (StringUtils.isNotEmpty((String)o)) {
					return Float.parseFloat((String)o);
				} else {
					return null;
				}
			} else if (o instanceof BigInteger) {
				return ((BigInteger) o).floatValue();
			} else {
				return Float.valueOf(String.valueOf(o));
			}
		} else {
			return null;
		}
	}

	public static Short objectToShort(Object o) {
		if (o != null) {
			if (o instanceof Short) {
				return ((Short) o);
			} else if (o instanceof Integer) {
				return ((Integer) o).shortValue();
			} else if (o instanceof Float) {
				return ((Float) o).shortValue();
			} else if (o instanceof Double) {
				return ((Double) o).shortValue();
			} else if (o instanceof BigDecimal) {
				return ((BigDecimal) o).shortValue();
			} else if (o instanceof Long) {
				return ((Long) o).shortValue();
			} else if (o instanceof Byte) {
				return ((Byte) o).shortValue();
			} else if (o instanceof String) {
				if (StringUtils.isNotEmpty((String)o)) {
					return Short.parseShort((String)o);
				} else {
					return null;
				}
			} else if (o instanceof BigInteger) {
				return ((BigInteger) o).shortValue();
			} else {
				return Short.valueOf(String.valueOf(o));
			}
		} else {
			return null;
		}
	}
	
	public static Byte objectToByte(Object o) {
		if (o != null) {
			if (o instanceof Byte) {
				return ((Byte) o);
			} else if (o instanceof Short) {
				return ((Short) o).byteValue();
			} else if (o instanceof Integer) {
				return ((Integer) o).byteValue();
			} else if (o instanceof Float) {
				return ((Float) o).byteValue();
			} else if (o instanceof Double) {
				return ((Double) o).byteValue();
			} else if (o instanceof BigDecimal) {
				return ((BigDecimal) o).byteValue();
			} else if (o instanceof Long) {
				return ((Long) o).byteValue();
			} else if (o instanceof String) {
				if (StringUtils.isNotEmpty((String)o)) {
					return Byte.parseByte((String)o);
				} else {
					return null;
				}
			} else if (o instanceof BigInteger) {
				return ((BigInteger) o).byteValue();
			} else {
				return Byte.valueOf(String.valueOf(o));
			}
		} else {
			return null;
		}
	}

	public static Long objectToLong(Object o, Object objDefault) {
		Object obj = objectToLong(o);
		if (obj != null) {
			return (Long) obj;
		} else {
			//return objDefault == null ? null : (Long) objDefault;
			return objectToLong(objDefault);
		}
	}

	public static Long objectToLong(Object o) {
		if (o != null) {
			if (o instanceof Long) {
				return (Long) o;
			} else if (o instanceof Integer) {
				return ((Integer) o).longValue();
			} else if (o instanceof Short) {
				return ((Short) o).longValue();
			} else if (o instanceof Double) {
				return ((Double) o).longValue();
			} else if (o instanceof Float) {
				return ((Float) o).longValue();
			} else if (o instanceof Boolean) {
				return ((Boolean) o == true ? 1L : 0L);
			} else if (o instanceof Byte) {
				return ((Byte) o).longValue();
			} else if (o instanceof String) {
				if (StringUtils.isNotEmpty((String)o)) {
					return Long.parseLong((String)o);
				} else {
					return null;
				}
			} else if (o instanceof BigDecimal) {
				return ((BigDecimal) o).longValue();
			} else if (o instanceof BigInteger) {
				return ((BigInteger) o).longValue();
			} else {
				return Long.valueOf(String.valueOf(o));
			}
		} else {
			return null;
		}
	}

	public static Integer objectToInteger(Object o, Object objDefault) {
		Object obj = objectToInteger(o);
		if (obj != null) {
			return (Integer) obj;
		} else {
			//return objDefault == null ? null : (Integer) objDefault;
			return objectToInteger(objDefault);
		}
	}

	public static Integer objectToInteger(Object o) {
		if (o != null) {
			if (o instanceof Integer) {
				return (Integer) o;
			} else if (o instanceof Long) {
				return ((Long) o).intValue();
			} else if (o instanceof Short) {
				return ((Short) o).intValue();
			} else if (o instanceof BigDecimal) {
				return ((BigDecimal) o).intValue();
			} else if (o instanceof Double) {
				return ((Double) o).intValue();
			} else if (o instanceof Float) {
				return ((Float) o).intValue();
			} else if (o instanceof Boolean) {
				return ((Boolean) o == true ? 1 : 0);
			} else if (o instanceof Byte) {
				return ((Byte) o).intValue();
			} else if (o instanceof String) {
				if (StringUtils.isNotEmpty((String)o)) {
					return Integer.parseInt((String)o);
				} else {
					return null;
				}
			} else if (o instanceof BigInteger) {
				return ((BigInteger) o).intValue();
			} else {
				return Integer.valueOf(String.valueOf(o));
			}
		} else {
			return null;
		}
	}

	public static BigInteger objectToBigInteger(Object o, Object objDefault) {
		BigInteger obj = objectToBigInteger(o);
		if (obj != null) {
			return (BigInteger) obj;
		} else {
			//return objDefault == null ? null : (Integer) objDefault;
			return objectToBigInteger(objDefault);
		}
	}

	public static BigInteger objectToBigInteger(Object o) {
		if (o != null) {
			if (o instanceof BigDecimal) {
				return ((BigDecimal) o).toBigInteger();
			} else if (o instanceof Integer) {
				return BigInteger.valueOf(((Integer) o).intValue());
			} else if (o instanceof Long) {
				return BigInteger.valueOf(((Long) o).longValue());
			} else if (o instanceof Double) {
				return BigInteger.valueOf(((Double) o).longValue());
			} else if (o instanceof Float) {
				return BigInteger.valueOf(((Float) o).longValue());
			} else if (o instanceof Short) {
				return BigInteger.valueOf(((Short) o).shortValue());
			} else if (o instanceof Boolean) {
				return BigInteger.valueOf( ((Boolean) o == true ? 1 : 0) );
			} else if (o instanceof Byte) {
				return BigInteger.valueOf(((Byte) o).byteValue());
			} else if (o instanceof String) {
				if (StringUtils.isNotEmpty((String)o)) {
					return new BigInteger((String)o);
				} else {
					return null;
				}
			} else if (o instanceof BigInteger) {
				return (BigInteger) o;
			} else {
				return new BigInteger(String.valueOf(o));
			}
		} else {
			return null;
		}
	}
	
	public static BigDecimal objectToBigDecimal(Object o, Object objDefault) {
		BigDecimal obj = objectToBigDecimal(o);
		if (obj != null) {
			return (BigDecimal) obj;
		} else {
			//return objDefault == null ? null : (Integer) objDefault;
			return objectToBigDecimal(objDefault);
		}
	}

	public static BigDecimal objectToBigDecimal(Object o) {
		if (o != null) {
			if (o instanceof BigDecimal) {
				return (BigDecimal) o;
			} else if (o instanceof Long) {
				return BigDecimal.valueOf(((Long) o).longValue());
			} else if (o instanceof Double) {
				return BigDecimal.valueOf(((Double) o).doubleValue());
			} else if (o instanceof Float) {
				return BigDecimal.valueOf(((Float) o).doubleValue());
			} else if (o instanceof Integer) {
				return BigDecimal.valueOf(((Integer) o).intValue());
			} else if (o instanceof Short) {
				return BigDecimal.valueOf(((Short) o).shortValue());
			} else if (o instanceof Boolean) {
				return BigDecimal.valueOf( ((Boolean) o == true ? 1 : 0) );
			} else if (o instanceof Byte) {
				return BigDecimal.valueOf(((Byte) o).byteValue());
			} else if (o instanceof String) {
				if (StringUtils.isNotEmpty((String)o)) {
					return new BigDecimal((String)o);
				} else {
					return null;
				}
			} else if (o instanceof BigInteger) {
				return new BigDecimal((BigInteger) o);
			} else {
				return new BigDecimal(String.valueOf(o));
			}
		} else {
			return null;
		}
	}

	public static Boolean objectToBoolean(Object o, Object objDefault) {
		Object obj = objectToBoolean(o);
		if (obj != null) {
			return (Boolean) obj;
		} else {
			return objectToBoolean(objDefault);
			//return objDefault == null ? null : objectToBoolean(objDefault);
		}
	}

	public static Boolean objectToBoolean(Object o) {
		if (o != null) {
			if (o instanceof Boolean) {
				return (Boolean) o;
			} else if (o instanceof Integer) {
				return ((Integer) o).byteValue() == byte_one ? true : false;
			} else if (o instanceof Short) {
				return ((Short) o).byteValue() == byte_one ? true : false;
			} else if (o instanceof Byte) {
				return ((Byte) o).byteValue() == byte_one ? true : false;
			} else if (o instanceof Long) {
				return ((Long) o).byteValue() == byte_one ? true : false;
			} else if (o instanceof Double) {
				return ((Double) o).byteValue() == byte_one ? true : false;
			} else if (o instanceof Float) {
				return ((Float) o).byteValue() == byte_one ? true : false;
			} else if (o instanceof BigDecimal) {
				return ((BigDecimal) o).byteValue() == byte_one ? true : false;
			} else if (o instanceof String) {
				String s = (String)o;
				if (StringUtils.isNotEmpty(s)) {
					if (StringUtils.equals(s, "1") || StringUtils.equals(s, "0")) {
						return s.equals("1") ? true : false;
					} else {
						return Boolean.parseBoolean(s);
					}
				} else {
					return null;
				}
			} else if (o instanceof BigInteger) {
				return ((BigInteger) o).byteValue() == byte_one ? true : false;
			} else {
				return Integer.valueOf(String.valueOf(o)) == byte_one ? true : false;
			}
		} else {
			return null;
		}
	}
	
	public static Long stringToLong(String s, Long objDefault) {
		Long l = stringToLong(s);
		if (l!=null) {
			return l;
		} else {
			return objDefault;
		}
	}
	
	public static Long stringToLong(String s) {
		try {
			return Long.valueOf(s);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	public static String objectToString(Object o, Object objDefault) {
		Object obj = objectToString(o);
		if (obj != null) {
			return (String) obj;
		} else {
			return objectToString(objDefault);
		}
	}
	
	public static String objectToString(Object o) {
		if (o != null) {
			if (o instanceof String) {
				return (String) o;
			} else {
				return String.valueOf(o);
			}
		} else {
			return null;
		}
	}

	public static int compareObject(Object o1, Object o2) {
		if (o1 == null && o2 == null) {
			return 0;
		} else if (o1 == null && o2 != null) {
			return 1;
		} else if (o1 != null && o2 == null) {
			return -1;
		} else {
			if (o1 instanceof String) {
				return ((String) o1).compareTo((String) o2);
			} else if (o1 instanceof Boolean || o1 instanceof Short
					|| o1 instanceof Integer) {
				Integer i1 = ObjectComm.objectToInteger(o1);
				Integer i2 = ObjectComm.objectToInteger(o2);
				return i1.compareTo(i2);
			} else if (o1 instanceof Long) {
				Long v1 = ObjectComm.objectToLong(o1);
				Long v2 = ObjectComm.objectToLong(o2);
				return v1.compareTo(v2);
			} else if (o1 instanceof Float || o1 instanceof Double) {
				Double v1 = ObjectComm.objectToDouble(o1);
				Double v2 = ObjectComm.objectToDouble(o2);
				return v1.compareTo(v2);
			} else if (o1 instanceof BigInteger) {
				BigInteger v1 = ObjectComm.objectToBigInteger(o1);
				BigInteger v2 = ObjectComm.objectToBigInteger(o2);
				return v1.compareTo(v2);
			} else if (o1 instanceof BigDecimal) {
				BigDecimal v1 = ObjectComm.objectToBigDecimal(o1);
				BigDecimal v2 = ObjectComm.objectToBigDecimal(o2);
				return v1.compareTo(v2);
			} else {
				return 0;
			}
		}
	}
	
	public static Date objectToDateTime(Object o) {
		if (o != null) {
			if (o instanceof Date) {
				return ((Date) o);
			} else if (o instanceof String) {
				return DateTimeComm.yyyyMMddHHmmssToDate((String) o);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static Date objectToDateTime(Object o, Date defaultValue) {
		Date date = objectToDateTime(o);
		if (date == null) {
			return defaultValue;
		} else {
			return date;
		}
	}
	
	public static Date objectToDate(Object o) {
		if (o != null) {
			if (o instanceof Date) {
				return ((Date) o);
			} else if (o instanceof String) {
				return DateTimeComm.yymmddToDate((String) o);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static Date objectToDate(Object o, Date defaultValue) {
		Date date = objectToDate(o);
		if (date == null) {
			return defaultValue;
		} else {
			return date;
		}
	}
	
	public static Date objectToDateFmt(Object o, String sFmt) {
		if (o != null) {
			if (o instanceof Date) {
				return ((Date) o);
			} else if (o instanceof String) {
				return DateTimeComm.stringToDate((String) o, sFmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public static Date objectToDateFmt(Object o, String sFmt, Date defaultValue) {
		Date date = objectToDateFmt(o, sFmt);
		if (date == null) {
			return defaultValue;
		} else {
			return date;
		}
	}
	
//	public static void main(String[] args) {
//		//String s="stid";
//		//Long l = stringToLong(s,0L);
//		Object o = null;
//		Long l = objectToLong(o, 0);
//		System.out.println(l);
//	}
	
}
