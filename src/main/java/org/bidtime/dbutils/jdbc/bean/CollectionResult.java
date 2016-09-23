package org.bidtime.dbutils.jdbc.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CollectionResult {
	
	@SuppressWarnings("rawtypes")
	Collection insert;
	
	@SuppressWarnings("rawtypes")
	Collection delete;

	@SuppressWarnings("rawtypes")
	Collection update;
	
	public CollectionResult() {
		
	}
	
	@SuppressWarnings("rawtypes")
	public CollectionResult(Collection insert, Collection delete) {
		this.insert = insert;
		this.delete = delete;
	}

	@SuppressWarnings("rawtypes")
	public Collection getInsert() {
		return insert;
	}

	@SuppressWarnings("rawtypes")
	public void setInsert(Collection insert) {
		this.insert = insert;
	}

	@SuppressWarnings("rawtypes")
	public Collection getDelete() {
		return delete;
	}

	@SuppressWarnings("rawtypes")
	public void setDelete(Collection delete) {
		this.delete = delete;
	}

	@SuppressWarnings("rawtypes")
	public Collection getUpdate() {
		return update;
	}

	@SuppressWarnings("rawtypes")
	public void setUpdate(Collection update) {
		this.update = update;
	}

	@SuppressWarnings({ "rawtypes" })
	public static CollectionResult getMerge(Collection listDb, Collection listNew) throws SQLException {
		Collection listInsert = null;
		Collection listDelete = null;
        if (listNew != null && !listNew.isEmpty()) {
            // 用户选择的不为空，数据库取出的也不为空，则使用集合的减集
            if (listDb != null && !listDb.isEmpty() ) {
                listDelete = subtract(listDb, listNew);        //减集
                listInsert = subtract(listNew, listDb);        //减集
            } else {    // 数据库取出的菜单为空，则需要直接增加
                listInsert = listNew;
            }
        } else {
            // 用户选择的为空，数据库取出的不为空，则需要删除数据库的
            if (listDb != null && !listDb.isEmpty() ) {
                listDelete = listDb;
            //} else {            //全部都为空，则不用做了
            //  return 1;
            }
        }
        return new CollectionResult(listInsert, listDelete);
	}

	// getInsert
	@SuppressWarnings("rawtypes")
	public static Collection getInsert(final Collection listDb, final Collection listNew) {
		Collection listInsert = null;
        if (listNew != null && !listNew.isEmpty()) {
            // 用户选择的不为空，数据库取出的也不为空，则使用集合的减集
            if (listDb != null && !listDb.isEmpty() ) {
                listInsert = subtract(listNew, listDb);        //减集
            } else {    // 数据库取出的菜单为空，则需要直接增加
                listInsert = listNew;
            }
        }
        return listInsert;
    }
	
	// getDelete
	@SuppressWarnings("rawtypes")
	public static Collection getDelete(final Collection listDb, final Collection listNew) {
		Collection listDelete = null;
        if (listNew != null && !listNew.isEmpty()) {
            // 用户选择的不为空，数据库取出的也不为空，则使用集合的减集
            if (listDb != null && !listDb.isEmpty() ) {
                listDelete = subtract(listDb, listNew);        //减集
            }
        } else {
            // 用户选择的为空，数据库取出的不为空，则需要删除数据库的
            if (listDb != null && !listDb.isEmpty() ) {
                listDelete = listDb;
            //} else {            //全部都为空，则不用做了
            //  return null;
            }
        }
        return listDelete;
    }
	
	//差集
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Collection subtract(final Collection a, final Collection b) {
        ArrayList list = new ArrayList( a );
        for (Iterator it = b.iterator(); it.hasNext();) {
            list.remove(it.next());
        }
        return list;
    }

}
