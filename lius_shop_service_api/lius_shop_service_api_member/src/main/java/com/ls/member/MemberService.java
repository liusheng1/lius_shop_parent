package com.ls.member;

import com.ls.base.BaseResponse;
import com.ls.member.output.dto.UserOutDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Api(tags = "会员服务接口")
public interface MemberService {

	/**
	 * 根据手机号码查询是否已经存在,如果存在返回当前用户信息
	 * 
	 * @param mobile
	 * @return
	 */
	@ApiOperation(value = "会员登录信息")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "mobile", dataType = "String", required = true, value = "用户手机号码"), })
	@GetMapping("/login")
	BaseResponse<UserOutDTO> existMobile(@RequestParam("mobile") String mobile);
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"), })
	@GetMapping("/getInfoByToken")
	public BaseResponse<UserOutDTO> getInfoByToken(@RequestParam("token") String token) ;

}
