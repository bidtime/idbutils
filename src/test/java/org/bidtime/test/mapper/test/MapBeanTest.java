package org.bidtime.test.mapper.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bidtime.dbutils.base.utils.BeanMapUtils;
import org.bidtime.test.BasicTest;
import org.junit.Test;

/**
 * Created by bidtim on 2015/9/23.
 */
public class MapBeanTest extends BasicTest {

//	private static List<A> getListA() {
//		return null;
//	}

	private static A getOneA() {
		A a = new A();
		//
		List<B> listB = new ArrayList<>();
		for (int i=0; i<10; i++) {
			B b = new B();
			b.setDetailId(i);
			listB.add(b);
		}
		a.setList(listB);
		//
		C c = new C();
		c.setCid(12);
		c.setCname("cname" + 12);
		//
		D d = new D();
		d.setDid(9);
		d.setDname("dname" + 9);
		c.setD(d);
		//
		a.setC(c);
		return a;
	}

	@Test
	public void test_mapToBean() throws Exception {
		
		Map<String, Object> map = BeanMapUtils.beanToMap(getOneA());
		Object oo = map.get("c.d");
		print(oo);
		print(map);		
	}
	
//	public static Object getKey(Object map, String fullKey) {
//		String[] arKey = fullKey.split(".");
//		Object o = null;
//		for (String s : arKey) {
//			if (o == null) {
//				o = map.get(s);
//			} else {
//			}
//		}
//		
//		return o;
//	}

}
