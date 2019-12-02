package com.search.client;

import com.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "item-service",url = "localhost:8081")
public interface GoodsClient extends GoodsApi {

}
