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
}
