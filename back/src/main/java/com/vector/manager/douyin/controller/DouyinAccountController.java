//package com.vector.manager.douyin.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import com.vector.manager.core.common.Result;
//import com.vector.manager.douyin.service.DouyinAccountService;
//
//import javax.annotation.PostConstruct;
//
//@RestController
//@RequestMapping("/douyin")
//public class DouyinAccountController {
//
//    @Autowired
//    private DouyinAccountService douyinAccountService;
//
//    @GetMapping("/list")
//    public Result list() {
//        return Result.ok().add(douyinAccountService.list());
//    }
//
//    @PostMapping("/setDefault/{openId}")
//    public Result setDefault(@PathVariable String openId) {
//        douyinAccountService.setDefault(openId);
//        return Result.ok();
//    }
//}
