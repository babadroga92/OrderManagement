package com.babadroga.OrderService.service;

import com.babadroga.OrderService.dao.OrderDao;
import com.babadroga.OrderService.entity.Order;
import com.babadroga.OrderService.exception.OrderServiceCustomException;
import com.babadroga.OrderService.external.client.PaymentService;
import com.babadroga.OrderService.external.client.ProductService;
import com.babadroga.OrderService.external.client.productDetails.ProductDetails;
import com.babadroga.OrderService.external.client.request.PaymentRequest;
import com.babadroga.OrderService.external.client.response.PaymentResponse;
import com.babadroga.OrderService.model.OrderRequest;
import com.babadroga.OrderService.model.OrderResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    public OrderServiceImpl(OrderDao orderDao, ProductService productService, PaymentService paymentService) {
        this.orderDao = orderDao;
        this.productService = productService;
        this.paymentService = paymentService;
    }

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Placing Order Request: {}", orderRequest);
        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("Creating Order with Status CREATED");

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();
        order = orderDao.save(order);

        log.info("Calling Payment Service to complete the payment");

        PaymentRequest paymentRequest =
                PaymentRequest.builder()
                        .orderId(order.getId())
                        .paymentMode(orderRequest.getPaymentMode())
                        .amount(orderRequest.getTotalAmount())
                        .build();

        String orderStatus = null;
        try{
            paymentService.doPayment(paymentRequest);
            log.info("Payment done successfully. Changing the order status to placed");
            orderStatus = "PLACED";
        } catch (Exception e){
            log.error("Error occured in payment. Changing order status to Payment failed");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderDao.save(order);

        log.info("Order Placed successfully with Order Id: {}", order.getId());
        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("Get Order details for Order Id: {}", orderId);

        Order order = orderDao.findById(orderId).orElseThrow(() -> new OrderServiceCustomException("Order not found", "NOT_FOUND", 404));

        log.info("Getting payment information from the payment Service");
        ResponseEntity<PaymentResponse> paymentResponse = paymentService.getPaymentDetailsByOrderId(orderId);
        ResponseEntity<ProductDetails> productDetails = productService.getProductById(order.getProductId());

        return OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .productDetails(productDetails.getBody())
                .paymentResponse(paymentResponse.getBody())
                .build();
    }

    @Override
    public List<Order> listOfAllOrders() {
        log.info("Fetching all orders");
        return orderDao.findAll();
    }
}
