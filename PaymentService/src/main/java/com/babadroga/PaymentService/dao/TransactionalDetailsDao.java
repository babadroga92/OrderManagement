package com.babadroga.PaymentService.dao;

import com.babadroga.PaymentService.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionalDetailsDao extends JpaRepository<TransactionDetails, Long> {
}
