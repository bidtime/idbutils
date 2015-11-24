package org.bidtime.web.utils;


public class UserHeadState {
	//sucess
	public static final short SUCCESS = 0;			//成功
	
	//failure
	public static final short ERROR = 1;			//通用错误
	public static final short USER_NO_LOGIN = -1;	//未登陆
	public static final short USER_NO_ONLINE = 2;	//不在线
	public static final short USER_NO_POWER = 3;		//无权限
	public static final short USER_IS_FREQUENT = 4;	//输验证码
	public static final short USER_NO_REFRESH = 5;	//未刷新
	public static final short USER_NO_ACTIVE = 6;	//未激活
	public static final short USER_NO_CHECK = 7;	//未审核
	
	public static final String NOT_LOGININ = "notLogin";	//not login

}
