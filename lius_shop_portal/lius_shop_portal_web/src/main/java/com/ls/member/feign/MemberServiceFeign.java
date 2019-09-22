package com.ls.member.feign;

import com.ls.member.MemberService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-ls-member")
public interface MemberServiceFeign extends MemberService {
}
