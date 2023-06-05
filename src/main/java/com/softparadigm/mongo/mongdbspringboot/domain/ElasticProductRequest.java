package com.softparadigm.mongo.mongdbspringboot.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ElasticProductRequest {
    private String id;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private Integer quantity;
    private String sportsCategory;
    private String manufacturer;
}
