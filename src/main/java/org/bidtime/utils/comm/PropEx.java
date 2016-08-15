package org.bidtime.utils.comm;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import org.bidtime.utils.basic.ObjectComm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

@SuppressWarnings("serial")
public class PropEx extends Properties {

	private static final Logger logger = LoggerFactory.getLogger(PropEx.class);

	protected String propFile = null;

	public String getPropFile() {
		return propFile;
	}

	public void setPropFile(String propFile) {
		if (FileUtils.isAbsolute(propFile)) {
			this.propFile = propFile;
		} else {
			this.propFile = FileUtils.mergeSubPath(FileUtils.getPath(), propFile);
		}
	}

	public Properties getProp() {
		return this;
	}

	public PropEx() {

	}

	public PropEx(String fileName) {
		setPropFile(fileName);
	}

	public String getString(Object key) {
		return String.valueOf(get(key));
	}

	public String getString(Object key, String sDefault) {
		Object o = get(key);
		if (o != null) {
			return String.valueOf(o);
		} else {
			return sDefault;
		}
	}

	public Integer getInteger(Object key) {
		Object o = get(key);
		return ObjectComm.objectToInteger(o);
	}

	public Integer getInteger(Object key, Integer nDefault) {
		Object o = get(key);
		return ObjectComm.objectToInteger(o, nDefault);
	}

	public Boolean getBoolean(Object key) {
		Object o = get(key);
		return ObjectComm.objectToBoolean(o);
	}

	public Boolean getBoolean(Object key, Boolean nDefault) {
		Object o = get(key);
		return ObjectComm.objectToBoolean(o, nDefault);
	}

	public Boolean getBoolean(Object key, Integer nDefault) {
		Object o = get(key);
		return ObjectComm.objectToBoolean(o, nDefault);
	}

	public Long getLong(Object key) {
		Object o = get(key);
		return ObjectComm.objectToLong(o);
	}

	public Long getLong(Object key, Long lDefault) {
		Object o = get(key);
		return ObjectComm.objectToLong(o, lDefault);
	}

	public void loadOutJar() {
		loadOutJar(this.propFile, true);
	}

	public void loadOutJar(boolean clear) {
		loadOutJar(this.propFile, clear);
	}

	private void loadOutJar(String fileName, boolean clear) {
		if (clear) {
			clear();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("load bef...");
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(propFile);
			load(fis);
		} catch (Exception e) {
			logger.error("loadOutJar", e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				logger.error("loadOutJar", e);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("load aft.");
		}
	}

	public void loadFromXML() {
		loadFromXML(this.propFile, true);
	}

	public void loadFromXML(boolean clear) {
		loadFromXML(this.propFile, clear);
	}

	private void loadFromXML(String fileName, boolean clear) {
		if (clear) {
			clear();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("load xml bef...");
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(propFile);
			super.loadFromXML(fis);
		} catch (Exception e) {
			logger.error("loadFromXML: loadFromXml(" + fileName + ")", e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				logger.error("loadFromXML: close(" + fileName + ")", e);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("load xml aft.");
		}
	}

	public void store() {
		this.store(propFile, null);
	}

	public void store(String comments) {
		this.store(propFile, comments);
	}

	private void store(String fileName, String comment) {
		if (logger.isDebugEnabled()) {
			logger.debug("store bef..." + propFile);
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(propFile);
			this.store(fos, comment);
		} catch (Exception e) {
			logger.error("loadOfSrc: store(" + fileName + ")", e);
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				logger.error("loadOfSrc: close(" + fileName + ")", e);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("store aft.");
		}
	}

	public void storeToXML() {
		storeToXML(this.propFile, null, "UTF-8");
	}

	public void storeToXML(String comment) {
		storeToXML(this.propFile, comment, "UTF-8");
	}

	public void storeToXML(String comment, String encoding) {
		storeToXML(this.propFile, comment, encoding);
	}

	private void storeToXML(String fileName, String comment, String encoding) {
		if (logger.isDebugEnabled()) {
			logger.debug("storeToXML bef..." + propFile);
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(propFile);
			logger.info("save bef...");
			super.storeToXML(fos, comment, encoding);
			logger.info("save aft.");
		} catch (Exception e) {
			logger.error("storeToXML: (" + fileName + ")", e);
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				logger.error("storeToXML: close(" + fileName + ")", e);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("storeToXML aft.");
		}
	}

	public boolean loadOfSrc(String fileName) {
		boolean result = false;
		InputStream input = null;
		try {
			input = FileUtils.getInputStream(fileName);
			if (input != null) {
				load(input);
				result = true;
			}
		} catch (Exception e) {
			logger.error("loadOfSrc: open(" + fileName + ")", e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("loadOfSrc: close(" + fileName + ")", e);
				}
			}
		}
		return result;
	}

	public boolean loadOfSrcSlient(String fileName) {
		boolean result = false;
		InputStream input = null;
		try {
			input = FileUtils.getInputStream(fileName);
			if (input != null) {
				load(input);
				result = true;
			}
		} catch (Exception e) {
			logger.warn("loadOfSrc: open(" + fileName + ")");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("loadOfSrc: close(" + fileName + ")", e);
				}
			}
		}
		return result;
	}

//	public <T> T mapToClazz(Class<T> type) throws Exception {
//		return JSONHelper.mapToClazz(this, type);
//	}
//
//	public void clazzToMap(Object object, boolean clear) {
//		Map<String, Object> map = JSONHelper.clazzToMap(object);
//		if (clear) {
//			this.clear();
//		}
//		putAll(map);
//	}

//	public void clazzToMap(Object object) {
//		clazzToMap(object, false);
//	}

    private static void writeComments(BufferedWriter bw, String comments)
    		throws IOException {
        bw.write("#");
        int len = comments.length();
        int current = 0;
        int last = 0;
        char[] uu = new char[6];
        uu[0] = '\\';
        uu[1] = 'u';
        while (current < len) {
            char c = comments.charAt(current);
            if (c > '\u00ff' || c == '\n' || c == '\r') {
                if (last != current)
                    bw.write(comments.substring(last, current));
                if (c > '\u00ff') {
                    uu[2] = toHex((c >> 12) & 0xf);
                    uu[3] = toHex((c >>  8) & 0xf);
                    uu[4] = toHex((c >>  4) & 0xf);
                    uu[5] = toHex( c        & 0xf);
                    bw.write(new String(uu));
                } else {
                    bw.newLine();
                    if (c == '\r' &&
                        current != len - 1 &&
                        comments.charAt(current + 1) == '\n') {
                        current++;
                    }
                    if (current == len - 1 ||
                        (comments.charAt(current + 1) != '#' &&
                        comments.charAt(current + 1) != '!'))
                        bw.write("#");
                }
                last = current + 1;
            }
            current++;
        }
        if (last != current)
            bw.write(comments.substring(last, current));
        bw.newLine();
    }
    
    public void store(OutputStream out, String comments)
    		throws IOException {
    	store(out, comments, null);
    }
    
    public void store(OutputStream out, String comments, String fmt)
    		throws IOException {
    	store0(new BufferedWriter(new OutputStreamWriter(out, "8859_1")),
    			comments, true, fmt);
    }
    
	private static String dateToyyyyMMddHHmmss(Date date) {
		return dateToString(date, "yyyy-MM-dd HH:mm:ss");
	}
    
	private static String dateToString(Date date, String fmt) {
		java.text.DateFormat df = new java.text.SimpleDateFormat(fmt);
		return df.format(date);
	}

    @SuppressWarnings("rawtypes")
	private void store0(BufferedWriter bw, String comments, boolean escUnicode
    		, String fmt) throws IOException {
    	if (comments != null) {
    		writeComments(bw, comments);
    	}
		bw.write("#" + new Date().toString());
		bw.newLine();
		synchronized (this) {
			for (Enumeration e = keys(); e.hasMoreElements();) {
				String key = (String)e.nextElement();
				Object val = get(key);
				String valRet = null;
				if (val == null) {
					valRet = "";
				} else if (val instanceof java.util.Date) {
					if (StringUtils.isEmpty(fmt)) {
						valRet = dateToyyyyMMddHHmmss((Date)val);
					} else {
						valRet = dateToString((Date)val, fmt);
					}
				} else {
					valRet = ObjectComm.objectToString(get(key));
				}
				key = saveConvert(key, true, escUnicode);
				val = saveConvert(valRet, false, escUnicode);
				bw.write(key + "=" + val);
				bw.newLine();
			}
		}
		bw.flush();
    }

    /*
     * Converts unicodes to encoded &#92;uxxxx and escapes
     * special characters with a preceding slash
     */
    private String saveConvert(String theString,
                               boolean escapeSpace,
                               boolean escapeUnicode) {
        int len = theString.length();
        int bufLen = len * 2;
        if (bufLen < 0) {
            bufLen = Integer.MAX_VALUE;
        }
        StringBuffer outBuffer = new StringBuffer(bufLen);

        for(int x=0; x<len; x++) {
            char aChar = theString.charAt(x);
            // Handle common case first, selecting largest block that
            // avoids the specials below
            if ((aChar > 61) && (aChar < 127)) {
                if (aChar == '\\') {
                    outBuffer.append('\\'); outBuffer.append('\\');
                    continue;
                }
                outBuffer.append(aChar);
                continue;
            }
            switch(aChar) {
                case ' ':
                    if (x == 0 || escapeSpace)
                        outBuffer.append('\\');
                    outBuffer.append(' ');
                    break;
                case '\t':outBuffer.append('\\'); outBuffer.append('t');
                          break;
                case '\n':outBuffer.append('\\'); outBuffer.append('n');
                          break;
                case '\r':outBuffer.append('\\'); outBuffer.append('r');
                          break;
                case '\f':outBuffer.append('\\'); outBuffer.append('f');
                          break;
                case '=': // Fall through
                case ':': // Fall through
                case '#': // Fall through
                case '!':
                    outBuffer.append('\\'); outBuffer.append(aChar);
                    break;
                default:
                    if (((aChar < 0x0020) || (aChar > 0x007e)) & escapeUnicode ) {
                        outBuffer.append('\\');
                        outBuffer.append('u');
                        outBuffer.append(toHex((aChar >> 12) & 0xF));
                        outBuffer.append(toHex((aChar >>  8) & 0xF));
                        outBuffer.append(toHex((aChar >>  4) & 0xF));
                        outBuffer.append(toHex( aChar        & 0xF));
                    } else {
                        outBuffer.append(aChar);
                    }
            }
        }
        return outBuffer.toString();
    }

    /**
     * Convert a nibble to a hex character
     * @param   nibble  the nibble to convert.
     */
    private static char toHex(int nibble) {
        return hexDigit[(nibble & 0xF)];
    }
    
    /** A table of hex digits */
    private static final char[] hexDigit = {
        '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
    };

}
