package com.example.les15.controller;

import com.example.les15.dto.InvoiceDto;
import com.example.les15.dto.OrderDto;
import com.example.les15.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> retrieveOrders() {
        return ResponseEntity.ok(service.getOrders());
    }

    @PostMapping
    public ResponseEntity<Integer> createOrder(@RequestBody OrderDto newOrderDto) {
        int orderid = service.createOrder(newOrderDto);
        URI uri = URI.create(
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/" + orderid).toUriString());

        return ResponseEntity.created(uri).body(orderid);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> retrieveOrder(@PathVariable int id) {
        OrderDto odto = service.getOrder(id);
        return ResponseEntity.ok(odto);
    }

    @GetMapping("/{id}/invoice")
    public ResponseEntity<InvoiceDto> getAmount(@PathVariable int id) {
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.orderid = id;
        invoiceDto.amount = service.getAmount(id);
        return ResponseEntity.ok(invoiceDto);
    }
}