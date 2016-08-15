package org.bidtime.test;

import org.bidtime.utils.spring.SpringContextUtils;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by bidtime on 2015/11/6. Basic Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config.xml",
		"classpath:spring-dataSource.xml"
	})

@Ignore
public class BasicTest implements ApplicationContextAware {

	// protected GenericXmlApplicationContext c;
	protected ApplicationContext ctx;

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.ctx = arg0;
		SpringContextUtils.setContext(arg0);
	}

//	public Page newPage() {
//		return newPage(1, 10);
//	}
//
//	public Page newPage(Integer size) {
//		return newPage(1, size);
//	}
//
//	public Page newPage(Integer idx, Integer size) {
//		Page page = new Page();
//		page.setNo(idx);
//		page.setSize(size);
//		return page;
//	}
	
}
