package com.Bo;

import com.pojo.Spu;

public class SpuBo extends Spu {
    private String cname;//商品分类名称
    private String bname;//品牌名称

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
