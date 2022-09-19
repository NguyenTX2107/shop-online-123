package com.assignment.shoponline.restapi;

import com.assignment.shoponline.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
public class OrderRestController {
    final OrderService orderService;
}
