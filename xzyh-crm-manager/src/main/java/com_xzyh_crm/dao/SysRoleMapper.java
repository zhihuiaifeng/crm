package com_xzyh_crm.dao;

import com_xzyh_crm.pojo.SysRole;
import com_xzyh_crm.vo.SysRoleVo;

import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    /*添加用户名字的时候，先查一下是否存在*/
    SysRole findByName(String name);

/* 这个是添加角色时，如果是员工，判断一下领导是否存在*/
    SysRole selectByPid(Long id);
    /*这个是获取全部信息*/
    List<SysRole> selectAll(SysRoleVo record);

}