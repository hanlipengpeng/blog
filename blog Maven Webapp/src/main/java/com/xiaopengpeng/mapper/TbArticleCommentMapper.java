package com.xiaopengpeng.mapper;

import com.xiaopengpeng.pojo.TbArticleComment;

public interface TbArticleCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbArticleComment record);

    int insertSelective(TbArticleComment record);

    TbArticleComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbArticleComment record);

    int updateByPrimaryKeyWithBLOBs(TbArticleComment record);

    int updateByPrimaryKey(TbArticleComment record);
}