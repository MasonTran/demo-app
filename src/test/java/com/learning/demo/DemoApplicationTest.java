package com.learning.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(args = {"--spring.config.location=classpath:/override.properties"})
class DemoApplicationTest {

	@Test
	void contextLoads() {
	}

}
