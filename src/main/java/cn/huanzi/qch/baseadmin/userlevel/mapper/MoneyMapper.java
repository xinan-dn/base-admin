package cn.huanzi.qch.baseadmin.userlevel.mapper;

import cn.huanzi.qch.baseadmin.userlevel.pojo.MoneyRecord;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MoneyMapper {
    void saveAll(@Param("list") List<MoneyRecord> paramList);

    void deleteAll();

    List<MoneyRecord> queryMoneyByUser(@Param("startRow") Integer paramInteger1, @Param("pageSize") Integer paramInteger2);

    List<MoneyRecord> getWithPage(@Param("offset") Integer paramInteger1, @Param("pageSize") Integer paramInteger2, @Param("userId") String paramString1, @Param("awardType") String paramString2, @Param("coinType") String paramString3);

    int getRecords(@Param("userId") String paramString1, @Param("awardType") String paramString2, @Param("coinType") String paramString3);

    Double getTotalMoney(@Param("userId") String paramString1, @Param("awardType") String paramString2, @Param("coinType") String paramString3);

    List<MoneyRecord> queryMoney(@Param("userId") String paramString1, @Param("awardType") String paramString2, @Param("coinType") String paramString3);

    List<String> getAwardTypeList();

    List<String> getCoinTypeList();
}
