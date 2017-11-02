package com.xiaopengpeng.mapper;

import java.util.List;

import com.xiaopengpeng.pojo.TbArticleCategory;

public interface TbArticleCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbArticleCategory record);

    int insertSelective(TbArticleCategory record);

    TbArticleCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbArticleCategory record);

    int updateByPrimaryKeyWithBLOBs(TbArticleCategory record);

    int updateByPrimaryKey(TbArticleCategory record);
    
    List<TbArticleCategory> getAll();
}