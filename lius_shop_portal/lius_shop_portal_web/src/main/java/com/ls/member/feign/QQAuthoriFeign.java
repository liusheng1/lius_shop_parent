package com.ls.member.feign;

import com.ls.member.QQAuthoriService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-ls-member")
public interface QQAuthoriFeign extends QQAuthoriService {

}
