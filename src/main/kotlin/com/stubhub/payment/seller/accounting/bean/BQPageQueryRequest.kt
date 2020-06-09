package com.stubhub.payment.seller.accounting.bean

/**
 * @author pengyang
 * @date 2020/5/25
 * @email pengyang@stubhub.com
 * @desc
 */

data class BQPageQueryRequest(var pageToken: String?, var pageSize: Long, var startIndex: Long?) {
  constructor() : this(null, 10L, 1)
}
