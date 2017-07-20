package cn.ydhl.framework.mybatis.Page;

import java.io.Serializable;
import java.util.List;

/**
 * @author Junpeng.Su
 * @create 2017-07-15 下午 11:56
 * @description
 */
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 8656597559014685635L;

    private List<T> rows;    //结果集

    private long total;        //总记录数

    public PageResult(List<T> rows, long total) {
        this.rows = rows;
        this.total = total;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
