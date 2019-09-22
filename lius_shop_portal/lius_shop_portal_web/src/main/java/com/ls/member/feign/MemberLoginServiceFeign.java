package com.ls.member.feign;

import com.ls.member.MemberLoginService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-ls-member")
public interface MemberLoginServiceFeign  extends MemberLoginService {
}
