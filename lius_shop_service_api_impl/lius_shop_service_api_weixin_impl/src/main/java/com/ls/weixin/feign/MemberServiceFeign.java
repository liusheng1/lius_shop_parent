package com.ls.weixin.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.ls.member.MemberService;

@FeignClient("app-mayikt-member")
public interface MemberServiceFeign extends MemberService {

}
