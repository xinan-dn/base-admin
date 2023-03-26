package cn.huanzi.qch.baseadmin.userlevel.controller;

import cn.huanzi.qch.baseadmin.userlevel.service.MoneyService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
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
@RequestMapping({"/money"})
public class MoneyController extends HttpServlet {
    @Autowired
    private MoneyService moneyService;

    @ResponseBody
    @RequestMapping(value = {"/uploadFile"}, method = {RequestMethod.POST})
    public void uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        this.moneyService.uploadFile(file);
    }

    @ResponseBody
    @RequestMapping(value = {"/calcuUserLevel"}, method = {RequestMethod.POST})
    public void calcuUserLevel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.moneyService.calcuUserLevel();
    }

    @ResponseBody
    @RequestMapping(value = {"/deleteAll"}, method = {RequestMethod.POST})
    public void deleteAll(HttpServletRequest request, HttpServletResponse response) {
        this.moneyService.deleteAll();
    }

    @ResponseBody
    @RequestMapping(value = {"/downResultFile"}, method = {RequestMethod.POST})
    public void queryUserMoney(HttpServletRequest request, HttpServletResponse response) {
        String downFilePath = this.moneyService.queryUserMoney();
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
    @RequestMapping(value = {"/listByPage"}, method = {RequestMethod.POST})
    public Object listByPage(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam("userId") String userId, @RequestParam("awardType") String awardType, @RequestParam("coinType") String coinType, HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pageIn = (page == null) ? 1 : page.intValue();
        int pageSizeIn = (pageSize == null) ? 20 : pageSize.intValue();
        return this.moneyService.listByPage(pageIn, pageSizeIn, userId, awardType, coinType);
    }

    @ResponseBody
    @RequestMapping(value = {"/downQueryResult"}, method = {RequestMethod.POST})
    public void downQueryResult(@RequestParam("userId") String userId, @RequestParam("awardType") String awardType, @RequestParam("coinType") String coinType, HttpServletRequest request, HttpServletResponse response) {
        String downFilePath = this.moneyService.downQueryResult(userId, awardType, coinType);
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
