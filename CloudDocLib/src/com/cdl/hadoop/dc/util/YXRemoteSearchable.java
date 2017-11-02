package com.cdl.hadoop.dc.util;

import java.io.File;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.FieldSelector;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RMIRemoteSearchable;
import org.apache.lucene.search.RemoteSearchable;
import org.apache.lucene.search.Searchable;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.search.Weight;
import org.apache.lucene.store.FSDirectory;

/**
 * 远程搜索实现类
 * @author root
 *
 */
public class YXRemoteSearchable extends UnicastRemoteObject implements RMIRemoteSearchable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Searchable local;
	public Searchable getLocal() {
		return local;
	}
	public void setLocal(Searchable local) {
		this.local = local;
	}
	public YXRemoteSearchable(Searchable local) throws RemoteException {
		this.local = local;
	}


	public void close() throws IOException {
		// TODO Auto-generated method stub
		local.close();
	}

	public Document doc(int i) throws CorruptIndexException, IOException {
		// TODO Auto-generated method stub
		return local.doc(i);
	}

	public Document doc(int i, FieldSelector fieldSelector)
			throws CorruptIndexException, IOException {
		// TODO Auto-generated method stub
		return local.doc(i,fieldSelector);
	}

	public int docFreq(Term term) throws IOException {
		// TODO Auto-generated method stub
		return local.docFreq(term);
	}

	public int[] docFreqs(Term[] terms) throws IOException {
		// TODO Auto-generated method stub
		return local.docFreqs(terms);
	}

	public Explanation explain(Weight weight, int doc) throws IOException {
		// TODO Auto-generated method stub
		return local.explain(weight, doc);
	}

	public int maxDoc() throws IOException {
		// TODO Auto-generated method stub
		return local.maxDoc();
	}

	public Query rewrite(Query arg0) throws IOException {
		// TODO Auto-generated method stub
		return local.rewrite(arg0);
	}

	public void search(Weight weight, Filter filter, Collector results)
			throws IOException {
		// TODO Auto-generated method stub
		local.search(weight, filter, results);
	}

	public TopDocs search(Weight weight, Filter filter, int n)
			throws IOException {
		// TODO Auto-generated method stub
		return local.search(weight, filter, n);
	}

	public TopFieldDocs search(Weight weight, Filter filter, int n, Sort sort)
			throws IOException {
		// TODO Auto-generated method stub
		return local.search(weight, filter, n,sort);
	}
	
	/**
	 * 绑定搜索地址
	 * @param args
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 */
	public static void main(String[] args) throws CorruptIndexException, IOException {
		String indexName = null;
		if(args!=null&&args.length==1){
			indexName=args[0];
		}
		if(indexName == null){
			return;
		}
		if(System.getSecurityManager()==null){
			System.setSecurityManager(new RMISecurityManager());
		}
		
		Searchable local = new IndexSearcher(FSDirectory.open(new File(indexName)),true);
		RemoteSearchable impl = new RemoteSearchable(local);
		//暴露出来一个访问地址
		Naming.rebind("//localhost/Searchable", impl);
	}

}
