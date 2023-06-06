package com.softparadigm.mongo.mongdbspringboot.service.impl;

import com.softparadigm.mongo.mongdbspringboot.domain.ElasticProduct;
import com.softparadigm.mongo.mongdbspringboot.domain.ElasticProductRequest;
import com.softparadigm.mongo.mongdbspringboot.service.ElasticProductService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ElasticProductServiceImpl implements ElasticProductService {
    @Autowired
    private  RestHighLevelClient elasticClient;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    /**
     * Creates a product index in Elasticsearch based on the provided ElasticProductRequest.
     * @param elasticProductRequest The ElasticProductRequest containing the product information.
     * @return The document ID of the created index in Elasticsearch.
     */
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

    /**
     * Finds products in Elasticsearch by the specified manufacturer.
     * @param manufacturer The manufacturer name to search for.
     * @return A list of ElasticProduct objects matching the specified manufacturer.
     */
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
        return productList;
    }

    /**
     * Retrieves product aggregations from Elasticsearch based on the provided search query, minimum price, and maximum price.
     * @param searchQuery The search query string.
     * @param minPrice The minimum price value for filtering.
     * @param maxPrice The maximum price value for filtering.
     * @return A SearchResponse containing the aggregated results.
     * @throws IOException If an error occurs while performing the Elasticsearch search.
     */
    @Override
    public SearchResponse getProductAggregations(String searchQuery, Integer minPrice, Integer maxPrice) throws IOException {
        SearchRequest searchRequest = new SearchRequest("elasticproductindex");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // Set the search query if provided
        if (searchQuery != null && !searchQuery.isEmpty()) {
            searchSourceBuilder.query(QueryBuilders.matchQuery("productName", searchQuery).fuzziness(Fuzziness.AUTO));
        }

        // Set the price range filter if provided
        if (minPrice != null || maxPrice != null) {
            searchSourceBuilder.query(QueryBuilders.rangeQuery("productPrice")
                    .gte(minPrice != null ? minPrice : 0)
                    .lte(maxPrice != null ? maxPrice : Integer.MAX_VALUE));
        }

        // Add sorting by productPrice in descending order
        searchSourceBuilder.sort(SortBuilders.fieldSort("productPrice").order(SortOrder.DESC));

        // Set the size to control the number of documents returned
        searchSourceBuilder.size(10);

        searchRequest.source(searchSourceBuilder);

        return elasticClient.search(searchRequest, RequestOptions.DEFAULT);
    }
}
