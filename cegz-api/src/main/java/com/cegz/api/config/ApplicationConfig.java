package com.cegz.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.cegz.api.config.pojo.ServerAck;

/**
 * 配置文件读取文件
 * @author lijiaxin
 * @date 2018年4月9日
 */
@Configuration
public class ApplicationConfig {
	
	@Bean(name="serverAck")
	@Scope("prototype")
	public ServerAck getServerAck() {
		return new ServerAck();
	}

}
