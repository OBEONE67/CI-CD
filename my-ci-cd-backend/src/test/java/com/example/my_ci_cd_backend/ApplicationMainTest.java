package com.example.my_ci_cd_backend;

import org.junit.jupiter.api.Test;

class ApplicationMainTest {

    @Test
    void main_runsWithoutException() {
        // simply invoke main to exercise the bootstrap code
        MyCiCdBackendApplication.main(new String[] {});
    }

}
