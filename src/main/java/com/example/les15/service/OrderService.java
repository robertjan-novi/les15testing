package com.example.les15.service;

import com.example.les15.dto.OrderDto;
import com.example.les15.exception.ResourceNotFoundException;
import com.example.les15.model.Order;
import com.example.les15.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepos;

    public OrderService(OrderRepository repos) {
        this.orderRepos = repos;
    }
    public int createOrder(OrderDto newOrderDto) {
        Order o = new Order(newOrderDto.productname, newOrderDto.unitprice, newOrderDto.quantity);

        orderRepos.save(o);

        return o.getOrderid();
    }

    public List<OrderDto> getOrders() {
        return orderRepos.findAll()
                .stream()
                .map(OrderService::transferToDto)
                .toList();
    }

    public OrderDto getOrder(int orderid) {
        Order o = orderRepos.findById(orderid).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return transferToDto(o);
    }

    public double getAmount(int orderid) {
        Order o = orderRepos.findById(orderid).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return o.calculateAmount();
    }

    private static OrderDto transferToDto(Order o) {
        OrderDto odto = new OrderDto();
        odto.productname = o.getProductname();
        odto.unitprice = o.getUnitprice();
        odto.quantity = o.getQuantity();
        return odto;
    }
}
