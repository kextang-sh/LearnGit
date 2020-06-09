package com.stubhub.payment.seller.accounting.sample.bigquery;

import com.google.cloud.bigquery.TableId;

import java.time.Instant;
import java.util.*;

/*
 * Author: zzhao3@stuhhub.com
 * Date: 2020/3/29 14:41
 * Desc: Sample BigQuery table: customer-dna-dev.demo.demo
 */
public class BQServiceTest {

  static final String project = "payments-platform-dev-17663";
  static final String dataset = "sample";
  static final String table = "demo";

  public static void main(String[] args) {
    BQService bqService = new BQService();
    TableId tableId = TableId.of(project, dataset, table);

    Map<String, Object> row = new HashMap<>();
    row.put("id", "demo1");
    row.put("value", 103);
    row.put("date", Date.from(Instant.now()).toString());
    bqService.insert(tableId, row);

    // List<Map<String, Object>> rows = Arrays.asList(
    //     new HashMap<String, Object>() {{
    //       put("id", UUID.randomUUID().toString());
    //       put("value", 100);
    //       put("date", new DateTime());
    //     }},
    //     new HashMap<String, Object>() {{
    //       put("id", "row5");
    //       put("value", 100);
    //       put("date", new DateTime());
    //     }}
    // );
    // bqService.batchInsert(tableId, rows);
  }
}
