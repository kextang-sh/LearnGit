package com.stubhub.payment.seller.accounting.controller

import com.google.cloud.bigquery.TableResult
import com.stubhub.payment.seller.accounting.bean.BQPageQueryRequest
import com.stubhub.payment.seller.accounting.bean.BQPageQueryResponse
import com.stubhub.payment.seller.accounting.bean.BillModel
import com.stubhub.payment.seller.accounting.service.VendorStatementService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("vendorStatement")
class VendorStatementController {

  @Autowired
  lateinit var vendorStatementService: VendorStatementService;

  @PostMapping("insertVendorStatement")
  fun insertVendorStatement(vendorStatement: BillModel) {
    vendorStatementService.insert(vendorStatement)
  }

  @PostMapping("batchInsertVendorStatement")
  fun batchInsertVendorStatement(vendorStatementList: MutableList<BillModel>) {
    vendorStatementService.batchInsert(vendorStatementList)
  }

  @PostMapping("queryVendorStatementList")
  fun queryVendorStatementList(vendorStatement: BillModel): TableResult? {
    val vendorStatementList = vendorStatementService.queryVendorStatementList(vendorStatement)
    return vendorStatementList
  }

  @PostMapping("pageQueryVendorStatementList")
  fun pageQueryVendorStatementList(bqPageQueryRequest: BQPageQueryRequest): Mono<BQPageQueryResponse>? {

    return vendorStatementService.pageQueryVendorStatementList(bqPageQueryRequest)
  }
}
