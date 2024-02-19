package com.babadroga.PaymentService.service;

import com.babadroga.PaymentService.dao.TransactionalDetailsDao;
import com.babadroga.PaymentService.entity.TransactionDetails;
import com.babadroga.PaymentService.model.PaymentRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    private TransactionalDetailsDao transactionalDetailsDao;
    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording Payment Details: {}", paymentRequest);

        TransactionDetails transactionDetails=
                TransactionDetails.builder()
                        .paymentDate(Instant.now())
                        .paymentMode(paymentRequest.getPaymentMode().name())
                        .paymentStatus("SUCCESS")
                        .orderId(paymentRequest.getOrderId())
                        .referenceNumber(paymentRequest.getReferenceNumber())
                        .amount(paymentRequest.getAmount())
                        .build();

        transactionalDetailsDao.save(transactionDetails);

        log.info("Transaction Completed with Id: {}", transactionDetails.getId());
        return transactionDetails.getId();
    }
}
