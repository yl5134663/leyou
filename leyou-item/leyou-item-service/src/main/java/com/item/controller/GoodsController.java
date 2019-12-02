package com.item.controller;

import com.bo.SpuBo;
import com.item.service.GoodsService;
import com.pojo.PageResult;
import com.pojo.Sku;
import com.pojo.Spu;
import com.pojo.SpuDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 查询商品信息
     *
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    ) {
        PageResult<SpuBo> result = goodsService.querySpuByPage(key, saleable, page, rows);
        if (CollectionUtils.isEmpty(result.getItems())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 添加商品
     * @param spuBo
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void>insertGoods(@RequestBody SpuBo spuBo){
        goodsService.insertGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /**
     * 查询spuDetail(修改商品信息回显oldVal)
     */
    @GetMapping("spu/detail/{spuId}")
    public ResponseEntity<SpuDetail>querySpuDetail(@PathVariable("spuId")Long spuId){
      SpuDetail spuDetail=goodsService.querySpuDetail(spuId);
      if(StringUtils.isEmpty(spuDetail)){
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
      return ResponseEntity.ok(spuDetail);
    }

    /**
     * 查询sku(修改商品信息回显oldVal)
     * @param spuId
     * @return
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>>querySkuBySpuId(@RequestParam("id")Long spuId){
        List<Sku>skus=goodsService.querySku(spuId);
        if(CollectionUtils.isEmpty(skus)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(skus);
    }
    /**
     * 更新商品
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo){
        this.goodsService.updateGoods(spuBo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("spu/{id}")
    public ResponseEntity<Spu> querySpuById(@PathVariable("id") Long id){
        Spu spu = this.goodsService.querySpuById(id);
        if(spu == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(spu);
    }
    @GetMapping("sku/{id}")
    public ResponseEntity<Sku> querySkuById(@PathVariable("id")Long id){
        Sku sku = this.goodsService.querySkuById(id);
        if (sku == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sku);
    }
}
