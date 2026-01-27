package com.company.ticket_service;

import com.company.ticket_service.config.TestCacheConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestCacheConfig.class)
class TicketServiceApplicationTests {

  @Test
  void contextLoads() {}
}