package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class ProjectMvcApplicationTests {

	@Test
	void testPasswordEncoder() {
		PasswordEncoder pe = new BCryptPasswordEncoder();
		String str = "admin";
		System.out.println("encoded string: "+str);
		System.out.println(pe.encode(str));
	}

}
