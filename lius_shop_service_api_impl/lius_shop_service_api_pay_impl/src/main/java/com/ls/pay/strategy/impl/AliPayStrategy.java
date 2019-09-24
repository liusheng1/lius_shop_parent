package com.ls.pay.strategy.impl;

import com.ls.pay.mapper.entity.PayMentChannelDo;
import com.ls.pay.output.dto.PayMentTransacDTO;
import com.ls.pay.strategy.PayStrategy;

public class AliPayStrategy implements PayStrategy {
    @Override
    public String toPayHtml(PayMentChannelDo pymentChannel, PayMentTransacDTO payMentTransacDTO) {
        return null;
    }
}
