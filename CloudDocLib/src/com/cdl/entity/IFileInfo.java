package com.cdl.entity;
import java.util.Date;

public interface IFileInfo
{
    /**
     * 鑾峰彇鏂囦欢id
     * @return
     */
    public String getId();
    
    /**
     * 鑾峰彇鐪熷疄鏂囦欢鍚�?     * @return
     */
    public String getFileRealName();
    
    /**
     * 鑾峰彇鏂囦欢鍚�
     * @return
     */
    public String getFileName();
    
    /**
     * 鑾峰彇鏂囦欢鎵╁睍鍚�?     * @return
     */
    public String getFileExtName();
    
    /**
     * 鑾峰彇鏂囦欢璺�?
     * @return
     */
    public String getFilePath();
    
    /**
     * 鑾峰彇鏂囦欢澶у皬
     * @return
     */
    public Long getFileSize();
    
    /**
     * 鑾峰彇鏂囦欢涓婁紶鏃堕棿
     * @return
     */
    public String getFileUploadDate();
    
    public void setId(String id);
    
}
