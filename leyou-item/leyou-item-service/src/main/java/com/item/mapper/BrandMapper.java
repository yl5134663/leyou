package com.item.mapper;

import com.pojo.Brand;
import com.pojo.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {
    @Insert("insert into tb_category_brand(category_id,brand_id)values(#{cid},#{bid})")
    void insertCategoryAndBrand(@Param("cid") Integer cid, @Param("bid") Long bid);

    /**
     * 通过分类id查询品牌信息
     * @param cid
     * @return
     */
    @Select("select * from tb_brand a inner join tb_category_brand b on a.id=b.brand_id where b.category_id=#{cid}")
    List<Brand> queryCategoryByCid(Long cid);
}
