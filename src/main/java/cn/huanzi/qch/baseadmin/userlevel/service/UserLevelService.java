package cn.huanzi.qch.baseadmin.userlevel.service;

import cn.huanzi.qch.baseadmin.common.pojo.PageInfo;
import cn.huanzi.qch.baseadmin.common.pojo.Result;
import cn.huanzi.qch.baseadmin.userlevel.dto.UserDTO;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface UserLevelService {
    String getUserLevelResFile(MultipartFile paramMultipartFile) throws IOException;

    List<UserDTO> getUserLevelData(MultipartFile paramMultipartFile);

    List<UserDTO> queryTreeByUserId(String paramString);

    Result<PageInfo<UserDTO>> listByPage(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4);

    String downQueryResult(String paramString1, String paramString2, String paramString3, String paramString4);

    List<UserDTO> uploadData(MultipartFile paramMultipartFile);

    void calcuUserLevel();
}
