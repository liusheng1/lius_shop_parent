package com.ls.pay.service;

import com.alibaba.fastjson.JSONObject;
import com.ls.base.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;

public interface PayContextService  {
    /**
     * 通过支付渠道id和token支付
     * @param channel
     * @param token
     * @return
     */
    @GetMapping("/toPayHtml")
    public BaseResponse<JSONObject> toPayHtml(String channel,String token);
}
