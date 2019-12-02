package com.client;

import com.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value= "item-service")
public interface SpecificationClient extends SpecificationApi {
}
