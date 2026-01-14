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
     * 
     * @return ข้อความต้อนรับ
     */
    @GetMapping("/api/hello")
    public String getHelloMessage() {
        return "Hello from Spring Boot!";
    }

    @GetMapping("/api/hello/{name}")
    public String getHelloForName(@org.springframework.web.bind.annotation.PathVariable String name) {
        return "Hello, " + name + "!";
    }

    @org.springframework.web.bind.annotation.PostMapping("/api/echo")
    public String echo(@org.springframework.web.bind.annotation.RequestBody String message) {
        return message;
    }

    @GetMapping("/api/status")
    public String status() {
        return "OK";
    }

    @GetMapping("/api/random")
    public int randomInt() {
        return new java.util.Random().nextInt(101); // 0-100
    }

    @org.springframework.web.bind.annotation.PutMapping("/api/reverse")
    public String reverse(@org.springframework.web.bind.annotation.RequestBody String s) {
        return new StringBuilder(s == null ? "" : s).reverse().toString();
    }
}