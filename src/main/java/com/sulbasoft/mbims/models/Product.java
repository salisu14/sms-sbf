package com.sulbasoft.mbims.models;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private String name;
    private BigDecimal price;
}