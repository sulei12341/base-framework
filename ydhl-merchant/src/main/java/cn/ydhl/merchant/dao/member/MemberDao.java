package cn.ydhl.merchant.dao.member;

import cn.ydhl.framework.mybatis.mapper.ExpandMapper;
import cn.ydhl.merchant.domain.member.Member;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Junpeng.Su
 * @create 2017-07-16 上午 12:24
 * @description
 */
public interface MemberDao extends ExpandMapper<Member> {

    @Select("select id from member where name like #{name}")
    List<Long> getMemberIdListByName(String name);
}
