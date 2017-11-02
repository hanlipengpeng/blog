package com.xiaopengpeng.mapper;

import java.util.List;

import com.xiaopengpeng.pojo.TbMessage;

public interface TbMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbMessage record);

    int insertSelective(TbMessage record);

    TbMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbMessage record);

    int updateByPrimaryKeyWithBLOBs(TbMessage record);

    int updateByPrimaryKey(TbMessage record);

	List<TbMessage> getAllMessage();
}