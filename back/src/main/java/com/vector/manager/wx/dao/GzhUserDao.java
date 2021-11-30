package com.vector.manager.wx.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.vector.manager.wx.entity.GzhUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vector.manager.wx.entity.WxParams;
import com.vector.manager.wx.entity.export.SearchDTO;
import com.vector.manager.wx.entity.export.UserExportV2;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 粉丝表 Mapper 接口
 * </p>
 *
 * @author YuYue
 * @since 2020-04-18
 */
public interface GzhUserDao extends BaseMapper<GzhUser> {

    List<UserExportV2> selectUserAndTags(SearchDTO searchDTO);

    @SqlParser(filter=true)
    List<GzhUser> getUserNewTags(WxParams param);

    List<Map<String, Object>> getFenweiTagList();
}
