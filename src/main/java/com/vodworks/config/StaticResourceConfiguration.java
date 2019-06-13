package com.vodworks.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class StaticResourceConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String externalFilePath = "file:" + System.getProperty("user.home") + File.separator + "files" + File.separator;

        System.out.println("File Path Exposed: " + externalFilePath);

        registry.addResourceHandler("/api/**").addResourceLocations(externalFilePath).setCachePeriod(43200);
    }
}
