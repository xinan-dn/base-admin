package cn.huanzi.qch.baseadmin.util;

import cn.huanzi.qch.baseadmin.userlevel.dto.UserDTO;
import cn.huanzi.qch.baseadmin.userlevel.pojo.MoneyRecord;
import jxl.Workbook;
import jxl.format.*;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class ExcelUtil {

    public static String writeExcel(List<UserDTO> userDTOList) {
        //String filePath = "/usr/local/用户层级报表.xlsx";
        String filePath = "用户层级报表.xlsx";

        WritableWorkbook workbook = null;
        try {
            File file = new File(filePath);

            // 创建写工作簿对象
            workbook = Workbook.createWorkbook(file);
            // 工作表
            WritableSheet sheet = workbook.createSheet("Sheet1", 0);
            // 设置字体;
            WritableFont font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

            WritableCellFormat cellFormat = new WritableCellFormat(font);
            // 设置背景颜色;
            cellFormat.setBackground(Colour.WHITE);
            // 设置边框;
            cellFormat.setBorder(Border.ALL, BorderLineStyle.DASH_DOT);
            // 设置文字居中对齐方式;
            cellFormat.setAlignment(Alignment.LEFT);
            // 设置垂直居中;
            cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            // 分别给1,5,6列设置不同的宽度;
            sheet.setColumnView(0, 25);
            sheet.setColumnView(1, 25);
            sheet.setColumnView(2, 15);
            sheet.setColumnView(3, 15);
            sheet.setColumnView(4, 25);
            sheet.setColumnView(5, 25);
            sheet.setColumnView(6, 500);
            // 给sheet电子版中所有的列设置默认的列的宽度;
            sheet.getSettings().setDefaultColumnWidth(20);
            // 给sheet电子版中所有的行设置默认的高度，高度的单位是1/20个像素点,但设置这个貌似就不能自动换行了
            // sheet.getSettings().setDefaultRowHeight(30 * 20);
            // 设置自动换行;
            cellFormat.setWrap(true);

            // 单元格
            Label label0 = new Label(0, 0, "账号", cellFormat);
            Label label1 = new Label(1, 0, "上级账号", cellFormat);
            Label label2 = new Label(2, 0, "层级", cellFormat);
            Label label3 = new Label(3, 0, "发展层级", cellFormat);
            Label label4 = new Label(4, 0, "下级账号数量", cellFormat);
            Label label5 = new Label(5, 0, "下级投资金额", cellFormat);
            Label label6 = new Label(6, 0, "层级线", cellFormat);

            sheet.addCell(label0);
            sheet.addCell(label1);
            sheet.addCell(label2);
            sheet.addCell(label3);
            sheet.addCell(label4);
            sheet.addCell(label5);
            sheet.addCell(label6);

            // 给第二行设置背景、字体颜色、对齐方式等等;
            WritableFont font2 = new WritableFont(WritableFont.ARIAL, 14, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat cellFormat2 = new WritableCellFormat(font2);
            // 设置文字居中对齐方式;
            cellFormat2.setAlignment(Alignment.LEFT);
            // 设置垂直居中;
            cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);
            cellFormat2.setBackground(Colour.WHITE);
            cellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
            cellFormat2.setWrap(true);

            // 记录行数
            int n = 1;

            for (UserDTO userDTO : userDTOList) {

                Label lt0 = new Label(0, n, userDTO.getName(), cellFormat2);
                Label lt1 = new Label(1, n, userDTO.getParent(), cellFormat2);
                Label lt2 = new Label(2, n, userDTO.getLevel()+"", cellFormat2);
                Label lt3 = new Label(3, n, userDTO.getFzLevel()+"", cellFormat2);
                Label lt4 = new Label(4, n, userDTO.getChildrenNum()+"", cellFormat2);
                Label lt5 = new Label(5, n, userDTO.getTotalMoney()+"", cellFormat2);
                Label lt6 = new Label(6, n, userDTO.getParentLongId(), cellFormat2);

                sheet.addCell(lt0);
                sheet.addCell(lt1);
                sheet.addCell(lt2);
                sheet.addCell(lt3);
                sheet.addCell(lt4);
                sheet.addCell(lt5);
                sheet.addCell(lt6);

                n++;
            }

            //开始执行写入操作
            workbook.write();
            //关闭流
            workbook.close();
        } catch (Exception e) {
            
        } finally {
            try {
                workbook.close();
            }catch (Exception e) {

            }

        }

        return filePath;
    }

    public static String writeMoneyExcel(List<MoneyRecord> moneyRecords) {
        //String filePath = "/usr/local/用户层级报表.xlsx";
        String filePath = "用户金额报表.xlsx";

        WritableWorkbook workbook = null;
        try {
            File file = new File(filePath);

            // 创建写工作簿对象
            workbook = Workbook.createWorkbook(file);
            // 工作表
            WritableSheet sheet = workbook.createSheet("Sheet1", 0);
            // 设置字体;
            WritableFont font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

            WritableCellFormat cellFormat = new WritableCellFormat(font);
            // 设置背景颜色;
            cellFormat.setBackground(Colour.WHITE);
            // 设置边框;
            cellFormat.setBorder(Border.ALL, BorderLineStyle.DASH_DOT);
            // 设置文字居中对齐方式;
            cellFormat.setAlignment(Alignment.LEFT);
            // 设置垂直居中;
            cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            // 分别给1,5,6列设置不同的宽度;
            sheet.setColumnView(0, 25);
            sheet.setColumnView(1, 25);
            // 给sheet电子版中所有的列设置默认的列的宽度;
            sheet.getSettings().setDefaultColumnWidth(20);
            // 给sheet电子版中所有的行设置默认的高度，高度的单位是1/20个像素点,但设置这个貌似就不能自动换行了
            // sheet.getSettings().setDefaultRowHeight(30 * 20);
            // 设置自动换行;
            cellFormat.setWrap(true);

            // 单元格
            Label label0 = new Label(0, 0, "账号", cellFormat);
            Label label1 = new Label(1, 0, "金额", cellFormat);

            sheet.addCell(label0);
            sheet.addCell(label1);

            // 给第二行设置背景、字体颜色、对齐方式等等;
            WritableFont font2 = new WritableFont(WritableFont.ARIAL, 14, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat cellFormat2 = new WritableCellFormat(font2);
            // 设置文字居中对齐方式;
            cellFormat2.setAlignment(Alignment.LEFT);
            // 设置垂直居中;
            cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);
            cellFormat2.setBackground(Colour.WHITE);
            cellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
            cellFormat2.setWrap(true);

            // 记录行数
            int n = 1;

            for (MoneyRecord moneyRecord : moneyRecords) {

                Label lt0 = new Label(0, n, moneyRecord.getName(), cellFormat2);
                Label lt1 = new Label(1, n, moneyRecord.getMoney().toString(), cellFormat2);

                sheet.addCell(lt0);
                sheet.addCell(lt1);

                n++;
            }

            //开始执行写入操作
            workbook.write();
            //关闭流
            workbook.close();
        } catch (Exception e) {

        } finally {
            try {
                workbook.close();
            }catch (Exception e) {

            }

        }

        return filePath;
    }

    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
