package com.stubhub.payment.seller.accounting.entity

import com.stubhub.payment.seller.accounting.common.Consts

class VendorStatement (var date_received: String?, var date_sent: String?, var date_confirmed: String?, var sh_payment_id: String?, var sh_order_id: String?,
                       var amount: String?, var payment_method: String?, var account_name: String?, var reference_id: String?, var payment_status: String?,
                       var return_code: String?, var date_returned: String?, var single_payment_sent_as: String?, var currency: String?) {
  constructor() : this(null, null, null, null, null, null, null, null, null, null, null, null, null, null)
  constructor(list: ArrayList<String?>) : this(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6),
    list.get(7), list.get(8), list.get(9), list.get(10), list.get(11), list.get(12), Consts.DEFAULT_CURRENCY)

}
