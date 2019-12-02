package com.service;

import com.client.BrandClient;
import com.client.CategoryClient;
import com.client.GoodsClient;
import com.client.SpecificationClient;
import com.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodsService {
    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;

    public Map<String, Object> loadData(Long spuId) {

        HashMap<String, Object> map = new HashMap<>();

        //查询spu
        Spu spu = this.goodsClient.querySpuById(spuId);
        //查询spuDetail
        SpuDetail spuDetail = this.goodsClient.querySpuDetail(spuId);
        //查询sku
        List<Sku> skus = this.goodsClient.querySkuBySpuId(spuId);
        //查询categories封装一个map中  因为我们需要id和categoryName
        List<Map<String, Object>> categories = new ArrayList<>();
        List<Long> cids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
        List<String> names = this.categoryClient.queryNameByIds(cids);
        for (int i = 0; i < cids.size(); i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", cids.get(i));
            hashMap.put("name", names.get(i));
            categories.add(hashMap);
        }
        //查询brand
        Brand brand = this.brandClient.queryBrandById(spu.getBrandId());
        //查询规格参数组
        List<SpecGroup> groups = this.specificationClient.queryGroupByCid(spu.getCid3());

        //查询规格参数
        List<SpecParam> params = this.specificationClient.querySpecParams(null, spu.getCid3(), null, null);
        /*groups.forEach(it -> {
            System.out.println(it);
            List<SpecParam> paramList = specificationClient.querySpecParams(it.getId(), null, null, null);
            it.setParams(paramList);
                }
        );*/
        Map<Long, String> paramMap = new HashMap<>();
        params.forEach(param -> {
            paramMap.put(param.getId(), param.getName());
        });

        // 封装spu
        map.put("spu", spu);
        // 封装spuDetail
        map.put("spuDetail", spuDetail);
        // 封装sku集合
        map.put("skus", skus);
        // 分类
        map.put("categories", categories);
        // 品牌
        map.put("brand", brand);
        // 规格参数组
        map.put("groups", groups);
        // 查询特殊规格参数
        map.put("paramMap", paramMap);

        return map;
    }
}
