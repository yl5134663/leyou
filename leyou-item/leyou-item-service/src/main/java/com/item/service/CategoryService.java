package com.item.service;

import com.item.mapper.CategoryMapper;
import com.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    public List<Category>queryCategoriesByPid(Long id){
        Category category=new Category();
        category.setParentId(id);
        List<Category> categoryList = categoryMapper.select(category);
        return categoryList;
    }
}
