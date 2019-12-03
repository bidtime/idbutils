package org.bidtime.dbutils.utils.comm;

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

  public static <T> List<T> populateList(Class<T> type, List<Map<String, Object>> list) {
    if (list==null) {
      return null;
    }
    if (list.isEmpty()) {
      return new ArrayList<>();
    }
    List<T> result = new ArrayList<>(list.size());
    for (Map<String, Object> m : list) {
      try {
        T u = type.newInstance();
        BeanUtils.populate(u, m);
        result.add(u);
      } catch (Exception e) {
      }
    }
    return result;
  }

  public static <T> T populate(Class<T> type, Map<String, Object> map) {
    T result = null;
    if (map != null) {
      try {
        result = type.newInstance();
        BeanUtils.populate(result, map);
      } catch (Exception e) {
      }
    }
    return result;
  }

  @SuppressWarnings("rawtypes")
  public static <T> List<T> cloneList(Class<T> type, List list) {
    return cloneList(type, list, null);
  }

  @SuppressWarnings("rawtypes")
  public static <T> List<T> cloneList(Class<T> type, List list, IObjectCallBack<T> cb) {
    List<T> listResult = null;
    if (list != null && !list.isEmpty()) {
      listResult = new ArrayList<>();
      for (Object u : list) {
        try {
          T modelBO = type.newInstance();
          BeanUtils.copyProperties(modelBO, u);
          if (cb != null) {
            cb.callback(modelBO);
          }
          listResult.add(modelBO);
        } catch (Exception e) {
        }
      }
    }
    return listResult;
  }

  public static <T> T cloneBean(Class<T> type, Object bo) {
    return cloneBean(type, bo, null);
  }

  public static <T> T cloneBean(Class<T> type, Object bo, IObjectCallBack<T> cb) {
    T result = null;
    if (bo != null) {
      try {
        result = type.newInstance();
        BeanUtils.copyProperties(result, bo);
      } catch (Exception e) {
      }
      if (cb != null) {
        cb.callback(result);
      }
    }
    return result;
  }

}
