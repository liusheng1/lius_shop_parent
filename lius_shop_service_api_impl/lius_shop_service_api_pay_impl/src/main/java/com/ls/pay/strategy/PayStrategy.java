package com.ls.pay.strategy;

import com.ls.pay.mapper.entity.PayMentChannelDo;
import com.ls.pay.output.dto.PayMentTransacDTO;

public interface PayStrategy {
    /**
     *
     * @param pymentChannel  支付渠道
     * @param payMentTransacDTO 支付信息
     * @return
     */
    public String toPayHtml(PayMentChannelDo pymentChannel, PayMentTransacDTO payMentTransacDTO);
}
