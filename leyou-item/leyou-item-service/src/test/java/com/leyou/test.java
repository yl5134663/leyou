package com.leyou;

import com.LeyouItemServiceApplication;
import com.api.GoodsApi;
import com.item.controller.GoodsController;
import com.pojo.Sku;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@SpringBootTest(classes = LeyouItemServiceApplication.class)
@RunWith(SpringRunner.class)
public class test {
    @Autowired
    private GoodsController goodsController;
    @Test
    public void testQuerySku(){
       // List<Sku> skus = goodsController.querySkuBySpuId(1L);
        ResponseEntity<List<Sku>> listResponseEntity = goodsController.querySkuBySpuId(10L);
        System.out.println(listResponseEntity);
    }
}
