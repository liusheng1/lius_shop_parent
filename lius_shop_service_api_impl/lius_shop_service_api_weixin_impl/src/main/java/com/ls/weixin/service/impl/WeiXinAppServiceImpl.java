package com.ls.weixin.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import com.ls.base.BaseApiService;
import com.ls.base.BaseResponse;
import com.ls.weixin.entity.AppEntity;
import com.ls.weixin.service.WeiXinAppService;

/**
 * 
 * 
 * @description:微信服务接口的实现
 * @author: 97后互联网架构师-余胜军
 * @contact: QQ644064779、微信yushengjun644 www.ls.com
 * @date: 2019年1月3日 下午3:03:17
 * @version V1.0
 * @Copyright 该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@RestController
public class WeiXinAppServiceImpl extends BaseApiService<AppEntity> implements WeiXinAppService {
	@Value("${ls.weixin.name}")
	private String name;

	public BaseResponse<AppEntity> getApp() {
		// return setResultSuccess(new AppEntity("1111", "22222"));
		return setResultError("系统错误");
	}
	// 定义泛型T的有一定缺点：该接口统一返回一种类型

}
