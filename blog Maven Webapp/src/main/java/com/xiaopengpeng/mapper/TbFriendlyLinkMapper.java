package com.xiaopengpeng.mapper;

import java.util.List;

import com.xiaopengpeng.pojo.TbFriendlyLink;

public interface TbFriendlyLinkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbFriendlyLink record);

    int insertSelective(TbFriendlyLink record);

    TbFriendlyLink selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbFriendlyLink record);

    int updateByPrimaryKey(TbFriendlyLink record);

	List<TbFriendlyLink> getAllFriendlyLinkMapper();
}