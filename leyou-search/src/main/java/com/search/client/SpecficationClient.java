package com.search.client;

import com.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface SpecficationClient extends SpecificationApi {
}
