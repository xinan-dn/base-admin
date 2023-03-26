package cn.huanzi.qch.baseadmin.userlevel.service;

import org.springframework.web.multipart.MultipartFile;

public interface MoneyService {
    void uploadFile(MultipartFile paramMultipartFile) throws RuntimeException;

    String queryUserMoney();

    void deleteAll();

    Object listByPage(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3);

    String downQueryResult(String paramString1, String paramString2, String paramString3);

    void calcuUserLevel();
}
