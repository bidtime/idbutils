package org.bidtime.test.mapper.test;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bidtime.basicdata.duty.bean.Duty;
import org.bidtime.basicdata.duty.bean.DutyCamel;
import org.bidtime.dbutils.gson.ResultDTO;
import org.bidtime.dbutils.jdbc.rs.handle.BeanDTOHandler;
import org.bidtime.dbutils.jdbc.rs.handle.BeanHandler;
import org.bidtime.dbutils.jdbc.rs.handle.BeanListDTOHandler;
import org.bidtime.dbutils.jdbc.rs.handle.BeanListHandler;
import org.bidtime.test.BasicTest;
import org.bidtime.test.mapper.dao.Duty2DAO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by bidtim on 2015/9/23.
 */
public class DutyDAO2Test extends BasicTest {

	@Autowired
	protected Duty2DAO dao;

  @Test
  public void test_beanList() throws SQLException {
    BeanListHandler<Duty> h = new BeanListHandler<Duty>(Duty.class);
    Map<String, Object> params = new HashMap<>();
    Collection<Duty> dto = dao.selectByQuery(h, params);
    print(dto);
  }

  @Test
  public void test_beanList_camel() throws SQLException {
    BeanListHandler<DutyCamel> h = new BeanListHandler<>(DutyCamel.class);
    Map<String, Object> params = new HashMap<>();
    Collection<DutyCamel> dto = dao.selectByQueryCamel(h, params);
    print(dto);
  }
	
	@Test
	public void test_beanList_DTO() throws SQLException {
		BeanListDTOHandler<Duty> h = new BeanListDTOHandler<>(Duty.class, 20);
		Map<String, Object> params = new HashMap<>();
		ResultDTO<Collection<Duty>> dto = dao.selectByQuery(h, params);
		print(dto);
	}

	@Test
	public void test_bean() throws SQLException {
		BeanHandler<Duty> h = new BeanHandler<>(Duty.class);
		Map<String, Object> params = new HashMap<>();
		Duty dto = dao.selectByQuery(h, params);
		print(dto);
	}

	@Test
	public void test_bean_dto() throws SQLException {
		BeanDTOHandler<Duty> h = new BeanDTOHandler<Duty>(Duty.class);
		Map<String, Object> params = new HashMap<>();
		ResultDTO<Duty> dto = dao.selectByQuery(h, params);
		print(dto);
	}

//	@Test
//	public void test_select_bean() throws SQLException {
//		BeanHandler<Duty> h = new BeanHandler<Duty>(Duty.class);
//		Map<String, Object> params = new HashMap<>();
//		Duty dto = dao.selectByQuery(h, params);
//		print(dto);
//	}

	@Test
	public void test_insert() throws SQLException {
		Long id = Long.valueOf(getId(10));
		Duty bean = new Duty();
		bean.setId(id);
		bean.setCode("code_" + id);
		bean.setCode("name_" + id);
		int n = dao.insert(bean);
		print(n);
	}

	@Test
	public void test_update() throws SQLException {
		Duty bean = new Duty();
		bean.setId(19L);
		Long id = Long.valueOf(getId(10));
		bean.setCode("code_" + id);
		bean.setCode("name_" + id);
		int n = dao.update(bean);
		print(n);
	}

	@Test
	public void test_delete_bean() throws SQLException {
		Duty bean = new Duty();
		bean.setId(19L);
		int n = dao.update(bean);
		print(n);
	}

	@Test
	public void test_delete_id() throws SQLException {
		Long id = 19L;
		int n = dao.delete(id);
		print(n);
	}

//	@Test
//	public void test_info_list() throws SQLException {
//		BeanListHandler<Duty> h = new BeanListHandler<Duty>(Duty.class);
//		List<Duty> dto = service.list(h);
//		print(dto);
//	}
//	
//	@Test
//	public void findIdByCode() throws SQLException {
//		List<Long> dto = service.findIdByCode("01");
//		print(dto);
//	}

}
