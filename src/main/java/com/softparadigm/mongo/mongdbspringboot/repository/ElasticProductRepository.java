package com.softparadigm.mongo.mongdbspringboot.repository;

import com.softparadigm.mongo.mongdbspringboot.domain.ElasticProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElasticProductRepository extends ElasticsearchRepository<ElasticProduct,String> {
    List<ElasticProduct> findByProductName(String productName);
}
