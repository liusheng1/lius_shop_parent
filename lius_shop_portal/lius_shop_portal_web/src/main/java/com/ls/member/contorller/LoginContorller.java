package com.ls.member.contorller;

import com.alibaba.fastjson.JSONObject;
import com.ls.base.BaseResponse;
import com.ls.member.contorller.req.vo.LoginVo;
import com.ls.member.feign.MemberLoginServiceFeign;
import com.ls.member.input.dto.UserLoginInpDTO;
import com.ls.web.WebConstants;
import com.ls.web.base.BaseWebController;
import com.ls.web.bean.ShopBeanUtils;
import com.ls.web.utils.CookieUtils;
import com.ls.web.utils.RandomValidateCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录请求
 */
@Controller
@Slf4j
public class LoginContorller extends BaseWebController {
    @Autowired
    private MemberLoginServiceFeign memberLoginServiceFeign;
    private static final String MB_LOGION = "member/login";
    /**
     * 重定向到首页
     */
    private static final String REDIRECT_INDEX = "redirect:/";

    @GetMapping("/login")
    public String getLogin() {
        return MB_LOGION;
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("loginVo") @Validated LoginVo loginVo,
                            BindingResult bindingResult, Model model, HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) {
        //1.检验参数
        if (bindingResult.hasErrors()) {
            String errMesg = bindingResult.getFieldError().getDefaultMessage();
            setErrorMsg(model, errMesg);
            return MB_LOGION;
        }
        //2.检验图形验证码
        if (!RandomValidateCodeUtil.checkVerify(loginVo.getGraphicCode(), httpSession)) {
            String errMesg = "验证码错误";
            setErrorMsg(model, errMesg);
            return MB_LOGION;
        }
        //3.登录服务
        //转化对象
        UserLoginInpDTO userLoginInpDTO = ShopBeanUtils.voToDto(loginVo, UserLoginInpDTO.class);
        //登录设备信息
        userLoginInpDTO.setDeviceInfor(webBrowserInfo(request));
        //登录设备类型
        userLoginInpDTO.setLoginType("PC");
        BaseResponse<JSONObject> login = memberLoginServiceFeign.login(userLoginInpDTO);
        log.warn("login=={}", login);
        if (!isSuccess(login)) {
            String errMesg = login.getMsg();
            setErrorMsg(model, errMesg);
            return MB_LOGION;
        }
        //4.登录成功，保存会话信息，将token存入到cookie 里面 首页读取cookietoken 查询用户信息返回到页面展示
        JSONObject data = login.getData();
        String token = data.getString("token");
        CookieUtils.setCookie(request, response, WebConstants.LOGIN_TOKEN_COOKIENAME, token);
        return REDIRECT_INDEX;
    }

    @RequestMapping("/exit")
    public String exit( HttpServletResponse response, HttpServletRequest request, Model model) {

        log.warn("start>>>>>>>>>>>>>>>>>>>");
        //1.获取cookie 中token
        String token = CookieUtils.getCookieValue(request, WebConstants.LOGIN_TOKEN_COOKIENAME, true);
        log.warn("token={}",token);
        //2.修改 token数据库
        //3.删除redis中的token
        BaseResponse<JSONObject> exit = memberLoginServiceFeign.exit(token);
        if (!isSuccess(exit)) {
            setErrorMsg(model,exit.getMsg());
            return REDIRECT_INDEX;

        }

        //4.清理浏览器中的token
        CookieUtils.setCookie(request, response, WebConstants.LOGIN_TOKEN_COOKIENAME, "");
        return REDIRECT_INDEX;
    }
}
