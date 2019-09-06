package com.ls.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ls.base.BaseApiService;
import com.ls.base.BaseResponse;
import com.ls.constants.Constants;
import com.ls.core.bean.ShopBeanUtils;
import com.ls.core.utils.MD5Util;
import com.ls.member.MemberRegisterService;
import com.ls.member.feign.VerificaCodeServiceFeign;
import com.ls.member.input.dto.UserInDTO;
import com.ls.member.mapper.UserMapper;
import com.ls.member.mapper.entity.UserDo;
import com.ls.member.output.dto.UserOutDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MemberRegisterServiceImpl extends BaseApiService<JSONObject> implements MemberRegisterService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VerificaCodeServiceFeign verificaCodeServiceFeign;

    @Transactional
    public BaseResponse<JSONObject> register(@RequestBody UserInDTO userInDTO, @RequestParam String registCode) {
        // 1.参数验证
        String userName = userInDTO.getUserName();
        if (StringUtils.isEmpty(userName)) {
            return setResultError("用户名称不能为空!");
        }

        String mobile = userInDTO.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("手机号码不能为空!");
        }
        String password = userInDTO.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空!");
        }
        log.error("userInDTO={}",userInDTO);
        // 2.验证码注册码是否正确 暂时省略 会员调用微信接口实现注册码验证
//        BaseResponse<JSONObject> verificaWeixinCode = verificaCodeServiceFeign.verificaWeixinCode(mobile, registCode);
//        if (!verificaWeixinCode.getCode().equals(Constants.HTTP_RES_CODE_200)) {
//            return setResultError(verificaWeixinCode.getMsg());
//        }
        // 3.对用户的密码进行加密 // MD5 可以解密 暴力破解
        String newPassword = MD5Util.MD5(password);

        userInDTO.setPassword(newPassword);
        UserDo userDo = new UserDo();
        userDo = ShopBeanUtils.dtoToDo(userInDTO, UserDo.class);
        log.error("userDo={}",userDo);
        // 4.调用数据库插入数据
        return userMapper.register(userDo) > 0 ? setResultSuccess("注册成功") : setResultError("注册失败!");
    }

}
