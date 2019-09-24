package com.ls.pay.service;

import com.alibaba.fastjson.JSONObject;
import com.ls.base.BaseResponse;
import com.ls.pay.input.dto.PayCratePayTokenDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

public interface PayMentTranscanTokenService {

    /**
     * 创建支付令牌
     *
     * @return
     */
    @GetMapping("/cratePayToken")
    public BaseResponse<JSONObject> cratePayToken(@Validated PayCratePayTokenDto payCratePayTokenDto);
}
