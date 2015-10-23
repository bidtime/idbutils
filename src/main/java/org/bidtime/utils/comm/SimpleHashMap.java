package org.bidtime.utils.comm;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author jss
 * 
 *         提供对从ResultSet中取出数据,封装成ListMap的功能
 * 
 */
public class SimpleHashMap<V> extends HashMap<String, V> {
	
	public SimpleHashMap() {
	}

	/**
	 * Required for serialization support.
	 * 
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = -2848100435296897392L;

	/** {@inheritDoc} */
	@Override
	public boolean containsKey(Object key) {
		Object realKey = key.toString().toLowerCase(Locale.ENGLISH);
		return super.containsKey(realKey);
	}

	/** {@inheritDoc} */
	@Override
	public V get(Object key) {
		Object realKey = key.toString().toLowerCase(Locale.ENGLISH);
		return super.get(realKey);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public V put(String key, Object value) {
		/*
		 * In order to keep the map and lowerCaseMap synchronized, we have to
		 * remove the old mapping before putting the new one. Indeed, oldKey and
		 * key are not necessaliry equals. (That's why we call
		 * super.remove(oldKey) and not just super.put(key, value))
		 */
		String realKey = key.toString().toLowerCase(Locale.ENGLISH);
		return super.put(realKey, (V)value);
	}

	/** {@inheritDoc} */
	@Override
	public void putAll(Map<? extends String, ? extends V> m) {
		for (Map.Entry<? extends String, ? extends V> entry : m.entrySet()) {
			this.put(entry.getKey(), entry.getValue());
		}
	}

	/** {@inheritDoc} */
	@Override
	public V remove(Object key) {
		return super.remove(key.toString().toLowerCase(
				Locale.ENGLISH));
	}
	
	protected String convertKey(String key) {
		return key.toLowerCase(Locale.ENGLISH);
	}

}
