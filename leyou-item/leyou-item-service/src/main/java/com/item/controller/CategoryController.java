package com.item.controller;

import com.item.service.CategoryService;
import com.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 通id查询商品分类信息
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>>queryCategoryByPid(@RequestParam(value = "pid")Long pid){
        if(pid==null||pid.longValue()<0){
            return ResponseEntity.badRequest().build();
        }
        List<Category> categoryList = categoryService.queryCategoriesByPid(pid);
        if(CollectionUtils.isEmpty(categoryList)){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(categoryList);
    }
    /**
     * 通过品牌id查询商品分类
     */
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>>findCategoryByBrandId(@PathVariable("bid")Long bid){
        List<Category>categories=categoryService.findCategoryByBrandId(bid);
        if(CollectionUtils.isEmpty(categories)){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(categories);
    }

    /**
     * 通过id列表查询分类名称
     * @param ids
     * @return
     */
    @GetMapping("names")
    public ResponseEntity<List<String>> queryNamesByIds(@RequestParam("ids")List<Long> ids){

        List<String> names = this.categoryService.selectNameByIds(ids);
        if (CollectionUtils.isEmpty(names)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(names);
    }
    /**
     * 根据3级分类id，查询1~3级的分类
     * @param id
     * @return
     */
    @GetMapping("all/level")
    public ResponseEntity<List<Category>> queryAllByCid3(@RequestParam("id") Long id){
        List<Category> list = this.categoryService.queryAllByCid3(id);
        if (list == null || list.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }
}
