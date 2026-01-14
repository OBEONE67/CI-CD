package com.example.my_ci_cd_backend.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloControllerTest {

    @Test
    void getHelloMessage_returnsExpectedText() {
        HelloController controller = new HelloController();
        String result = controller.getHelloMessage();
        assertEquals("Hello from Spring Boot!", result);
    }

}
