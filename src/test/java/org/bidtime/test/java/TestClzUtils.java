package org.bidtime.test.java;

import org.bidtime.utils.proxy.ClzUtils;
import org.bidtime.utils.proxy.OperExistsParam;
import org.junit.Test;

public class TestClzUtils {
  
  @Test
  @OperExistsParam(value="hello")
  public void a() {
    System.out.println( b(this.getClass()) );
  }  
  
  private <T> String b(Class<T> clz) {
    return c(clz);
  }  
  
  private <T> String c(Class<T> clz) {
    String str = ClzUtils.getOperExistsParamDef(clz, "123456");
    return str;
  }  
    
//  public static void main(String... args) {
//    //String s = TestClzUtils.a(TestClzUtils.class, "1234");
//    String s = new TestClzUtils().a(null, "1234");
//    System.out.println(s);
//  }
  
}