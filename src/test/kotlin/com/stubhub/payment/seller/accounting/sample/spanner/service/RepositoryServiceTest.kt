package com.stubhub.payment.seller.accounting.sample.spanner.service

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import javax.annotation.Resource

/**
 * @author xudluo
 * @date 2020/5/21
 * @email xudluo@stubhub.com
 * @desc
 */
@SpringBootTest
@ActiveProfiles("local")
internal class RepositoryServiceTest(
  @Autowired
  val repositoryService: RepositoryService
) {


  @BeforeEach
  fun setUp() {
  }

  @AfterEach
  fun tearDown() {
  }

  @Test
  fun hello() {
    repositoryService.hello()
  }
}