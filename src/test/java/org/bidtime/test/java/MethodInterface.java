package org.bidtime.test.java;

import org.bidtime.utils.proxy.OperDataParam;
import org.bidtime.utils.proxy.OperExistsParam;

public interface MethodInterface {

	String helloWorld();

	@OperDataParam(value="菜单功能")
	@OperExistsParam(value="不存在")
	String helloWorld(String name);
	
}
