package com.api;

import com.bo.SpuBo;
import com.pojo.PageResult;
import com.pojo.Sku;
import com.pojo.Spu;
import com.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface GoodsApi {
    /**
     * 根据spu的id查询spu
     * @param id
     * @return
     */
    @GetMapping("spu/{id}")
    public Spu querySpuById(@PathVariable("id") Long id);
    /**
     * 分页查询商品结果集
     */
    @GetMapping("spu/page")
    public PageResult<SpuBo> querySpuByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    );

    /**
     * 新增商品
     *
     * @param spuBo
     * @return
     */
    @PostMapping("goods")
    public Void insertGoods(@RequestBody SpuBo spuBo);

    /**
     * 查询spuDetail(修改商品信息回显oldVal)
     */
    @GetMapping("spu/detail/{spuId}")
    public SpuDetail querySpuDetail(@PathVariable("spuId") Long spuId);

    /**
     * 查询sku(修改商品信息回显oldVal)
     *
     * @param spuId
     * @return
     */
    @GetMapping("sku/list")
    public List<Sku> querySkuBySpuId(@RequestParam("id") Long spuId);
    /**
     * 更新商品
     */
    @PutMapping("goods")
    public Void updateGoods(@RequestBody SpuBo spuBo);
    @GetMapping("sku/{id}")
    public Sku querySkuById(@PathVariable("id")Long id);
}
