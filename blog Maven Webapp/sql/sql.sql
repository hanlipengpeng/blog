create database blog;
use blog;

#友情链接
create table tb_friendly_link(
	id int(11) primary key auto_increment  comment "唯一索引",
	link_name varchar(10) comment "连接的显示名称",
	link_url varchar(50) comment "连接的url",
	link_time timestamp  comment "创建友情链接的时间"
);
#用户信息
create table tb_user_info(
	id int(11) primary key auto_increment comment "唯一索引",
	login_name varchar(30)  comment "登陆名称",
	login_password varchar(30)  comment "登陆密码",
	net_name varchar(10)  comment "网名",
	job varchar(30) comment "工作",
	address varchar(30)  comment "地址",
	phone_num varchar(15) comment "电话号",
	email varchar(30) comment "email地址",
	head_url varchar(50) comment "头像",
	introduce_myself longtext comment "自我介绍"
);
#文章分类
create table tb_article_category(
	id int(11) primary key auto_increment comment "唯一索引",
	name varchar(20) comment "分类名字",
	description text comment "文章分类描述",
	sort int(11) comment "用于排序"
);

#文章
create table tb_article(
	id int(11) primary key auto_increment comment "唯一索引",
	category int(11) not null comment "文章分类",
	title varchar(100) comment "文章标题",
	content longtext comment "文章内容",
	create_time timestamp comment "文章创建时间",
	author varchar(20) comment "作者",
	img_url varchar(100) comment "文章的图片",
	read_count int(11) comment "浏览次数",
	like_count int(11) comment "点赞数量"
);

#评论
create table tb_article_comment(
	id int(11) primary key auto_increment  comment "唯一索引",
	article_id int(11) comment "评论的是哪篇文章",
	user_name varchar(20) comment "评论用户名",
	content text comment "评论的内容",
	create_time timestamp comment "评论时间",
	email varchar(50) comment "评论人的email地址",
	father_comm_id int(11) comment "父评论，没有的话为空",
	like_count int(11) comment "点赞数量"
);

#留言
create table tb_message(
	id int(11) primary key auto_increment  comment "唯一索引",
	user_name varchar(20) comment "留言的用户",
	email varchar(20) comment "评论人的email",
	content text comment "评论的内容",
	create_time timestamp comment "评论时间",
	ischeck int(1) comment "是否通过审核"
);

#相册
create table tb_photo(
	id int(11) primary key auto_increment  comment "唯一索引",
	create_time timestamp comment "上传照片的时间",
	photo_url varchar(20) comment "照片的url地址",
	photo_alt varchar(50) comment "照片描述"
);














有哪些实体类：
	文章,评论,友情链接
	留言
	用户
	
	个人相册展示


#前台展示需要的是两张页面，一个是index，也就是个人首页，还有一个是文章的详细内容：
#	文章，评论 
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
