package com.stubhub.payment.seller.accounting.bean

/**
 * @author pengyang
 * @date 2020/5/25
 * @email pengyang@stubhub.com
 * @desc
 */
class BQTableId(var project: String?, var dataset: String?, var table: String?) {
  constructor() : this(null, null, null)
}
