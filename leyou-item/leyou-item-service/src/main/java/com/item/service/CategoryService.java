package com.item.service;

import com.item.mapper.CategoryMapper;
import com.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    /**
     * 通id查询商品分类信息
     *
     * @param pid
     * @return
     */
    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> queryCategoriesByPid(Long id) {
        Category category = new Category();
        category.setParentId(id);
        List<Category> categoryList = categoryMapper.select(category);
        return categoryList;
    }

    /**
     * 通过品牌id查询商品分类
     *
     * @param bid
     * @return
     */
    public List<Category> findCategoryByBrandId(Long bid) {
        List<Category> categories = categoryMapper.findCategoryByBrandId(bid);
        return categories;
    }

    public List<String> selectNameByIds(List<Long> longs) {
        List<Category> categories = categoryMapper.selectByIdList(longs);
        List<String> names = new ArrayList<>();
        for (Category category : categories) {
            names.add(category.getName());
        }
        return names;
    }
}
