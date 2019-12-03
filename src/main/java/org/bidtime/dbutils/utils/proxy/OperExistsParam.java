package org.bidtime.dbutils.utils.proxy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@Inherited
public @interface OperExistsParam {

  String value() default "数据重复";
  
}
