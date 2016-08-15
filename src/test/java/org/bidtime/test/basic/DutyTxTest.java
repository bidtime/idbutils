package org.bidtime.test.basic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.bidtime.basicdata.duty.bean.Duty;
import org.bidtime.basicdata.duty.service.DutyServiceTx;
import org.bidtime.dbutils.gson.ResultDTO;
import org.bidtime.dbutils.jdbc.rs.handle.BeanDTOHandler;
import org.bidtime.test.BasicTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by bidtime on 2015/9/23.
 */
public class DutyTxTest extends BasicTest {

	@Autowired
	protected DutyServiceTx service;

	@Test
	public void test_insert() throws SQLException {
		Duty duty = new Duty();
		duty.setName("销售部");
		int n = service.insert(duty);
		System.out.println("insert: " + n);
	}

	@Test
	public void test_insertList() throws SQLException {
		List<Duty> list = new ArrayList<>();
		//
		Duty duty1 = new Duty();
		duty1.setName("1 dept");
		list.add(duty1);
		//
		Duty duty2 = new Duty();
		duty2.setName("2 dept");
		duty2.setCode("002");
		list.add(duty2);
		//
		int n = service.insert(list);
		System.out.println("insert: " + n);
	}

	@Test
	public void test_updateList() throws SQLException {
		List<Duty> list = new ArrayList<>();
		//
		Duty duty1 = new Duty();
		duty1.setId(7L);
		duty1.setName("11 dept");
		list.add(duty1);
		//
		Duty duty2 = new Duty();
		duty2.setId(8L);
		duty2.setName("22 dept");
		duty2.setCode("022");
		list.add(duty2);
		//
		int n = service.update(list);
		System.out.println("update: " + n);
	}

	@Test
	public void test_insertForPK() throws SQLException {
		Duty duty = new Duty();
		duty.setName("销售部");
		Long id = service.insertForPK(duty);
		System.out.println("insert: pk-> " + id);
	}

//	@Test
//	public void test_insertForPK_all() throws SQLException {
//		Duty duty = new Duty();
//		duty.setName("销售部");
//		Long id = service.insertForPK(duty);
//		System.out.println("insert: pk -> " + id);
//		//
//		duty.setId(id);
//		duty.setName("销售部-");
//		//
//		int nUpdate = service.update(duty);
//		System.out.println("update: " + nUpdate);
//		//
//		int nDelete = service.delete(duty);
//		System.out.println("delete: " + nDelete);
//	}

	@Test
	public void test_update() throws SQLException {
		Duty duty = new Duty();
		duty.setId(7L);
		duty.setName("销售部-");
		int n = service.update(duty);
		System.out.println("update: " + n);
	}

	@Test
	public void test_delete() throws Exception {
		Duty duty = new Duty();
		duty.setId(5L);
		int n = service.delete(duty);
		System.out.println("update: " + n);
	}

	@Test
	public void test_deleteArray() throws SQLException {
		Object[] ids = new Object[]{2L, 8L};
		int n = service.delete(ids);
		System.out.println("update: " + n);
	}

	@Test
	public void test_deleteList() throws SQLException {
		List<Object> list = new ArrayList<>();
		list.add(9L);
		int n = service.delete(list);
		System.out.println("update: " + n);
	}

	@Test
	public void test_getBeanDTOHandler() throws SQLException {
		BeanDTOHandler<Duty> h = new BeanDTOHandler<>(Duty.class);
		ResultDTO<Duty> dto = service.list(h);
		if (dto != null) {
			System.out.println(dto);
		} else {
			System.out.println("null");
		}
	}

//	@Test
//	public void test_getBeanPageHandler() throws SQLException {
//		Page page = newPage(4, 2);
//		BeanPageHandler<Duty> h = new BeanPageHandler<>(Duty.class, page);
//		ResultPage<Duty> dto = service.list(h);
//		if (dto != null) {
//			System.out.println(dto);
//		} else {
//			System.out.println("null");
//		}
//		System.out.println("page:" + page.toString());
//	}
//
//	@Test
//	public void test_getBeanListPageHandler() throws SQLException {
//		Page page = newPage(1, 2);
//		BeanListPageHandler<Duty> h = new BeanListPageHandler<>(Duty.class, page, false);
//		ResultData<List<Duty>> dto = service.list(h);
//		if (dto != null) {
//			System.out.println(dto);
//		} else {
//			System.out.println("null");
//		}
//		System.out.println("page:" + page.toString());
//	}
//
//	@Test
//	public void test_getBeanListDataHandler() throws SQLException {
//		Page page = newPage(1, 2);
//		BeanListDataHandler<Duty> h = new BeanListDataHandler<>(Duty.class, page);
//		ResultData<List<Duty>> dto = service.list(h);
//		if (dto != null) {
//			System.out.println(dto);
//		} else {
//			System.out.println("null");
//		}
//		System.out.println("page:" + page.toString());
//	}
//
//	@Test
//	public void test_getBeanDataHandler() throws SQLException {
//		Page page = newPage(1, 2);
//		BeanDataHandler<Duty> h = new BeanDataHandler<>(Duty.class, page);
//		ResultData<Duty> dto = service.list(h);
//		if (dto != null) {
//			System.out.println(dto);
//		} else {
//			System.out.println("null");
//		}
//	}
//
//	@Test
//	public void test_getMapDataHandler() throws SQLException {
//		Page page = newPage(1, 2);
//		MapDataHandler h = new MapDataHandler(page);
//		ResultData<Map<String, Object>> dto = service.list(h);
//		if (dto != null) {
//			System.out.println(dto);
//		} else {
//			System.out.println("null");
//		}
//	}
//
//	@Test
//	public void test_getMapPageHandler() throws SQLException {
//		Page page = newPage(1, 2);
//		MapPageHandler h = new MapPageHandler(page);
//		ResultPage<Map<String, Object>> dto = service.list(h);
//		if (dto != null) {
//			System.out.println(dto);
//		} else {
//			System.out.println("null");
//		}
//	}

	@Test
	public void test_info() throws SQLException {
		BeanDTOHandler<Duty> h = new BeanDTOHandler<>(Duty.class);
		ResultDTO<Duty> dto = service.info(h, 0);
		if (dto != null) {
			System.out.println(dto);
		} else {
			System.out.println("null");
		}
	}

	@Test
	public void test_info_() throws SQLException {
		BeanHandler<Duty> h = new BeanHandler<>(Duty.class);
		Duty dto = service.info(h, 0);
		if (dto != null) {
			System.out.println(dto);
		} else {
			System.out.println("null");
		}
	}

}
