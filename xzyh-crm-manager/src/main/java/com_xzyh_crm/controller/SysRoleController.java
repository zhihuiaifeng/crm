package com_xzyh_crm.controller;
import com_xzyh_crm.exception.CustomException;
import com_xzyh_crm.group.PageValidGroup;
import com_xzyh_crm.group.RoleEditValidGroup;
import com_xzyh_crm.myEnum.UserEnum;
import com_xzyh_crm.pojo.SysRole;
import com_xzyh_crm.service.SysRoleService;
import com_xzyh_crm.util.FormatUtil;
import com_xzyh_crm.util.PublicDictUtil;
import com_xzyh_crm.util.Result;
import com_xzyh_crm.util.StringUtils;
import com_xzyh_crm.vo.SysRoleAddVo;
import com_xzyh_crm.vo.SysRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/SysRole")
@Slf4j
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private FormatUtil formatUtil;

    /*添加操作*/
    @PostMapping("save")
    public Result save(@RequestBody SysRoleAddVo sysRoleVo){
        /*判断传入的参数*/
        if (sysRoleVo.getLeaderListRoleVo().isEmpty()|| sysRoleVo.getLeaderListRoleVo().size()!=2) {
            throw new CustomException("传入参数错误",PublicDictUtil.ERROR_VALUE);
        }
         return sysRoleService.save(sysRoleVo);

    }
    /*查询获取分页消息，并且可以进行字段的搜索*/
    @PostMapping(value="/findAll")
    public Result findAll(@Validated(PageValidGroup.class) @RequestBody SysRoleVo sysRoleVo) {
        return sysRoleService.findAll(sysRoleVo);
    }

  /*  修改操作*/
    @PutMapping("edit")
    public Result edit(@RequestBody SysRoleVo sysRoleVo){
        /*查看传入的字段是否为空，因为前端只传入名字和描述两个字段,还有一个字段就是id*/
        if (!formatUtil.checkStringNull(sysRoleVo.getRoleName(), sysRoleVo.getRoleDesc())){
            return Result.resultData(PublicDictUtil.ERROR_VALUE,"传入的参数不正确");
        }
        if(UserEnum.sys_SHOPS.getMsg().equalsIgnoreCase(sysRoleVo.getRoleName())) {
            throw new CustomException(PublicDictUtil.ERROR_VALUE,"超级管理员不允许修改!");
        }
        /*return sysRoleService.edit(sysRoleVo);*/
        return sysRoleService.edit(sysRoleVo);
    }

    /*修改的时候先去查一下*/
    @GetMapping(value="/findRoleMenus")
    public Result findRoleMenus(@PathVariable Long id) {
        /*判断传入的id是否存在*/
        if (id!=null) {
            System.out.println(true);
        }
        System.out.println(id.intValue());

        if (id == null||id.intValue()<=0){
            throw new CustomException("传入的id不存在",PublicDictUtil.ERROR_VALUE);
        }
        return sysRoleService.findById(id);
    }

    /*删除操作*/
    @DeleteMapping(value="/delete")
    public Result delete(@PathVariable Long id) {
        if (!formatUtil.checkLong(id)) {
            throw new CustomException("传入的ID不存在",PublicDictUtil.ERROR_VALUE);
        }

        return sysRoleService.delete(id);
    }

}
