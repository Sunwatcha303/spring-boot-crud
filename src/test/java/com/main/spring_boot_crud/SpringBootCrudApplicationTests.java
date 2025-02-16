package com.main.spring_boot_crud;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootCrudApplicationTests {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> SpringBootCrudApplication.main(new String[]{}));
	}

}
