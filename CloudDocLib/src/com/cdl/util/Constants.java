package com.cdl.util;

public final class Constants {
	   
    public static final String YES_STRING = "Y"; // 是否标识
    public static final String NO_STRING = "N"; // 是否标识
    public static final String YES_STRING_NUMBER = "1"; // 是否标识 数字格式
    public static final String NO_STRING_NUMBER = "0"; // 是否标识 数字格式
    public static final String ROLE_PRE = "RL"; // 角色模块流水号前缀
    public static final String INFO_ORG_PRE = "OG"; // 组织编号前缀
    // 系统日志记录级别静态字符
    public static final int LOG_LEVEL_TRACE = 1; // 日记级别 描述输出
    public static final int LOG_LEVEL_DEDUG = 2; // 日记级别 调试输出
    public static final int LOG_LEVEL_INFO = 3; // 日记级别 信息输出
    public static final int LOG_LEVEL_WARN = 4; // 日记级别 警告输出
    public static final int LOG_LEVEL_ERROR = 5; // 日记级别 错误输出
    public static final int LOG_LEVEL_FATAL = 6; // 日记级别 致命输出
    public static final String SYSTEM_LOG = "syslog";  // 系统日志类型
    public static final String OPERAT_LOG = "operatlog"; // 操作日志类型
    
    public static final String SUPER_USER_CODE = "admin"; // 超级用户 userCode
    public static final String SPACE = ""; // 空字符
    public static final String COMMA = ","; // 逗号
    public static final String DOT = "."; // 点号
    public static final String SEMICOLON = ";"; // 分号
    public static final String UNDERLINE = "_"; // 下划线
    public static final String HORIZONTAL_LINE = "-"; // 横线
    public static final String LEFT_SLASH = "/";
    public static final String DELETE_STRING = "D"; // 已删除标识
    
    public static final Long ROOT_PARENT_ID = -1L;  //文件分类中，顶层分类的父节点id
    /**
     * 文件存储根路径
     */
    public static final String FILE_STROE_ROOT_PATH = "fileRootPath";
    /**
     * HDFS节点管理命令
     */
    public static final String DATANODE_START="/bin/hadoop-daemon.sh start datanode ";//启动数据节点
    public static final String DATANODE_STOP ="/bin/hadoop-daemon.sh stop datanode ";//停掉数据节点
    public static final String SECONDARY_START="/bin/hadoop-daemon.sh start secondarynamenode ";//启动二级名称节点
    public static final String SECONDARY_STOP="/bin/hadoop-daemon.sh stop secondarynamenode ";//停掉二级名称节点
    public static final String CATE_PUB = "cat /root/.ssh/id_dsa.pub >> /root/.ssh/authorized_keys";//拷贝公钥内容到认证文件中
    public static final String KEY_ORDER = "ssh-keygen -t dsa -P '' -f /root/.ssh/id_dsa";//生成无密钥文件的命令
    public static final String START_HADOOP = "/bin/start-all.sh";//启动HADOOP系统,HADOOP的实际路径从数据库中获取
    public static final String STOP_HADOOP="/bin/stop-all.sh";//停止HADOOP系统，HADOOP的时机路径从数据库中获取
    public static final String START_DFS="/bin/start-dfs.sh";//启动HDFS守护进程，用户添加名称节点使用
    public static final String STOP_DFS="/bin/stop-dfs.sh";//停止HDFS守护进程，备用
    public static final String FORMAT_NAMENODE="/bin/hadoop namenode -format";
    public static final String REFRESH_NODES="/bin/hadoop dfsadmin -refreshNodes";
    
    public static final String NAMENODE="namenode";
    public static final String DATANODE="datanode";
    public static final String SECONDNAMENODE="secondnamenode";
    
    public static final String MASTERS="masters";
    public static final String SLAVES="slaves";
    
    public static final String NODESTART="start";//节点启动状态
    public static final String NODESTOP="stop";//节点停止状态
    public static final String NODEDELETEING="deleting";//节点删除中状态
    public static final String NODELIVE="live";
    
    //报警级别
    public static final String ALARM_SERIOUS="serious";
    public static final String ALARM_MEDIUM="medium";
    public static final String ALARM_COMMON="common";
    
    //按下载量进行排序
    public static final String ORDER_BY_DOWNLOAD_NUM = "downloadNum";
    //按上传时间进行排序
    public static final String ORDER_BY_UPLOAD_DATE = "uploadDate";
    
    /**
     * 获取文件流类型：原文件
     */
    public static final int FILE_STREAM_TYPE_ORIG = 1;
    
    /**
     * 获取文件流类型：SWF文件
     */
    public static final int FILE_STREAM_TYPE_SWF = 2;
    
    /**
     * 获取文件流类型：GIF文件
     */
    public static final int FILE_STREAM_TYPE_GIF = 3;
    
    /**
     * 日期比较范围 大于
     */
    public static String DATE_GE = "ge";
    
    /**
     * 日期比较范围 小于
     */
    public static String DATE_LE = "le";
    /**
     * 文档已上传未提交
     */
    public static Long FILE_TRANS_STATUS_UNCONVERSION = 0L;
    
    /**
     * 文档提交中
     */
    public static Long FILE_TRANS_STATUS_SUBMITTING = 10L;
    
    /**
     * 文档转换中
     */
    public static Long FILE_TRANS_STATUS_CONVERSION = 20L;
    
    /**
     * 积分类型代码
     */
    public static String SCORE_CODE_TYPE = "score"; 
    
    /**
     * 用户注册获取经验代码
     */
    public static String SCORE_EXP_REGISTER = "exp_register";
    
    /**
     * 用户注册获取财富代码
     */
    public static String SCORE_MONEY_REGISTER = "money_register";
   
    /**
     * 用户当天首次登录获取经验代码
     */
    public static String SCORE_EXP_FIRST_LOGIN = "exp_first_login";
    
    /**
     * 文档被下载默认获取经验代码
     */
    public static String SCORE_DEFAULT_DOWNLOAD_EXP = "exp_download";
    
    /**
     * 免费文档被下载默认获取财富代码
     */
    public static String SCORE_DEFAULT_DOWNLOAD_MONEY = "money_download";
    
    /**
     * 上传文档默认获取经验代码
     */
    public static String SCORE_DEFAULT_UPLOAD_EXP = "exp_upload";
    
    /**
     * 单个用户每天默认的评价次数代码
     */
    public static String SCORE_DEFAULT_EVAL_COUNT = "eval_count";
    
    /**
     * 登录用户评价一份文档获取的有效财富值代码
     */
    public static String SCORE_DEFAULT_EVAL_MONEY = "eval_money";
    
    /**
     * 用户创建一份文辑，默认获取创建经验代码
     */
    public static String SCORE_DEFAULT_CREATE_ALBUM_EXP = "exp_album_create";
    
    /**
     * 用户创建一份文辑，默认获取创建财富代码
     */
    public static String SCORE_DEFAULT_CREATE_ALBUM_MONEY = "money_album_create";
    
    /**
     * 用户文辑被收藏，默认获取经验代码
     */
    public static String SCORE_DEFAULT_FAVOURITE_ALBUM_EXP = "exp_album_favourite";
    
    /**
     * 用户文辑被收藏，默认获取财富代码
     */
    public static String SCORE_DEFAULT_FAVOURITE_ALBUM_MONEY = "money_album_favourite";
    
    /**
     * 为文辑添加积分操作(创建操作)
     */
    public static String OS_ALBUM_TYPE_CREATE = "add";
    /**
     * 为文辑添加积分操作(收藏文辑)
     */
    public static String OS_ALBUM_TYPE_FAVOURITE = "favourite";
    
    
    /**
     * 文档可选的售价列表
     */
    public static String DOC_PRICE_LIST = "doc_price_list";
    
    /**
     * 文档共享邮件状态：初始化
     */
    public static Long DOC_SHARE_STATUS_INIT = 0L;
    
    /**
     * 文档共享邮件状态：待发送
     */
    public static Long DOC_SHARE_STATUS_TOSEND = 1L;

    /**
     * 文档通过状态
     */
    public static Long ALBUM_PASS_STATUS = 1L;
    
    /**
     * 文档未通过状态
     */
    public static Long ALBUM_REJECT_STATUS = 0L;
    
    /**
     * 分类下的文档列表
     */
    public static String CATEGORY_DETAIL_DOC = "doc";
    
    /**
     * 分类下的文辑列表
     */
    public static String CATEGORY_DETAIL_ALBUM = "album";
    // 树叶子节点标记值
    public static Long TREE_NODE_LEAF_FLAG = 1l;
    // 树父节点标记值
    public static Long TREE_NODE_PARENT_FLAG = 0l;
    
    public final static String DOWNLOAD_LICENSE = "DOWNLOAD_LICENSE";
    
   /**
    * 自动生成编码
    */
    // 岗位前缀
    public final static String POST_CODE_PREFIX ="P";
    // 岗位生成编码序列名称
    public final static String POST_CODE_SEQ = "LIB_POST_INFO_CODE_SEQ";
    // 组织前缀
    public final static String ORG_CODE_PREFIX ="O";
    // 组织生成编码序列名称
    public final static String ORG_CODE_SEQ = "LIB_ORG_INFO_CODE_SEQ";
	// 密级前缀
	public static final String SECRET_LVL_CODE_PREFIX = "S";
	// 密级生成编码序列名称
	public static final String SECRET_LVL_CODE_SEQ = "LIB_DENSE_INFO_SEQ";
	
	/**
	 * 文档操作action方法名
	 */
	// 下载文档
	public static final String FILE_ACTION_METHOD_DOWNLOAD = "downloadOrigFile";
	// 浏览文档
	public static final String FILE_ACTION_METHOD_VIEW = "toViewPage";
	// 加载swf文件
	public static final String FILE_ACTION_METHOD_VIEW_SWF = "viewSwfFile";
	// 密级授权操作权限编码
	public static final String FILE_OPER_AUTH_DOWNLOAD_CODE = "Y";//有下载权限
    public static final String FILE_OPER_AUTH_VIEW_CODE = "N";//无下载权限
    
    public static final String TYPE_OF_NO_CONVERT_KEY ="yxlib.file.suffix.noConvert";
    
    public static final String SAME_LVL_CATEGORY_FLAG = "1";
    public static final String NEXT_LVL_CATEGORY_FLAG = "2";
}

