package cn.ydhl.merchant.helper.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Junpeng.Su
 * @create 2017-07-14 下午 5:37
 * @description
 */
public class BaseDto<PK extends Serializable> implements Serializable {

    private static final long serialVersionUID = -3761675344684204302L;

    private PK id;

    private Date createTime;

    private Date updateTime;

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
