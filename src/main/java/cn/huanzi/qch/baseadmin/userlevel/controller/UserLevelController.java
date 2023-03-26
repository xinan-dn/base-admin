package cn.huanzi.qch.baseadmin.userlevel.controller;

import cn.huanzi.qch.baseadmin.userlevel.dto.UserDTO;
import cn.huanzi.qch.baseadmin.userlevel.service.UserLevelService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/userLevel/"})
public class UserLevelController {
    @Autowired
    private UserLevelService userLevelService;

    @ResponseBody
    @RequestMapping(value = {"/getUserLevelResFile"}, method = {RequestMethod.POST})
    public void getUserLevelResFile(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String downFilePath = this.userLevelService.getUserLevelResFile(file);
        OutputStream out = null;
        try {
            response.addHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(downFilePath, "utf-8"));
            ServletOutputStream servletOutputStream = response.getOutputStream();
            InputStream is = new FileInputStream(downFilePath);
            byte[] b = new byte[4096];
            int size = is.read(b);
            while (size > 0) {
                servletOutputStream.write(b, 0, size);
                size = is.read(b);
            }
            servletOutputStream.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = {"/calcuUserLevel"}, method = {RequestMethod.POST})
    public void calcuUserLevel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.userLevelService.calcuUserLevel();
    }

    @ResponseBody
    @RequestMapping(value = {"/getUserLevelData"}, method = {RequestMethod.POST})
    public List<UserDTO> getUserLevelData(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return this.userLevelService.getUserLevelData(file);
    }

    @ResponseBody
    @RequestMapping(value = {"/uploadData"}, method = {RequestMethod.POST})
    public List<UserDTO> uploadData(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return this.userLevelService.uploadData(file);
    }

    @ResponseBody
    @RequestMapping(value = {"/queryTreeByUserId"}, method = {RequestMethod.POST})
    public List<UserDTO> queryTreeByUserId(@RequestParam("userId") String userId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return this.userLevelService.queryTreeByUserId(userId);
    }

    @ResponseBody
    @RequestMapping(value = {"/listByPage"}, method = {RequestMethod.POST})
    public Object listByPage(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam("userId") String userId, @RequestParam("fzLevel") String fzLevel, @RequestParam("fzNum") String fzNum, @RequestParam("remark") String remark, HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pageIn = (page == null) ? 1 : page.intValue();
        int pageSizeIn = (pageSize == null) ? 20 : pageSize.intValue();
        return this.userLevelService.listByPage(pageIn, pageSizeIn, userId, fzLevel, fzNum, remark);
    }

    @ResponseBody
    @RequestMapping(value = {"/downQueryResult"}, method = {RequestMethod.POST})
    public void downQueryResult(@RequestParam("userId") String userId, @RequestParam("fzLevel") String fzLevel, @RequestParam("fzNum") String fzNum, @RequestParam("remark") String remark, HttpServletRequest request, HttpServletResponse response) {
        String downFilePath = this.userLevelService.downQueryResult(userId, fzLevel, fzNum, remark);
        OutputStream out = null;
        try {
            response.addHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(downFilePath, "utf-8"));
            ServletOutputStream servletOutputStream = response.getOutputStream();
            InputStream is = new FileInputStream(downFilePath);
            byte[] b = new byte[4096];
            int size = is.read(b);
            while (size > 0) {
                servletOutputStream.write(b, 0, size);
                size = is.read(b);
            }
            servletOutputStream.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
