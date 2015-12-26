package org.ryu1.utils.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �y�[�W���O�����p��UTIL
 * 
 * @author R.Ishitsuka
 */
public class SimplePagerUtil {
    // ���O
    private Log log = LogFactory.getLog(SimplePagerUtil.class);

    private int pageNum = 1;

    private int pageSize = 10;

    private long totalNum = 0;

    /**
     * @author R.Ishitsuka
     * @return the pageNum
     */
    public int getPageNum() {
        return pageNum;
    }

    /**
     * @author R.Ishitsuka
     * @param pageNum
     *            the pageNum to set
     */
    public void setPageNum(int pageNum) {
        this.pageNum = (pageNum > 0) ? pageNum : 1;
    }

    /**
     * @author R.Ishitsuka
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @author R.Ishitsuka
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = (pageSize > 0) ? pageSize : 10;
    }

    /**
     * @author R.Ishitsuka
     * @return the totalNum
     */
    public long getTotalNum() {
        return totalNum;
    }

    /**
     * @author R.Ishitsuka
     * @param totalNum
     *            the totalNum to set
     */
    public void setTotalNum(long totalNum) {
        this.totalNum = (totalNum > 0) ? totalNum : 0;
    }

    /**
     * @author R.Ishitsuka
     * @return the totalPageNum
     */
    public int getTotalPageNum() {
        return (int) ((totalNum - 1) / pageSize + 1);
    }

    /**
     * �O�̃y�[�W�����邩���f����B
     *
     * @author R.Ishitsuka
     * @return
     */
    public boolean isPrevPage() {
        log.debug("pageNum=" + pageNum);
        return this.pageNum > 1;
    }

    /**
     * ���̃y�[�W�����邩���f����B
     *
     * @author R.Ishitsuka
     * @return
     */
    public boolean isNextPage() {
        log.debug("pageNum=" + pageNum + ", totalPageNum=" + getTotalPageNum());
        return this.pageNum < getTotalPageNum();
    }
}
