package com_xzyh_crm.dao;

import com_xzyh_crm.pojo.DepartmentRole;

public interface DepartmentRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DepartmentRole record);

    int insertSelective(DepartmentRole record);

    DepartmentRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DepartmentRole record);

    int updateByPrimaryKey(DepartmentRole record);
}