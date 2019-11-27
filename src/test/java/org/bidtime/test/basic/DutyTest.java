package org.bidtime.test.basic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.bidtime.basicdata.duty.bean.Duty;
import org.bidtime.basicdata.duty.bean.DutyCamel;
import org.bidtime.basicdata.duty.service.DutyService;
import org.bidtime.test.BasicTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by bidtim on 2015/9/23.
 */
public class DutyTest extends BasicTest {

	@Autowired
	protected DutyService service;

	@Test
	public void test_insert() throws SQLException {
		Duty duty = new Duty();
		duty.setName("销售部");
		int n = service.insert(duty);
		System.out.println("insert: " + n);
	}

	@Test
	public void test_insertIgnore() throws SQLException {
		Duty duty = new Duty();
		duty.setId(3L);
		duty.setCode("D-3");
		duty.setName("销售部");
		int n = service.insertIgnore(duty);
		System.out.println("insert: " + n);
	}

	@Test
	public void test_insertIgnoreList() throws SQLException {
		List<Duty> list = new ArrayList<Duty>();
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
		int n = service.insertIgnore(list);
		System.out.println("insert: " + n);
	}

	@Test
	public void test_insertList() throws SQLException {
		List<Duty> list = new ArrayList<Duty>();
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
		List<Duty> list = new ArrayList<Duty>();
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

	@Test
	public void test_insertForPK_all() throws SQLException {
		Duty duty = new Duty();
		duty.setName("销售部");
		Long id = service.insertForPK(duty);
		System.out.println("insert: pk -> " + id);
		//
		duty.setId(id);
		duty.setName("销售部-");
		//
		int nUpdate = service.update(duty);
		System.out.println("update: " + nUpdate);
		//
		int nDelete = service.delete(duty);
		System.out.println("delete: " + nDelete);
	}

	@Test
	public void test_update() throws SQLException {
		Duty duty = new Duty();
		duty.setId(7L);
		duty.setName("销售部-");
		int n = service.update(duty);
		System.out.println("update: " + n);
	}

	@Test
	public void test_delete() throws SQLException {
		Duty duty = new Duty();
		duty.setId(3L);
		int n = service.delete(duty);
		System.out.println("update: " + n);
	}

	@Test
	public void test_delete_heads() throws SQLException {
		Duty duty = new Duty();
		duty.setId(3L);
		duty.setCode("e");
		int n = service.delete(duty, new String[]{"dutycode", "dutyid"});
		System.out.println("update: " + n);
	}

	@Test
	public void test_deleteMap() throws SQLException {
		Map<String, Object> map = new HashMap<>();
		map.put("dutyid", 3L);
		map.put("dutycode", "e");
		int n = service.delete(map, new String[]{"dutyid", "dutycode"});
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
		List<Object> list = new ArrayList<Object>();
		list.add(9L);
		int n = service.delete(list);
		System.out.println("update: " + n);
	}

//	@Test
//	public void test_getBeanDTOHandler() throws SQLException {
//		BeanDTOHandler<Duty> h = new BeanDTOHandler<Duty>(Duty.class);
//		ResultDTO<Duty> dto = service.list(h);
//		print(dto);		
//	}
//
//	@Test
//	public void test_info_dto() throws SQLException {
//		BeanDTOHandler<Duty> h = new BeanDTOHandler<Duty>(Duty.class);
//		ResultDTO<Duty> dto = service.info(h, 1);
//		print(dto);		
//	}
//
//	@Test
//	public void test_column_set_dto() throws SQLException {	
//		ColumnSetDTOHandler<Long> h = new ColumnSetDTOHandler<>(new SetCallback<Long>() {
//		    @Override
//		    public Set<Long> callback() {
//		    	return new HashSet<Long>();
//		    }
//		});
//		ResultDTO<Set<Long>> dto = service.list(h);
//		print(dto);		
//	}
//
//	@Test
//	public void test_column_set() throws SQLException {	
//		ColumnSetHandler<Long> h = new ColumnSetHandler<>(new SetCallback<Long>() {
//		    @Override
//		    public Set<Long> callback() {
//		    	return new HashSet<Long>();
//		    }
//		});
//		Set<Long> dto = service.list(h);
//		print(dto);		
//	}
//
//	@Test
//	public void test_list() throws SQLException {
//		BeanListDTOHandler<Duty> h = new BeanListDTOHandler<Duty>(Duty.class);
//		ResultDTO<List<Duty>> dto = service.list(h);
//		print(dto);		
//	}
//	
//	@Test
//	public void test_set() throws SQLException {
//		ColumnSetHandler<String> h = new ColumnSetHandler<>("code");
//		Set<String> dto = service.list(h);
//		print(dto);		
//	}
//	
//	@Test
//	public void test_set_dto() throws SQLException {
//		ColumnSetDTOHandler<String> h = new ColumnSetDTOHandler<>("code");
//		ResultDTO<Set<String>> dto = service.list(h);
//		print(dto);		
//	}

	@Test
	public void test_info_bean() throws SQLException {
		BeanHandler<Duty> h = new BeanHandler<Duty>(Duty.class);
		Duty dto = service.info(h, 0);
		print(dto);
	}

  @Test
  public void test_info_list() throws SQLException {
    BeanListHandler<Duty> h = new BeanListHandler<Duty>(Duty.class);
    List<Duty> dto = service.list(h);
    print(dto);
  }

  @Test
  public void test_info_list_camel() throws SQLException {
    BeanListHandler<DutyCamel> h = new BeanListHandler<>(DutyCamel.class);
    List<DutyCamel> dto = service.list(h);
    print(dto);
  }
  
  @Test
  public void findIdByCode() throws SQLException {
    List<Long> dto = service.findIdByCode("01");
    print(dto);
  }

}
