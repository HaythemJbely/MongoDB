package com.softparadigm.mongo.mongdbspringboot.repository;

import com.softparadigm.mongo.mongdbspringboot.domain.ElasticProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticProductRepository extends ElasticsearchRepository<ElasticProduct,String> {
}
