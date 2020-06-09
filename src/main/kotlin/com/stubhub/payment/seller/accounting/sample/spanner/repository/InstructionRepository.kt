package com.stubhub.payment.seller.accounting.sample.spanner.repository

import com.stubhub.payment.seller.accounting.sample.spanner.vo.PaymentInstruction
import org.springframework.cloud.gcp.data.spanner.repository.SpannerRepository
import org.springframework.stereotype.Repository

/* 
 * Author: zzhao3@stuhhub.com
 * Date: 2020/5/6 18:14
 * Desc: 
 */
@Repository
interface InstructionRepository : SpannerRepository<PaymentInstruction, Long>
