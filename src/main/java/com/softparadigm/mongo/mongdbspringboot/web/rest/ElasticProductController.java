package com.softparadigm.mongo.mongdbspringboot.web.rest;

import com.softparadigm.mongo.mongdbspringboot.domain.ElasticProduct;
import com.softparadigm.mongo.mongdbspringboot.domain.ElasticProductRequest;
import com.softparadigm.mongo.mongdbspringboot.service.ElasticProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/elasticProduct")
public class ElasticProductController {

    @Autowired
    private ElasticProductService elasticProductService;
    @PostMapping("/add")
    public String createProduct(@RequestBody ElasticProductRequest elasticProductRequest) {
        return elasticProductService.createProductIndex(elasticProductRequest);
    }

    @GetMapping("/manufacturer/{manufacturer}")
    public List<ElasticProduct> findProductByBrand(@PathVariable("manufacturer") String manufacturer) {
        return elasticProductService.findProductByManufacturer(manufacturer);
    }
}
