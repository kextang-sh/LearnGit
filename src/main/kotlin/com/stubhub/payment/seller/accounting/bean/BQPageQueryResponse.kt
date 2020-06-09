package com.stubhub.payment.seller.accounting.bean

import com.google.cloud.bigquery.TableId

/**
 * @author pengyang
 * @date 2020/5/25
 * @email pengyang@stubhub.com
 * @desc
 */
data class BQPageQueryResponse(var tableId: BQTableId?, var totalRows: Long=0L, var pageToken: String?, var rowSize: Int=0, var rows: List<Map<String, Any>>?) {
  constructor() : this(null, 0L, null, 0, null)
  constructor(tableId: TableId, totalRows: Long, pageToken: String?, nullsFirst: Any) : this()
}
