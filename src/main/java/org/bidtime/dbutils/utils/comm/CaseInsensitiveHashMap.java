package org.bidtime.dbutils.utils.comm;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author jss
 * 
 *         提供对从ResultSet中取出数据,封装成ListMap的功能
 * 
 */
@SuppressWarnings("serial")
public class CaseInsensitiveHashMap<V> extends HashMap<String, V> {

	private final Map<String, String> lowerCaseMap = new HashMap<String, String>();
	
	public CaseInsensitiveHashMap() {
	}

	/**
	 * Required for serialization support.
	 * 
	 * @see java.io.Serializable
	 */

	/** {@inheritDoc} */
	@Override
	public boolean containsKey(Object key) {
		Object realKey = lowerCaseMap.get(key.toString().toLowerCase(
				Locale.ENGLISH));
		return super.containsKey(realKey);
		// Possible optimisation here:
		// Since the lowerCaseMap contains a mapping for all the keys,
		// we could just do this:
		// return lowerCaseMap.containsKey(key.toString().toLowerCase());
	}

	/** {@inheritDoc} */
	@Override
	public V get(Object key) {
		Object realKey = lowerCaseMap.get(key.toString().toLowerCase(
				Locale.ENGLISH));
		return super.get(realKey);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public V put(String key, V value) {
		/*
		 * In order to keep the map and lowerCaseMap synchronized, we have to
		 * remove the old mapping before putting the new one. Indeed, oldKey and
		 * key are not necessaliry equals. (That's why we call
		 * super.remove(oldKey) and not just super.put(key, value))
		 */
        Object oldKey = lowerCaseMap.put(key.toLowerCase(Locale.ENGLISH), key);
        Object oldValue = super.remove(oldKey);
        super.put(key, value);
        return (V)oldValue;
	}

	/** {@inheritDoc} */
	@Override
	public void putAll(Map<? extends String, ? extends V> m) {
		for (Map.Entry<? extends String, ? extends V> entry : m.entrySet()) {
			//String key = entry.getKey();
			//Object value = entry.getValue();
			this.put(entry.getKey(), entry.getValue());
		}
	}

	/** {@inheritDoc} */
	@Override
	public V remove(Object key) {
		Object realKey = lowerCaseMap.remove(key.toString().toLowerCase(
				Locale.ENGLISH));
		return super.remove(realKey);
	}

}
