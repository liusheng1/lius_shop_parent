package com.ls.pay.service;

import com.ls.base.BaseResponse;
import com.ls.pay.output.dto.PayMentTransacDTO;
import org.springframework.web.bind.annotation.GetMapping;

public interface PayMentTransacInfoService {

    @GetMapping("/getPayMentTransacInfoByToken")
    public BaseResponse<PayMentTransacDTO>getPayMentTransacInfoByToken(String token);
}
