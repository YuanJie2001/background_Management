//package com.vector.manager.douyin.service.impl;
//
//
//import com.douyin.open.api.DataExternalUserApi;
//import com.douyin.open.models.*;
//import org.springframework.stereotype.Service;
//import com.vector.manager.core.utils.TokenUtil;
//import com.vector.manager.douyin.service.DouYinUserReportDataService;
//
//@Service
//public class DouYinUserReportDataServiceImpl implements DouYinUserReportDataService {
//
//    @Override
//    public ExternalDataUserExternalDataUserInlineResponse200Data dataExternalUserItemGet(Integer dateType) {
//        DataExternalUserApi apiInstance = new DataExternalUserApi();
//        return apiInstance.dataExternalUserItemGet(TokenUtil.openId(), TokenUtil.accessToken(), dateType).getData();
//    }
//
//    @Override
//    public ExternalDataUserExternalDataUserInlineResponse2001Data dataExternalUserFansGet(Integer dateType) {
//        DataExternalUserApi apiInstance = new DataExternalUserApi();
//        return apiInstance.dataExternalUserFansGet(TokenUtil.openId(), TokenUtil.accessToken(), dateType).getData();
//    }
//
//    @Override
//    public ExternalDataUserExternalDataUserInlineResponse2002Data dataExternalUserLikeGet(Integer dateType) {
//        DataExternalUserApi apiInstance = new DataExternalUserApi();
//        return apiInstance.dataExternalUserLikeGet(TokenUtil.openId(), TokenUtil.accessToken(), dateType).getData();
//    }
//
//    @Override
//    public ExternalDataUserExternalDataUserInlineResponse2003Data dataExternalUserCommentGet(Integer dateType) {
//        DataExternalUserApi apiInstance = new DataExternalUserApi();
//        return apiInstance.dataExternalUserCommentGet(TokenUtil.openId(), TokenUtil.accessToken(), dateType).getData();
//    }
//
//    @Override
//    public ExternalDataUserExternalDataUserInlineResponse2004Data dataExternalUserShareGet(Integer dateType) {
//        DataExternalUserApi apiInstance = new DataExternalUserApi();
//        return apiInstance.dataExternalUserShareGet(TokenUtil.openId(), TokenUtil.accessToken(), dateType).getData();
//    }
//
//    @Override
//    public ExternalDataUserExternalDataUserInlineResponse2005Data dataExternalUserProfileGet(Integer dateType) {
//        DataExternalUserApi apiInstance = new DataExternalUserApi();
//        return apiInstance.dataExternalUserProfileGet(TokenUtil.openId(), TokenUtil.accessToken(), dateType).getData();
//    }
//
//}
