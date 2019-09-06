package com.ls.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.ls.base.BaseApiService;
import com.ls.base.BaseResponse;
import com.ls.constants.Constants;
import com.ls.core.bean.ShopBeanUtils;
import com.ls.core.token.GenerateToken;
import com.ls.core.type.TypeCastHelper;
import com.ls.core.utils.RedisUtil;
import com.ls.member.MemberService;
import com.ls.member.input.dto.UserInDTO;
import com.ls.member.mapper.UserMapper;
import com.ls.member.mapper.entity.UserDo;
import com.ls.member.output.dto.UserOutDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MemberServiceImpl extends BaseApiService<UserOutDTO> implements MemberService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GenerateToken generateToken;


    @Override
    //@RequestMapping("/existMobile")
    public BaseResponse<UserOutDTO> existMobile(String mobile) {
        log.error("mobile={}", mobile);
        // 1.验证参数
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("手机号码不能为空!");
        }

        // 2.根据手机号码查询用户信息 单独定义code 表示是用户信息不存在把
        UserDo userDo = userMapper.existMobile(mobile);
		log.error("userDo={}",userDo);
        if (userDo == null) {
            return setResultError(Constants.HTTP_RES_CODE_EXISTMOBILE_203, "用户信息不存在!");
        }
        // 对特殊铭感字段需要做脱敏

        log.error("userDo={}",JSON.toJSON(userDo));
        //;
        return setResultSuccess(ShopBeanUtils.doToDto(userDo, UserOutDTO.class));
    }
    @Override
    public BaseResponse<UserOutDTO> getInfoByToken(String token) {
        //1.验证token
        if (StringUtils.isEmpty(token)){
            return  setResultError("token 不能为空");
        }
        //2.通过token获取redis存储的userid
        String userId = generateToken.getToken(token);
        if (StringUtils.isEmpty(userId)) {
            return  setResultError("token不存在或已失效");
        }
        //3.通过userID查询用户信息
        UserDo userDo = userMapper.findByUserId(TypeCastHelper.toLong(userId));
        if (userDo==null){
            return  setResultError("用户不存在");
        }
        return setResultSuccess(ShopBeanUtils.doToDto(userDo, UserOutDTO.class));
    }

}
