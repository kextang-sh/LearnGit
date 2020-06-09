package com.stubhub.payment.seller.accounting.sample.bigquery;

import com.google.cloud.bigquery.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * Author: zzhao3@stuhhub.com
 * Date: 2020/3/29 14:14
 * Desc:
 */
@Service
public class BQService {
  @Value("${gcp.bigquery.demo.project}")
  private String project;
  @Value("${gcp.bigquery.demo.dataset}")
  private String dataset;

  private BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

  public void insert(TableId tableId, Map<String, Object> row) {
    InsertAllRequest.RowToInsert rowToInsert =
        InsertAllRequest.RowToInsert.of(generateRowId(row, "id"), row);
    InsertAllResponse insertResponse =
        bigquery.insertAll(InsertAllRequest.newBuilder(tableId).addRow(rowToInsert).build());
    if (insertResponse.hasErrors()) {
      System.out.println("Errors occurred while inserting rows: " + insertResponse);
    }
  }

  public void batchInsert(TableId tableId, List<Map<String, Object>> rowList) {
    Set<InsertAllRequest.RowToInsert> rowsSet =
        rowList
            .stream()
            .map(row -> InsertAllRequest.RowToInsert.of(generateRowId(row, "id", "value"), row))
            .collect(Collectors.toSet());
    InsertAllResponse insertResponse =
        bigquery.insertAll(InsertAllRequest.newBuilder(tableId).setRows(rowsSet).build());
    if (insertResponse.hasErrors()) {
      System.out.println("Errors occurred while inserting rows: " + insertResponse);
    }
  }

  /*
   * Row with the same rowId will not be inserted as a new row
   */
  private String generateRowId(Map<String, Object> row, String... rowKeys) {
    StringBuilder sb = new StringBuilder();
    Arrays.asList(rowKeys).forEach(key -> sb.append(row.get(key)));
    return sb.toString();
  }
}
