package cn.huanzi.qch.baseadmin.userlevel.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BankCardMapper {
    List<Map<String, String>> queryByPrefix(@Param("list") List<String> paramList);
}
