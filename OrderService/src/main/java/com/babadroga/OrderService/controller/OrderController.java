package com.babadroga.OrderService.controller;

import com.babadroga.OrderService.entity.Order;
import com.babadroga.OrderService.model.OrderRequest;
import com.babadroga.OrderService.model.OrderResponse;
import com.babadroga.OrderService.service.OrderService;

import lombok.extern.log4j.Log4j2;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){
        long orderId = orderService.placeOrder(orderRequest);
        log.info("Order Id: {}", orderId);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId){
        OrderResponse orderResponse = orderService.getOrderDetails(orderId);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);

        //TODO: Orders created before adding Payment and Product Details into an order do not get returned. Instead, the error is thrown
        //TODO: This needs fixing.
    }

    @GetMapping("/list")
    public ResponseEntity<List<Order>> listOfAllOrders(){
        List<Order> listOfAllOrders = orderService.listOfAllOrders();
        return new ResponseEntity<>(listOfAllOrders, HttpStatus.OK);
    }
}
