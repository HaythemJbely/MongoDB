package com.softparadigm.mongo.mongdbspringboot.web.rest;

import com.softparadigm.mongo.mongdbspringboot.domain.ElasticProduct;
import com.softparadigm.mongo.mongdbspringboot.repository.ElasticProductRepository;
import org.elasticsearch.action.search.SearchResponse;
import com.softparadigm.mongo.mongdbspringboot.domain.ElasticProductRequest;
import com.softparadigm.mongo.mongdbspringboot.service.ElasticProductService;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

@RestController
@RequestMapping("/elasticProduct")
public class ElasticProductController {

    @Autowired
    private ElasticProductService elasticProductService;

    @Autowired
    private ElasticProductRepository elasticProductRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @PostMapping("/add")
    public String createProduct(@RequestBody ElasticProductRequest elasticProductRequest) {
        return elasticProductService.createProductIndex(elasticProductRequest);
    }

    @GetMapping("/manufacturer/{manufacturer}")
    public List<ElasticProduct> findProductByBrand(@PathVariable("manufacturer") String manufacturer) {
        return elasticProductService.findProductByManufacturer(manufacturer);
    }

    @GetMapping("/aggregations")
    public SearchResponse getProductAggregations(
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice
    ) throws IOException {
        return elasticProductService.getProductAggregations(searchQuery, minPrice, maxPrice);
    }


    @PostMapping
    public ElasticProduct createProduct(@RequestBody ElasticProduct product) {
        return elasticProductRepository.save(product);
    }

    @GetMapping("/{id}")
    public ElasticProduct getProductById(@PathVariable String id) {
        return elasticProductRepository.findById(id).orElse(null);
    }

    @GetMapping("/search")
    public List<ElasticProduct> searchProducts(@RequestParam String query) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryStringQuery(query))
                .build();

        SearchHits<ElasticProduct> searchHits = elasticsearchOperations.search(searchQuery, ElasticProduct.class);
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ElasticProduct updateProduct(@PathVariable String id, @RequestBody ElasticProduct product) {
        product.setId(id);
        return elasticProductRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        elasticProductRepository.deleteById(id);
    }

    @GetMapping("/phraseSearch")
    public List<ElasticProduct> phraseSearchProducts(@RequestParam String query) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchPhraseQuery("productName",query).slop(1))
                .build();

        SearchHits<ElasticProduct> searchHits = elasticsearchOperations.search(searchQuery, ElasticProduct.class);
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    @GetMapping("/fuzzinessSearch")
    public List<ElasticProduct> fuzzinessSearchProducts(@RequestParam String query) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("productName",query).operator(Operator.AND).fuzziness(Fuzziness.ONE).prefixLength(3))
                .build();

        SearchHits<ElasticProduct> searchHits = elasticsearchOperations.search(searchQuery, ElasticProduct.class);
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    @GetMapping("/findBy/{productName}")
    public List<ElasticProduct> findByProductName(@PathVariable("productName") String productName) {
        return elasticProductRepository.findByProductName(productName);
    }
}
