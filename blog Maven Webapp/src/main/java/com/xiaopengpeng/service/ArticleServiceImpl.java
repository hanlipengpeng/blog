package com.xiaopengpeng.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xiaopengpeng.mapper.TbArticleMapper;
import com.xiaopengpeng.mapper.TbFriendlyLinkMapper;
import com.xiaopengpeng.mapper.TbMessageMapper;
import com.xiaopengpeng.mapper.TbPhotoMapper;
import com.xiaopengpeng.mapper.TbUserInfoMapper;
import com.xiaopengpeng.pojo.TbArticle;
import com.xiaopengpeng.pojo.TbFriendlyLink;
import com.xiaopengpeng.pojo.TbMessage;
import com.xiaopengpeng.pojo.TbPhoto;
import com.xiaopengpeng.pojo.TbUserInfo;

public class ArticleServiceImpl implements ArticleService{
	@Autowired
	private TbArticleMapper tbArticleMapper;
	@Autowired
	private TbUserInfoMapper tbUserInfoMapper;
	@Autowired
	private TbFriendlyLinkMapper tbFriendlyLinkMapper;
	@Autowired
	private TbMessageMapper tbMessageMapper;
	@Autowired
	private TbPhotoMapper tbPhotoMapper;
	

	@Override
	public List<TbArticle> getAllArticle() {
		return tbArticleMapper.getAllArticle();
	}

	@Override
	public TbUserInfo getUserInfo() {
		
		return tbUserInfoMapper.selectByPrimaryKey(1);
	}

	@Override
	public List<TbFriendlyLink> getAllFrindlyLink() {
		
		return tbFriendlyLinkMapper.getAllFriendlyLinkMapper();
	}

	@Override
	public TbArticle getArticleById(int id) {
		return tbArticleMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<TbArticle> getArticleByWhere(String ArticleName) {
		
		return tbArticleMapper.getArticleByTitle(ArticleName);
	}

	@Override
	public String getIntroduce_myself() {
		TbUserInfo tbUserInfo = tbUserInfoMapper.selectByPrimaryKey(1);
		return tbUserInfo.getIntroduce_myself();
	}

	@Override
	public List<TbMessage> getAllMessage() {
		// TODO Auto-generated method stub
		return tbMessageMapper.getAllMessage();
	}

	@Override
	public void leaveMessage(TbMessage tbMessage) {
		tbMessageMapper.insert(tbMessage);
	}

	@Override
	public List<TbPhoto> getAllPhoto() {
		return tbPhotoMapper.getAllPhoto();
	}

}
