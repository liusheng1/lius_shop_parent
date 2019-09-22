package com.ls.member.feign;

import com.ls.member.MemberRegisterService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-ls-member")
public interface MemberRegisterServiceFeign extends MemberRegisterService {
}
