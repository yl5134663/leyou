package com.item.service;

import com.item.mapper.CategoryMapper;
import com.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
    /**
     * 通过id列表查询分类名称
     * @param ids
     * @return
     */
    public List<String> selectNameByIds(List<Long> ids) {
        List<Category> categories = categoryMapper.selectByIdList(ids);
        List<String> names = new ArrayList<>();
        for (Category category : categories) {
            names.add(category.getName());
        }
        return names;
    }

    public List<Category> queryAllByCid3(Long id) {
        Category c3 = this.categoryMapper.selectByPrimaryKey(id);
        Category c2 = this.categoryMapper.selectByPrimaryKey(c3.getParentId());
        Category c1 = this.categoryMapper.selectByPrimaryKey(c2.getParentId());
        return Arrays.asList(c1,c2,c3);
    }
}
