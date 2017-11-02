package com.cdl.service;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.rmi.Naming;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanFilter;
import org.apache.lucene.search.FilterClause;
import org.apache.lucene.search.MultiSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.Searchable;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.cdl.entity.LibFileInfo;
import com.cdl.entity.Page;
import com.cdl.hadoop.dc.model.XNG;

public class FileSearchServiceImpl implements FileSearchService{
	private static final Log log = LogFactory.getLog(FileSearchServiceImpl.class);

	/**
	 * 根据关键字和文件类型查询文件信息
	 * 1.档查询类型为NULL，查询所有文件类型
	 * 2.根据文件标题和内容查询文件元数据索引目录
	 * 3.根据查询到的doc对象的ID属性查询文件内容
	 * 4.高亮显示查询结果
	 * @author root
	 */
	
	public Page<LibFileInfo> searchFile(String keywords, String searchFileType,
			int sortType, Page<LibFileInfo> page) {
		// TODO Auto-generated method stub
		page = (page==null)?new Page<LibFileInfo>():page;
		//声明两个查询域的组合条件
		BooleanClause.Occur[] clause={
				BooleanClause.Occur.SHOULD,
				BooleanClause.Occur.SHOULD,
		};
		
		String searchFields[] = XNG.YXLIB_SEARCH_FIELDS_ALL;
		Query query = null;
		Searcher searcher = null; 
		try {
			//查找存活可执行搜索主机
			Searchable[] hosts = lookupRemote();
			if(hosts.length<1){
				return page;
			}
			//多索引文件检索
			searcher = new MultiSearcher(hosts);
			
		} catch (Exception e) {
			log.error("构建查询类异常");
			e.printStackTrace();
		}
		final Analyzer luceneAnalyzer = new IKAnalyzer();
		try {
			query = MultiFieldQueryParser.parse(Version.LUCENE_30, QueryParser.escape(keywords), searchFields, clause,luceneAnalyzer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//开始执行检索（分页）
		try {
			doPagingSearch(searcher,query,searchFileType,sortType,page,"zhangsan");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}

    /**
     * 查询默认执行者
     * 
     * @param searcher
     * @param query
     * @param hitsPerPage
     * @param data
     * @throws IOException
     */
    private void doPagingSearch(Searcher searcher, Query query,
    		String searchFileType, int sortType, 
    		Page<LibFileInfo> page,String userId) throws IOException
    {
        int start = (page.getNewPageNo() - 1)*page.getPageSize();
        int end = page.getNewPageNo()*page.getPageSize();
        System.out.println("**********page_start:"+start);
        System.out.println("**********page_end:"+end);
        page.setData(new LinkedList<LibFileInfo>());
        
        String sortFieldName = getFieldNameBySortType(sortType);
        
        TopDocs hits = null;
        BooleanFilter filter = new BooleanFilter();
        if(StringUtils.isNotBlank(searchFileType))
        {
            TermQuery extQuery = new TermQuery(new Term("fileExtName",searchFileType));
            QueryWrapperFilter extQF = new QueryWrapperFilter(extQuery);
            FilterClause  extFc = new FilterClause(extQF, Occur.MUST);
            filter.add(extFc);
        }
        
        
        if(sortFieldName != null)
        {
        	System.out.println("1");
            Sort sort = new Sort(new SortField(sortFieldName, SortField.LONG, true));
            hits = searcher.search(query,filter, end, sort);
        }
        else
        {
        	System.out.println("2");
            hits = searcher.search(query,filter, end);
        }

        int numTotalHits = hits.totalHits;
        page.setTotalCount(numTotalHits);
        page.setStart(start);
        System.out.println("搜索条数："+numTotalHits);
        int length = Math.min(numTotalHits, end);
        for(int i = start; i < length; i++)
        {
        	//java语言中为对象的引用分为了四个级别，分别为 强引用 、软引用、弱引用、虚引用。
        	//引用的好处！他可以在你对对象结构和拓扑不是很清晰的情况下，帮助你合理的释放对象，造成不必要的内存泄漏！！
            WeakReference<Document> wr = new WeakReference<Document>(searcher.doc(hits.scoreDocs[i].doc));
            String docId = (String)((Document) wr.get()).get("id");
            System.out.println("*************"+docId);
            
            if(docId != null)
            {
                LibFileInfo lfi = new LibFileInfo();
                try
                {
                	//将查询出来的lucene文档对象转换为业务对象LibFileInfo
                    doc2LibFileInfo(query,(Document) wr.get(),lfi);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                page.getData().add(lfi);
                lfi = null;
            }
            
            wr = null;
        }
    }
    /**
     * 将查询出来的lucene文档对象转换成业务对象LibFileInfo
     * @param query
     * @param document
     * @param lfi
     */
	private void doc2LibFileInfo(Query query, Document doc, LibFileInfo lfi) {
		// TODO Auto-generated method stub
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    lfi.setSid(doc.get("id"));
		    lfi.setFileTitle(doc.get("title"));
		    lfi.setUploadBy(doc.get("uploadBy"));
		    lfi.setUploadByName(doc.get("uploadLoginName"));
		    lfi.setUploadDate(doc.get("uploadDate"));
		    lfi.setDownloadNum(Long.valueOf(doc.get("fileDownCount")));
		    lfi.setEvalNum(Long.valueOf(doc.get("fileScoreCount")));
		    lfi.setEvalValue(Float.valueOf(doc.get("fileScore")));
		    lfi.setFileExtName(doc.get("fileExtName"));
		    lfi.setPageNum(Long.valueOf(doc.get("filePageNo")));
	        
	        try
	        {
	        	lfi.setFileTitle(bestFragementV2(query,"title",doc,1));
	            if(StringUtils.isBlank(lfi.getFileTitle()))
	            {
	            	lfi.setFileTitle(doc.get("title"));
	            }
	            lfi.setBestFragment(bestFragementV2(query,"context",doc,4));
	            if(StringUtils.isBlank(lfi.getBestFragment()))
	            {
	                String context = doc.get("context");
	                lfi.setBestFragment(context.substring(0,Math.min(100, context.length())));
	            }
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
	}

	/**
	 * 取得文档ID为docid的文档对于查询结果最佳匹配结果高亮
	 * @param query
	 * @param string
	 * @param doc
	 * @param i
	 * @return
	 * @throws InvalidTokenOffsetsException 
	 * @throws IOException 
	 */
	private String bestFragementV2(Query query, String fieldName, Document doc,
			int fragmentNum) throws Exception {
		    String highLightContent[] = null;
	        
	        final Analyzer luceneAnalyzer = new IKAnalyzer();
	        TokenStream tokenStream = TokenSources.getTokenStream(doc,fieldName,luceneAnalyzer);
	        SimpleHTMLFormatter shf = new SimpleHTMLFormatter(XNG.YXLIB_SEARCH_RESULT_HIGHLIGHTER_PREFIX, XNG.YXLIB_SEARCH_RESULT_HIGHLIGHTER_SUFFIX);
	        Highlighter highlighter = new Highlighter(shf, new QueryScorer(query));
	        highlighter.setTextFragmenter(new SimpleFragmenter(32));
	        
	        highLightContent = highlighter.getBestFragments(tokenStream, doc.get(fieldName),fragmentNum);
	        return StringUtils.join(highLightContent,"\t");
	}

	private String getFieldNameBySortType(int sortType) {
		// TODO Auto-generated method stub
		  switch(sortType)
	        {
	        // 按最多下载次数排序
	        case 1:
	            return "fileDownCount";
	        // 按最新上传排序
	        case 2:
	            return "uploadTimestamp";
	        }
	        return null;
	}

	
	//RMI查找存活可执行搜索主机
	private Searchable[] lookupRemote() throws Exception{
		String tmp = "//{ip}:9530/Searchable";
		ArrayList<Searchable> servers = new ArrayList<Searchable>();
		synchronized (XNG.liveHost) {
			Iterator iter = XNG.liveHost.keySet().iterator();
			while(iter.hasNext()){
				String ip = (String)iter.next();
				try {
					servers.add((Searchable)Naming.lookup(tmp.replace("{ip}", ip)));
				} catch (Exception e) {
					log.error("检索索引主机出错",e);
				}
			
			}
		}
		Searchable[] s = new Searchable[servers.size()];
		servers.toArray(s);
		return s;
	}
}
