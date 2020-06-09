package com.stubhub.payment.seller.accounting.sample.bigquery.common

import com.stubhub.payment.seller.accounting.bean.BillModel
import org.apache.commons.lang3.StringUtils
import java.text.SimpleDateFormat
import java.util.*

@Suppress("TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
class Util {

  companion object {
    val date_format_default: String = "yyyy-MM-dd HH:mm:ss" //default date format

    val FORMAT_NOT_SUPPORT = "formatNotSupport"
    val FILE_SIZE_1G = 1073741824L
    val FILE_SIZE_32G = 32000000000L

    val BQ_REQUEST_MSG_FORMAT = "job=%s, received request=%s"
    val BQ_JOB_STATUS_MSG_FORMAT = "status=%s, job=%s, request=%s"

    val ERROR_MSG_FORMAT = "class=%s, method=%s, msg=%s"
    val ERROR_MSG_FORMAT_OBJECT = "class=%s, method=%s, msg=%s, request=%s"

    /**
     * date format
     */
    fun parseLongDate(date: Date): String {
      return SimpleDateFormat(date_format_default).format(date)
    }

    fun generateRowId(row: Map<String, Any>, vararg rowKeys: String): String? {
      val sb = StringBuilder()
      Arrays.asList(rowKeys).forEach { key -> sb.append(row.get(key.toString())) }
      return sb.toString()
    }

    fun isEmpty(vendorStatement: BillModel): Boolean {
      if (StringUtils.isBlank(vendorStatement.sh_payment_id) && StringUtils.isBlank(vendorStatement.account_name) && StringUtils.isBlank(vendorStatement.amount) &&
        StringUtils.isBlank(vendorStatement.sh_order_id) && StringUtils.isBlank(vendorStatement.payment_status) && StringUtils.isBlank(vendorStatement.payment_method) &&
        StringUtils.isBlank(vendorStatement.date_received)) {
        return true
      }
      return false
    }
  }





}
