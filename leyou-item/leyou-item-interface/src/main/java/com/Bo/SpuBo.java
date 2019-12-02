package com.bo;

import com.pojo.Sku;
import com.pojo.Spu;
import com.pojo.SpuDetail;

import java.util.List;

public class SpuBo extends Spu {
    private String cname;//商品分类名称
    private String bname;//品牌名称
    private SpuDetail spuDetail;//商品详情
    private List<Sku> skus;//sku列表

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skus = skuList;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }
}
