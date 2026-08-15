package com.userexprior.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.userexprior.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ElasticSearch {

    @Autowired
    private ElasticsearchClient client;

    public List<Product> getProductsByBrand(String brandName) throws IOException {
        SearchResponse<Product> response = client.search(s -> s
                        .index("products")
                        .query(q -> q.match(m -> m.field("name").query(brandName))),
                Product.class
        );

        List<Product> products = new ArrayList<>();
        for (Hit<Product> hit : response.hits().hits()) {
            products.add(hit.source());
        }
        return products;
    }
}
