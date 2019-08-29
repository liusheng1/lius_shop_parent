package com.ls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
//@EnableSwagger2Doc
//@MapperScan(basePackages = "com.ls.zuul")
// @EnableApolloConfig
@EnableFeignClients
public class AppGateWay {
    //
    // @ApolloConfig
    // private Config config;
//	@Value("${ls.zuul.swagger.document}")
//	private String document;

    public static void main(String[] args) {
        SpringApplication.run(AppGateWay.class, args);
    }

    // 添加文档来源
//	@Component
//	@Primary
//	class DocumentationConfig implements SwaggerResourcesProvider {
//		@Override
//		public List<SwaggerResource> get() {
//			return resources();
//		}
//
//		private List<SwaggerResource> resources() {
//			// 从阿波罗平台获取配置文件
//			// String swaDocJson =
//			// config.getProperty("ls.zuul.swagger.document", null);
//			JSONArray docJsonArray = JSONArray.parseArray(document);
//			List resources = new ArrayList<>();
//			// 遍历集合数组
//			for (Object object : docJsonArray) {
//				JSONObject jsonObject = (JSONObject) object;
//				String name = jsonObject.getString("name");
//				String location = jsonObject.getString("location");
//				String version = jsonObject.getString("version");
//				resources.add(swaggerResource(name, location, version));
//			}
//			return resources;
//		}
//
//		private SwaggerResource swaggerResource(String name, String location, String version) {
//			SwaggerResource swaggerResource = new SwaggerResource();
//			swaggerResource.setName(name);
//			swaggerResource.setLocation(location);
//			swaggerResource.setSwaggerVersion(version);
//			return swaggerResource;
//		}

//	}
    // 微服务网关Swagger 如何集成阿波罗 实现动态添加微服务Swagger文档

    /**
     * 大体思路分析：<br>
     * 1.将swaggerResource封装程json数组格式、<br>
     * 2.使用原生代码方式获取配置文件 3.写一个监听，监听文件是否发生变化，发生变化重新加载值 递归算法
     * 4.ls.zuul.swagger.document=
     *
     */

}
