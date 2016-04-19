package com.bird.annotations.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.bird.annotations.ServiceScan;

@Configuration
@ComponentScan(basePackageClasses=ServiceScan.class)
public class ServiceConfig {

}
