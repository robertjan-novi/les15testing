package com.example.les15.service;

import com.example.les15.dto.OrderDto;
import com.example.les15.model.Order;
import com.example.les15.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<Order> oo = orderRepos.findById(orderid);
        if (oo.isPresent()) {
            Order o = oo.get();
            return transferToDto(o);
        }
        return null;
    }

    public double getAmount(int orderid) {
        Optional<Order> oo = orderRepos.findById(orderid);
        if (oo.isPresent()) {
            Order o = oo.get();
            return o.calculateAmount();
        }
        return -1;
    }

    private static OrderDto transferToDto(Order o) {
        OrderDto odto = new OrderDto();
        odto.productname = o.getProductname();
        odto.unitprice = o.getUnitprice();
        odto.quantity = o.getQuantity();
        return odto;
    }
}
