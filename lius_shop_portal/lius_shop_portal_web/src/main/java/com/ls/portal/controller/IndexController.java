package com.ls.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	/**
	 * 跳转到index页面
	 */
	private static final String INDEX_FTL = "index";

	@RequestMapping("/")
	public String index() {

		return INDEX_FTL;
	}
	// 作业题：完成退出功能 实现唯一登陆心跳检测 前端定时器 定时 使用js 读取本地cookie 信息 使用token 查询当前状态 如果token状态为1的话，页面直接刷新下。

}
