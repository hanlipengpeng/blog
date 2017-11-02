package com.xiaopengpeng.service;

import java.util.List;

import com.xiaopengpeng.pojo.TbArticle;
import com.xiaopengpeng.pojo.TbArticleCategory;
import com.xiaopengpeng.pojo.TbUserInfo;

public interface UserService {
	TbUserInfo getUserByName(String login_name);
	List<TbArticleCategory> getAllArticalCategory();
	void addArticleClassify(TbArticleCategory tbArticleCategory);
	void deletArticleClassify(int id);
	void addArticle(TbArticle tbArticle);
	List<TbArticle> getAllArticle();
	void deletArticle(int id);
}
