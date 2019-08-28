//package com_xzyh_crm.controller;
//
//import com_xzyh_crm.pojo.SysMenu;
//import com_xzyh_crm.service.SysMenuService;
//import com_xzyh_crm.util.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/SysMenu")
//public class SysMenuController {
//    @Autowired
//    private SysMenuService sysMenuService;
//
//
//    @PostMapping(value="/save")
//    public Result save(@RequestBody SysMenu record) {
//
//        return HttpResult.ok(sysMenuService.save(record));
//    }
//
//
//    @PostMapping(value="/delete")
//    public HttpResult delete(@RequestBody List<SysMenu> records) {
//        return HttpResult.ok(sysMenuService.delete(records));
//    }
//
//
//    @GetMapping(value="/findNavTree")
//    public HttpResult findNavTree(@RequestParam String userName) {
//        return HttpResult.ok(sysMenuService.findTree(userName, 1));
//    }
//
//
//    @GetMapping(value="/findMenuTree")
//    public HttpResult findMenuTree() {
//        return HttpResult.ok(sysMenuService.findTree(null, 0));
//    }
//
//}
