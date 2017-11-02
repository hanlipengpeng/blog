package com.cdl.entity;

/**
 * 鏍戣彍鍗曡妭鐐规帴鍙�? */
public interface INode
{
    /**
     * 鑺傜偣鍚嶇�?
     * @return
     */
    public String getNodeName();
    /**
     * 鑺傜偣id
     * @return
     */
    public String getNodeId();
    /**
     * 鑺傜偣鐖秈d
     * @return
     */
    public String getNodeParentId();
    /**
     * 鏄惁鍙跺瓙
     * @return
     */
    public boolean isLeaf();
    /**
     * 鍥炬爣璺緞
     * @return
     */
    public String getCls();
    /**
     * 閾炬帴璺緞
     * @return
     */
    public String getUrl();
    /**
     * 鑺傜偣绛夌骇
     */
    public Integer getNodeLevel();
    /**
     * 鏄惁鏍硅妭鐐�
     * @return
     */
    public boolean isRoot();
    /**
     * 鏄惁閫変腑
     * @return
     */
    public boolean isChecked();
    /**
     * 鏄惁鍙互閫夋�?
     * @return
     */
    public boolean isChkable();
}