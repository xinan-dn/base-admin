package cn.huanzi.qch.baseadmin.userlevel.mapper;

import cn.huanzi.qch.baseadmin.userlevel.pojo.Address;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AddressMapper {
    void saveAll(@Param("list") List<Address> paramList);

    List<Address> findByAddress(@Param("chain") String paramString, @Param("list") List<String> paramList);

    void deleteByAddress(@Param("chain") String paramString, @Param("list") List<String> paramList);

    int getRecords(@Param("chain") String paramString1, @Param("address") String paramString2, @Param("label") String paramString3);

    List<Address> getWithPage(@Param("offset") Integer paramInteger1, @Param("pageSize") Integer paramInteger2, @Param("chain") String paramString1, @Param("address") String paramString2, @Param("label") String paramString3);

    void deleteLabelNull();

    void deleteDupAddress();
}
