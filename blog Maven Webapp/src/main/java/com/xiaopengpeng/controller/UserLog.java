package com.xiaopengpeng.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.xiaopengpeng.pojo.TbArticle;
import com.xiaopengpeng.pojo.TbArticleCategory;
import com.xiaopengpeng.pojo.TbUserInfo;
import com.xiaopengpeng.service.UserService;

@Controller
@RequestMapping("/admin")
public class UserLog {
	@Autowired
	private UserService userService;
	@Value("D:/Program/apache-tomcat-8.0.39/webapps/blog/WEB-INF/saveImages/")
	private String preFileName;

	
	
	
	
	
	
	/**
	 * 跳转到增加文章页面 
	 * @return
	 */
	@RequestMapping("toAddArticle")
	public String toAddArticle(Model model){
		List<TbArticleCategory> list = userService.getAllArticalCategory();
		model.addAttribute("list",list);
		return "addArticle";
	}
	@RequestMapping("addArticle")
	public String addArticle(TbArticle tbArticle,MultipartFile file,Model model) throws IllegalStateException, IOException{
		String filename = file.getOriginalFilename();
		String newFileName = UUID.randomUUID()+filename.substring(filename.lastIndexOf("."), filename.length());
		tbArticle.setImg_url("/blog/saveImages/"+newFileName);
		userService.addArticle(tbArticle);
		System.out.println(newFileName);
		file.transferTo(new File(preFileName+newFileName));
		List<TbArticleCategory> list = userService.getAllArticalCategory();
		model.addAttribute("list",list);
		return "addArticle";
	}
	@RequestMapping("toDeletArticle")
	public String toDeletArticle(Model model){
		List<TbArticle> list = userService.getAllArticle();
		model.addAttribute("list", list);
		return "deletArticle";
	}
	@RequestMapping("deletArticle")
	public String toDeletArticle(int id,Model model){
		userService.deletArticle(id);
		List<TbArticle> list = userService.getAllArticle();
		model.addAttribute("list", list);
		return "deletArticle";
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 跳转到文章分类
	 * @return
	 */
	@RequestMapping("toArticleClassify")
	public String toArticleClassify(Model model){
		List<TbArticleCategory> list = userService.getAllArticalCategory();
		model.addAttribute("list",list);
		return "articleClassify";
	}
	/**
	 * 添加文章分类
	 * @return
	 */
	@RequestMapping("addArticleClassify")
	public String addArticleClassify(TbArticleCategory tbArticleCategory,Model model){
		userService.addArticleClassify(tbArticleCategory);
		List<TbArticleCategory> list = userService.getAllArticalCategory();
		model.addAttribute("list",list);
		return "articleClassify";
	}
	
	/**
	 * 删除文章分类
	 * @return
	 */
	@RequestMapping("deletArticleClassify")
	public String deletArticleClassify(int id,Model model){
		userService.deletArticleClassify(id);
		List<TbArticleCategory> list = userService.getAllArticalCategory();
		model.addAttribute("list",list);
		return "articleClassify";
	}
	
	/**
	 * 跳转到登陆页面
	 * @return
	 */
	@RequestMapping("tologin")
	public String tologin(){
		return "login";
	}
	
	/**
	 * 登陆成功后跳转的页面
	 * @return
	 */
	@RequestMapping("login")
	public String login(String userName,String password,TbUserInfo user,Model modle){
		System.out.println("userName："+userName +"password："+ password);
		TbUserInfo userByName = userService.getUserByName(userName);
		if(userByName==null | password == null |password.equals("") | !password.equals(userByName.getLogin_password()) ){
			return "login";
		}
		//登陆成功
		return "adminindex";
	}
	

}
