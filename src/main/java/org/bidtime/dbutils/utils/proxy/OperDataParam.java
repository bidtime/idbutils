package org.bidtime.dbutils.utils.proxy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@Inherited
public @interface OperDataParam {

  String value() default "操作数据出错";
  
}
