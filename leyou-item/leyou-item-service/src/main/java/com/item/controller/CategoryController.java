package com.item.controller;

import com.item.service.CategoryService;
import com.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
