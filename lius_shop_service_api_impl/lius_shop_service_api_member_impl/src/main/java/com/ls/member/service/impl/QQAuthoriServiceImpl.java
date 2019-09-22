package com.ls.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ls.base.BaseApiService;
import com.ls.base.BaseResponse;
import com.ls.constants.Constants;
import com.ls.core.token.GenerateToken;
import com.ls.member.QQAuthoriService;
import com.ls.member.mapper.UserMapper;
import com.ls.member.mapper.entity.UserDo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QQAuthoriServiceImpl extends BaseApiService<JSONObject> implements QQAuthoriService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private GenerateToken generateToken;

	@Override
	public BaseResponse<JSONObject> findByOpenId(String qqOpenId) {
		// 1.根据qqOpenId查询用户信息
		if (StringUtils.isEmpty(qqOpenId)) {
			return setResultError("qqOpenId不能为空!");
		}
		// 2.如果没有查询到 直接返回状态码203
		UserDo userDo = userMapper.findByOpenId(qqOpenId);
		if (userDo == null) {
			return setResultError(Constants.HTTP_RES_CODE_NOTUSER_203, "根据qqOpenId没有查询到用户信息");
		}
		// 3.如果能够查询到用户信息的话 返回对应用户信息token
		String keyPrefix = Constants.MEMBER_TOKEN_KEYPREFIX + "QQ_LOGIN";
		Long userId = userDo.getUserid();
		String userToken = generateToken.createToken(keyPrefix, userId + "");
		JSONObject data = new JSONObject();
		data.put("token", userToken);
		return setResultSuccess(data);
	}

}
