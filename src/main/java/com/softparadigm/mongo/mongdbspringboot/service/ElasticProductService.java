package com.softparadigm.mongo.mongdbspringboot.service;

import com.softparadigm.mongo.mongdbspringboot.domain.ElasticProduct;
import com.softparadigm.mongo.mongdbspringboot.domain.ElasticProductRequest;
import org.elasticsearch.action.search.SearchResponse;

import java.io.IOException;
import java.util.List;

public interface ElasticProductService {
    String createProductIndex(ElasticProductRequest elasticProductRequest);

    List<ElasticProduct> findProductByManufacturer(String manufacturer);

    SearchResponse getProductAggregations(String searchQuery, Integer minPrice, Integer maxPrice) throws IOException;
}
