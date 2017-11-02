package com.cdl.hadoop.dc.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class XNG
{

    /**
     * XNG版本
     */
    private static final String VERSION = "1.0";

    /**
     * 默认提交任务名称
     */
    public static final String JOB_NAME = "yxlib-job-";

    /**
     * pdf文件后缀
     */
    public static final String PDF_EXTENSION = ".pdf";

    /**
     * swf文件后缀
     */
    public static final String SWF_EXTENSION = ".swf";

    /**
     * gif文件后缀
     */
    public static final String GIF_EXTENSION = ".gif";
    
    /**
     * png文件后缀
     */
    public static final String PNG_EXTENSION = ".png";

    /**
     * txt文件后缀
     */
    public static final String TXT_EXTENSION = ".txt";

    /**
     * 生成缩略图类型
     */
    public static final String YXLIB_THUMB_KIND = "png";

    /**
     * 文档类型之元数据类型
     */
    public static final int YXLIB_DOCUMENT_TYPE_META = 1;

    /**
     * 文档类型之文本内容类型
     */
    public static final int YXLIB_DOCUMENT_TYPE_CONTENT = 2;

    /**
     * 默认openoffice开放端口
     */
    public static final int YXLIB_OPENOFFICE_PORT = 9527;
    /**
     * 守护进程开启端口
     */
    public static final int YXLIB_DAEMON_PORT = 9529;
    /**
     * 检索服务守护端口
     */
    public static final int YXLIB_SEARCHER_PORT = 9530;
    
    /**
     * 默认查询每页面个数
     */
    public static final int YXLIB_PER_PAGE_SEARCH_COUNT = 10;

    /**
     * 默认文档支持类型
     * @deprecated
     */
    public static final String YXLIB_SUPPORT_EXTENSION[] = new String[]
    { 
            "pdf", 
            "doc", 
            "txt", 
            "ppt", 
            "xls" 
    };

    /**
     * 默认查询字段
     */
    public static final String YXLIB_SEARCH_FIELDS_ALL[] = new String[]
    { 
             "title", 
             "context" 
     };
    
    /**
     * @deprecated
     */
    public static final String YXLIB_SEARCH_FIELDS_EXTENSION[] = new String[]
     { 
           "fileExtName",
           "title", 
           "context"
     };
    
    /**
     * 默认文件元数据索引位置
     * @deprecated
     */
    public static final String YXLIB_INDEX_META_DIR = "/indexes/{meta}/metaindex";
    
    /**
     * 默认文件文本数据索引位置
     * @deprecated
     */
    public static final String YXLIB_INDEX_CONTEXT_DIR = "/indexes/{content}/contextindex";

    /**
     * @deprecated
     */
    public static final String YXLIB_TASK_OUTPUT_DIR = "hdfs://hadoop-nn:9000/output";

    /**
     * 文件转换任务类型
     */
    public static final int YXLIB_JOB_TYPE_FILE_CONVERT = 1;

    /**
     * 建立索引任务类型
     */
    public static final int YXLIB_JOB_TYPE_INDEX_BUILD = 2;
    
    /**
     * 删除索引任务类型
     */
    public static final int YXLIB_JOB_TYPE_INDEX_DELETE = 3;
    
    /**
     * 启动守护进程
     */
    public static final int YXLIB_JOB_TYPE_START_DEAMON = 4;

    /**
     * 文档创建后索引创建
     */
    public static final int YXLIB_JOB_INDEX_TYPE_CREATE = 1;
    
    /**
     * 索引更新任务执行索引更新
     */
    public static final int YXLIB_JOB_INDEX_TYPE_UPDATE = 2;

    /**
     * 索引更新任务执行索引删除
     */
    public static final int YXLIB_JOB_INDEX_TYPE_DELETE = 3;

    /**
     * 可以将此参数写入到配置文件中，如hadoop-site.xml
     * @deprecated
     */
    public static final String FILE_CONVERT_LOCAL_TMP_DIR = "/home/filetrans/tmp/";
    
    /**
     * 查询结果高亮显示前后缀
     */
    public static final String YXLIB_SEARCH_RESULT_HIGHLIGHTER_PREFIX = "<em>";
    public static final String YXLIB_SEARCH_RESULT_HIGHLIGHTER_SUFFIX = "</em>";
    
    /**
     * 等待索引更新错误,可能hdfs文件本身就不存在  updated by zemi
     */
    public static final long YXLIB_JOB_INDEX_UPDATE_ERROR = 0;
    /**
     * 等待更新索引
     */
    public static final long YXLIB_JOB_INDEX_WAIT_UPDATE = 1;
    
    /**
     * 索引正在更新
     */
    public static final long YXLIB_JOB_INDEX_UPDATING = 2;
    
    /**
     * 索引已完成
     */
    public static final long YXLIB_JOB_INDEX_FINISHED = 3;
    
    /**
     * 不需要做索引
     */
    public static final long YXLIB_JOB_INDEX_NOTNEED = 4;

    /**
     * 默认第三方依赖
     */
    public static final String[] DEPENDENCY_LIB = new String[]
    {
        //文档转换
        "/libs/jod/commons-lang.jar",
        "/libs/jod/commons-io-1.4.jar",
        "/libs/jod/jodconverter-2.2.2.jar",
        "/libs/jod/jodconverter-cli-2.2.2.jar",
        "/libs/jod/juh-3.0.1.jar",
        "/libs/jod/jurt-3.0.1.jar",
        "/libs/jod/ridl-3.0.1.jar",
        "/libs/jod/slf4j-api-1.5.6.jar",
        "/libs/jod/slf4j-jdk14-1.5.6.jar",
        "/libs/jod/unoil-3.0.1.jar",
        "/libs/jod/xstream-1.3.1.jar",
        "/libs/jod/JMuPdf.jar",
        //索引建立与查询
        "/libs/lucene/lucene-core-3.0.2.jar",
        "/libs/lucene/lucene-remote-3.0.3.jar",
        "/libs/lucene/lucene-queries-3.0.3.jar",
        "/libs/lucene/IKAnalyzer3.2.3Stable.jar",
        //webservice
        "/libs/webservice/cxf-2.2.4.jar",
        "/libs/webservice/XmlSchema-1.4.7.jar",
        "/libs/webservice/wsdl4j-1.6.2.jar",
        //berkeley db
        "/libs/bdb/je-4.1.7.jar"
    };
    
    /**
     * 文件处理异常
     */
    public static final String FTE_PK_HDFS_FILE_NOTFOUND = "FTE_PK_HDFS_FILE_NOTFOUND";
    public static final String FTE_PK_WS_ADDR_NULL = "FTE_PK_WS_ADDR_NULL";
    public static final String FTE_F2P_FNF = "FTE_F2P_FNF";
    public static final String FTE_F2P_FAILED = "FTE_F2P_FAILED";
    public static final String FTE_P2S_SNF = "FTE_P2S_SNF";
    public static final String FTE_P2S_FAILED = "FTE_P2S_FAILED";
    public static final String FTE_THUMB_FAILED = "FTE_THUMB_FAILED";
    public static final String FTE_THUMB_SNF = "FTE_THUMB_SNF";
    public static final String FTE_P2T_SNF = "FTE_P2T_SNF";
    public static final String FTE_P2T_FAILED = "FTE_P2T_FAILED";
    public static final String FTE_INDEX_FAILED = "FTE_INDEX_FAILED";
    public static final String FTE_IWRITE_FAILED = "FTE_IWRITE_FAILED";
    public static final String FTE_UOLOAD_SNF = "FTE_UOLOAD_SNF";
    public static final String FTE_TASK_KILLED = "FTE_TASK_KILLED";
    
    /**
     * 索引主目录
     */
    public static final String YXLIB_INDEX_MAIN_PATH = "/indexes/{ip}/allinone";
    
    /**
     * 索引待合并目录
     */
    public static final String YXLIB_INXEX_MERGE_PATH = "/indexes/{ip}/wait2merge";
    
    /**
     * 工作单元状态
     */
    public static final int WU_STATUS_UNSTART = 0;
    public static final int WU_STATUS_FILE_DOWNLOADED = 1;
    public static final int WU_STATUS_FILE_TO_PDF = 2;
    public static final int WU_STATUS_PICK_THUMB = 3;
    public static final int WU_STATUS_PDF_TO_SWF = 4;
    public static final int WU_STATUS_FILE_TO_TXT = 5;
    public static final int WU_STATUS_INDEX_WRITED = 6;
    public static final int WU_STATUS_FILE_UPLOADED = 7;
    public static final int WU_STATUS_CALLBACKED = 8;
    public static final int WU_STATUS_SUCCESS = 9;
    public static final int WU_STATUS_KILLED = 999;
    
    /**
     * 索引状态
     */
    public static final int WU_STATUS_INDEX_DELETED = 1;
    public static final int WU_STATUS_INDEX_REBUILDED = 2;
    
    /**
     * 启动守护进程
     */
    public static final int YXLIB_DEAMON_START = 0;
    
    /**
     * 停止守护进程
     */
    public static final int YXLIB_DEAMON_STOP = 1;
    
    /**
     * 重启动守护进程
     */
    public static final int YXLIB_DEAMON_RESTART = 2;
    
    /**
     * 默认日志行数
     */
    public static final int YXLIB_DEAMON_DEFAULT_LOG_SIZE = 1000;
    
    /**
     * 转换swf文件时，每个文件所含的页数
     */
    public static final int TRAN_PAGE_NUM = 5;
    
    /**
     * 报告队列类型
     * 1：已执行
     * 2：待执行
     * 3：执行中
     */
    public static final int YXLIB_REPORT_WU_QUEUE_EXECUTED = 1;
    public static final int YXLIB_REPORT_WU_QUEUE_WAIT_EXECUTE = 2;
    public static final int YXLIB_REPORT_WU_QUEUE_RUNNING = 3;
    
    /**
     * 工作单元队列类型
     * 1：成功执行队列
     * 2：失败队列
     * 3：待转换队列
     * 4：待重建索引队列
     * 5：待删除队列
     */
    public static final int YXLIB_WU_QUEUE_SUCCESSED = 1;
    public static final int YXLIB_WU_QUEUE_FAILED = 2;
    public static final int YXLIB_WU_QUEUE_WAIT_TRANS =3;
    public static final int YXLIB_WU_QUEUE_INDEX_REBUILD = 4;
    public static final int YXLIB_WU_QUEUE_INDEX_DELETE = 5;
    
    /**
     * 当前持有索引锁holder
     */
    public static final String YXLIB_DAEMON_LOCK_HOLDER_FILEHANDLER = "filehandler";
    public static final String YXLIB_DAEMON_LOCK_HOLDER_INDEXOPTIMIZER = "indexoptimizer";
    
    /**
     * 默认守护进程工作目录
     */
    public static final String YXLIB_DAEMON_BASEDIR = "/usr/tools/dcdaemon/";
    
    /**
     * 系统全局存活主机map
     */
    public static Map liveHost = Collections.synchronizedMap(new HashMap());
    public static Map deadHost = Collections.synchronizedMap(new HashMap());
    
    public static final String YXLIB_DCDEAMON_IGNORE_FILETYPE = "*.wps,*.ceb";
}

