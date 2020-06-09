package com.stubhub.payment.seller.accounting.nocheck.service

import com.stubhub.payment.seller.accounting.bean.BillModel
import com.stubhub.payment.seller.accounting.bean.ParseRetModel
import com.stubhub.payment.seller.accounting.common.Consts
import com.stubhub.payment.seller.accounting.log
import com.stubhub.payment.seller.accounting.utils.ExcelUtil
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.codec.multipart.Part
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import java.io.InputStream
import java.io.SequenceInputStream

/**
 * @author xudluo
 * @date 2020/5/22
 * @email xudluo@stubhub.com
 * @desc
 */
@Service
class NocheckService {
  /**
   * @Author xudluo
   * @Desc
   * @Date 11:05 AM 2020/5/22
   * @Param
   * @Return
   */
  suspend fun parseModel(multi: MultiValueMap<String, Part>): ParseRetModel? {
    val listBill: MutableList<BillModel> = ArrayList<BillModel>()
    val part = multi.getFirst("file")
    if (part != null) {
      val multipartList: MutableList<DataBuffer>? = part.content().buffer().awaitFirstOrNull()?.toMutableList()
      val ioCount = multipartList?.size
//        println("size2 : " + ioCount)
      val listIO: ArrayList<InputStream> = ArrayList<InputStream>()
      for (ioIndex in 0 until ioCount!!) {
        val oneIO = multipartList.get(ioIndex)?.asInputStream()
        if (oneIO != null) {
          listIO.add(oneIO)
        }
      }
      println("listIO size : " + listIO.size)
      var fileX: InputStream? = null
      for (streamIndex in 0 until listIO.size) {
        if (streamIndex == 0) {
          fileX = listIO[streamIndex]
        } else {
          fileX = SequenceInputStream(fileX, listIO[streamIndex])
        }
      }
      val workbook = XSSFWorkbook(fileX)
      val sheet = workbook.getSheet("Sheet1")
      val rowTotalNum = sheet.physicalNumberOfRows
      if (rowTotalNum < 2 || listIO.size == 0) {
        log.info("excel is empty, fileName : {}", part.name())
        return ParseRetModel(-1, null)
      } else {
        for (rowNum in 1 until rowTotalNum) {
          val oneBIllModel = ExcelUtil.rowToBIllModel(sheet, rowNum, Consts.BILL_COLUM_MAX)
          listBill.add(oneBIllModel)
        }
      }
      listBill.forEach { println(it.date_received + "\t" + it.amount + "\t" + it.single_payment_sent_as) }
      println("listBill size : " + listBill.size)
      workbook.close()
      if (fileX != null) {
        fileX.close()
      }
    } else {
      log.error("excel is null!")
      return ParseRetModel(-1, null)
    }

    return ParseRetModel(1, listBill)
  }
}
