package com.example.springjdbc.mapper.payment;


import com.example.springjdbc.dto.payment.PaymentDTO;
import com.example.springjdbc.model.payment.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public PaymentDTO toDTO(Payment p) {
        return new PaymentDTO(
                p.getCustomerNumber(),
                p.getCheckNumber(),
                p.getPaymentDate(),
                p.getAmount()
        );
    }

    public Payment toEntity(PaymentDTO dto) {
        return new Payment(
                dto.getCustomerNumber(),
                dto.getCheckNumber(),
                dto.getPaymentDate(),
                dto.getAmount()
        );
    }
}
