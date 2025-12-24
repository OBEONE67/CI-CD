package com.example.my_ci_cd_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller สำหรับจัดการคำขอ HTTP ที่เกี่ยวข้องกับการทักทาย
 */
@RestController
public class HelloController {

    /**
     * Endpoint สำหรับการเรียก GET ที่ Path /api/hello
     * @return ข้อความต้อนรับ
     */
    @GetMapping("/ap/")
    public String getHelloMessage() {
        return "Hello from Spring Boot!";
    }
}