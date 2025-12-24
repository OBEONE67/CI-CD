package com.example.my_ci_cd_backend;

import com.example.my_ci_cd_backend.controller.HelloController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyCiCdBackendApplicationTests {

	@Test
	void testControllerDirectly() {
		// 1. สร้าง Object ของ Controller ขึ้นมาตรงๆ (ไม่ต้องพึ่งระบบ Auto ของ Spring)
		HelloController controller = new HelloController();

		// 2. เรียกใช้ Method ที่ต้องการทดสอบ
		String result = controller.getHelloMessage();

		// 3. เช็คผลลัพธ์ว่าตรงกับที่เขียนไว้ไหม
		assert(result.equals("Hello from "));
	}
	void contextLoads() {
	}

}
