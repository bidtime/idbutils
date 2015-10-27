package org.bidtime.utils.comm;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

/**
 * @author jss
 * 
 *         提供对从ResultSet中取出数据,封装成ListMap的功能
 * 
 */
public class SimpleHashSet extends HashSet<String> {
	
	public SimpleHashSet() {
	}
	
//	public SimpleHashSet(Collection<? extends String> c) {
//		super(c);
//	}
	
	public SimpleHashSet(Collection<? extends String> c) {
		for (String s : c) {
			this.add(s);
		}
	}


	/**
	 * Required for serialization support.
	 * 
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = -2848100435296897392L;

	/** {@inheritDoc} */
	@Override
	public boolean contains(Object key) {
		Object realKey = key.toString().toLowerCase(Locale.ENGLISH);
		return super.contains(realKey);
		// Possible optimisation here:
		// Since the lowerCaseMap contains a mapping for all the keys,
		// we could just do this:
		// return lowerCaseMap.containsKey(key.toString().toLowerCase());
	}

	/** {@inheritDoc} */
//	@Override
//	public Object get(Object key) {
//		Object realKey = lowerCaseMap.get(key.toString().toLowerCase(
//				Locale.ENGLISH));
//		return super.get(realKey);
//	}

	/** {@inheritDoc} */
	@Override
	public boolean add(String key) {
		/*
		 * In order to keep the map and lowerCaseMap synchronized, we have to
		 * remove the old mapping before putting the new one. Indeed, oldKey and
		 * key are not necessaliry equals. (That's why we call
		 * super.remove(oldKey) and not just super.put(key, value))
		 */
		return super.add(key.toLowerCase(Locale.ENGLISH));
	}

	/** {@inheritDoc} */
//	@Override
//	public void putAll(Map<? extends String, ?> m) {
//		for (Map.Entry<? extends String, ?> entry : m.entrySet()) {
//			String key = entry.getKey();
//			Object value = entry.getValue();
//			this.put(key, value);
//		}
//	}

	/** {@inheritDoc} */
	@Override
	public boolean remove(Object key) {
		return super.remove(key.toString().toLowerCase(
				Locale.ENGLISH));
	}

}
