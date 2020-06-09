package com.stubhub.payment.seller.accounting.nocheck.handler

import com.stubhub.payment.seller.accounting.nocheck.service.NocheckService
import com.stubhub.payment.seller.accounting.service.VendorStatementService
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait


/**
 * @author xudluo
 * @date 2020/5/19
 * @email xudluo@stubhub.com
 * @desc
 */
@Component
class NocheckHandler(
  @Autowired
  val nocheckService: NocheckService,
  val vendorStatementService: VendorStatementService
) {
  val log: Logger = LoggerFactory.getLogger(this::class.java)
  /**
   * @author xudluo
   * @date 2020/5/19
   * @email xudluo@stubhub.com
   * @desc
   */
  suspend fun importExcel(request: ServerRequest): ServerResponse {
    val multi = request.multipartData().awaitFirstOrNull()
    val parseRet = multi?.let { nocheckService.parseModel(it) }
    if (parseRet != null) {
      if (1 == parseRet.status) {
        //call batchInsert into BQ
        parseRet.listBillModel?.let { vendorStatementService.batchInsert(it) }
        return ServerResponse.ok().bodyValueAndAwait("ok!")
      } else if (-1 == parseRet.status) {
        return ServerResponse.ok().bodyValueAndAwait("error file")
      } else return ServerResponse.ok().bodyValueAndAwait("unknowed file")
    } else {
      //TODO define a normal DTO
      return ServerResponse.ok().bodyValueAndAwait("error file")
    }
  }
}
