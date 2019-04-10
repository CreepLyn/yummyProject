package com.yummy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

/**
 * 项目启动
 * @author Creeplyn
 * @Date 2019/2/17
 */
@EnableJpaRepositories(basePackages = "com.yummy.repository")
@EntityScan(basePackages = "com.yummy.modal")
@ComponentScan(basePackages="com.yummy")
@EnableAutoConfiguration
@EnableScheduling
@SpringBootApplication
public class YummyApplication {
    public static void main(String[] args){
        SpringApplication.run(YummyApplication.class, args);
    }
}
