package com.babadroga.PaymentService.service;

import com.babadroga.PaymentService.dao.TransactionalDetailsDao;
import com.babadroga.PaymentService.entity.TransactionDetails;
import com.babadroga.PaymentService.exception.PaymentServiceCustomException;
import com.babadroga.PaymentService.model.PaymentMode;
import com.babadroga.PaymentService.model.PaymentRequest;
import com.babadroga.PaymentService.model.PaymentResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private TransactionalDetailsDao transactionalDetailsDao;

    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording Payment Details: {}", paymentRequest);

        TransactionDetails transactionDetails =
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

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(long orderId) {
        log.info("Getting payment details for the Order Id: {}", orderId);
        Optional<TransactionDetails> transactionDetails = transactionalDetailsDao.findByOrderId(orderId);
        //TODO: Check whether i need optional in this case
        if (transactionDetails.isPresent()) {
            PaymentResponse paymentResponse =
                    PaymentResponse.builder()
                            .paymentId(transactionDetails.get().getId())
                            .paymentMode(PaymentMode.valueOf(transactionDetails.get().getPaymentMode()))
                            .paymentDate(transactionDetails.get().getPaymentDate())
                            .orderId(transactionDetails.get().getOrderId())
                            .status(transactionDetails.get().getPaymentStatus())
                            .amount(transactionDetails.get().getAmount())
                            .build();
            return paymentResponse;
        } else {
            throw new PaymentServiceCustomException("Transaction with given Id not found", "TRANSACTION_NOT_FOUND");
        }
    }
}

