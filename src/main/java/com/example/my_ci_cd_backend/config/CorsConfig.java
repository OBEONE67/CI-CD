package com.example.my_ci_cd_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // อนุญาตให้ทุก Path (/**) สามารถถูกเรียกได้
        registry.addMapping("/**")
                // อนุญาตเฉพาะ Origin ที่เป็น Vue.js Development Server ของเรา
                .allowedOrigins("http://localhost:5173")
                // อนุญาต HTTP Methods ที่จำเป็น เช่น GET, POST, PUT, DELETE
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // อนุญาตให้ส่ง Header และ Credentials (ถ้ามี)
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}