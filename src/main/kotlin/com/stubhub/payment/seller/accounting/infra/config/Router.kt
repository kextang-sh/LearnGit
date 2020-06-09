package com.stubhub.payment.seller.accounting.infra.config

import com.stubhub.payment.seller.accounting.sample.bigquery.BQHandler
import com.stubhub.payment.seller.accounting.nocheck.handler.NocheckHandler
import com.stubhub.payment.seller.accounting.sample.spanner.handler.InstructionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.coRouter

/* 
 * Author: zzhao3@stuhhub.com
 * Date: 2020/5/8 16:57
 * Desc: 
 */
@Configuration
class Router {

  /**
   * @Author xudluo
   * @Desc Nocheck Router
   * @Date 11:39 AM 2020/5/22
   * @Param
   * @Return
   */
  @Bean
  fun nocheckRouters(nocheckHandler: NocheckHandler): RouterFunction<ServerResponse> = coRouter {
    POST("/nocheck/import", nocheckHandler::importExcel)
  }

  @Bean
  fun gcpTestRouters(instructionHandler: InstructionHandler, bqHandler: BQHandler): RouterFunction<ServerResponse> =
    coRouter {
      // Spanner
      "spanner".nest {
        POST("/save", instructionHandler::save)
        GET("/find", instructionHandler::findById)
      }
      // BigQuery
      "bigquery".nest { GET("/save", bqHandler::save) }
    }
}
