//package com.vector.manager.douyin.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import com.vector.manager.core.common.Result;
//import com.vector.manager.douyin.request.DouyinParam;
//import com.vector.manager.douyin.service.DouYinStarHotListService;
//
//@RestController
//public class DouYinStarHotListController {
//
//    @Autowired
//    private DouYinStarHotListService douYinStarHotListService;
//
//
//    @GetMapping("/starHotListGet")
//    public Result getFansList(@RequestParam(required = false, defaultValue = DouyinParam.HOT_LIST_TYPE) Integer hotListType) {
//        return Result.ok().add(douYinStarHotListService.starHotListGet(hotListType));
//    }
//
//    @GetMapping("/starAuthorScoreV2Get")
//    public Result starAuthorScoreV2Get(@RequestParam String uniqueId) {
//        return Result.ok().add(douYinStarHotListService.starAuthorScoreV2Get(uniqueId));
//    }
//
//    @GetMapping("/starAuthorScoreGet")
//    public Result starAuthorScoreGet() {
//        return Result.ok().add(douYinStarHotListService.starAuthorScoreGet());
//    }
//
//}
