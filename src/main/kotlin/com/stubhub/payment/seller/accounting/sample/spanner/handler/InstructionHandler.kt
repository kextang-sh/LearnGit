package com.stubhub.payment.seller.accounting.sample.spanner.handler

import com.stubhub.payment.seller.accounting.sample.spanner.repository.InstructionRepository
import com.stubhub.payment.seller.accounting.sample.spanner.vo.PaymentInstruction
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import org.springframework.web.reactive.function.server.queryParamOrNull

/* 
 * Author: zzhao3@stuhhub.com
 * Date: 2020/5/6 18:27
 * Desc: 
 */
@RestController
class InstructionHandler(
  private val instructionRepository: InstructionRepository
) {
  suspend fun save(request: ServerRequest): ServerResponse {
    val instruction = request.bodyToMono(PaymentInstruction::class.java).awaitFirstOrNull()
    return instruction?.let {
      instructionRepository.save(it)
      ServerResponse.ok().bodyValueAndAwait(instruction)
    } ?: ServerResponse.ok().buildAndAwait()
  }

  suspend fun findById(request: ServerRequest): ServerResponse {
    val id = request.queryParamOrNull("id")!!.toLong()
    val result = instructionRepository.findById(id)
    return ok().buildAndAwait()
  }
}
