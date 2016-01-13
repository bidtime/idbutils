package org.bidtime.dbutils.params;

import org.bidtime.utils.comm.PropEx;

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
	private Boolean debug = true;				//是否调试版本
	private Boolean sqlOutParam = false;		//sqlOutParam -> false: sql里面含有参数; true: sql和参数分别输出
	private Boolean formatSql = true;			//是否格式化sql

	public Boolean getSqlOutParam() {
		return sqlOutParam;
	}

	public void setSqlOutParam(Boolean sqlOutParam) {
		this.sqlOutParam = sqlOutParam;
	}

	public Boolean getFormatSql() {
		return formatSql;
	}

	public void setFormatSql(Boolean formatSql) {
		this.formatSql = formatSql;
	}

	public Boolean getDebug() {
		return debug;
	}

	public void setDebug(Boolean debug) {
		this.debug = debug;
	}

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
		PropEx propUtils = new PropEx();
		try {
			if (propUtils.loadOfSrcSlient(fileName)) {
				stmtQueryTimeOut = propUtils.getInteger("stmtQueryTimeOut", 300);
				stmtUpdateTimeOut = propUtils.getInteger("stmtUpdateTimeOut", 300);
				stmtBatchTimeOut = propUtils.getInteger("stmtBatchTimeOut", 600);
				spanTimeOut = propUtils.getInteger("spanTimeOut", 30000);
				fetchSize = propUtils.getInteger("fetchSize", 100);
				dataSource = propUtils.getString("dataSource", dataSource);
				//many boolean prop
				debug = propUtils.getBoolean("debug", debug);
				sqlOutParam = propUtils.getBoolean("sqlOutOne", sqlOutParam);
				formatSql = propUtils.getBoolean("formatSql", formatSql);
			}
		} finally {
			propUtils = null;
		}
	}

}
