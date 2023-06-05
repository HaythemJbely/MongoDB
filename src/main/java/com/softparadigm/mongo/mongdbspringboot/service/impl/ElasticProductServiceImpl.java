package com.softparadigm.mongo.mongdbspringboot.service.impl;

import com.softparadigm.mongo.mongdbspringboot.domain.ElasticProduct;
import com.softparadigm.mongo.mongdbspringboot.domain.ElasticProductRequest;
import com.softparadigm.mongo.mongdbspringboot.service.ElasticProductService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ElasticProductServiceImpl implements ElasticProductService {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;
    @Override
    public String createProductIndex(ElasticProductRequest elasticProductRequest) {
        ModelMapper modelMapper = new ModelMapper();
        ElasticProduct product = modelMapper.map(elasticProductRequest, ElasticProduct.class);

        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(product.getId())
                .withObject(product).build();

        String documentId = elasticsearchOperations.index(indexQuery, IndexCoordinates.of("elasticproductindex"));
        return documentId;
    }

    @Override
    public List<ElasticProduct> findProductByManufacturer(String manufacturer) {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("manufacturer",manufacturer);

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();

        SearchHits<ElasticProduct> productHits = elasticsearchOperations.search(searchQuery,
                ElasticProduct.class,
                IndexCoordinates.of("elasticproductindex"));

        List<ElasticProduct> productList = new ArrayList<ElasticProduct>();
        productHits.forEach( productHit -> {
            productList.add(productHit.getContent());
        });
        return productList;    }
}
