package com_xzyh_crm.dao;

import com_xzyh_crm.pojo.MenuBtn;

public interface MenuBtnMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MenuBtn record);

    int insertSelective(MenuBtn record);

    MenuBtn selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MenuBtn record);

    int updateByPrimaryKey(MenuBtn record);
}