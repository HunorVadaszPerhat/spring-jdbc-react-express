package com.example.springjdbc.service.orderdetail;

import java.io.Serializable;
import java.util.Objects;

public class OrderDetailId implements Serializable {
    private int orderNumber;
    private String productCode;

    public OrderDetailId() {}

    public OrderDetailId(int orderNumber, String productCode) {
        this.orderNumber = orderNumber;
        this.productCode = productCode;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getProductCode() {
        return productCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetailId that)) return false;
        return orderNumber == that.orderNumber && Objects.equals(productCode, that.productCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, productCode);
    }

    @Override
    public String toString() {
        return "OrderDetailId{" +
                "orderNumber=" + orderNumber +
                ", productCode='" + productCode + '\'' +
                '}';
    }
}
