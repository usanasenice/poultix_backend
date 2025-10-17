package com.poultix.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload.directory:uploads}")
    private String uploadDirectory;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve general uploads
        registry.addResourceHandler("/public/**")
                .addResourceLocations("file:" + uploadDirectory + "/");
        
        // Serve pharmacy documents
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:" + uploadDirectory + "/");
    }
}
