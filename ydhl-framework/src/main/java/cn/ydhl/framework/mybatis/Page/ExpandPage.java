package cn.ydhl.framework.mybatis.Page;

import cn.ydhl.framework.utils.BeanUtilPlus;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author Junpeng.Su
 * @date 2016/12/16
 */
public class ExpandPage<E> extends Page<E> {

    public List setContentList(Class entityClass) {
        List<E> result = super.getResult();
        return  BeanUtilPlus.copyAs(result, entityClass);
    }


}
