package com.ls.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
/**
 * 〈一句话功能简述〉<br>
 * 〈注册中心eureka〉
 *
 * @author liusheng
 * @create 2019/8/2
 * @since 1.0.0
 */

@SpringBootApplication
@EnableEurekaServer
public class AppEureka {

	public static void main(String[] args) {
		SpringApplication.run(AppEureka.class, args);
	}
}
