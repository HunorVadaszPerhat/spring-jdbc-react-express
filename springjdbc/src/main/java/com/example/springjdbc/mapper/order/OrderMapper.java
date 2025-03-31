package com.example.springjdbc.mapper.order;

import com.example.springjdbc.dto.order.OrderDTO;
import com.example.springjdbc.model.order.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderDTO toDTO(Order o) {
        return new OrderDTO(
                o.getOrderNumber(),
                o.getOrderDate(),
                o.getRequiredDate(),
                o.getShippedDate(),
                o.getStatus(),
                o.getComments(),
                o.getCustomerNumber()
        );
    }

    public Order toEntity(OrderDTO dto) {
        return new Order(
                dto.getOrderNumber(),
                dto.getOrderDate(),
                dto.getRequiredDate(),
                dto.getShippedDate(),
                dto.getStatus(),
                dto.getComments(),
                dto.getCustomerNumber()
        );
    }
}
