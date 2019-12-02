package com.item.controller;

import com.item.service.BrandService;
import com.pojo.Brand;
import com.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * @param key    搜索关键字
     * @param page   当前页默认1
     * @param rows   每页的个数
     * @param sortBy 排序字段
     * @param desc   是否降序
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", required = false) Boolean desc
    ) {
        PageResult<Brand> brandPageResult = brandService.queryBrandByPage(key, page, rows, sortBy, desc);
        if (CollectionUtils.isEmpty(brandPageResult.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brandPageResult);
    }

    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id") Long id) {
        Brand brand = brandService.queryBrandById(id);
        if (brand == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brand);
    }

    /**
     * 新增品牌
     *
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addBrand(Brand brand, @RequestParam("cids") List<Integer> cids) {
        brandService.addBrand(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改品牌信息
     */
    @PutMapping()
    public ResponseEntity<Void> updateBrand(Brand brand) {
        brandService.updateBrand(brand);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除
     */
    @DeleteMapping({"bid"})
    public ResponseEntity<Void> deleteByBid(@PathVariable("bid") Long bid) {
        brandService.deleteByPid(bid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据分类id查询品牌列表
     */
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryCategoryByCid(@PathVariable("cid") Long cid) {
        List<Brand> brands = brandService.queryCategoryByCid(cid);
        if (CollectionUtils.isEmpty(brands)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(brands);
    }
}
