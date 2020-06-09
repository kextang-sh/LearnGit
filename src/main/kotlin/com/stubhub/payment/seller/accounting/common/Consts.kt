package com.stubhub.payment.seller.accounting.common

/**
 * @Author xudluo
 * @Date 2020/5/15
 * @Email xudluo@stubhub.com
 * @Desc public values
 */
class Consts {
  companion object {
    //Currently, the number of nocheck table columns is 13
    val BILL_COLUM_MAX = 13

    //default Currency $
    val DEFAULT_CURRENCY = "$"

    //STATUS
    //EMPTY BILL EXCEL
    val EXCEL_OK_CODE = 1
    val ECXEL_EMPTY_CODE = -1


    val project:String = "payments-platform-dev-17663"
    val dataset:String = "payment_accounting"
    val table:String = "Vendor_Statement"

    var queryVendorStatementSql:String = "select * from Vendor_Statement "

  }
}
