package com.cdl.hadoop.dc.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFamily;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.cdl.hadoop.dc.model.WorkUnit;
import com.cdl.hadoop.dc.model.XNG;
import com.cdl.hadoop.dc.util.DataAccessor;
import com.cdl.hadoop.dc.util.FsDirectory;
import com.cdl.hadoop.dc.util.LibConfiguration;
import com.cdl.hadoop.dc.util.YXFileUtil;
import com.jmupdf.PdfDocument;
import com.sleepycat.je.Transaction;

/**
 * 文件处理类
 * 1.将文件下载到本地
 * 2.将文件转换为PDF
 * 3.将PDF转换为SWF
 * 4.将PDF第一页抽出来作为缩略图
 * 5.转换TXT
 * 6.建立文件第一次索引
 * 7.上传HDFS
 * 8.回调
 * 9.删除临时文件
 * @author root
 *
 */
public class FileHandleService {

	private static final Log log = LogFactory.getLog(FileHandleService.class);
	private Configuration conf = new Configuration();
	private LibConfiguration libconf = new LibConfiguration(conf);
	private String msg; //出错信息
	private DataAccessor da;
	private Transaction txn;
	private String fm; //处理文件元数据
	private String address; //绑定地址
	public FileHandleService(){
		
	}
	
	public static FileHandleService getInstant(){
		return new FileHandleService();
	}
	
	public FileHandleService(DataAccessor da,Transaction txn){
		this.da = da;
		this.txn = txn;
	}
	
	protected void handle(WorkUnit wu){
		fm = wu.getFileMeta();
		int pageNo = 0;
		String fileId = fm.split(",")[0];
		String jobLocalDir = null;
		String filePath = fm.split(",")[2];
		address = wu.getSuccessHost();
		
		try {
			FileSystem fs = FileSystem.get(conf);
			log.info("执行文件处理第一步，任务参数检查------------已经开始处理任务了");
			
			String wait2TransHDFSFile = filePath.replaceAll("\\\\", "\\/");
			jobLocalDir = getLocalJobDir(libconf, wu.getJobId());
			wu.setWorkDir(jobLocalDir);

			int lastDot = wait2TransHDFSFile.lastIndexOf(".");
			int lastSlash = wait2TransHDFSFile.lastIndexOf("/");

			String fext = wait2TransHDFSFile.substring(lastDot);
			String fname = wait2TransHDFSFile.substring(lastSlash + 1, lastDot);
			String fprefix = wait2TransHDFSFile.substring(0, lastDot);
			String tmpDownloadOriginFile = jobLocalDir + fname + fext;

			String transferedHDFSPDFFile = fprefix + XNG.PDF_EXTENSION;
			String transferedHDFSSWFFile = fprefix + XNG.SWF_EXTENSION;
			String transferedHDFSGIFFile = fprefix + XNG.GIF_EXTENSION;
			String transferedHDFSTXTFile = fprefix + XNG.TXT_EXTENSION;

			String tmpOriginFile2PDF = jobLocalDir + fname + XNG.PDF_EXTENSION;
			String tmpOriginFile2SWF = jobLocalDir + fname;
			String tmpOriginFile2PNG = jobLocalDir + fname + XNG.PNG_EXTENSION;
			String tmpOriginFile2TXT = jobLocalDir + fname + XNG.TXT_EXTENSION;
			
			// 下载HDFS文件到本地
			if (wu.getStatus() < XNG.WU_STATUS_FILE_DOWNLOADED) {
				log.info("下载到本地文件:" + tmpDownloadOriginFile);
				fs.copyToLocalFile(false, new Path(wait2TransHDFSFile),
						new Path(tmpDownloadOriginFile));
				wu.setStatus(XNG.WU_STATUS_FILE_DOWNLOADED);
				wu.setCheckPointData(tmpDownloadOriginFile.getBytes());
			}
			// openoffce转换PDF
			if (wu.getStatus() < XNG.WU_STATUS_FILE_TO_PDF) {
				log.info("开始转换PDF:" + tmpDownloadOriginFile);
				if (!new File(tmpDownloadOriginFile).exists()) {
					msg = XNG.FTE_F2P_FNF;
					throw new IOException("转换PDF的文件不存在");
				}
				taskFile2PdfProcess(tmpDownloadOriginFile, tmpOriginFile2PDF,conf);
				wu.setStatus(XNG.WU_STATUS_FILE_TO_PDF);
				wu.setCheckPointData(tmpOriginFile2PDF.getBytes());
			}

			// 缩略图获取检查
			if (wu.getStatus() < XNG.WU_STATUS_PICK_THUMB) {
				log.info("开始转换缩略图:" + tmpOriginFile2PDF);
				if (!new File(tmpOriginFile2PDF).exists()) {
					throw new IOException("转换后的PDF文件不存在");
				}
				pageNo = taskThumbProcess(tmpOriginFile2PDF, tmpOriginFile2PNG,conf);
				wu.setStatus(XNG.WU_STATUS_PICK_THUMB);
				wu.setCheckPointData(tmpOriginFile2PNG.getBytes());
			} else {
				pageNo = getPdfPageNum(tmpOriginFile2PDF);
			}

			// 转换SWF
			log.info("swftools文件转换检查");
			if (wu.getStatus() < XNG.WU_STATUS_PDF_TO_SWF) {
				if (!new File(tmpOriginFile2PDF).exists()) {
					msg = XNG.FTE_P2S_SNF;
					throw new IOException("检查PDF文件地址是否存在");
				}

				task2Pdf2SwfProcess(tmpOriginFile2PDF, tmpOriginFile2SWF,pageNo);
				wu.setStatus(XNG.WU_STATUS_PDF_TO_SWF);
				wu.setCheckPointData(tmpOriginFile2SWF.getBytes());
			}

			log.info("pdf2txt输入检查");
			if (wu.getStatus() < XNG.WU_STATUS_FILE_TO_TXT) {
				if (!fext.toLowerCase().equals(XNG.TXT_EXTENSION)) {
					if (!new File(tmpOriginFile2PDF).exists()) {
						msg = XNG.FTE_P2S_SNF;
						throw new IOException("检查PDF文件地址是否存在");
					}
					taskPdf2TxtProcess(tmpOriginFile2PDF, tmpOriginFile2TXT);
					wu.setStatus(XNG.WU_STATUS_FILE_TO_TXT);
					wu.setCheckPointData(tmpOriginFile2TXT.getBytes());
				}
			}

			// 7.初次建立索引
			if (wu.getStatus() < XNG.WU_STATUS_INDEX_WRITED) {
				taskUploadCheck(tmpOriginFile2SWF, new File(tmpOriginFile2PNG),
						new File(tmpOriginFile2TXT), pageNo);
				synchronized ("indexlocker") {
					// 初次建立索引
					index(conf, tmpOriginFile2TXT, pageNo);
				}
				wu.setStatus(XNG.WU_STATUS_INDEX_WRITED);

			}

			// 8.上传所有文件到HDFS上
			if (wu.getStatus() < XNG.WU_STATUS_FILE_UPLOADED) {
				int totalFileNum = pageNo / XNG.TRAN_PAGE_NUM
						+ (pageNo % XNG.TRAN_PAGE_NUM == 0 ? 0 : 1);

				taskFileUploadProcess(fs, totalFileNum, tmpOriginFile2SWF,
						fprefix, fext, tmpOriginFile2PNG,
						transferedHDFSGIFFile, tmpOriginFile2TXT,
						transferedHDFSTXTFile);

				wu.setStatus(XNG.WU_STATUS_FILE_UPLOADED);
			}
			
		  } catch (Exception e) {
			log.error(e);
		}
	}
	
	/**
	 * 初次建立索引
	 * 
	 * @param conf
	 * @param indexfilePath
	 * @param pageNo
	 * @return
	 */
	private String index(Configuration conf, String indexfilePath, int pageNo) {
		String fileId = null;
		try {
			String fileMeta = fm;
			String fileMetaData[] = fileMeta.split(",");
			fileId = fileMetaData[0]; // 文档ID
			String fileName = fileMetaData[1]; //
			String filePath = fileMetaData[2];
			String uploadBy = fileMetaData[3];
			String uploadDate = fileMetaData[5];
			String fileTitle = fileMetaData[7];
			String fileExtName = fileMetaData[8];

			Document metaDoc = new Document();
			metaDoc.add(new Field("id", fileId, Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			metaDoc.add(new Field("name", fileName, Field.Store.YES,
					Field.Index.ANALYZED,
					Field.TermVector.WITH_POSITIONS_OFFSETS));
			metaDoc.add(new Field("path", filePath, Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			metaDoc.add(new Field("uploadBy", uploadBy, Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			metaDoc.add(new Field("uploadDate", uploadDate, Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			metaDoc.add(new Field("fileTitle", fileTitle, Field.Store.YES,
					Field.Index.ANALYZED,
					Field.TermVector.WITH_POSITIONS_OFFSETS));
			metaDoc.add(new Field("fileExtName", fileExtName, Field.Store.YES,
					Field.Index.NOT_ANALYZED));

			File f = new File(indexfilePath);
			byte[] filecontent = new byte[(int) f.length()];
			FileInputStream fis = new FileInputStream(f);
			fis.read(filecontent);
			String fc = new String(filecontent, "GBK");

			metaDoc.add(new Field("context", fc, Field.Store.YES,
					Field.Index.ANALYZED,
					Field.TermVector.WITH_POSITIONS_OFFSETS));
			writeIndex(conf, fileExtName.toLowerCase(),
					XNG.YXLIB_DOCUMENT_TYPE_META, metaDoc);

		} catch (Exception e) {
			// TODO: handle exception
			msg = XNG.FTE_INDEX_FAILED;
			e.printStackTrace();

		}
		return fileId;
	}
	
	
	/**
	 * 写索引到hdfs文件系统中
	 * 
	 * @param conf
	 * @param fileExt
	 * @param indexType
	 * @param doc
	 */
	synchronized private void writeIndex(Configuration conf, String fileExt,
			int indexType, Document doc) {
		log.info("writeIndex");
		IndexWriter writer = null;
		String indexPathStr = null;
		indexPathStr = XNG.YXLIB_INXEX_MERGE_PATH.replace("{ip}", address);
		Path indexPath = new Path(indexPathStr);
		FileSystem fs = null;
		try {
			fs = FileSystem.get(conf);
			boolean isNewCreate = false;
			if (!fs.exists(indexPath)) {
	       			fs.mkdirs(indexPath);
			}
			FileStatus[] fileStatus = fs.listStatus(indexPath);
			if (fileStatus == null || fileStatus.length == 0) {
				isNewCreate = true;
			}

			// 往索引目录写索引(面试关键点)
			// 1.方案1，根据HDFS原理重写这个类
			// 2.方案2，因为nutch是支持hadoop索引，我们可以直接把nutch提供的FSDirectory类拿过来用
			FsDirectory dir = new FsDirectory(fs, indexPath, false, conf);
			Analyzer luceneAnalyzer = new IKAnalyzer();
			writer = new IndexWriter(dir, luceneAnalyzer, isNewCreate,
					MaxFieldLength.UNLIMITED);
			writer.addDocument(doc, luceneAnalyzer);
			writer.optimize(); // 索引优化，其中主要是索引合并
			writer.close();
			YXFileUtil.deleteIndexTempFiles(libconf);
		} catch (Exception e) {
			msg = XNG.FTE_INDEX_FAILED;
			e.printStackTrace();
		}
		log.info("index finished");
	}
	
	
	private void taskFileUploadProcess(FileSystem fs, int totalFileNum,
			String tmpOriginFile2SWF, String fprefix, String fext,
			String tmpOriginFile2PNG, String transferedHDFSGIFFile,
			String tmpOriginFile2TXT, String transferedHDFSTXTFile)
			throws IOException {
		try {
			System.out.println((fprefix + "_" + "00" + XNG.SWF_EXTENSION)
					+ "    ||   " + transferedHDFSGIFFile + "   ||    "
					+ transferedHDFSTXTFile);
			log.info(" 7.文件上传  Swf  源:tmpOriginFile2SWF:"
					+ (tmpOriginFile2SWF + "_" + "00" + XNG.SWF_EXTENSION)
					+ "   目的路径：transferedHDFSGIFFile ："
					+ (fprefix + "_" + "00" + XNG.SWF_EXTENSION));
			for (int i = 0; i < totalFileNum; i++) {
				fs.copyFromLocalFile(new Path(tmpOriginFile2SWF + "_" + i
						+ XNG.SWF_EXTENSION), new Path(fprefix + "_" + i
						+ XNG.SWF_EXTENSION));
			}

			log.info(" 7.文件上传  PNG  源:tmpOriginFile2PNG:"
							+ tmpOriginFile2PNG
							+ "   目的路径：transferedHDFSGIFFile ："
							+ transferedHDFSGIFFile);
			fs.copyFromLocalFile(new Path(tmpOriginFile2PNG), new Path(
					transferedHDFSGIFFile));
			// 如果文件已为txt类型，则不需要上传。否则与原有txt会发生校验和异常。
			if (!fext.toLowerCase().equals(XNG.TXT_EXTENSION)) {
				log.info(" 7.文件上传  Txt  源:tmpOriginFile2TXT:"
						+ tmpOriginFile2TXT + "   目的路径：transferedHDFSTXTFile ："
						+ transferedHDFSTXTFile);
				fs.copyFromLocalFile(new Path(tmpOriginFile2TXT), new Path(
						transferedHDFSTXTFile));
			}
			if (fs.exists(new Path(transferedHDFSTXTFile))) {
				log.info("----------- 7.  文件转换上传成功   -----------");
			} else {
				log.info("----------- 7.  文件转换上传失败   -----------");
			}
		} catch (Exception e) {
			msg = XNG.FTE_UOLOAD_SNF;
			throw new IOException(e);
		}

	}
	
	
	
	/**
	 * 执行pdftotxt命令
	 * 
	 * @param tmpOriginFile2PDF
	 * @param tmpOriginFile2TXT
	 * @throws IOException
	 */
	private void taskPdf2TxtProcess(String tmpOriginFile2PDF,
			String tmpOriginFile2TXT) throws IOException {
		Process process = null;
		InputStreamReader ir = null;
		try {
			String pdf2txtCmd = "pdftotext -enc GBK  " + tmpOriginFile2PDF
					+ "  " + tmpOriginFile2TXT;
			process = Runtime.getRuntime().exec(pdf2txtCmd);

			ir = new InputStreamReader(process.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			String line = null;
			while ((line = input.readLine()) != null) {
				log.info(line);
			}

			log.info("pdf2txt finished.");
		} catch (Exception e) {
			msg = XNG.FTE_P2T_FAILED;
			e.printStackTrace();
			throw new IOException(e);
		} finally {
			if (ir != null)
				ir.close();
			if (process != null)
				process.destroy();
		}
	}
	/**
	 * 执行pdf2swf命令
	 * 
	 * @param tmpOriginFile2PDF
	 * @param tmpOriginFile2SWF
	 * @throws IOException
	 */
	// TODO liwei@此处考虑用shell脚本实现
	private void task2Pdf2SwfProcess(String tmpOriginFile2PDF,
			String tmpOriginFile2SWF, int pageNum) throws IOException {
		log.info("Swf 转换前路径：" + tmpOriginFile2PDF);

		int totalFileNum = pageNum / XNG.TRAN_PAGE_NUM
				+ (pageNum % XNG.TRAN_PAGE_NUM == 0 ? 0 : 1);

		for (int i = 0, start = 1, end = XNG.TRAN_PAGE_NUM > pageNum ? pageNum
				: XNG.TRAN_PAGE_NUM; i < totalFileNum; i++, start += XNG.TRAN_PAGE_NUM, end = (end + XNG.TRAN_PAGE_NUM) > pageNum ? pageNum
				: (end + XNG.TRAN_PAGE_NUM)) {
			Process process = null;
			InputStreamReader ir = null;
			FileHandleTimeOutDetectionThread timeOutDetector = null;
			try {
				String cmd = "pdf2swf -s storeallcharacters -s poly2bitmap -s languagedir=/usr/tools/xpdf-chinese-simplified -p "
						+ start
						+ "-"
						+ end
						+ " "
						+ tmpOriginFile2PDF
						+ " -f -t -T9 -o "
						+ tmpOriginFile2SWF
						+ "_"
						+ i
						+ XNG.SWF_EXTENSION;

				process = Runtime.getRuntime().exec(cmd);
				InputStream is = process.getInputStream();
				ir = new InputStreamReader(is);
				LineNumberReader input = new LineNumberReader(ir);

				// 超时直接抛IO异常
				// timeOutDetector = new
				// FileHandleTimeOutDetectionThread(is,System.currentTimeMillis(),conf.getLong("yxlib.dcdaemon.pdf2swf.timeout",
				// 3*60)*60*1000);
				Thread t = new Thread(timeOutDetector);
				t.start();

				String line = null;
				while ((line = input.readLine()) != null) {
					log.info(line);
				}

				log.info("pdf2swf finish " + (i + 1) + " file.");

			} catch (Exception e) {
				msg = XNG.FTE_P2S_FAILED;
				e.printStackTrace();
				throw new IOException(e);
			} finally {
				// timeOutDetector.exit();
				if (ir != null)
					ir.close();
				if (process != null)
					process.destroy();
			}
		}
		log.info("swf 文件转换后路径 ：" + tmpOriginFile2SWF);
		log.info("pdf2swf finished.");
	}


	/**
	 * 获取PDF文件总页数
	 * 
	 * @param tmpOriginFile2PDF
	 *            文件路径
	 * @return 总页数
	 * @throws Exception
	 */
	private int getPdfPageNum(String tmpOriginFile2PDF) throws IOException {
		int pages = 0;
		try {
			PdfDocument pdfDoc = null;
			pdfDoc = new PdfDocument(tmpOriginFile2PDF, null);
			pages = pdfDoc.getPageCount();
			return pages;
		} catch (Exception e) {
			msg = XNG.FTE_THUMB_FAILED;
			e.printStackTrace();
			throw new IOException(e);
		}
	}
	
	/**
	 * 取第一页作缩略图
	 * 
	 * @param tmpOriginFile2PDF
	 * @param tmpOriginFile2GIF
	 * @param conf
	 * @return
	 * @throws IOException
	 */
	private int taskThumbProcess(String tmpOriginFile2PDF,
			String tmpOriginFile2PNG, Configuration conf) throws IOException {

		int pages = 0;
		int height = conf.getInt("yxlib.thumb.height", 156);
		int width = conf.getInt("yxlib.thumb.width", 120);
		// Open document&nbsp;
		PdfDocument pdfDoc = null;
		try {
			pdfDoc = new PdfDocument(tmpOriginFile2PDF, null);
			float zoom = Math.max((float) height
					/ pdfDoc.getPage(1).getHeight(), (float) width
					/ pdfDoc.getPage(1).getWidth());
			if (zoom == 0)
				zoom = 0.2f;
			if (pdfDoc.writePng(1, tmpOriginFile2PNG, zoom,
							PdfDocument.IMAGE_SAVE_NO_ALPHA,
							PdfDocument.IMAGE_TYPE_RGB)) {
				log.info("pdf pick up thumb finished.");
			} else {
				log.info("pdf pick up thumb failed.");
			}
			pages = pdfDoc.getPageCount();
		} catch (Exception e) {
			msg = XNG.FTE_THUMB_FAILED;
			e.printStackTrace();
			throw new IOException(e);
		} finally {
			if (pdfDoc != null)
				pdfDoc.dispose();
		}

		return pages;
	}
	
	
	private void taskUploadCheck(String tmpOriginFile2SWF,
			File tmpOriginFile2GIF, File tmpOriginFile2TXT, int pageNum)
			throws IOException {
		boolean swfExit = true;

		int totalFileNum = pageNum / XNG.TRAN_PAGE_NUM
				+ (pageNum % XNG.TRAN_PAGE_NUM == 0 ? 0 : 1);

		// 判断所有子文件是否都存在
		for (int i = 0; i < totalFileNum; i++) {
			if (!new File(tmpOriginFile2SWF + "_" + i + XNG.SWF_EXTENSION)
					.exists()) {
				swfExit = false;
				break;
			}
		}
		if (!swfExit || !tmpOriginFile2GIF.exists()
				|| !tmpOriginFile2TXT.exists()) {
			msg = XNG.FTE_UOLOAD_SNF;
			throw new IOException(
					"task step of upload precheck failed,swf or gif or txt file not found.");
		}
	}
	
	/**
	 * 调用openoffice进行转换
	 * 
	 * @param tmpDownloadOriginFile
	 *            下载而来的需要转换的原始文件
	 * @param tmpOriginFile2PDF
	 *            转换中产生的pdf文件
	 * @param conf
	 *            用来获取openoffice监听端口
	 * @throws ConnectException
	 */
	private void taskFile2PdfProcess(String tmpDownloadOriginFile,
			String tmpOriginFile2PDF, Configuration conf) throws IOException {
		try {
			DocumentFormat txt = null;
			if (tmpDownloadOriginFile.endsWith("txt")) {
				boolean isUTF8 = isTxtEncodedUTF8(tmpDownloadOriginFile);
				String encode = isUTF8 ? "UTF8" : "GBK";
				log.info(tmpDownloadOriginFile + " was encoded " + encode);
				txt = new DocumentFormat("Plain Text", DocumentFamily.TEXT,
						"text/plain", "txt");
				txt.setImportOption("FilterName", "Text (encoded)");
				txt.setImportOption("FilterOptions", encode + ",CRLF");
				txt.setExportFilter(DocumentFamily.TEXT, "Text (encoded)");
				txt.setExportOption(DocumentFamily.TEXT, "FilterOptions",
						encode + ",CRLF");
			}

			int openofficePort = conf.getInt("yxlib.openoffice.port",XNG.YXLIB_OPENOFFICE_PORT);
			OpenOfficeConnection connection = new SocketOpenOfficeConnection(openofficePort);
			connection.connect();
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			log.info("   // 文件待转换路径：" + tmpDownloadOriginFile);
			converter.convert(new File(tmpDownloadOriginFile), txt, new File(tmpOriginFile2PDF), null);
			connection.disconnect();
			log.info("   // 文件转换后路径：" + tmpOriginFile2PDF);
			log.info("file2pdf finished.");
		} catch (Exception e) {
			msg = XNG.FTE_F2P_FAILED;
			e.printStackTrace();
			throw new IOException(e);
		}

	}

	/**
	 * 如果报找不到/usr/bin/enca这样的错误，就把这个方法注释掉
	 * 
	 * @param txtFilePath
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private boolean isTxtEncodedUTF8(String txtFilePath)
			throws InterruptedException, IOException {
		try {
			String jpsCmd = "/usr/bin/enca " + txtFilePath;
			StringBuffer jpsBuffer = new StringBuffer();
			Process process = Runtime.getRuntime().exec(jpsCmd);
			InputStreamReader ir = new InputStreamReader(process
					.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			String line = null;
			while ((line = input.readLine()) != null) {
				jpsBuffer.append(line);
			}
			return jpsBuffer.indexOf("UTF-8") != -1;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	//得到任务临时文件夹
	private String getLocalJobDir(LibConfiguration conf,String jobid){
		String cachedir = conf.getWorkDir()+jobid+"/";
		File f = new File(cachedir);
		if(f.exists()){
			f.delete();
		}
		f.mkdir();
		return f.toString()+"/";
	}
}
