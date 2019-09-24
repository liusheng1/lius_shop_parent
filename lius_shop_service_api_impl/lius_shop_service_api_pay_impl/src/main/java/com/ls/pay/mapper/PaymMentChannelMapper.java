package com.ls.pay.mapper;

import com.ls.pay.mapper.entity.PayMentChannelDo;
import com.ls.pay.output.dto.PaymentChannelDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PaymMentChannelMapper {
    @Select("SELECT channel_Name  AS channelName , channel_Id AS channelId, merchant_Id AS merchantId,sync_Url AS syncUrl, asyn_Url AS asynUrl,public_Key AS publicKey, private_Key AS privateKey,channel_State AS channelState ,class_ADDRES as classAddres  FROM payment_channel WHERE CHANNEL_STATE='0';")
    public List<PaymentChannelDTO> getAllChannel();
    @Select("SELECT channel_Name  AS channelName , channel_Id AS channelId, merchant_Id AS merchantId,sync_Url AS syncUrl, asyn_Url AS asynUrl,public_Key AS publicKey, private_Key AS privateKey,channel_State AS channelState ,class_ADDRES as classAddres  FROM payment_channel WHERE CHANNEL_STATE='0'  AND channel_Id=#{channelId} ;")
    PayMentChannelDo selectBychannelId(String channelId);
}
