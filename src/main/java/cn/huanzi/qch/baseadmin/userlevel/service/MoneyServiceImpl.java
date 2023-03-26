package cn.huanzi.qch.baseadmin.userlevel.service;

import cn.huanzi.qch.baseadmin.common.pojo.PageInfo;
import cn.huanzi.qch.baseadmin.common.pojo.Result;
import cn.huanzi.qch.baseadmin.userlevel.mapper.MoneyMapper;
import cn.huanzi.qch.baseadmin.userlevel.pojo.MoneyRecord;
import cn.huanzi.qch.baseadmin.userlevel.service.MoneyService;
import cn.huanzi.qch.baseadmin.util.ExcelUtil;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class MoneyServiceImpl implements MoneyService {
    private static final Logger log = LoggerFactory.getLogger(cn.huanzi.qch.baseadmin.userlevel.service.MoneyServiceImpl.class);

    @Autowired
    private MoneyMapper moneyMapper;

    public void uploadFile(MultipartFile multipartFile) throws RuntimeException {
        ExecutorService service = null;
        try {
            File file = ExcelUtil.multipartFileToFile(multipartFile);
            FileInputStream fileInputStream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            if (workbook != null) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                int lastRow = sheet.getLastRowNum();
                List<MoneyRecord> moneyRecordList = new ArrayList<>();
                DecimalFormat df = new DecimalFormat("#");
                for (int j = 1; j <= lastRow; j++) {
                    XSSFRow row = sheet.getRow(j);
                    String name = "";
                    if (row.getCell(0) != null)
                        try {
                            Double nameNo = Double.valueOf(row.getCell(0).getNumericCellValue());
                            name = df.format(nameNo);
                        } catch (Exception e) {
                            name = row.getCell(0).toString();
                        }
                    String awardType = (row.getCell(1) != null) ? row.getCell(1).toString().toLowerCase().trim() : "";
                    String coinType = (row.getCell(2) != null) ? row.getCell(2).toString().toLowerCase().trim() : "";
                    String money = (row.getCell(3) != null) ? row.getCell(3).toString().trim() : "";
                    if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(money)) {
                        MoneyRecord moneyRecord = new MoneyRecord();
                        if (name.endsWith(".0"))
                            name = name.replaceAll(".0", "");
                        moneyRecord.setName(name);
                        moneyRecord.setAwardType(awardType);
                        moneyRecord.setCoinType(coinType);
                        moneyRecord.setMoney(new BigDecimal(money));
                        moneyRecordList.add(moneyRecord);
                    }
                }
                if (!CollectionUtils.isEmpty(moneyRecordList))
                    batchSaveAll(moneyRecordList);
            }
        } catch (Exception e) {
            log.error("读取Excel异常", e);
        } finally {
            if (service != null && !service.isShutdown())
                service.shutdown();
        }
    }

    public String queryUserMoney() {
        List<MoneyRecord> moneyRecordList = batchGetAll();
        String filePath = ExcelUtil.writeMoneyExcel(moneyRecordList);
        return filePath;
    }

    public void deleteAll() {
        this.moneyMapper.deleteAll();
    }

    public Object listByPage(int page, int pageSize, String userId, String awardType, String coinType) {
        PageInfo<MoneyRecord> result = new PageInfo();
        List<String> awardTypeList = this.moneyMapper.getAwardTypeList();
        awardTypeList = (List<String>)awardTypeList.stream().filter(e -> !StringUtils.isEmpty(e)).collect(Collectors.toList());
        List<String> coinTypeList = this.moneyMapper.getCoinTypeList();
        coinTypeList = (List<String>)coinTypeList.stream().filter(e -> !StringUtils.isEmpty(e)).collect(Collectors.toList());
        int count = this.moneyMapper.getRecords(userId, awardType, coinType);
        int offset = (page - 1) * pageSize;
        List<MoneyRecord> moneyList = this.moneyMapper.getWithPage(Integer.valueOf(offset), Integer.valueOf(pageSize), userId, awardType, coinType);
        Double totalMoney = this.moneyMapper.getTotalMoney(userId, awardType, coinType);
        result.setPage(page);
        result.setPageSize(pageSize);
        result.setRecords(count);
        result.setRows(moneyList);
        result.setTotal(count);
        result.setAwardTypeList(awardTypeList);
        result.setCoinTypeList(coinTypeList);
        result.setAwardType(awardType);
        result.setCoinType(coinType);
        DecimalFormat df = new DecimalFormat("#,##0.000000");
        if (totalMoney != null)
            result.setSidx(df.format(totalMoney));
        return Result.of(result);
    }

    public String downQueryResult(String userId, String awardType, String coinType) {
        List<MoneyRecord> moneyList = this.moneyMapper.queryMoney(userId, awardType, coinType);
        String filePath = ExcelUtil.writeMoneyExcel(moneyList);
        return filePath;
    }

    public void calcuUserLevel() {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void batchSaveAll(List<MoneyRecord> moneyRecordList) {
        if (!CollectionUtils.isEmpty(moneyRecordList)) {
            int startRow = 0;
            int endRow = 5000;
            List<MoneyRecord> insertMoneyList = new ArrayList<>();
            while (true) {
                endRow = (endRow > moneyRecordList.size()) ? moneyRecordList.size() : endRow;
                insertMoneyList = moneyRecordList.subList(startRow, endRow);
                if (!CollectionUtils.isEmpty(insertMoneyList))
                    this.moneyMapper.saveAll(insertMoneyList);
                if (endRow == moneyRecordList.size())
                    break;
                startRow = endRow;
                endRow += 5000;
            }
        }
    }

    public List<MoneyRecord> batchGetAll() {
        List<MoneyRecord> moneyRecordList = new ArrayList<>();
        int page = 1;
        int pageSize = 5000;
        while (true) {
            int startRow = (page - 1) * pageSize;
            List<MoneyRecord> dbMoneyList = this.moneyMapper.queryMoneyByUser(Integer.valueOf(startRow), Integer.valueOf(pageSize));
            moneyRecordList.addAll(dbMoneyList);
            if (dbMoneyList.size() < pageSize)
                break;
            page++;
        }
        return moneyRecordList;
    }
}
