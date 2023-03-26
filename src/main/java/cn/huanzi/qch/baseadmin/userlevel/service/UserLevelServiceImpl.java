package cn.huanzi.qch.baseadmin.userlevel.service;

import cn.huanzi.qch.baseadmin.common.pojo.PageInfo;
import cn.huanzi.qch.baseadmin.common.pojo.Result;
import cn.huanzi.qch.baseadmin.userlevel.dto.UserDTO;
import cn.huanzi.qch.baseadmin.userlevel.mapper.UserInfoMapper;
import cn.huanzi.qch.baseadmin.userlevel.pojo.UserInfo;
import cn.huanzi.qch.baseadmin.userlevel.service.UserLevelService;
import cn.huanzi.qch.baseadmin.util.ExcelUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserLevelServiceImpl implements UserLevelService {
    private static final Logger log = LoggerFactory.getLogger(cn.huanzi.qch.baseadmin.userlevel.service.UserLevelServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    public String getUserLevelResFile(MultipartFile multipartFile) throws IOException {
        List<UserDTO> userDTOList = getUserByFile(multipartFile);
        List<UserDTO> treeList = deal4TreeNode(userDTOList, null, true);
        String excelPath = dealUserLevel4File(treeList);
        return excelPath;
    }

    public List<UserDTO> getUserLevelData(MultipartFile file) {
        List<UserDTO> userDTOList = getUserByFile(file);
        List<UserDTO> treeList = deal4TreeNode(userDTOList, null, true);
        List<String> userIdList = new ArrayList<>();
        List<UserDTO> resUserList = new ArrayList<>();
        for (UserDTO userDTO : treeList) {
            if (!userIdList.contains(userDTO.getName()))
                resUserList.add(userDTO);
            userIdList.add(userDTO.getName());
        }
        this.userInfoMapper.deleteAll();
        if (!CollectionUtils.isEmpty(resUserList)) {
            List<UserDTO> allUserList = new ArrayList<>();
            for (UserDTO userDTO : resUserList)
                forEach(allUserList, userDTO);
            List<UserInfo> userInfoList = new ArrayList<>();
            for (UserDTO userDTO : allUserList) {
                UserInfo userInfo = new UserInfo();
                BeanUtils.copyProperties(userDTO, userInfo);
                userInfoList.add(userInfo);
            }
            batchSaveAll(userInfoList);
        }
        for (UserDTO userDTO : resUserList) {
            BigDecimal totalMoney = userDTO.getTotalMoney();
            totalMoney = (totalMoney == null) ? BigDecimal.ZERO : totalMoney;
            String desc = userDTO.getName() + "(层级" + userDTO.getLevel() + "发展层数"+ userDTO.getFzLevel() + "人数"+ userDTO.getChildrenNum() + "下级金额"+ totalMoney.toString() + ")";
            userDTO.setName(desc);
            if (!CollectionUtils.isEmpty(userDTO.getChildren()))
                for (UserDTO child : userDTO.getChildren())
                    forEachDesc(child);
        }
        UserDTO topUser = new UserDTO("点击展开关系图（虚拟节点非数据）", "", BigDecimal.ZERO);
                topUser.setChildren(resUserList);
        return Arrays.asList(new UserDTO[] { topUser });
    }

    public List<UserDTO> queryTreeByUserId(String userId) {
        List<UserDTO> resUserList = null;
        List<UserInfo> userInfoList = batchGetAll();
        if (!CollectionUtils.isEmpty(userInfoList)) {
            List<UserDTO> allUserDTOList = new ArrayList<>();
            for (UserInfo userInfo : userInfoList) {
                UserDTO userDTO = new UserDTO();
                BeanUtils.copyProperties(userInfo, userDTO);
                allUserDTOList.add(userDTO);
            }
            List<UserDTO> treeList = deal4TreeNode(allUserDTOList, userId, false);
            if (!CollectionUtils.isEmpty(treeList)) {
                List<String> userIdList = new ArrayList<>();
                resUserList = new ArrayList<>();
                for (UserDTO userDTO : treeList) {
                    if (!userIdList.contains(userDTO.getName()))
                        resUserList.add(userDTO);
                    userIdList.add(userDTO.getName());
                }
                for (UserDTO userDTO : resUserList) {
                    BigDecimal totalMoney = userDTO.getTotalMoney();
                    totalMoney = (totalMoney == null) ? BigDecimal.ZERO : totalMoney;
                    String desc = userDTO.getName() + "(层级"+ userDTO.getLevel() + "发展层数"+ userDTO.getFzLevel() + "人数"+ userDTO.getChildrenNum() + "下级金额"+ totalMoney.toString() + ")";
                    userDTO.setName(desc);
                    if (!CollectionUtils.isEmpty(userDTO.getChildren()))
                        for (UserDTO child : userDTO.getChildren())
                            forEachDesc(child);
                }
            }
        }
        return resUserList;
    }

    public Result<PageInfo<UserDTO>> listByPage(int page, int pageSize, String userId, String fzLevel, String fzNum, String remark) {
        PageInfo<UserDTO> result = new PageInfo();
        Integer fzLevelStart = null;
        Integer fzLevelEnd = null;
        Integer fzNumStart = null;
        Integer fzNumEnd = null;
        if (!StringUtils.isEmpty(fzLevel) && fzLevel.contains("-")) {
            String[] fzLevels = fzLevel.split("-");
            fzLevelStart = Integer.valueOf(Integer.parseInt(fzLevels[0]));
            if (fzLevels.length > 1)
                fzLevelEnd = Integer.valueOf(Integer.parseInt(fzLevels[1]));
        }
        if (!StringUtils.isEmpty(fzNum) && fzNum.contains("-")) {
            String[] fzNums = fzNum.split("-");
            fzNumStart = Integer.valueOf(Integer.parseInt(fzNums[0]));
            if (fzNums.length > 1)
                fzNumEnd = Integer.valueOf(Integer.parseInt(fzNums[1]));
        }
        int count = this.userInfoMapper.getRecords(userId, fzLevelStart, fzLevelEnd, fzNumStart, fzNumEnd, remark);
        int offset = (page - 1) * pageSize;
        List<UserInfo> userInfoList = this.userInfoMapper.getWithPage(Integer.valueOf(offset), Integer.valueOf(pageSize), userId, fzLevelStart, fzLevelEnd, fzNumStart, fzNumEnd, remark);
        List<UserDTO> allUserDTOList = new ArrayList<>();
        for (UserInfo userInfo : userInfoList) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(userInfo, userDTO);
            userDTO.setName(userInfo.getName());
            userDTO.setParent(userInfo.getParent());
            allUserDTOList.add(userDTO);
        }
        result.setPage(page);
        result.setPageSize(pageSize);
        result.setRecords(count);
        result.setRows(allUserDTOList);
        result.setTotal(count);
        return Result.of(result);
    }

    public String downQueryResult(String userId, String fzLevel, String fzNum, String remark) {
        Integer fzLevelStart = null;
        Integer fzLevelEnd = null;
        Integer fzNumStart = null;
        Integer fzNumEnd = null;
        if (!StringUtils.isEmpty(fzLevel) && fzLevel.contains("-")) {
            String[] fzLevels = fzLevel.split("-");
            fzLevelStart = Integer.valueOf(Integer.parseInt(fzLevels[0]));
            if (fzLevels.length > 1)
                fzLevelEnd = Integer.valueOf(Integer.parseInt(fzLevels[1]));
        }
        if (!StringUtils.isEmpty(fzNum) && fzNum.contains("-")) {
            String[] fzNums = fzNum.split("-");
            fzNumStart = Integer.valueOf(Integer.parseInt(fzNums[0]));
            if (fzNums.length > 1)
                fzNumEnd = Integer.valueOf(Integer.parseInt(fzNums[1]));
        }
        List<UserInfo> userInfoList = this.userInfoMapper.queryUserLevel(userId, fzLevelStart, fzLevelEnd, fzNumStart, fzNumEnd, remark);
        List<UserDTO> allUserDTOList = new ArrayList<>();
        for (UserInfo userInfo : userInfoList) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(userInfo, userDTO);
            allUserDTOList.add(userDTO);
        }
        String excelPath = dealUserLevel4File(allUserDTOList);
        return excelPath;
    }

    public List<UserDTO> uploadData(MultipartFile file) {
        List<UserDTO> userDTOList = getUserByFile(file);
        if (!CollectionUtils.isEmpty(userDTOList))
            batchSaveAllUserMoney(userDTOList);
        return null;
    }

    public void calcuUserLevel() {
        List<UserDTO> userDTOList = batchGetAllUserMoney();
        List<UserDTO> treeList = deal4TreeNode(userDTOList, null, true);
        List<String> userIdList = new ArrayList<>();
        List<UserDTO> resUserList = new ArrayList<>();
        for (UserDTO userDTO : treeList) {
            if (!userIdList.contains(userDTO.getName()))
                resUserList.add(userDTO);
            userIdList.add(userDTO.getName());
        }
        this.userInfoMapper.deleteAll();
        if (!CollectionUtils.isEmpty(resUserList)) {
            List<UserDTO> allUserList = new ArrayList<>();
            for (UserDTO userDTO : resUserList)
                forEach(allUserList, userDTO);
            List<UserInfo> userInfoList = new ArrayList<>();
            for (UserDTO userDTO : allUserList) {
                UserInfo userInfo = new UserInfo();
                BeanUtils.copyProperties(userDTO, userInfo);
                userInfoList.add(userInfo);
            }
            batchSaveAll(userInfoList);
        }
    }

    private List<UserDTO> batchGetAllUserMoney() {
        List<UserDTO> userInfoList = new ArrayList<>();
        int page = 1;
        int pageSize = 5000;
        while (true) {
            int startRow = (page - 1) * pageSize;
            List<UserDTO> dbUserList = this.userInfoMapper.findAllUserMoney(Integer.valueOf(startRow), Integer.valueOf(pageSize));
            userInfoList.addAll(dbUserList);
            if (dbUserList.size() < pageSize)
                break;
            page++;
        }
        return userInfoList;
    }

    private List<UserDTO> getUserByFile(MultipartFile multipartFile) {
        List<UserDTO> userDTOList = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#");
        try {
            File file = ExcelUtil.multipartFileToFile(multipartFile);
            FileInputStream fileInputStream = new FileInputStream(file);
            XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = sheets.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();
            for (int i = 1; i <= rowNum; i++) {
                XSSFRow row = sheet.getRow(i);
                XSSFCell cell1 = row.getCell(0);
                XSSFCell cell2 = row.getCell(1);
                XSSFCell cell3 = row.getCell(2);
                XSSFCell cell4 = row.getCell(3);
                if (cell1 != null && cell2 != null) {
                    String userId = null;
                    String parentId = null;
                    try {
                        Double userIdNo = Double.valueOf(cell1.getNumericCellValue());
                        userId = df.format(userIdNo);
                    } catch (Exception e) {
                        userId = cell1.getStringCellValue();
                    }
                    try {
                        Double parentNo = Double.valueOf(cell2.getNumericCellValue());
                        parentId = df.format(parentNo);
                    } catch (Exception e) {
                        parentId = cell2.getStringCellValue();
                    }
                    userId = userId.trim().toLowerCase();
                    parentId = parentId.trim().toLowerCase();
                    BigDecimal moneyBig = (cell3 == null) ? BigDecimal.ZERO : new BigDecimal(cell3.toString());
                    String remark = (cell4 == null) ? "" : cell4.getStringCellValue();
                    if (!StringUtils.isEmpty(userId)) {
                        UserDTO userDTO = new UserDTO(userId, parentId, moneyBig);
                        userDTO.setRemark(remark);
                        userDTOList.add(userDTO);
                    }
                }
            }
        } catch (Exception e) {
            log.error("读取Excel异常", e);
        }
        return userDTOList;
    }

    private List<UserDTO> deal4TreeNode(List<UserDTO> userDTOList, String userId, boolean ifForEach) {
        List<UserDTO> treeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userDTOList)) {
            Map<String, List<UserDTO>> byParentIdMap = (Map<String, List<UserDTO>>)userDTOList.stream().collect(Collectors.groupingBy(e -> e.getParent()));
            List<UserDTO> topUserList = (List<UserDTO>)userDTOList.stream().filter(e -> (!StringUtils.isEmpty(e.getParent()) && "最顶级".equals(e.getParent().trim()))).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(topUserList))
                log.error("没有最顶级的用户！");
            for (UserDTO topUser : topUserList)
                forEach(byParentIdMap, topUser);
            if (ifForEach) {
                for (UserDTO userDTO : topUserList)
                    forEach(userDTO, 0, "");
                for (UserDTO userDTO : topUserList)
                    forEach(userDTO);
                for (UserDTO userDTO : topUserList) {
                    int maxLevel = forEachFzLevel(userDTO);
                    int fzLevel = maxLevel - userDTO.getLevel().intValue();
                    userDTO.setFzLevel(Integer.valueOf(fzLevel));
                }
            }
            treeList = new ArrayList<>();
            if (!StringUtils.isEmpty(userId)) {
                for (UserDTO userDTO : topUserList) {
                    if (userId.trim().toLowerCase().equals(userDTO.getName())) {
                        treeList = Arrays.asList(new UserDTO[] { userDTO });
                        break;
                    }
                    treeList = forEachFindTree(userDTO, userId);
                    if (treeList != null && treeList.size() > 0)
                        break;
                }
            } else {
                treeList = topUserList;
            }
        }
        return treeList;
    }

    private String dealUserLevel4File(List<UserDTO> treeList) {
        List<UserDTO> allUserList = new ArrayList<>();
        for (UserDTO userDTO : treeList)
            forEach(allUserList, userDTO);
        List<String> userIdList = new ArrayList<>();
        List<UserDTO> resUserList = new ArrayList<>();
        for (UserDTO userDTO : allUserList) {
            if (!userIdList.contains(userDTO.getName()))
                resUserList.add(userDTO);
            userIdList.add(userDTO.getName());
        }
        resUserList = (List<UserDTO>)resUserList.stream().sorted(Comparator.comparing(UserDTO::getParentLongId)).collect(Collectors.toList());
        String excelPath = ExcelUtil.writeExcel(resUserList);
        return excelPath;
    }

    private static void forEach(List<UserDTO> allUserList, UserDTO userDTO) {
        allUserList.add(userDTO);
        if (!CollectionUtils.isEmpty(userDTO.getChildren()))
            for (UserDTO child : userDTO.getChildren())
                forEach(allUserList, child);
    }

    private static List<UserDTO> forEachFindTree(UserDTO userDTO, String userId) {
        List<UserDTO> treeUserList = new ArrayList<>();
        if (userId.trim().toLowerCase().equals(userDTO.getName()))
            return Arrays.asList(new UserDTO[] { userDTO });
        if (!CollectionUtils.isEmpty(userDTO.getChildren()))
            for (UserDTO child : userDTO.getChildren()) {
                treeUserList = forEachFindTree(child, userId);
                if (treeUserList != null && treeUserList.size() > 0)
                    break;
            }
        return treeUserList;
    }

    private static void forEachDesc(UserDTO userDTO) {
        BigDecimal totalMoney = userDTO.getTotalMoney();
        totalMoney = (totalMoney == null) ? BigDecimal.ZERO : totalMoney;
        String desc = userDTO.getName() + "(层级"+ userDTO.getLevel() + "发展层数"+ userDTO.getFzLevel() + "人数"+ userDTO.getChildrenNum() + "下级金额"+ totalMoney.toString() + ")";
        userDTO.setName(desc);
        if (!CollectionUtils.isEmpty(userDTO.getChildren()))
            for (UserDTO child : userDTO.getChildren())
                forEachDesc(child);
    }

    private static int forEachFzLevel(UserDTO userDTO) {
        int fzLevel = userDTO.getLevel().intValue();
        if (!CollectionUtils.isEmpty(userDTO.getChildren()))
            for (UserDTO child : userDTO.getChildren()) {
                int maxLevel = forEachFzLevel(child);
                if (maxLevel > fzLevel)
                    fzLevel = maxLevel;
            }
        userDTO.setFzLevel(Integer.valueOf(fzLevel - userDTO.getLevel().intValue()));
        return fzLevel;
    }

    private static void forEach(UserDTO userDTO, int level, String parentLongId) {
        String userId = userDTO.getName();
        parentLongId = parentLongId + "->" + userId;
        level++;
        userDTO.setParentLongId(parentLongId);
        userDTO.setLevel(Integer.valueOf(level));
        List<UserDTO> children = userDTO.getChildren();
        if (!CollectionUtils.isEmpty(children))
            for (UserDTO child : children)
                forEach(child, level, parentLongId);
    }

    private static void forEach(UserDTO userDTO) {
        List<UserDTO> children = userDTO.getChildren();
        int childrenNum = 0;
        BigDecimal totalMoney = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(children)) {
            childrenNum = children.size();
            for (UserDTO child : children) {
                forEach(child);
                int childNum = child.getChildrenNum().intValue();
                childrenNum += childNum;
                BigDecimal childBig = (child.getTotalMoney() == null) ? BigDecimal.ZERO : child.getTotalMoney();
                BigDecimal money = (child.getMoney() == null) ? BigDecimal.ZERO : child.getMoney();
                totalMoney = totalMoney.add(childBig).add(money);
            }
        }
        userDTO.setTotalMoney(totalMoney);
        userDTO.setChildrenNum(Integer.valueOf(childrenNum));
    }

    private static void forEach(Map<String, List<UserDTO>> byParentIdMap, UserDTO userDTO) {
        List<UserDTO> treeMenuNodes = byParentIdMap.get(userDTO.getName());
        if (!CollectionUtils.isEmpty(treeMenuNodes)) {
            treeMenuNodes.stream().sorted(Comparator.comparing(UserDTO::getName)).collect(Collectors.toList());
            userDTO.setChildren(treeMenuNodes);
            userDTO.getChildren().forEach(t -> forEach(byParentIdMap, t));
        }
    }

    public void batchSaveAll(List<UserInfo> userInfoList) {
        if (!CollectionUtils.isEmpty(userInfoList)) {
            int startRow = 0;
            int endRow = 5000;
            List<UserInfo> insertUserList = new ArrayList<>();
            while (true) {
                endRow = (endRow > userInfoList.size()) ? userInfoList.size() : endRow;
                insertUserList = userInfoList.subList(startRow, endRow);
                if (!CollectionUtils.isEmpty(insertUserList))
                    this.userInfoMapper.saveAll(insertUserList);
                if (endRow == userInfoList.size())
                    break;
                startRow = endRow;
                endRow += 5000;
            }
        }
    }

    public void batchSaveAllUserMoney(List<UserDTO> userInfoList) {
        if (!CollectionUtils.isEmpty(userInfoList)) {
            this.userInfoMapper.deleteAllUserMoney();
            int startRow = 0;
            int endRow = 5000;
            List<UserDTO> insertUserList = new ArrayList<>();
            while (true) {
                endRow = (endRow > userInfoList.size()) ? userInfoList.size() : endRow;
                insertUserList = userInfoList.subList(startRow, endRow);
                if (!CollectionUtils.isEmpty(insertUserList))
                    this.userInfoMapper.saveAllUserMoney(insertUserList);
                if (endRow == userInfoList.size())
                    break;
                startRow = endRow;
                endRow += 5000;
            }
        }
    }

    public List<UserInfo> batchGetAll() {
        List<UserInfo> userInfoList = new ArrayList<>();
        int page = 1;
        int pageSize = 5000;
        while (true) {
            int startRow = (page - 1) * pageSize;
            List<UserInfo> dbUserList = this.userInfoMapper.findAll(Integer.valueOf(startRow), Integer.valueOf(pageSize));
            userInfoList.addAll(dbUserList);
            if (dbUserList.size() < pageSize)
                break;
            page++;
        }
        return userInfoList;
    }
}
