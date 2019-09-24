package com.ls.pay.service;

import com.ls.pay.output.dto.PaymentChannelDTO;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface PayMentChannelService {
    /**
     * 获取说有的支付渠道
     * @return
     */
    @GetMapping("/getAllChannel")
    public List<PaymentChannelDTO> getAllChannel();
}
