package com.xiaopengpeng.service;

import java.util.List;

import com.xiaopengpeng.pojo.TbArticle;
import com.xiaopengpeng.pojo.TbFriendlyLink;
import com.xiaopengpeng.pojo.TbMessage;
import com.xiaopengpeng.pojo.TbPhoto;
import com.xiaopengpeng.pojo.TbUserInfo;

public interface ArticleService {
	//主页展示
	//文章的问题
	List<TbArticle> getAllArticle();
	//获取个人信息
	TbUserInfo getUserInfo();
	//获取友情链接
	List<TbFriendlyLink> getAllFrindlyLink();
	//获取文章详情
	TbArticle getArticleById(int id);
	//博文检索
	List<TbArticle> getArticleByWhere(String ArticleName);
	
	//个人介绍
	String getIntroduce_myself();
	
	//显示留言板
	List<TbMessage> getAllMessage();
	//留言
	void leaveMessage(TbMessage tbMessage);
	
	//获取相册
	List<TbPhoto> getAllPhoto();
	
	
	
	
}
