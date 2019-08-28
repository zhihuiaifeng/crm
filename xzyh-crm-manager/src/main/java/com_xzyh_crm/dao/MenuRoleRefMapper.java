package com_xzyh_crm.dao;

import com_xzyh_crm.pojo.MenuRoleRef;

public interface MenuRoleRefMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MenuRoleRef record);

    int insertSelective(MenuRoleRef record);

    MenuRoleRef selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MenuRoleRef record);

    int updateByPrimaryKey(MenuRoleRef record);
}