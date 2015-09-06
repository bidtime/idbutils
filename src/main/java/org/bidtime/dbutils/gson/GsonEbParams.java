package org.bidtime.dbutils.gson;


public class GsonEbParams {
	//sucess
	public static final short STATE_SUCCESS = 0;			//成功
	
	//failure
	public static final short STATE_FAILURE = 1;			//通用错误
	public static final short STATE_USER_NOT_LOGIN = -1;	//未登陆
	public static final short STATE_USER_NOT_ONLINE = 2;	//不在线
	public static final short STATE_USER_NOT_POWER = 3;		//无权限
	public static final short STATE_USER_IS_FREQUENT = 4;	//输验证码
	public static final short STATE_USER_NOT_REFRESH = 5;	//未刷新
}
