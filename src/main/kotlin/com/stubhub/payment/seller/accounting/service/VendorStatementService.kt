package com.stubhub.payment.seller.accounting.service

import com.google.cloud.bigquery.TableResult
import com.stubhub.payment.seller.accounting.bean.BQPageQueryRequest
import com.stubhub.payment.seller.accounting.bean.BQPageQueryResponse
import com.stubhub.payment.seller.accounting.bean.BillModel
import reactor.core.publisher.Mono

interface VendorStatementService {

  fun insert(vendorStatement: BillModel)

  fun batchInsert(vendorStatementList: MutableList<BillModel>)

  fun queryVendorStatementList(vendorStatement: BillModel): TableResult?

  fun pageQueryVendorStatementList(bqPageQueryRequest: BQPageQueryRequest): Mono<BQPageQueryResponse>?

}
