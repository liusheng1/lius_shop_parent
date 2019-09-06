package com.ls.member.feign;

import com.ls.weixin.service.VerificaCodeService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-ls-weixin")
public interface VerificaCodeServiceFeign extends VerificaCodeService {

}
