package com.vector.manager.wx.service;

import com.vector.manager.wx.entity.GzhAccount;

public interface IWxGzhApiService {

    void tagAnalysis(GzhAccount gzhAccount, String lock);

    void tagRemove(GzhAccount gzhAccount, String lock);
}
