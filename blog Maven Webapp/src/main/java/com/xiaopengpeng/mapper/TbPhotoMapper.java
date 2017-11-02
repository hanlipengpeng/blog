package com.xiaopengpeng.mapper;

import java.util.List;

import com.xiaopengpeng.pojo.TbPhoto;

public interface TbPhotoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbPhoto record);

    int insertSelective(TbPhoto record);

    TbPhoto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbPhoto record);

    int updateByPrimaryKey(TbPhoto record);

	List<TbPhoto> getAllPhoto();
}