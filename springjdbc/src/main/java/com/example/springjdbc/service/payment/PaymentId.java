package com.example.springjdbc.service.payment;

import java.io.Serializable;
import java.util.Objects;

public class PaymentId implements Serializable {
    private int customerNumber;
    private String checkNumber;

    public PaymentId() {}

    public PaymentId(int customerNumber, String checkNumber) {
        this.customerNumber = customerNumber;
        this.checkNumber = checkNumber;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentId that)) return false;
        return customerNumber == that.customerNumber &&
                Objects.equals(checkNumber, that.checkNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerNumber, checkNumber);
    }

    @Override
    public String toString() {
        return "PaymentId{" +
                "customerNumber=" + customerNumber +
                ", checkNumber='" + checkNumber + '\'' +
                '}';
    }
}

