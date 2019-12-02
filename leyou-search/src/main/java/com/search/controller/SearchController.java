package com.search.controller;

import com.search.pojo.SearchRequest;
import com.search.pojo.SearchResult;
import com.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    /**
     * 商品查询
     * @param request
     * @return
     */
    @PostMapping("page")
    public ResponseEntity<SearchResult> search(@RequestBody SearchRequest request) {
        SearchResult goodsResult = searchService.search(request);
        if (goodsResult == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(goodsResult);
    }
}
