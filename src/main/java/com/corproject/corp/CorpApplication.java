package com.corproject.corp;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CorpApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(CorpApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(CorpApplication.class, args);
	}

	@Bean
	public RedissonClient redissonClient(){
		Config config = new Config();
		config.useSingleServer().setAddress("redis://127.0.0.1:6379");
		return Redisson.create(config);
	}
}
