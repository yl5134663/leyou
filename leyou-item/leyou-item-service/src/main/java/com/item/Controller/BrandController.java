package com.item.Controller;

import com.item.service.BrandService;
import com.pojo.Brand;
import com.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     *
     * @param key 搜索关键字
     * @param page 当前页默认1
     * @param rows 每页的个数
     * @param sortBy 排序字段
     * @param desc 是否降序
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>>queryBrandByPage(
            @RequestParam(value = "key",required = false)String key,
            @RequestParam(value="page",defaultValue ="1")Integer page,
            @RequestParam(value="rows",defaultValue = "5")Integer rows,
            @RequestParam(value="sortBy",required = false)String sortBy,
            @RequestParam(value = "desc",required = false)Boolean desc
    ){
      PageResult<Brand>brandPageResult= brandService.queryBrandByPage(key,page,rows,sortBy,desc);
      if(CollectionUtils.isEmpty(brandPageResult.getItems())){
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(brandPageResult);
    }
}
