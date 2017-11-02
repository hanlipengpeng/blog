package com.xiaopengpeng.mapper;

import java.util.List;

import com.xiaopengpeng.pojo.TbArticle;

public interface TbArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbArticle record);

    int insertSelective(TbArticle record);

    TbArticle selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbArticle record);

    int updateByPrimaryKeyWithBLOBs(TbArticle record);

    int updateByPrimaryKey(TbArticle record);

	List<TbArticle> getAllArticle();

	List<TbArticle> getArticleByTitle(String articleName);
}