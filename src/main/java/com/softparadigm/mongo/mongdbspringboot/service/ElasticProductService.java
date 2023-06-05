package com.softparadigm.mongo.mongdbspringboot.service;

import com.softparadigm.mongo.mongdbspringboot.domain.ElasticProduct;
import com.softparadigm.mongo.mongdbspringboot.domain.ElasticProductRequest;

import java.util.List;

public interface ElasticProductService {
    String createProductIndex(ElasticProductRequest elasticProductRequest);

    List<ElasticProduct> findProductByManufacturer(String manufacturer);
}
