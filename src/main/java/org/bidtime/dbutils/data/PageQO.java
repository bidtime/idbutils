package org.bidtime.dbutils.data;

@SuppressWarnings("serial")
public class PageQO extends BaseQO {

  protected Integer pageIdx = 0;
  
  protected Integer pageSize = 10;
  
  public PageQO() {
    this(0, 10);
  }
  
  public PageQO(Integer pageIdx, Integer pageSize) {
    this.pageIdx = pageIdx;
    this.pageSize = pageSize;
  }
  
  public Integer getPageIdx() {
    return pageIdx;
  }
  
  public void setPageIdx(Integer pageIdx) {
    this.pageIdx = pageIdx;
  }
  
  public Integer getPageSize() {
    return pageSize;
  }
  
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
  
}
