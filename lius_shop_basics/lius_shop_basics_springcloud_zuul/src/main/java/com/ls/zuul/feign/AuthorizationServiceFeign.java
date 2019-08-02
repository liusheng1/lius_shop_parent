package com.ls.zuul.feign;

import com.alibaba.fastjson.JSONObject;
import com.ls.auth.service.api.AuthorizationService;
import com.ls.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("app-mayikt-auth")
public interface AuthorizationServiceFeign extends AuthorizationService {

    BaseResponse<JSONObject> getAppInfo(String accessToken);
}
