package com.xiaopengpeng.mapper;

import com.xiaopengpeng.pojo.TbUserInfo;

public interface TbUserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbUserInfo record);

    int insertSelective(TbUserInfo record);

    TbUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbUserInfo record);

    int updateByPrimaryKeyWithBLOBs(TbUserInfo record);

    int updateByPrimaryKey(TbUserInfo record);
    
    TbUserInfo selectByUserName(String login_name);
}