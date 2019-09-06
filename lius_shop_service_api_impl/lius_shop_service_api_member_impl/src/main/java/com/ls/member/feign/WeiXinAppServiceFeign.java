package com.ls.member.feign;

import com.ls.weixin.service.WeiXinAppService;
import org.springframework.cloud.openfeign.FeignClient;



@FeignClient(name = "app-ls-weixin")
public interface WeiXinAppServiceFeign extends WeiXinAppService {

	// /**
	// * 功能说明： 应用服务接口
	// */
	// @GetMapping("/getApp")
	// public AppEntity getApp();
}
