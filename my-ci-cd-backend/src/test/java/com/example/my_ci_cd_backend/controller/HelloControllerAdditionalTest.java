package com.example.my_ci_cd_backend.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloControllerAdditionalTest {

    @Test
    void getHelloForName_returnsGreeting() {
        HelloController c = new HelloController();
        assertEquals("Hello, Alice!", c.getHelloForName("Alice"));
    }

    @Test
    void echo_returnsSameString() {
        HelloController c = new HelloController();
        assertEquals("ping", c.echo("ping"));
    }

    @Test
    void status_returnsOK() {
        HelloController c = new HelloController();
        assertEquals("OK", c.status());
    }

    @Test
    void randomInt_inRange() {
        HelloController c = new HelloController();
        int r = c.randomInt();
        assertTrue(r >= 0 && r <= 100, "randomInt should be between 0 and 100");
    }

    @Test
    void reverse_reversesString() {
        HelloController c = new HelloController();
        assertEquals("cba", c.reverse("abc"));
        assertEquals("", c.reverse(null));
    }
}
