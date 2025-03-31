package com.example.springjdbc.mapper.orderdetail;

import com.example.springjdbc.dto.orderdetail.OrderDetailDTO;
import com.example.springjdbc.model.orderdetail.OrderDetail;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailMapper {
    public OrderDetailDTO toDTO(OrderDetail od) {
        return new OrderDetailDTO(
                od.getOrderNumber(),
                od.getProductCode(),
                od.getQuantityOrdered(),
                od.getPriceEach(),
                od.getOrderLineNumber()
        );
    }

    public OrderDetail toEntity(OrderDetailDTO dto) {
        return new OrderDetail(
                dto.getOrderNumber(),
                dto.getProductCode(),
                dto.getQuantityOrdered(),
                dto.getPriceEach(),
                dto.getOrderLineNumber()
        );
    }
}
