package com.stubhub.payment.seller.accounting.infra.config

import com.google.cloud.bigquery.BigQuery
import com.google.cloud.bigquery.BigQueryOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/* 
 * Author: zzhao3@stuhhub.com
 * Date: 2020/4/27 09:37
 * Desc: 
 */
@Configuration
class VendorStatementConfig {

  @Value("\${spring.cloud.gcp.spanner.database}")
  val datasetOK: String? = null

  @Bean
  fun bigQuery(): BigQuery? {
    return BigQueryOptions.getDefaultInstance().service
  }
}
