package org.bidtime.dbutils.params;

import org.bidtime.utils.comm.PropUtils;

public class StmtParams {
	
	private static final StmtParams instance = new StmtParams();

	public static StmtParams getInstance() {
		return instance;
	}

	private int stmtQueryTimeOut = 300;			//5m
	private int stmtUpdateTimeOut = 300;		//5m
	private int stmtBatchTimeOut = 600;			//10m
	private int fetchSize = 100;				//100per
	private int spanTimeOut = 30000;			//30s
	private String dataSource = "dataSource";

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public int getSpanTimeOut() {
		return spanTimeOut;
	}

	public void setSpanTimeOut(int spanTimeOut) {
		this.spanTimeOut = spanTimeOut;
	}

	public int getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	public int getStmtQueryTimeOut() {
		return stmtQueryTimeOut;
	}

	public void setStmtQueryTimeOut(int stmtQueryTimeOut) {
		this.stmtQueryTimeOut = stmtQueryTimeOut;
	}

	public int getStmtUpdateTimeOut() {
		return stmtUpdateTimeOut;
	}

	public void setStmtUpdateTimeOut(int stmtUpdateTimeOut) {
		this.stmtUpdateTimeOut = stmtUpdateTimeOut;
	}

	public int getStmtBatchTimeOut() {
		return stmtBatchTimeOut;
	}

	public void setStmtBatchTimeOut(int stmtBatchTimeOut) {
		this.stmtBatchTimeOut = stmtBatchTimeOut;
	}

	public StmtParams() {
		loadOfSrc("stmtparams.properties");
	}

	public void loadOfSrc(String fileName) {
		PropUtils propUtils = new PropUtils();
		try {
			propUtils.loadOfSrc(fileName);
			stmtQueryTimeOut = propUtils.getInteger("stmtQueryTimeOut", 300);
			stmtUpdateTimeOut = propUtils.getInteger("stmtUpdateTimeOut", 300);
			stmtBatchTimeOut = propUtils.getInteger("stmtBatchTimeOut", 600);
			spanTimeOut = propUtils.getInteger("spanTimeOut", 30000);
			fetchSize = propUtils.getInteger("fetchSize", 100);
			dataSource = propUtils.getString("dataSource", dataSource);
		} finally {
			propUtils = null;
		}
	}

}
