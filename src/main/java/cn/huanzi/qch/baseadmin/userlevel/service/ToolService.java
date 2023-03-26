package cn.huanzi.qch.baseadmin.userlevel.service;

public interface ToolService {
    Object addressQuery(String paramString1, String paramString2) throws Exception;

    Object phoneQuery(String paramString);

    Object idcardQuery(String paramString);

    Object bankcardQuery(String paramString);

    Object listByPage(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3);

    void deleteDupAddress();
}
