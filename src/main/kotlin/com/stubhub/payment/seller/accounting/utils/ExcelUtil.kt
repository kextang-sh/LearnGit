package com.stubhub.payment.seller.accounting.utils

import com.stubhub.payment.seller.accounting.bean.BillModel
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFSheet

/**
 * @Author xudluo
 * @Date 2020/5/15
 * @Email xudluo@stubhub.com
 * @Desc execl related util
 */
class ExcelUtil {
  companion object {
    /**
     * @Author xudluo
     * @Desc parse one row to BIllModel
     * @Date 5:16 PM 2020/5/16
     * @Param [sheet, rowNUm, maxColum]
     * @Return BillModel
     */
    fun rowToBIllModel(sheet: XSSFSheet, rowNum: Int, maxColum: Int): BillModel {
      val oneRow = sheet.getRow(rowNum)
      val listCell = ArrayList<String?>()
      for (colNum in 0 until maxColum) {
        val oneCell = oneRow.getCell(colNum)
        if (null == oneCell) listCell.add(null)
        else {
          if (oneCell.getCellTypeEnum() === CellType.STRING) {
            if ("NULL".equals(oneCell.stringCellValue)) {
              listCell.add(null)
            } else listCell.add(oneCell.stringCellValue)
          } else {
            listCell.add(oneCell.numericCellValue.toString())
//            println("style : " + oneCell.cellStyle.dataFormatString)
          }

        }
      }
      return BillModel(listCell)
    }
  }
}
