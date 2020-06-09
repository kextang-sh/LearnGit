package com.stubhub.payment.seller.accounting.sample.spanner.vo

import com.google.spanner.v1.TypeCode
import java.util.Date
import org.springframework.cloud.gcp.data.spanner.core.mapping.Column
import org.springframework.cloud.gcp.data.spanner.core.mapping.PrimaryKey
import org.springframework.cloud.gcp.data.spanner.core.mapping.Table
import org.springframework.data.annotation.Id

/* 
 * Author: zzhao3@stuhhub.com
 * Date: 2020/5/6 17:48
 * Desc: 
 */
@Table(name = "PAYMENT_INSTR_NC")
data class PaymentInstruction(
  @PrimaryKey
  @Id
  @Column(name = "PAYMENT_INSTR_NC_ID")
  private val spiId: Long,

  @Column(name = "RESPONSE_CONTENT")
  private val message: String,

  @Column(name = "PAYMENT_INSTR_NC_STATUS_ID")
  private val status: Int,

  @Column(name = "CREATED_BY")
  private val createdBy: String,

  @Column(name = "CREATED_DATE", spannerType = TypeCode.TIMESTAMP)
  private val creationDate: Date,

  @Column(name = "LAST_UPDATED_BY")
  private val lastUpdatedBy: String,

  @Column(name = "LAST_UPDATED_DATE", spannerType = TypeCode.TIMESTAMP)
  private val lastUpdatedDate: Date
)
