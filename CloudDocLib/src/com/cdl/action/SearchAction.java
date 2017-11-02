package com.cdl.action;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.formula.functions.Trim;

import com.cdl.entity.Page;
import com.cdl.service.FileSearchService;
import com.opensymphony.xwork2.ActionSupport;

public class SearchAction extends ActionSupport{
	private boolean success;
	private int od;
	private String kw;
	private String type;
	private String tc;
	private String order;
	private Page page;
	private FileSearchService  fileSearchService;
	
	public String execute(){
		long start = System.currentTimeMillis();
		if(StringUtils.isNotBlank(kw)){
			if(type!=null&&type.equals("all")){
				type = null;
			}
			fileSearchService.searchFile(kw.trim(), type, od, page);
		}else{
			page = new Page();
		}
		long end =System.currentTimeMillis();
		System.out.println(end-start);
		return "search_result";
		
	}

	public boolean isSuccess() { 
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getOd() {
		return od;
	}

	public void setOd(int od) {
		this.od = od;
	}

	public String getKw() {
		return kw;
	}

	public void setKw(String kw) {
		this.kw = kw;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTc() {
		return tc;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setFileSearchService(FileSearchService fileSearchService) {
		this.fileSearchService = fileSearchService;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	

}
