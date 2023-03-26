package cn.huanzi.qch.baseadmin.userlevel.mapper;

import cn.huanzi.qch.baseadmin.userlevel.dto.UserDTO;
import cn.huanzi.qch.baseadmin.userlevel.pojo.UserInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {
    List<UserInfo> findAll(@Param("startRow") Integer paramInteger1, @Param("pageSize") Integer paramInteger2);

    void saveAll(@Param("list") List<UserInfo> paramList);

    void deleteAll();

    List<UserInfo> getWithPage(@Param("offset") Integer paramInteger1, @Param("pageSize") Integer paramInteger2, @Param("userId") String paramString1, @Param("fzLevelStart") Integer paramInteger3, @Param("fzLevelEnd") Integer paramInteger4, @Param("fzNumStart") Integer paramInteger5, @Param("fzNumEnd") Integer paramInteger6, @Param("remark") String paramString2);

    int getRecords(@Param("userId") String paramString1, @Param("fzLevelStart") Integer paramInteger1, @Param("fzLevelEnd") Integer paramInteger2, @Param("fzNumStart") Integer paramInteger3, @Param("fzNumEnd") Integer paramInteger4, @Param("remark") String paramString2);

    List<UserInfo> queryUserLevel(@Param("userId") String paramString1, @Param("fzLevelStart") Integer paramInteger1, @Param("fzLevelEnd") Integer paramInteger2, @Param("fzNumStart") Integer paramInteger3, @Param("fzNumEnd") Integer paramInteger4, @Param("remark") String paramString2);

    void saveAllUserMoney(@Param("list") List<UserDTO> paramList);

    List<UserDTO> findAllUserMoney(@Param("startRow") Integer paramInteger1, @Param("pageSize") Integer paramInteger2);

    void deleteAllUserMoney();
}
