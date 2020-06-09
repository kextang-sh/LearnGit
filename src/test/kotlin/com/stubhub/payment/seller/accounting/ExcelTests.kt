package com.stubhub.payment.seller.accounting

import com.stubhub.payment.seller.accounting.bean.BillModel
import com.stubhub.payment.seller.accounting.common.Consts
import com.stubhub.payment.seller.accounting.utils.ExcelUtil
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.String
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.io.FileInputStream


@ExtendWith(SpringExtension::class)
@ActiveProfiles("local")
class ExcelTests {

  @Test
  fun testExcel() {
    val file = FileInputStream("/Users/xudluo/work/Book3.xlsx")
    val workbook = XSSFWorkbook(file)
    val sheet = workbook.getSheet("Sheet1")
    val rows = sheet.iterator()
    while (rows.hasNext()) {
      val currentRow = rows.next()
      val cellsInRow = currentRow.iterator()
      while (cellsInRow.hasNext()) {
        val currentCell = cellsInRow.next()
        if (currentCell.getCellTypeEnum() === CellType.STRING) {
          print(currentCell.getStringCellValue() + " | ")
        } else {
          print(currentCell.getNumericCellValue().toString())
        }
      }
      println()
    }

    workbook.close()
    file.close()
  }

  @Test
  fun testExcelIndex() {
    val listBill: ArrayList<BillModel> = ArrayList<BillModel>()
    val file = FileInputStream("/Users/xudluo/work/Mar_Tatum-USA-US-Residents_ACH_2019_June_Full_Details.xlsx")
    val workbook = XSSFWorkbook(file)
    val sheet = workbook.getSheet("Sheet1")
    val rowTotalNum = sheet.physicalNumberOfRows
    if (rowTotalNum < 2) return
    else {
      for (rowNum in 1 until rowTotalNum) {
        println("rowNum : " + rowNum)
        val oneBIllModel = ExcelUtil.rowToBIllModel(sheet, rowNum, Consts.BILL_COLUM_MAX)
        listBill.add(oneBIllModel)
      }
    }
    listBill.forEach { println(it.date_received + "\t" + it.amount + "\t" + it.single_payment_sent_as) }
    workbook.close()
    file.close()
  }

  @Test
  fun testExcelCell() {
    val file = FileInputStream("/Users/xudluo/work/Book2.xlsx")
    val workbook = XSSFWorkbook(file)
    val sheet = workbook.getSheet("Sheet1")
    var row = sheet.getRow(0)
    var cell = row.getCell(1)
    println("cell : " + cell.toString())
    var row12 = sheet.getRow(1)
    var cell12 = row12.getCell(12)
    if (null == cell12) {
      println("cell12 : null")
    } else println("cell12 : " + cell12.toString())
  }

  @Test
  fun testExcelInputStream() {
    val file = FileInputStream("/Users/xudluo/work/Book3.xlsx")
    do {
      val a = file.read()
      print(a)
      println("\t" + a.toChar())
    } while (a != -1)

  }
}
