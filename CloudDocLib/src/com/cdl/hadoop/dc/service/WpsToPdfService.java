package com.cdl.hadoop.dc.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.DispatchEvents;
import com.jacob.com.Variant;
import com.cdl.util.ConfigUtil;
import com.cdl.hadoop.dc.model.XNG;

/**
 * wps office系统文档转换，包括.wps .dps .et
 * @author zemi
 *
 */
public class WpsToPdfService {
	   private static final Log log = LogFactory.getLog(WpsToPdfService.class);
	   private static Configuration conf = new Configuration();
	   public static Converter newConverter(String name) {
		   if(name.equals("wps")){
			   return new Wps();
		   }
		   else if(name.equals("dps")){
			   return new DPS();
		   }
		   else if(name.equals("et")){
			   return new ET();
		   }
		   else{
			   return null;
		   }
	    }
	   
	   /**
	    * 处理wps,et,dps
	    * @param fileMetaData
	    * @return
	    */
	   public static boolean isCanHandleWps(String fileMetaData){
		 //扩展名
          // 10158,905f6054-edf8-4585-82e5-19342ea3e3b3,\root\txt\201209\905f6054-edf8-4585-82e5-19342ea3e3b3\905f6054-edf8-4585-82e5-19342ea3e3b3.txt,1,admin,2012-09-19,1348028760000,0,部署信息,txt,0,0,0,null,1348028777859

		   String[] strs=fileMetaData.split(",");
		   //文件名
		    String filePath=strs[2];
		 //扩展名
		    String ext=strs[8];
		    
		    if(ext!=null&&(ext.toLowerCase().equals("wps")||ext.toLowerCase().equals("et")||ext.toLowerCase().equals("dps"))){
		    	return true;
		    }
		   
		   return false;
	   }
	   /**
	    * wps,dps,et格式
	    * @param fileMetaData
	    */
	   public static void handleWPS(String fileMetaData){
		   String[] strs=fileMetaData.split(",");
		   //文件路径
		    String filePath=strs[2];
		   //扩展名
		    String ext=strs[8];
		    //文件名
		    String fileName = strs[1];
		    log.debug("文件名:"+filePath);
		    log.debug("扩展名:"+ext);
		    String storePath=ConfigUtil.getByKey("wps.store.path");
		    if(storePath==null) storePath="d:\\hdfs\\";
		    File dir = new File(storePath);
		    if(!dir.exists()) dir.mkdirs();
		    try{
			      
		    	FileSystem fs = FileSystem.get(conf);
		    	taskSetupCheck(new Path(filePath),fs);
		    	String in = filePath.replaceAll("\\\\", "\\/");
		    	String out=storePath+fileName+"."+ext.toLowerCase();
		    	String pdf=storePath+fileName+".pdf";
		    	downloadToLocalFile(in,out,fs);
		    	convert(ext.toLowerCase(),out,pdf);
		    	uploadFile(pdf,in.substring(0,in.lastIndexOf("/"))+"/"+fileName+".pdf",fs);
		    	deleteFiles(new String[]{out,pdf});
		    }catch(Exception ex){
		    	log.error("文件转换失败:"+ex);
		    }
	   }
	   /**
	    * 删除临时文件
	    */
	   private static void deleteFiles(String[] paths){
		   for(int i=0;i<paths.length;i++){
			   boolean success = (new File(paths[i])).delete();
		   }
	   }
	   
	   /**
	    * 下载文件到本地
	    * @param in
	    * @param out
	    * @param fs
	    */
	   private static void downloadToLocalFile(String in,String out,FileSystem fs) {
			
			log.debug("开始下载,从 "+in+" 到 "+out+".");
			
			File file = new File(out);
			if(!file.exists()){
				file.getParentFile().mkdirs();
			}
			
			BufferedOutputStream ostream = null;
			DataInputStream istream = null;
			
			try {
				istream = fs.open(new Path(in));
				ostream = new BufferedOutputStream(new FileOutputStream(file));

				byte[] buffer = new byte[1024];
				int bytes;
				while ((bytes = istream.read(buffer)) >= 0) {
					ostream.write(buffer, 0, bytes);
				}
			} catch (Exception e) {
				log.debug("文件下载失败",e);
			} finally {
				if (istream != null) {
					try {
						istream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					ostream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			log.debug("文件下载完成，已存入"+out);
		}
	   
	   //上传文件到hdfs
	   private static void uploadFile(String src,String dest ,FileSystem fs) throws IOException {
		   log.debug("开始上传,从 "+src+" 到 "+dest+".");
			BufferedInputStream istream = null;
			DataOutputStream ostream = null;
			try {
				istream = new BufferedInputStream(new FileInputStream(src));
				ostream = fs.create(new Path(dest));

				byte[] buffer = new byte[1024];
				int bytes;
				while ((bytes = istream.read(buffer)) >= 0) {
					ostream.write(buffer, 0, bytes);
				}
			} catch (Exception e) {
				log.error(" WpsToPdfService uploadFile error",e);
			} finally {
				try {
					if (istream != null)
						istream.close();
				} catch (IOException e) {
					log.error(" WpsToPdfService uploadFile error ",e);
				}
				try {
					if (ostream != null)
						ostream.close();
				} catch (IOException e) {
					log.error(" WpsToPdfService uploadFile error ",e);
				}
			}
		}

	   /**
	     * 任务运行必要参数检查，如不满足直接抛出异常返回
	     * 
	     * @param path 此次任务处理的输入文件路径，此路径文件必须在hdfs系统中存在
	     * @param input 文件输入流，与文件路径必须二都有其一
	     * @param conf 任务配置
	     * @param fs hdfs文件系统
	     * @throws IOException
	     */
	    private static void taskSetupCheck(Path path,FileSystem fs) throws IOException
	    {
	        //检查此次任务处理的输入文件
	        if(!fs.exists(path))
	        {
	        	//msg = XNG.FTE_PK_HDFS_FILE_NOTFOUND;
	            throw new IOException("task input path is not exits,path: "+path.toString());
	        }
	    }
	  
	   
	    public synchronized static boolean convert(String ext, String source, String pdf) {
	        return newConverter(ext).convert(source, pdf);
	    }

	    public abstract static interface Converter {

	        public boolean convert(String word, String pdf);
	    }

	    public static class Wps implements Converter {
	        public synchronized boolean convert(String word, String pdf) {
	            File pdfFile = new File(pdf);
	            File wordFile = new File(word);
	            log.info("开始转换wps文件:"+word);
	            ActiveXComponent wps = null;
	            try {
	                wps = new ActiveXComponent("wps.application");
	                ActiveXComponent doc = wps.invokeGetComponent("Documents").invokeGetComponent("Open", new Variant(wordFile.getAbsolutePath()));
	                doc.invoke("ExportPdf", new Variant(pdfFile.getAbsolutePath()));
	                doc.invoke("Close");
	                doc.safeRelease();
	                log.info("生成pdf文件:"+pdf);
	            } catch (Exception ex) {
	                log.error(ex);
	                return false;
	            } catch (Error ex) {
	            	log.error(ex);
	                return false;
	            } finally {
	                if (wps != null) {
	                    wps.invoke("Terminate");
	                    wps.safeRelease();
	                }
	            }
	            return true;
	        }
	    }

	    //相当于excel
	    public static class ET implements Converter {

	        public synchronized boolean convert(String word, String pdf) {
	            File pdfFile = new File(pdf);
	            File wordFile = new File(word);
	            log.info("开始转换ET文件:"+word);
	            ActiveXComponent wps = null;
	            try {
	                wps = new ActiveXComponent("et.application");
	                ActiveXComponent doc = wps.invokeGetComponent("Workbooks").invokeGetComponent("Open", new Variant(wordFile.getAbsolutePath()));
	                doc.invoke("ExportPdf", new Variant(pdfFile.getAbsolutePath()));
	                doc.invoke("Close");
	                doc.safeRelease();
	                log.info("生成pdf文件:"+pdf);
	            } catch (Exception ex) {
	            	log.error(ex);
	                return false;
	            } catch (Error ex) {
	            	log.error(ex);
	                return false;
	            } finally {
	                if (wps != null) {
	                    wps.invoke("Terminate");
	                    wps.safeRelease();
	                }
	            }
	            return true;
	        }
	    }
	    //相当于ppt
	    public static class DPS implements Converter {

	        public synchronized boolean convert(String word, String pdf) {
	            File pdfFile = new File(pdf);
	            File wordFile = new File(word);
	            log.info("开始转换DPS文件:"+word);
	            ActiveXComponent wps = null;
	            try {
	                wps = new ActiveXComponent("wpp.application");
	                ActiveXComponent doc = wps.invokeGetComponent("Presentations").invokeGetComponent("Open", new Variant(wordFile.getAbsolutePath()));
	                doc.invoke("ExportPdf", new Variant(pdfFile.getAbsolutePath()));
	                doc.invoke("Close");
	                doc.safeRelease();
	                log.info("生成pdf文件:"+pdf);
	            } catch (Exception ex) {
	            	log.error(ex);
	                return false;
	            } catch (Error ex) {
	            	log.error(ex);
	                return false;
	            } finally {
	                if (wps != null) {
	                	 wps.safeRelease();
	                     ComThread.Release();
	                }
	            }
	            return true;
	        }
	    }
	  

      public static void testUploadWPS() throws Exception{
    	  
    	  String hdfs_root="/root/testzemi/";
    	  FileSystem fs = FileSystem.get(conf);
    	  uploadFile("d:\\test1.wps",hdfs_root+"test1.wps",fs);
    	  downloadToLocalFile(hdfs_root+"test1.wps","d:\\hdfs\\test1.wps",fs);
	      convert("wps","d:\\hdfs\\test1.wps","d:\\hdfs\\test1.pdf");
	      uploadFile("d:\\hdfs\\test1.pdf",hdfs_root+"test1.pdf",fs);
      }
      
      public static void testUploadDPS() throws Exception{
    	  
    	  String hdfs_root="/root/testzemi/";
    	  FileSystem fs = FileSystem.get(conf);
    	  uploadFile("d:\\test1.dps",hdfs_root+"test1.dps",fs);
    	  downloadToLocalFile(hdfs_root+"test1.dps","d:\\hdfs\\test1.dps",fs);
	      convert("dps","d:\\hdfs\\test1.dps","d:\\hdfs\\test1.pdf");
	      uploadFile("d:\\hdfs\\test1.pdf",hdfs_root+"test1.pdf",fs);
      }
      public static void testUploadET() throws Exception{
    	  
    	  String hdfs_root="/root/testzemi/";
    	  FileSystem fs = FileSystem.get(conf);
    	  uploadFile("d:\\test1.et",hdfs_root+"test1.et",fs);
    	  downloadToLocalFile(hdfs_root+"test1.et","d:\\hdfs\\test1.et",fs);
	      convert("et","d:\\hdfs\\test1.et","d:\\hdfs\\test1.pdf");
	      uploadFile("d:\\hdfs\\test1.pdf",hdfs_root+"test1.pdf",fs);
      }
	    public static void main(String[] args) throws Exception {
	    	//testUploadWPS();
	    	//testUploadET();
	    	testUploadDPS();
	    	//String filePath="/abc/dfds/123";
	    //	System.out.println(filePath.substring(0,filePath.lastIndexOf("/")));
	    	//System.out.println(isCanHandleWps("10158,905f6054-edf8-4585-82e5-19342ea3e3b3,\\root\\txt\\201209\\905f6054-edf8-4585-82e5-19342ea3e3b3\\905f6054-edf8-4585-82e5-19342ea3e3b3.txt,1,admin,2012-09-19,1348028760000,0,部署信息,wps,0,0,0,null,1348028777859"));
	    //    convert("wps","d:\\test1.wps","d:\\test1.pdf");
//	        convert("wps","d:\\test2.wps","d:\\test2.pdf");
//	        convert("wps","d:\\test3.wps","d:\\test3.pdf");
//	        convert("wps","d:\\test4.wps","d:\\test4.pdf");
//	        convert("wps","d:\\test1.et","d:\\test1.pdf");
//	    	System.out.println(System.getProperty("java.library.path"));
	    }
}
