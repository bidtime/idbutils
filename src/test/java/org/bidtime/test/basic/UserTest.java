package org.bidtime.test.basic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.bidtime.basicdata.user.bean.User;
import org.bidtime.basicdata.user.service.UserService;
import org.bidtime.dbutils.gson.ResultDTO;
import org.bidtime.dbutils.jdbc.rs.handle.BeanDTOHandler;
import org.bidtime.dbutils.jdbc.rs.handle.BeanListDTOHandler;
import org.bidtime.dbutils.jdbc.rs.handle.ColumnSetDTOHandler;
import org.bidtime.dbutils.jdbc.rs.handle.ColumnSetHandler;
import org.bidtime.dbutils.jdbc.rs.handle.cb.SetCallback;
import org.bidtime.test.BasicTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by bidtime on 2015/9/23.
 */
public class UserTest extends BasicTest {

	@Autowired
	protected UserService service;

	@Test
	public void test_insert() throws SQLException {
		User duty = new User();
		duty.setUserName("user");
		int n = service.insert(duty);
		System.out.println("insert: " + n);
	}

	@Test
	public void test_insertIgnore() throws SQLException {
		User duty = new User();
		duty.setUserId(3L);
		duty.setUserCode("u-3");
		duty.setUserName("user");
		int n = service.insertIgnore(duty);
		System.out.println("insert: " + n);
	}

	@Test
	public void test_insertIgnoreList() throws SQLException {
		List<User> list = new ArrayList<User>();
		//
		User duty1 = new User();
		duty1.setUserName("1 user");
		list.add(duty1);
		//
		User duty2 = new User();
		duty2.setUserName("2 user");
		duty2.setUserCode("002");
		list.add(duty2);
		//
		int n = service.insertIgnore(list);
		System.out.println("insert: " + n);
	}

	@Test
	public void test_insertList() throws SQLException {
		List<User> list = new ArrayList<User>();
		//
		User duty1 = new User();
		duty1.setUserName("1 user");
		list.add(duty1);
		//
		User duty2 = new User();
		duty2.setUserName("2 user");
		duty2.setUserCode("002");
		list.add(duty2);
		//
		int n = service.insert(list);
		System.out.println("insert: " + n);
	}

	@Test
	public void test_updateList() throws SQLException {
		List<User> list = new ArrayList<User>();
		//
		User duty1 = new User();
		duty1.setUserId(7L);
		duty1.setUserName("11 user");
		list.add(duty1);
		//
		User duty2 = new User();
		duty2.setUserId(8L);
		duty2.setUserName("22 user");
		duty2.setUserCode("022");
		list.add(duty2);
		//
		int n = service.update(list);
		System.out.println("update: " + n);
	}

	@Test
	public void test_insertForPK() throws SQLException {
		User u = new User();
		u.setUserName("user");
		Long id = service.insertForPK(u);
		System.out.println("insert: pk-> " + id);
	}

	@Test
	public void test_insertForPK_all() throws SQLException {
		User duty = new User();
		duty.setUserName("user");
		Long id = service.insertForPK(duty);
		System.out.println("insert: pk -> " + id);
		//
		duty.setUserId(id);
		duty.setUserName("user-");
		//
		int nUpdate = service.update(duty);
		System.out.println("update: " + nUpdate);
		//
		int nDelete = service.delete(duty);
		System.out.println("delete: " + nDelete);
	}

	@Test
	public void test_update() throws SQLException {
		User duty = new User();
		duty.setUserId(7L);
		duty.setUserName("user-");
		int n = service.update(duty);
		System.out.println("update: " + n);
	}

	@Test
	public void test_delete() throws SQLException {
		User duty = new User();
		duty.setUserId(3L);
		int n = service.delete(duty);
		System.out.println("update: " + n);
	}

	@Test
	public void test_delete_heads() throws SQLException {
		User duty = new User();
		duty.setUserId(3L);
		duty.setUserCode("e");
		int n = service.delete(duty, new String[]{"usercode", "userid"});
		System.out.println("update: " + n);
	}

	@Test
	public void test_deleteMap() throws SQLException {
		Map<String, Object> map = new HashMap<>();
		map.put("userid", 3L);
		map.put("usercode", "e");
		int n = service.delete(map, new String[]{"userid", "usercode"});
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

	@Test
	public void test_getBeanDTOHandler() throws SQLException {
		BeanDTOHandler<User> h = new BeanDTOHandler<User>(User.class);
		ResultDTO<User> dto = service.list(h);
		print(dto);
	}

	@Test
	public void test_info_dto() throws SQLException {
		BeanDTOHandler<User> h = new BeanDTOHandler<User>(User.class);
		ResultDTO<User> dto = service.info(h, 1);
		print(dto);
	}

	@Test
	public void test_column_set_dto() throws SQLException {	
		ColumnSetDTOHandler<Long> h = new ColumnSetDTOHandler<>(new SetCallback<Long>() {
		    @Override
		    public Set<Long> callback() {
		    	return new HashSet<Long>();
		    }
		});
		ResultDTO<Set<Long>> dto = service.list(h);
		print(dto);
	}

	@Test
	public void test_column_set() throws SQLException {	
		ColumnSetHandler<Long> h = new ColumnSetHandler<>(new SetCallback<Long>() {
		    @Override
		    public Set<Long> callback() {
		    	return new HashSet<Long>();
		    }
		});
		Set<Long> dto = service.list(h);
		print(dto);
	}

	@Test
	public void test_list() throws SQLException {
		BeanListDTOHandler<User> h = new BeanListDTOHandler<User>(User.class);
		ResultDTO<List<User>> dto = service.list(h);
		print(dto);
	}
	
	@Test
	public void test_set() throws SQLException {
		ColumnSetHandler<String> h = new ColumnSetHandler<>("usercode");
		Set<String> dto = service.list(h);
		print(dto);
	}
	
	@Test
	public void test_set_dto() throws SQLException {
		ColumnSetDTOHandler<String> h = new ColumnSetDTOHandler<>("usercode");
		ResultDTO<Set<String>> dto = service.list(h);
		print(dto);
	}

	@Test
	public void test_info_bean() throws SQLException {
		BeanHandler<User> h = new BeanHandler<User>(User.class);
		User dto = service.info(h, 1);
		print(dto);
	}
	
	@Test
	public void findIdByCode() throws SQLException {
		List<Long> dto = service.findIdByCode("01");
		print(dto);
	}

}
