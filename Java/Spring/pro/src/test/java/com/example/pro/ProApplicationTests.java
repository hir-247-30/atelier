package com.example.pro;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mainMethodCoverage() {
		ProApplication.main(new String[]{});
	}

}
