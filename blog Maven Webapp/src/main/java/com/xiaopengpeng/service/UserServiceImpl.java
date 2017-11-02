package com.xiaopengpeng.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaopengpeng.mapper.TbArticleCategoryMapper;
import com.xiaopengpeng.mapper.TbArticleMapper;
import com.xiaopengpeng.mapper.TbUserInfoMapper;
import com.xiaopengpeng.pojo.TbArticle;
import com.xiaopengpeng.pojo.TbArticleCategory;
import com.xiaopengpeng.pojo.TbUserInfo;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private TbUserInfoMapper tbUserInfoMapper;
	@Autowired
	private TbArticleCategoryMapper tbArticleCategoryMapper;
	@Autowired
	private TbArticleMapper tbArticleMapper;
	
	
	@Override
	public TbUserInfo getUserByName(String login_name) {
		TbUserInfo selectByUserName = tbUserInfoMapper.selectByUserName(login_name);
		return selectByUserName;
	}


	@Override
	public List<TbArticleCategory> getAllArticalCategory() {
		return tbArticleCategoryMapper.getAll();
		
	}


	@Override
	public void addArticleClassify(TbArticleCategory tbArticleCategory) {
		tbArticleCategoryMapper.insert(tbArticleCategory);
	}


	@Override
	public void deletArticleClassify(int id) {
		tbArticleCategoryMapper.deleteByPrimaryKey(id);
	}


	@Override
	public void addArticle(TbArticle tbArticle) {
		tbArticleMapper.insert(tbArticle);
		
	}


	@Override
	public List<TbArticle> getAllArticle() {
		
		return tbArticleMapper.getAllArticle();
	}


	@Override
	public void deletArticle(int id) {
		tbArticleMapper.deleteByPrimaryKey(id);
		
	}

}
