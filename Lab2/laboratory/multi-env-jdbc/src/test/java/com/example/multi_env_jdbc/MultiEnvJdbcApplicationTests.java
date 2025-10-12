package com.example.multi_env_jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MultiEnvJdbcApplicationTests {

	@Autowired
	private DbProperties dbProperties;

	@Test
	void contextLoads() {
		// Verificăm că DbProperties a fost încărcat corect
		System.out.println("=== TEST DbProperties ===");
		System.out.println("DB Type: " + dbProperties.getType());
		System.out.println("DB Host: " + dbProperties.getHost());
		System.out.println("DB Port: " + dbProperties.getPort());
		System.out.println("DB Name: " + dbProperties.getName());
		System.out.println("DB Username: " + dbProperties.getUsername());
		System.out.println("DB URL: " + dbProperties.getUrl());
		System.out.println("=========================");
	}
}
