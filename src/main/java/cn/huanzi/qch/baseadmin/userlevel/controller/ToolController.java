package cn.huanzi.qch.baseadmin.userlevel.controller;

import cn.huanzi.qch.baseadmin.userlevel.service.ToolService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/tool"})
public class ToolController extends HttpServlet {
    @Autowired
    private ToolService toolService;

    @ResponseBody
    @RequestMapping(value = {"/address/query"}, method = {RequestMethod.POST})
    public Object addressQuery(@RequestParam("address") String address, @RequestParam("chain") String chain, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.toolService.addressQuery(address, chain);
    }

    @ResponseBody
    @RequestMapping(value = {"/phone/query"}, method = {RequestMethod.POST})
    public Object phoneQuery(@RequestParam("phone") String phone, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.toolService.phoneQuery(phone);
    }

    @ResponseBody
    @RequestMapping(value = {"/idcard/query"}, method = {RequestMethod.POST})
    public Object idcardQuery(@RequestParam("idcard") String idcard, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.toolService.idcardQuery(idcard);
    }

    @ResponseBody
    @RequestMapping(value = {"/bankcard/query"}, method = {RequestMethod.POST})
    public Object bankcardQuery(@RequestParam("bankcard") String bankcard, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.toolService.bankcardQuery(bankcard);
    }

    @ResponseBody
    @RequestMapping(value = {"/address/listByPage"}, method = {RequestMethod.POST})
    public Object listByPage(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam("chain") String chain, @RequestParam("address") String address, @RequestParam("label") String label, HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pageIn = (page == null) ? 1 : page.intValue();
        int pageSizeIn = (pageSize == null) ? 20 : pageSize.intValue();
        return this.toolService.listByPage(pageIn, pageSizeIn, chain, address, label);
    }

    @ResponseBody
    @RequestMapping(value = {"/address/deleteDupAddress"}, method = {RequestMethod.POST})
    public String deleteDupAddress(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.toolService.deleteDupAddress();
        return "success!";
    }
}
