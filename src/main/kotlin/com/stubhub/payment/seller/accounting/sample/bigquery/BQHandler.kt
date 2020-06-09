package com.stubhub.payment.seller.accounting.sample.bigquery

import java.time.Instant
import java.util.Date
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

/* 
 * Author: zzhao3@stuhhub.com
 * Date: 2020/5/8 17:02
 * Desc: 
 */
@Component
class BQHandler(val bqService: BQService) {
  suspend fun save(request: ServerRequest): ServerResponse {
    // val demoEntity = DemoEntity(UUID.randomUUID().toString(), 100, Date.from(Instant.now()).toString())
    val row: MutableMap<String, Any> = HashMap()
    row["id"] = "demo1"
    row["value"] = 103
    row["date"] = Date.from(Instant.now()).toString()
    // TODO insert record to BQ
    return ServerResponse.ok().bodyValueAndAwait(row)
  }
}

data class DemoEntity(
  val id: String,
  val value: Int,
  val date: String
)
