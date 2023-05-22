package org.bidtime.test.java;

import java.util.ArrayList;
import java.util.List;

public class TestJavaClass {

	public static boolean isJavaClass(Class<?> clz) {
		return clz != null && clz.getClassLoader() == null;
	}

	public static void main(String... args) {
		// System.out.println(isJavaClass(Integer.class)); // true
		// System.out.println(isJavaClass(Long.class)); // true
		// System.out.println(isJavaClass(TestJavaClass.class)); // false
		//
		//test1();
		List<Integer> list = new ArrayList<>(100);
		System.out.println(list);
	}

//	private static void test1() {
//		//Object[] oo = new Object[] { 11, 12 };
//		Map<String, Object> oo = new HashMap<>();
//		Object o = oo;
//		if (o instanceof Collection) {
//			System.out.println("is collection");
//		} else if (o instanceof Object[]) {
//			System.out.println("is object[]");
//		} else if (o instanceof Map) {
//			System.out.println("is object[]");
//		} else {
//			System.out.println("is not collection");
//		}
//	}

}
