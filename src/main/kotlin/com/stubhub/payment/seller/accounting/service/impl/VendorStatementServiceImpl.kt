package com.stubhub.payment.seller.accounting.service.impl

import com.google.cloud.bigquery.*
import com.google.cloud.bigquery.InsertAllRequest.RowToInsert
import com.stubhub.payment.seller.accounting.bean.BQPageQueryRequest
import com.stubhub.payment.seller.accounting.bean.BQPageQueryResponse
import com.stubhub.payment.seller.accounting.bean.BillModel
import com.stubhub.payment.seller.accounting.common.Consts
import com.stubhub.payment.seller.accounting.log
import com.stubhub.payment.seller.accounting.sample.bigquery.common.Util
import com.stubhub.payment.seller.accounting.service.VendorStatementService
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Instant
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors
import java.util.stream.IntStream
import java.util.stream.StreamSupport


@Suppress("UNREACHABLE_CODE")
@Service("vendorStatementService")
class VendorStatementServiceImpl() : VendorStatementService {


  private var tableId = TableId.of(Consts.project, Consts.dataset, Consts.table)

  @Autowired
  lateinit var bigQuery: BigQuery

  override fun insert(vendorStatement: BillModel) {

    val dataMap = vendorStatementToMap(vendorStatement)
    val rowToInsert = RowToInsert.of(UUID.randomUUID().toString(), dataMap)
    val insertResponse: InsertAllResponse = bigQuery.insertAll(InsertAllRequest.newBuilder(tableId).addRow(rowToInsert).build())
    if (insertResponse.hasErrors()) {
      println("Errors occurred while inserting rows: $insertResponse")
    }
  }

  override fun batchInsert(vendorStatementList: MutableList<BillModel>) {
    val tempList = mutableListOf<Map<String, Any>>()
    vendorStatementList.forEach {
      val dataMap = vendorStatementToMap(it)
      dataMap?.let { data -> tempList.add(data) }
    }
    val rowsSet: Set<RowToInsert> = tempList
      .stream()
      .map { row -> RowToInsert.of(UUID.randomUUID().toString(), row) }
      .collect(Collectors.toSet())
    val insertResponse: InsertAllResponse = bigQuery.insertAll(InsertAllRequest.newBuilder(tableId).setRows(rowsSet).build())
    if (insertResponse.hasErrors()) {
      println("Errors occurred while inserting rows: $insertResponse")
    }
  }

  /**
   * Query Vendor Statement
   */
  override fun queryVendorStatementList(vendorStatement: BillModel): TableResult? {
    var executeSql = StringBuffer(Consts.queryVendorStatementSql)
    if (!Util.isEmpty(vendorStatement)) {
      executeSql.append(" where 1=1")
      if (!StringUtils.isBlank(vendorStatement.date_received)) {
        executeSql.append(" and date_received >= @date_received")
      }
      if (!StringUtils.isBlank(vendorStatement.payment_method)) {
        executeSql.append(" and payment_method= @payment_method")
      }
      if (!StringUtils.isBlank(vendorStatement.payment_status)) {
        executeSql.append(" and payment_status= @payment_status")
      }
      if (!StringUtils.isBlank(vendorStatement.sh_order_id)) {
        executeSql.append(" and sh_order_id = @sh_order_id")
      }
      if (!StringUtils.isBlank(vendorStatement.sh_payment_id)) {
        executeSql.append(" and sh_payment_id = @sh_payment_id")
      }
      if (!StringUtils.isBlank(vendorStatement.amount)) {
        executeSql.append(" and amount = @amount")
      }
      if (!StringUtils.isBlank(vendorStatement.account_name)) {
        executeSql.append(" and account_name = @account_name")
      }
    }

    val queryJobConfiguration = QueryJobConfiguration.newBuilder(executeSql.toString())
    queryJobConfiguration.addNamedParameter("sh_payment_id", QueryParameterValue.string(vendorStatement.sh_payment_id))
    queryJobConfiguration.addNamedParameter("account_name", QueryParameterValue.string(vendorStatement.account_name))
    queryJobConfiguration.addNamedParameter("amount", QueryParameterValue.string(vendorStatement.amount))
    queryJobConfiguration.addNamedParameter("sh_order_id", QueryParameterValue.string(vendorStatement.sh_order_id))
    queryJobConfiguration.addNamedParameter("payment_status", QueryParameterValue.string(vendorStatement.payment_status))
    queryJobConfiguration.addNamedParameter("payment_method", QueryParameterValue.string(vendorStatement.payment_method))
    queryJobConfiguration.addNamedParameter("date_received", QueryParameterValue.string(vendorStatement.date_received))
    queryJobConfiguration.setDefaultDataset(Consts.dataset)
    val build = queryJobConfiguration.build()
    return bigQuery.query(build)
  }

  private fun vendorStatementToMap(vendorStatement: BillModel): MutableMap<String, Any>? {
    val dataMap = mutableMapOf<String, Any>()
    dataMap.put("account_name", vendorStatement.account_name!!)
    if (StringUtils.isBlank(vendorStatement.amount)) {
      dataMap.put("amount", 0)
    } else {
      if (!Character.isDigit(vendorStatement.amount!![0])) {
        dataMap.put("amount", vendorStatement.amount!!.substring(1))
      } else {
        dataMap.put("amount", vendorStatement.amount!!)
      }
    }
    if (!Character.isDigit(vendorStatement.amount!![0])) {
      dataMap.put("currency", vendorStatement.amount!![0].toString())
    } else {
      dataMap.put("currency", vendorStatement.currency!!)
    }
    dataMap.put("date_confirmed", vendorStatement.date_confirmed!!)
    dataMap.put("date_received", vendorStatement.date_received!!)
    if (StringUtils.isBlank(vendorStatement.date_returned)) {
      dataMap.put("date_returned", Util.parseLongDate(Date.from(Instant.now())))
    } else {
      dataMap.put("date_returned", vendorStatement.date_returned!!)
    }
    dataMap.put("date_sent", vendorStatement.date_sent!!)
    dataMap.put("payment_method", vendorStatement.payment_method!!)
    dataMap.put("payment_status", vendorStatement.payment_status!!)
    dataMap.put("reference_id", vendorStatement.reference_id!!)
    if (StringUtils.isBlank(vendorStatement.return_code)) {
      dataMap.put("return_code", "0") // TODO re define
    } else dataMap.put("return_code", vendorStatement.return_code!!)
    dataMap.put("sh_order_id", vendorStatement.sh_order_id!!)
    dataMap.put("sh_payment_id", vendorStatement.sh_payment_id!!)
    if (StringUtils.isBlank(vendorStatement.single_payment_sent_as)) {
      dataMap.put("single_payment_sent_as", 0)
    } else {
      if (!Character.isDigit(vendorStatement.single_payment_sent_as!![0])) {
        dataMap.put("single_payment_sent_as", vendorStatement.single_payment_sent_as!!.substring(1))
      } else {
        dataMap.put("single_payment_sent_as", vendorStatement.single_payment_sent_as!!)
      }
    }
    return dataMap
  }

  override fun pageQueryVendorStatementList(bqPageQueryRequest: BQPageQueryRequest): Mono<BQPageQueryResponse> {

    try {
      val table = bigQuery.getTable(tableId)
      return Mono.just(table).map(fun(table: Table): BQPageQueryResponse {
        var tableResult = Optional.ofNullable(bqPageQueryRequest.startIndex).map {
          bigQuery.listTableData(tableId, table.getDefinition<TableDefinition>().schema, BigQuery.TableDataListOption.startIndex(it), BigQuery.TableDataListOption.pageSize(bqPageQueryRequest.pageSize))
        }.orElse(
          bigQuery.listTableData(tableId, table.getDefinition<TableDefinition>().schema, BigQuery.TableDataListOption.pageSize(bqPageQueryRequest.pageSize), BigQuery.TableDataListOption.pageToken(bqPageQueryRequest.pageToken))
        )
        val fieldList = tableResult
          .schema
          .fields
          .stream()
          .map { obj: Field -> obj.name }
          .collect(Collectors.toList())
        val bqPageQueryResponse = BQPageQueryResponse(tableId, tableResult.totalRows, tableResult.nextPageToken, StreamSupport.stream(tableResult.values.spliterator(), false)
          .map { fieldValueList: FieldValueList ->
            fieldValueList
              .stream()
              .map { obj: FieldValue -> obj.value }
              .collect(Collectors.toList())
          }
          .collect(Collectors.toList())
          .stream()
          .map { objectList: List<Any> ->
            IntStream.range(0, fieldList.size)
              .boxed()
              .collect(
                Collectors.toMap(Function { index: Int? -> fieldList[index!!] },
                  Function { i: Int? ->
                    Optional.ofNullable(objectList[i!!])
                      .orElse(Optional.empty<Any>())
                  }))
          }
          .collect(Collectors.toList()))
        return bqPageQueryResponse
      }).map (fun (bqPageQueryResponse: BQPageQueryResponse): BQPageQueryResponse {
        bqPageQueryResponse.rowSize = bqPageQueryResponse.rows!!.size
        return bqPageQueryResponse
      })
    } catch (queryex: BigQueryException) {
      log.info(java.lang.String.format(
        Util.BQ_JOB_STATUS_MSG_FORMAT, "Failed", "Page query table", "BigQueryException"))
      return Mono.error<BQPageQueryResponse>(queryex)
    } catch (nullex: NullPointerException) {
      log.info(java.lang.String.format(
        Util.BQ_JOB_STATUS_MSG_FORMAT, "Failed", "Page query table", "NullPointerException"))
      return Mono.error(nullex)
    } catch (ex: Exception) {
      log.info(java.lang.String.format(
        Util.BQ_JOB_STATUS_MSG_FORMAT, "Failed", "Page query table", "Exception"))
      return Mono.error(ex)
    }
  }
}


