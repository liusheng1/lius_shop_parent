package com.ls.member.contorller;

import com.alibaba.fastjson.JSONObject;
import com.ls.base.BaseResponse;
import com.ls.member.contorller.req.vo.RegisterVo;
import com.ls.member.feign.MemberRegisterServiceFeign;
import com.ls.member.input.dto.UserInDTO;
import com.ls.web.base.BaseWebController;
import com.ls.web.bean.ShopBeanUtils;
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

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class RegisterContorller extends BaseWebController {

    @Autowired
    private MemberRegisterServiceFeign memberRegisterServiceFeign;
    /**
     * 跳转到注册页面
     */
    private static final String MB_REGISTEWR = "member/register";
    /**
     * 跳转到登陆页面页面
     */
    private static final String MB_LOGIN = "member/login";

    @GetMapping("/register")
    public String getRegister() {
        return MB_REGISTEWR;
    }

    /**
     * 跳转到注册页面
     *
     * @return
     */
    @PostMapping("/register")
    public String postRegister(@ModelAttribute("registerVo") @Validated RegisterVo registerVo,
                               BindingResult bindingResult, Model model, HttpSession httpSession) {
        //1.参数验证
        log.warn("registerVo={}",registerVo);
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            setErrorMsg(model, errorMsg);
            return MB_REGISTEWR;
        }
        //2.检验  图形验证码
        Boolean aBoolean = RandomValidateCodeUtil.checkVerify(registerVo.getGraphicCode(),httpSession);
        if (!aBoolean) {
            String errorMsg = "验证码 不正确 ";
            setErrorMsg(model, errorMsg);
            return MB_REGISTEWR;
        }
        //3.写书数据库  VO转为DO
        UserInDTO userInDTO = ShopBeanUtils.voToDto(registerVo, UserInDTO.class);
        log.warn("userInDTO={}",userInDTO);
        BaseResponse<JSONObject> register = memberRegisterServiceFeign.register(userInDTO, registerVo.getRegistCode());
        log.warn("register={}",register);
        if (!isSuccess(register)) {
            String errorMsg = register.getMsg();
            setErrorMsg(model, errorMsg);
            return MB_REGISTEWR;

        }
        log.warn("register={}",register);
        // 4.跳转到登陆页面
        return MB_LOGIN;
    }

}
