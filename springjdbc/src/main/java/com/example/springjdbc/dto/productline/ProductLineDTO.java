package com.example.springjdbc.dto.productline;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductLineDTO {

    @NotBlank(message = "Product line is required")
    private String productLine;

    private String textDescription;

    private String htmlDescription;

    private byte[] image;
}
