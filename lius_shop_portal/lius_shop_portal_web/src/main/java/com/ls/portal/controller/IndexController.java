package com.ls.portal.controller;

import com.ls.base.BaseResponse;
import com.ls.member.feign.MemberServiceFeign;
import com.ls.member.output.dto.UserOutDTO;
import com.ls.web.WebConstants;
import com.ls.web.base.BaseWebController;
import com.ls.web.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController extends BaseWebController {

    @Autowired
    private MemberServiceFeign memberServiceFeign;
    /**
     * 跳转到index页面
     */
    private static final String INDEX_FTL = "index";

    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        //1.获取cookie中的token
        String token = CookieUtils.getCookieValue(request, WebConstants.LOGIN_TOKEN_COOKIENAME, true);
        //2.通过token查询用户信息
        BaseResponse<UserOutDTO> userInfo = memberServiceFeign.getInfoByToken(token);
        //接口调用是否成功
        if (isSuccess(userInfo)) {
            UserOutDTO data = userInfo.getData();
            if (data != null) {
                String mobile = data.getMobile();
                //手机号码脱敏
                String desensMobile = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                model.addAttribute("desensMobile", desensMobile);
            }
        }


        return INDEX_FTL;
    }
    // 作业题：完成退出功能 实现唯一登陆心跳检测 前端定时器 定时 使用js 读取本地cookie 信息 使用token 查询当前状态 如果token状态为1的话，页面直接刷新下。

}
