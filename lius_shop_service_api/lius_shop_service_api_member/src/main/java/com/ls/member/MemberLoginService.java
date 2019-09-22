package com.ls.member;

import com.alibaba.fastjson.JSONObject;
import com.ls.base.BaseResponse;
import com.ls.member.input.dto.UserLoginInpDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "用户服务接口")
public interface MemberLoginService {
    /**
     * 用户登陆接口
     *
     * @param userLoginInpDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "会员用户登陆信息接口")
    BaseResponse<JSONObject> login(@RequestBody UserLoginInpDTO userLoginInpDTO);
    /**
     * 用户退出
     */
    @RequestMapping("/exit")
    @ApiOperation(value = "会员用户退出接口")
    BaseResponse<JSONObject>exit(@RequestBody String token);

}

