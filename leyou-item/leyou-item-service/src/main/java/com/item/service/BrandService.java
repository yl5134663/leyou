package com.item.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.item.mapper.BrandMapper;
import com.pojo.Brand;
import com.pojo.Category;
import com.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    BrandMapper brandMapper;
    /**
     *
     * @param key 搜索关键字
     * @param page 当前页默认1
     * @param rows 每页的个数
     * @param sortBy 排序字段
     * @param desc 是否降序
     * @return
     */
    public PageResult<Brand> queryBrandByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        //初始化Example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        //根据name模糊查询或根据首字母查询
        if (!StringUtils.isEmpty(key)) {
            criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }
        //添加分页条件
        PageHelper.startPage(page, rows);
        if (!StringUtils.isEmpty(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }
        List<Brand> brandsList = brandMapper.selectByExample(example);
        PageInfo<Brand> brandPageInfo = new PageInfo<>(brandsList);
        return new PageResult<>(brandPageInfo.getTotal(), brandPageInfo.getList());
    }

    /**
     * 新增商品
     * @param brand
     * @param cids
     */
    @Transactional
    public void addBrand(Brand brand, List<Integer> cids) {
        brandMapper.insertSelective(brand);
        cids.forEach(cid->{
            brandMapper.insertCategoryAndBrand(cid,brand.getId());
        });
    }

    /**
     * 修改品牌
     */
    public void updateBrand(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }

    /**
     * 删除品牌
     * @param bid
     */
    public void deleteByPid(Long bid) {
        brandMapper.deleteByPrimaryKey(bid);
    }

    /**
     * 根据分类id查询品牌
     * @param cid
     * @return
     */
    public List<Brand> queryCategoryByCid(Long cid) {
       List<Brand>brands= brandMapper.queryCategoryByCid(cid);
       return brands;
    }
}
