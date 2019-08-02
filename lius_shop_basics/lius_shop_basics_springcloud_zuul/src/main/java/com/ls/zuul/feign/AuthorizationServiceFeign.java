package com.ls.zuul.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.ls.auth.service.api.AuthorizationService;

@FeignClient("app-mayikt-auth")
public interface AuthorizationServiceFeign extends AuthorizationService {

}
