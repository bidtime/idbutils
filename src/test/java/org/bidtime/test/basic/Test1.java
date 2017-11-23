package org.bidtime.test.basic;

import java.sql.SQLException;

import org.bidtime.test.BasicTest;
import org.junit.Test;

/**
 * Created by bidtim on 2015/9/23.
 */
public class Test1 extends BasicTest {
	
	@Test
	public void findIdByCode() throws SQLException {
		Integer n = 100;
		print(n);
	}

}
