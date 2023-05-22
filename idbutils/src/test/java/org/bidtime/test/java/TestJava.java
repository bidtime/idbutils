package org.bidtime.test.java;

import java.sql.SQLException;

import org.bidtime.dbutils.utils.proxy.ClzUtils;
import org.bidtime.dbutils.utils.proxy.OperDataParam;
import org.bidtime.dbutils.utils.proxy.OperExistsParam;
import org.bidtime.test.BasicTest;
import org.junit.Test;

/**
 * Created by bidtime on 2017/8/31.
 */
public class TestJava extends BasicTest {
	
//	@Test
//	public void test_method() throws SQLException {
//		MethodInterface method = MethodProxyFactory.newInstance(MethodInterface.class);
//		method.helloWorld();
//	}
//	
//	@Test
//	public void test_method_1() throws SQLException {
//		MethodInterface method = MethodProxyFactory.newInstance(MethodInterface.class);
//		String s = method.helloWorld("jss");
//		print(s);
//	}
	
	@Test
	@OperDataParam(value="菜单功能1")
	@OperExistsParam(value="不存在1")
	public void test_anno() throws SQLException {
		print(test_anno_1());
	}

	private String test_anno_1() throws SQLException {
		return test_anno_2();
	}

	private String test_anno_2() throws SQLException {
		return ClzUtils.getOperExistsParamValue(this.getClass());
	}

}
