package cn.ydhl.framework.mybatis.mapper;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author Junpeng.Su
 * @date 2016/12/15
 */

public interface ExpandMapper<T> extends SelectPageMapper<T>, Mapper<T> {
}
