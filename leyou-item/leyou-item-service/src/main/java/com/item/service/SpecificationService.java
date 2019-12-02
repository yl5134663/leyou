package com.item.service;

import com.item.mapper.SpecGroupMapper;
import com.item.mapper.SpecParamMapper;
import com.pojo.SpecGroup;
import com.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationService {
    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 通过分类信息查询规格参数分组
     *
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroupByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> specGroups = specGroupMapper.select(specGroup);
        return specGroups;
    }

    /**
     * 通过规格参数分组id查询规格参数
     *
     * @param gid
     * @return
     */
    public List<SpecParam> queryParamsByGid(Long gid) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        List<SpecParam> specParams = specParamMapper.select(specParam);
        return specParams;
    }

    /**
     * 修改规格参数分组
     * @param specGroup
     */
    public void updateGroup(SpecGroup specGroup) {
    specGroupMapper.updateByPrimaryKey(specGroup);
    }

    /**
     *删除规格参数分组
     * @param id
     */
    public void deleteGroup(Long id) {
    specGroupMapper.deleteByPrimaryKey(id);
    }

    /**
     * 添加规格参数分组
     * @param specGroup
     */
    public void insertGroup(SpecGroup specGroup) {
    specGroupMapper.insert(specGroup);
    }

    /**
     * 查询规格参数
     * @param gid
     * @param cid
     * @param generic
     * @param searching
     * @return
     */
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam specParam=new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        specParam.setGeneric(generic);
        //如果param中有属性为null，则不会把属性作为查询条件，因此该方法具备通用性，即可根据gid查询，也可根据cid查询。
        List<SpecParam> specParams = specParamMapper.select(specParam);
        return specParams;
    }

    /**
     * 商品详情页需要的数据
     * @param cid
     * @return
     */
    public List<SpecGroup> querySpecsByCid(Long cid) {
        // 查询规格组
        List<SpecGroup> groups = this.queryGroupByCid(cid);
        groups.forEach(g -> {
            // 查询组内参数
            g.setParams(this.queryParams(g.getId(), null, null, null));
        });
        return groups;
    }
}
