package com_xzyh_crm.service;

import com.github.pagehelper.PageHelper;
import com_xzyh_crm.dao.SysRoleMapper;
import com_xzyh_crm.exception.CustomException;
import com_xzyh_crm.myEnum.UserEnum;
import com_xzyh_crm.pojo.SysRole;
import com_xzyh_crm.util.*;
import com_xzyh_crm.vo.SysRoleAddVo;
import com_xzyh_crm.vo.SysRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private FormatUtil formatUtil;

    /*添加角色*/
    public Result save(SysRoleAddVo sysRoleVo ) {
        SysRole leaderRole = new SysRole();
        SysRole followRole = new SysRole();
        /*获取到的角色进行进行遍历，拿到谁是主谁是从*/
       for (SysRoleVo roleVo : sysRoleVo.getLeaderListRoleVo()) {
           if (roleVo.getIsLeader()!=null) {
                /*这里存在疑问，前端是否传入pid还是自己生成的*/
               if (roleVo.getIsLeader().equals(UserEnum.IS_LEADER.getCode()) && roleVo.getPid().intValue() == (UserEnum.IS_LEADERPID.getCode())) {
                   BeanUtils.copyProperties(roleVo, leaderRole);
                  /* 存在疑问的leaderRole.setPid(UserEnum.IS_LEADER.getCode().longValue());*/
                   continue;
               } else {
                   BeanUtils.copyProperties(roleVo, followRole);
               }
           }
           else{
               throw new CustomException("参数错误",PublicDictUtil.ERROR_VALUE);
           }
        }
       /*这个判断传入的String字段是否为空*/
        if (!formatUtil.checkStringNull(leaderRole.getRoleName(),leaderRole.getRoleDesc(),followRole.getRoleName(),followRole.getRoleDesc())){
            throw new CustomException("参数错误",PublicDictUtil.ERROR_VALUE);
        }

        /*传完了值，进行查看一下，leader和follow中是否有数据*/
        if (StringUtils.isNull(leaderRole)&&StringUtils.isNull(followRole)){
            throw new CustomException("Leader传入的参数有问题",PublicDictUtil.ERROR_VALUE);
        }
        /*先leader用户名查一下对象*/
        SysRole byName = findByName(leaderRole.getRoleName());
        if (byName!=null){
            throw new CustomException("角色"+leaderRole.getRoleName()+"已经存在",PublicDictUtil.ERROR_VALUE);
        }
        /*创建时间*/
        leaderRole.setCreateTime(new Date());

        if (sysRoleMapper.insert(leaderRole)<0) {
           throw new CustomException(PublicDictUtil.ERROR_VALUE,"Leader角色插入失败");
        }
        /*获取刚才插入的数据*/
        SysRole byName1 = sysRoleMapper.findByName(leaderRole.getRoleName());
        followRole.setPid(byName1.getId());
        followRole.setCreateTime(new Date());
        /*插入从角色*/
        if (sysRoleMapper.insert(followRole)<0) {
            throw new CustomException("Follow角色插入失败",PublicDictUtil.ERROR_VALUE);
        }
        return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "添加成功", null);
    }

    /*根据id进行数据库查询*/
    public Result findById(Long id) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
        if (StringUtils.isNull(sysRole)){
            throw new CustomException("查询失败(Query Failure)",PublicDictUtil.ERROR_VALUE);
        }
        return Result.resultData(PublicDictUtil.SUCCESS_VALUE,"查询成功",sysRole);
    }
    /*通过用户名进行查询*/
    public SysRole findByName(String name) {

        return sysRoleMapper.findByName(name);
    }
    /* 这个是添加角色时，如果是员工，判断一下领导是否存在*/
    public SysRole findByPid(Long id) {
        /*根据传入的id*/
        return sysRoleMapper.selectByPid(id);
    }
    /*获取全部的信息*/
    public Result  findAll(SysRoleVo sysRoleVo) {
        log.info("获取全部的数据信息");
//        这个进行分页查询，传入当前页和显示大小
        if (sysRoleVo.getPageNo()!=null && sysRoleVo.getPageSize()!=null ) {
            PageHelper.startPage(sysRoleVo.getPageNo(), sysRoleVo.getPageSize());
        } else {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "分页未获取到数据，参数有误", null);}
        /*获取全部的角色名*/
        List<SysRole> sysRoles = sysRoleMapper.selectAll(sysRoleVo);
//        PageInfo<UserInfoDto> userInfoDtoPageInfo = new PageInfo<UserInfoDto>(userInfoDtos1);
//        返回数据列表
        if (StringUtils.isEmpty(sysRoles)) {
            throw new CustomException("查询失败(Query Failure)",PublicDictUtil.SUCCESS_VALUE);
        }
        /*这个是返回分页的消息*/
        return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "查询成功", new BasePage<SysRole>(sysRoles));
    }

    /*修改数据*/
    public Result edit(SysRoleVo sysRoleVo){
       /* 通过id进行查询原来的值*/
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(sysRoleVo.getId());
        /*如果传进来的数据和原有数据不同*/
        String roleName = sysRole.getRoleName();
        String roleName1 = sysRoleVo.getRoleName();
        if (sysRoleVo.getRoleName().equals(sysRole.getRoleName()))
        {
            /*如果传入的角色名字，和查询的角色名字相同，没有更新角色名字*/
        }
        else {
            /*通过名字查数据库，看对象是否存在*/
            if(StringUtils.isNotNull(findByName(sysRoleVo.getRoleName())))
            {
                return Result.resultData(PublicDictUtil.ERROR_VALUE, "传入的名字已经存在", null);
            }
        }
        /*int i = sysRoleMapper.updateByPrimaryKeySelective(sysRole);*/
        sysRoleVo.setUpdateTime(new Date());
        BeanUtils.copyProperties(sysRoleVo, sysRole);

        int count = sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        if (count > 0) {
            return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "修改成功");
        } else {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "修改失败");
        }



    }

    public Result delete(Long id) {
        /*当删除的时候先看一下是否是leader是否有子ID，还要看一下中间表是否有关联*/
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
        if (StringUtils.isNull(sysRole)){
            throw new CustomException("Query Failure参数异常",PublicDictUtil.ERROR_VALUE);
        }
        /*创建一个对象为了假删除使用*/
        SysRole sysRole1 = new SysRole();
        /*如果查询出来是leader*/
        if (sysRole.getIsLeader().equals(UserEnum.IS_LEADER.getCode())) {

            sysRole1.setId(id);
            sysRole1.setRoleStatus(UserEnum.N_STATUS.getCode());
            /*如果能拿到子类型，不能删除 ,||这里要查询一下角色菜单中间表*/
            if (StringUtils.isNotNull(findByPid(sysRole.getId()))) {
                throw new CustomException("子类型存在不能删除",PublicDictUtil.ERROR_VALUE);
            }
            /*else{
                *//*这个是真删除，*//*
                sysRoleMapper.deleteByPrimaryKey(id);
                *//*这个是假删除，设置为不可用状态*//*
                sysRoleMapper.updateByPrimaryKeySelective(sysRole1);
            }*/
        }
        /*这个是真删除，*/
        int count = sysRoleMapper.deleteByPrimaryKey(id);
        /*这个是假删除，设置为不可用状态*/
//        int count = sysRoleMapper.updateByPrimaryKeySelective(sysRole1);
        if (count > 0) {
            return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "删除成功");
        } else {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "删除失败");
        }

    }
}
