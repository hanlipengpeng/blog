package com.xiaopengpeng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping(value = "/article")
public class Article {
	//article/index
	@RequestMapping("index")
	public String index(){
		//return 一个视图
		return "index";
	}
	
	//article/aboutme
	@RequestMapping("about")
	public String about(){
		return "about";
	}
	
	//article/leave
	@RequestMapping("leave")
	public String leave(){
		return "leave";
	}
	
	//article/leave
	@RequestMapping("photo")
	public String photo(){
		return "photo";
	}

}
