package org.bidtime.web.utils;

import java.util.HashMap;
import java.util.Map;

import org.bidtime.dbutils.gson.JSONHelper;
import org.json.JSONObject;

public class UserHeadState {
	//数据状态: sucess, state
	public static final short SUCCESS = 0;			//成功
	public static final short ERROR = 1;			//通用错误
	//public static final short DATA_NO_REFRESH = 2;	//未刷新
	
	//帐户审核状态
	public static final String CHK_USER = "chkusr";		//chk user
	public static final short CHK_USER_NO_ACTIVE = 1;	//未激活
	public static final short CHK_USER_NO_CHECK = 2;	//未审核
	public static final short CHK_USER_NO_PASS = 3;		//审核未通过
	
	//用户登陆状态
	public static final String NOT_LOGININ = "notLogin";	//not login
	public static final short USER_NO_LOGIN = -1;	//未登陆
	public static final short USER_NO_ONLINE = 2;	//不在线
	public static final short USER_NO_POWER = 3;	//无权限
	public static final short USER_IS_FREQUENT = 4;	//输验证码

//	public static JSONObject toCheckJsonMsg(int state, String msg) {
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("state", state);
//		//if (StringUtils.isBlank(msg)) {
//		//	jsonObject.put("msg", JSONObject.NULL);
//		//} else {
//		jsonObject.put("msg", this.msg);
//		//}
//		return jsonObject;
//	}

	public static JSONObject noPassJsonMsg(int chkstate, String msg) {
		return stateMsgToJson(chkstate, msg);
	}

	public static JSONObject noCheckJsonMsg(int chkstate, String msg) {
		return stateMsgToJson(chkstate, msg);
	}

	public static JSONObject noActiveJsonMsg(int chkstate, String msg) {
		return stateMsgToJson(chkstate, msg);
	}
	
	public static JSONObject stateMsgToJson(int chkstate, String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CHK_USER, chkstate);
		map.put("msg", msg);
		JSONObject json = JSONHelper.mapToJson(map, null);
		return json;
	}
	
}
