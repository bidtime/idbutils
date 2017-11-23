package org.bidtime.dbutils.base.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class DAOUtils {
  
  static {
    //注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
    //ConvertUtils.register(new SqlDateConverter(), java.util.Date.class);
    ////ConvertUtils.register(new SqlTimestampConverter(), java.sql.Timestamp.class);
    //注册util.date的转换器，即允许BeanUtils.copyProperties时的源目标的util类型的值允许为空
    //ConvertUtils.register(new UtilDateConverter(), java.util.Date.class);
    //
    //ConvertUtils.register(new DateConverter(null), java.util.Date.class);//添加这一行代码，重新注册一个转换器，也可以自定义
  }

  public static <T> List<T> populateList(Class<T> type, List<Map<String, Object>> list) throws Exception {
    if (list==null) {
      return null;
    }
    if (list.isEmpty()) {
      return new ArrayList<>();
    }
    List<T> result = new ArrayList<>(list.size());
    for (Map<String, Object> m : list) {
      T u = type.newInstance();
      BeanUtils.populate(u, m);
      result.add(u);
    }
    return result;
  }

  public static <T> T populate(Class<T> type, Map<String, Object> map) throws Exception {
    T result = null;
    if (map != null) {
      result = type.newInstance();
      BeanUtils.populate(result, map);
    }
    return result;
  }

  @SuppressWarnings("rawtypes")
  public static <T> List<T> cloneList(Class<T> type, List list) throws Exception {
    List<T> listResult = null;
    if (list != null && !list.isEmpty()) {
      listResult = new ArrayList<>();
      for (Object u : list) {
        T modelBO = type.newInstance();
        BeanUtils.copyProperties(modelBO, u);
        listResult.add(modelBO);
      }
    }
    return listResult;
  }

  @SuppressWarnings("rawtypes")
  public static <T> List<T> cloneList(Class<T> type, List list, IObjectCallBack<T> cb) throws Exception {
    List<T> listResult = null;
    if (list != null && !list.isEmpty()) {
      listResult = new ArrayList<>();
      for (Object u : list) {
        T modelBO = type.newInstance();
        BeanUtils.copyProperties(modelBO, u);
        if (cb != null) {
          cb.callback(modelBO);
        }
        listResult.add(modelBO);
      }
    }
    return listResult;
  }

//  @SuppressWarnings("rawtypes")
//  public static <T> PageBO<T> clonePage(Class<T> type, PageBO bo) throws Exception {
//    List<T> listResult = null;
//    if (bo.getContent() != null) {
//      listResult = new ArrayList<>();
//      for (Object u : bo.getContent()) {
//        T modelBO = type.newInstance();
//        BeanUtils.copyProperties(modelBO, u);
//        listResult.add(modelBO);
//      }
//    }
//    return new PageBO<T>(listResult, bo.getTotal());
//  }
//
//  @SuppressWarnings("rawtypes")
//  public static <T> PageBO<T> clonePage(Class<T> type, PageBO bo, IObjectCallBack<T> cb) throws Exception {
//    List<T> listResult = null;
//    if (bo.getContent() != null) {
//      listResult = new ArrayList<>();
//      for (Object u : bo.getContent()) {
//        T modelBO = type.newInstance();
//        BeanUtils.copyProperties(modelBO, u);
//        if (cb != null) {
//          cb.callback(modelBO);
//        }
//        listResult.add(modelBO);
//      }
//    }
//    return new PageBO<T>(listResult, bo.getTotal());
//  }

  public static <T> T cloneBean(Class<T> type, Object bo) throws Exception {
    T result = null;
    if (bo != null) {
      result = type.newInstance();
      BeanUtils.copyProperties(result, bo);
    }
    return result;
  }

  public static <T> T cloneBean(Class<T> type, Object bo, IObjectCallBack<T> cb) throws Exception {
    T result = null;
    if (bo != null) {
      result = type.newInstance();
      BeanUtils.copyProperties(result, bo);
      if (cb != null) {
        cb.callback(result);
      }
    }
    return result;
  }

//  public static Map<String, Object> bean2map(Object obj) throws Exception {
//    if (obj == null) {
//      return null;
//    }    
//    Map<String, Object> map = new HashMap<String, Object>();
//    try {
//      BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
//      PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//      for (PropertyDescriptor property : propertyDescriptors) {
//        String key = property.getName();
//        // 过滤class属性  
//        if (!key.equals("class")) {
//          // 得到property对应的getter方法  
//          Method getter = property.getReadMethod();
//          Object value = getter.invoke(obj);
//          //if ( value != null ) {
//            map.put(key, value);
//          //}
//        }
//      }
//    } catch (Exception e) {
//      //System.out.println("transBean2Map Error " + e);
//      throw new Exception(e);
//    }
//    return map;
//  }

//  public static <T> T map2bean(Class<T> type, Map<String, Object> map) throws Exception {
//    T result = null;
//    if (map != null) {
//      result = type.newInstance();
//      BeanUtils.populate(result, map);
//      //BeanUtils.populate(result, map);
//      //map2bean(result, map);
//    }
//    return result;
//  }
  
//  // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean  
//  public static void map2bean(Object obj, Map<String, Object> map) throws Exception {
//    try {
//      BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
//      PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//      for (PropertyDescriptor property : propertyDescriptors) {
//        String key = property.getName();
//        if (map.containsKey(key)) {
//          Object value = map.get(key);
//          if ( value != null ) {
//            // 得到property对应的setter方法  
//            Method setter = property.getWriteMethod();
//            setter.invoke(obj, value);
//          }
//        }
//      }
//    } catch (Exception e) {
//      //System.out.println("transMap2Bean Error " + e);
//      throw new Exception(e);
//    }
//  }

}
