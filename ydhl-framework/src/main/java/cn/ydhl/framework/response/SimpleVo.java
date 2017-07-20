package cn.ydhl.framework.response;

import java.io.Serializable;


public class SimpleVo implements Serializable {
    private static final long serialVersionUID = -4139646494152202139L;

    /**
     * 返回结果
     */
    private Object result;

    public SimpleVo(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
