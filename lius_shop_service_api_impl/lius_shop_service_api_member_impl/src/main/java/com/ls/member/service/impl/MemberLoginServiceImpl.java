package com.ls.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ls.base.BaseApiService;
import com.ls.base.BaseResponse;
import com.ls.constants.Constants;
import com.ls.core.token.GenerateToken;
import com.ls.core.transaction.RedisDataSoureceTransaction;
import com.ls.core.utils.MD5Util;
import com.ls.member.MemberLoginService;
import com.ls.member.input.dto.UserLoginInpDTO;
import com.ls.member.mapper.UserMapper;
import com.ls.member.mapper.UserTokenMapper;
import com.ls.member.mapper.entity.UserDo;
import com.ls.member.mapper.entity.UserTokenDo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MemberLoginServiceImpl extends BaseApiService implements MemberLoginService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GenerateToken generateToken;
    @Autowired
    private UserTokenMapper userTokenMapper;

    /**
     * 手动事务工具类
     */
    @Autowired
    private RedisDataSoureceTransaction redisDataSoureceTransaction;

    @Override
    public BaseResponse<JSONObject> login(@RequestBody UserLoginInpDTO userLoginInpDTO) {
        //1.验证参数
        if (userLoginInpDTO == null) {
            return setResultError("登录失败");
        }
        String mobile = userLoginInpDTO.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("手机号码不能为空");
        }
        String password = userLoginInpDTO.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空");
        }
        // 判断登陆类型
        String loginType = userLoginInpDTO.getLoginType();
        if (StringUtils.isEmpty(loginType)) {
            return setResultError("登陆类型不能为空!");
        }
        // 目的是限制范围
        if (!(loginType.equals(Constants.MEMBER_LOGIN_TYPE_ANDROID) || loginType.equals(Constants.MEMBER_LOGIN_TYPE_IOS)
                || loginType.equals(Constants.MEMBER_LOGIN_TYPE_PC))) {
            return setResultError("登陆类型出现错误!");
        }

        // 设备信息
        String deviceInfor = userLoginInpDTO.getDeviceInfor();
        if (StringUtils.isEmpty(deviceInfor)) {
            return setResultError("设备信息不能为空!");
        }

        String newPassword = MD5Util.MD5(password);

        //2.通过手机号和密码查询数据库
        UserDo userDo = userMapper.login(mobile, newPassword);
        if (userDo == null) {
            return setResultError("用户名或密码错误");
        }

        //3.创建token
        TransactionStatus transactionStatus = null;
        try {
            Long userId = userDo.getUserid();
            UserTokenDo userTokenDo = userTokenMapper.selectByUserIdAndLoginType(userId, loginType);
            log.warn("手动开启事物");
            transactionStatus = redisDataSoureceTransaction.begin();
            if (userTokenDo != null) {
                // 4.清除之前的token
                String token = userTokenDo.getToken();
                generateToken.removeToken(token);
                int updateTokenAvailability = userTokenMapper.updateTokenAvailability(token);
                if (updateTokenAvailability < 0) {
                    log.error("用户数据状态修改失败 ，开始回滚");
                    redisDataSoureceTransaction.rollback(transactionStatus);
                    log.error("用户数据状态修改失败 ，回滚成功");
                    return setResultError("系统错误");
                }

            }

            // 插入新的token
            UserTokenDo userToken = new UserTokenDo();
            userToken.setUserId(userId);
            userToken.setLoginType(userLoginInpDTO.getLoginType());
            // 生成对应用户令牌存放在redis中
            String keyPrefix = Constants.MEMBER_TOKEN_KEYPREFIX + loginType;
            String newToken = generateToken.createToken(keyPrefix, userId + "");
            userToken.setToken(newToken);
            userToken.setDeviceInfor(deviceInfor);
            int result = userTokenMapper.insertUserToken(userToken);
            if (!toDaoResult(result)) {
                log.error("用户token添加失败 ，开始回滚");
                redisDataSoureceTransaction.rollback(transactionStatus);
                log.error("用户token添加失败 ，回滚成功");
                return setResultError("系统错误");
            }
            log.warn("开始提交手动提交事物");
            redisDataSoureceTransaction.commit(transactionStatus);
            JSONObject data = new JSONObject();
            data.put("token", newToken);
            return setResultSuccess(data);
        } catch (Exception e) {
            log.error("数据不同步 数据库事物回滚");
            try {
                redisDataSoureceTransaction.rollback(transactionStatus);
            } catch (Exception ex) {
                log.error("数据回滚失败");
            }
            return setResultError("系统错误!");
        }

    }

    @Override
    public BaseResponse<JSONObject> exit(@RequestBody String token) {
        //1.验证参数
        if (token == null) {
            return setResultError("token不存在");
        }
        //2.删除token
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = redisDataSoureceTransaction.begin();
            // 3.清除之前的token
            generateToken.removeToken(token);
            int updateTokenAvailability = userTokenMapper.updateTokenAvailability(token);
            if (updateTokenAvailability < 0) {
                log.error("用户数据状态修改失败 ，开始回滚");
                redisDataSoureceTransaction.rollback(transactionStatus);
                log.error("用户数据状态修改失败 ，回滚成功");
                return setResultError("系统错误");
            }

        } catch (Exception ex) {
            try {
                redisDataSoureceTransaction.rollback(transactionStatus);
            } catch (Exception e) {
                log.error("数据回滚失败");;
            }

        }
        JSONObject data = new JSONObject();
        return setResultSuccess(data);
    }
}
