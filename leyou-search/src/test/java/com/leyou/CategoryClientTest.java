package com.leyou;

import com.LeyouSearchService;
import com.bo.SpuBo;
import com.pojo.PageResult;
import com.pojo.Sku;
import com.search.client.CategoryClient;
import com.search.client.GoodsClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeyouSearchService.class)
public class CategoryClientTest {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;
    @Test
    public void testQueryCategories() {
        List<String> names = this.categoryClient.queryNameByIds(Arrays.asList(1L, 2L, 3L));
        names.forEach(System.out::println);
    }
    @Test
    public void testQuerySku(){
        List<Sku> skus = goodsClient.querySkuBySpuId(1L);
        System.out.println(skus);
    }
    @Test
    public void testQueryGoodsByPage(){
        PageResult<SpuBo> spuBoPageResult = goodsClient.querySpuByPage(null, true, 1, 5);
        System.out.println(spuBoPageResult);
    }
}
