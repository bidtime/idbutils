package org.bidtime.test;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

/**
 * Created by bidtime on 2015/11/6. Basic Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config.xml", "classpath:spring-dataSource.xml" })

@Ignore
public class BasicTest {

	// public Page newPage() {
	// return newPage(1, 10);
	// }
	//
	// public Page newPage(Integer size) {
	// return newPage(1, size);
	// }
	//
	// public Page newPage(Integer idx, Integer size) {
	// Page page = new Page();
	// page.setNo(idx);
	// page.setSize(size);
	// return page;
	// }

	public int getId(int nextInt) {
		java.util.Random random = new java.util.Random();// 定义随机类
		int result = random.nextInt(nextInt);// 返回[0,10)集合中的整数，注意不包括10
		return result + 1; // +1后，[0,10)集合变为[1,11)集合，满足要求
	}

	public void print(Object dto) {
		System.out.println("---- Result ------------------");
		if (dto != null) {
			System.out.println(JSON.toJSON(dto));
		} else {
			System.out.println("null");
		}
		System.out.println("---- end ---------------------");
	}

}
