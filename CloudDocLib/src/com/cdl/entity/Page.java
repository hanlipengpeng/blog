package com.cdl.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 鍒嗛〉瀵硅薄. 鍖呭惈褰撳墠椤垫暟鎹強鍒嗛〉淇℃伅.
 * 
 */
public class Page<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	static private int DEFAULT_PAGE_SIZE = 10;

	/**
	 * 姣忛〉鐨勮褰曟暟
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;

	private int newPageNo = 1;
	/**
	 * 褰撳墠椤电涓?潯鏁版嵁鍦↙ist涓殑浣嶇疆,浠?寮?
	 */
	private int start;

	/**
	 * 褰撳墠椤典腑瀛樻斁鐨勮褰?绫诲瀷涓?埇涓篖ist
	 */
	private List<T> data;

	private long totalCount; // 鎬昏褰曟暟

	private String sort; // 鎺掑簭瀛楁

	private String direction; // 鎺掑簭鏂瑰紡

	// 鎬婚〉鏁?
	private List<Integer> pageNumber;
	
	/**
     * 褰撳墠椤甸潰鏄剧ず鐨勫紑濮嬮〉鍙?
     */
    private int startPageNo;
    
    /**
     * 褰撳墠椤甸潰鏄剧ず鐨勭粨鏉熼〉鍙?
     */
    private int endPageNo;
	
	/**
	 * 鏋勯?鏂规硶锛屽彧鏋勯?绌洪〉
	 */
	public Page() {
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<T>());
	}

	/**
	 * 榛樿鏋勯?鏂规硶
	 * 
	 * @param start
	 *            鏈〉鏁版嵁鍦ㄦ暟鎹簱涓殑璧峰浣嶇疆
	 * @param totalSize
	 *            鏁版嵁搴撲腑鎬昏褰曟潯鏁?
	 * @param pageSize
	 *            鏈〉瀹归噺
	 * @param data
	 *            鏈〉鍖呭惈鐨勬暟鎹?
	 */
	public Page(int start, long totalSize, int pageSize, List<T> data) {
		this.pageSize = pageSize;
		this.start = start;
		this.totalCount = totalSize;
		this.data = data;
	}

	/**
	 * 鍙栨暟鎹簱涓寘鍚殑鎬昏褰曟暟
	 */
	public long getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 鍙栨?椤垫暟
	 */
	public long getTotalPageCount() {
		if (totalCount % pageSize == 0)
			return totalCount / pageSize;
		else
			return totalCount / pageSize + 1;
	}

	/**
	 * 鍙栨瘡椤垫暟鎹閲?
	 */
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 褰撳墠椤典腑鐨勮褰?
	 */
	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * 鍙栧綋鍓嶉〉鐮?椤电爜浠?寮?
	 */
	public int getCurrentPageNo() {
		/***********update by luchun 澧炲姞0鍋氶櫎鏁扮殑鍒ゆ柇****/
		int pageNo = 0;// 缁欏畾榛樿鍊?
		if (pageSize != 0) {
			pageNo = start / pageSize + 1;
		}
		return pageNo;
	}

	public int getNewPageNo() {
		return newPageNo;
	}

	public void setNewPageNo(int newPageNo) {
		this.newPageNo = newPageNo;
	}

	/**
	 * 鏄惁鏈変笅涓?〉
	 */
	public boolean hasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount();
	}

	/**
	 * 鏄惁鏈変笂涓?〉
	 */
	public boolean hasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}

	/**
	 * 鑾峰彇浠讳竴椤电涓?潯鏁版嵁鐨勪綅缃紝姣忛〉鏉℃暟浣跨敤榛樿鍊?
	 */
	protected static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	/**
	 * 鑾峰彇浠讳竴椤电涓?潯鏁版嵁鐨勪綅缃?startIndex浠?寮?
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
		this.newPageNo = start / pageSize + 1;
		this.startPageNo = (newPageNo/DEFAULT_PAGE_SIZE)*DEFAULT_PAGE_SIZE+1;
		this.endPageNo = Math.min(startPageNo + 9, (int)getTotalPageCount());
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@SuppressWarnings("unchecked")
	public List getPageNumber() {
		pageNumber = new ArrayList();

		if (getTotalPageCount() > 0) {
			int pages = Integer.parseInt(getTotalPageCount() + "");
			for (int i = 1; i <= pages; i++) {
				pageNumber.add(i);
			}
		}

		return pageNumber;
	}

    public int getStartPageNo()
    {
        //update by yangchangpeng 2011-01-06
        //update by liwei@20110228 鍘婚櫎杩斿洖鐨勮礋鍊?
        return  this.getCurrentPageNo()<5 && this.getTotalPageCount() <= 10 ?1:Math.max(this.getCurrentPageNo()-5, 1);
    }

    public int getEndPageNo()
    {
        //update by yangchangpeng 2011-01-06
        return  (int)(this.getCurrentPageNo()+5<this.getTotalPageCount()?this.getCurrentPageNo()+5:this.getTotalPageCount());
        //return endPageNo;
    }
    
    public int getCurrentPageEnd()
    {
        return Math.min(start + pageSize, (int)totalCount);
    }
}