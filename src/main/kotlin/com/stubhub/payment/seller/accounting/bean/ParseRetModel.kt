package com.stubhub.payment.seller.accounting.bean

/**
 * @author xudluo
 * @date 2020/5/22
 * @email xudluo@stubhub.com
 * @desc
 */
data class ParseRetModel(var status: Int?, var listBillModel: MutableList<BillModel>?) {
  constructor() : this(null, null)
}
