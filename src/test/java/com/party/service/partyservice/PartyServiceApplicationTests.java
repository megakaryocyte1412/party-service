package com.party.service.partyservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

@SpringBootTest
class PartyServiceApplicationTests {

	@Test
	void contextLoads() {
		System.out.println(StringUtils.hasText("123"));
	}

}
