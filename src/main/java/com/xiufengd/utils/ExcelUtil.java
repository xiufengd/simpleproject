package com.xiufengd.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelUtil {
    /**
     * @Author Ray
     * @Return org.apache.poi.ss.usermodel.Workbook
     * @Description 加载Excel模板文件
     * @Date 2018/10/11 19:31
     */
    public Workbook loadExcel(String filePath) {
        FileInputStream is = null;

        Workbook workbook = null;
        try {
            is = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return workbook;
    }

    public Workbook newExcel(String version) {
        Workbook workbook = null;
        if ("2003".equals(version)) {
            workbook = new HSSFWorkbook();
        } else {
            workbook = new XSSFWorkbook();
        }
        return workbook;
    }

    /**
     *
     * 根据后缀判断是否为 Excel 文件，后缀匹配xls和xlsx
     *
     * @param pathname
     * @return
     *
     */
    public boolean isExcel(String pathname) {
        if (pathname == null) {
            return false;
        }
        return pathname.endsWith(".xls") || pathname.endsWith(".xlsx");
    }

    /**
     *
     * 读取 Excel 第一页所有数据
     *
     * @return
     * @throws Exception
     *
     */
    public List<List<String>> read(Workbook workbook) throws Exception {
        return read(workbook,0, 0, getRowCount(workbook,0) - 1);
    }

    /**
     *
     * 读取指定sheet 页所有数据
     *
     * @param sheetIx
     *            指定 sheet 页，从 0 开始
     * @return
     * @throws Exception
     */
    public List<List<String>> read(Workbook workbook, int sheetIx) throws Exception {
        return read(workbook, sheetIx, 0, getRowCount(workbook, sheetIx) - 1);
    }

    /**
     *
     * 读取指定sheet 页指定行数据
     *
     * @param sheetIx
     *            指定 sheet 页，从 0 开始
     * @param start
     *            指定开始行，从 0 开始
     * @param end
     *            指定结束行，从 0 开始
     * @return
     * @throws Exception
     */
    public List<List<String>> read(Workbook workbook, int sheetIx, int start, int end) throws Exception {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        List<List<String>> list = new ArrayList<List<String>>();

        if (end > getRowCount(workbook, sheetIx)) {
            end = getRowCount(workbook, sheetIx);
        }

        int cols = sheet.getRow(0).getLastCellNum(); // 第一行总列数

        for (int i = start; i <= end; i++) {
            List<String> rowList = new ArrayList<String>();
            Row row = sheet.getRow(i);
            for (int j = 0; j < cols; j++) {
                if (row == null) {
                    rowList.add(null);
                    continue;
                }
                rowList.add(getCellValueToString(row.getCell(j)));
            }
            list.add(rowList);
        }

        return list;
    }

    /**
     *
     * 将数据写入到 Excel 默认第一页中，从第1行开始写入
     *
     * @param rowData
     *            数据
     * @return
     * @throws IOException
     *
     */
    public boolean write(Workbook workbook, List<List<String>> rowData) throws IOException {
        return write(workbook, 0, rowData, 0);
    }

    /**
     *
     * 将数据写入到 Excel 新创建的 Sheet 页
     *
     * @param rowData
     *            数据
     * @param sheetName
     *            长度为1-31，不能包含后面任一字符: ：\ / ? * [ ]
     * @return
     * @throws IOException
     */
    public boolean write(Workbook workbook, List<List<String>> rowData, String sheetName, boolean isNewSheet) throws IOException {
        Sheet sheet = null;
        if (isNewSheet) {
            sheet = workbook.createSheet(sheetName);
        } else {
            sheet = workbook.createSheet();
        }
        int sheetIx = workbook.getSheetIndex(sheet);
        return write(workbook, sheetIx, rowData, 0);
    }

    /**
     *
     * 将数据追加到sheet页最后
     *
     * @param rowData
     *            数据
     * @param sheetIx
     *            指定 Sheet 页，从 0 开始
     * @param isAppend
     *            是否追加,true 追加，false 重置sheet再添加
     * @return
     * @throws IOException
     */
    public boolean write(Workbook workbook, int sheetIx, List<List<String>> rowData, boolean isAppend) throws IOException {
        if (isAppend) {
            return write(workbook, sheetIx, rowData, getRowCount(workbook, sheetIx));
        } else {// 清空再添加
            clearSheet(workbook, sheetIx);
            return write(workbook, sheetIx, rowData, 0);
        }
    }

    /**
     *
     * 将数据写入到 Excel 指定 Sheet 页指定开始行中,指定行后面数据向后移动
     *
     * @param rowData
     *            数据
     * @param sheetIx
     *            指定 Sheet 页，从 0 开始
     * @param startRow
     *            指定开始行，从 0 开始
     * @return
     * @throws IOException
     */
    public boolean write(Workbook workbook, int sheetIx, List<List<String>> rowData, int startRow) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        int dataSize = rowData.size();
        if (getRowCount(workbook, sheetIx) > 0) {// 如果小于等于0，则一行都不存在
            sheet.shiftRows(startRow, getRowCount(workbook, sheetIx), dataSize);
        }
        for (int i = 0; i < dataSize; i++) {
            Row row = sheet.createRow(i + startRow);
            for (int j = 0; j < rowData.get(i).size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(rowData.get(i).get(j) + "");
            }
        }
        return true;
    }

    /**
     *
     * 设置cell 样式
     *
     * @param sheetIx
     *            指定 Sheet 页，从 0 开始
     * @param colIndex
     *            指定列，从 0 开始
     * @return
     * @throws IOException
     */
    public boolean setStyle(Workbook workbook, int sheetIx, int rowIndex, int colIndex, CellStyle style) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        // sheet.autoSizeColumn(colIndex, true);// 设置列宽度自适应
        sheet.setColumnWidth(colIndex, 4000);

        Cell cell = sheet.getRow(rowIndex).getCell(colIndex);
        cell.setCellStyle(style);

        return true;
    }

    /**
     *
     * 设置样式
     *
     * @param type
     *            1：标题 2：第一行
     * @return
     */
    public CellStyle makeStyle(Workbook workbook, int type) {
        CellStyle style = workbook.createCellStyle();

        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("@"));// // 内容样式 设置单元格内容格式是文本
        style.setAlignment(HorizontalAlignment.CENTER);// 内容居中


        Font font = workbook.createFont();// 文字样式

        if (type == 1) {
            // style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);//颜色样式
            // 前景颜色
            // style.setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);//背景色
            // style.setFillPattern(CellStyle.ALIGN_FILL);// 填充方式
            font.setBold(true);
            font.setFontHeight((short) 500);
        }

        if (type == 2) {
            font.setBold(true);
            font.setFontHeight((short) 300);
        }

        if (type == 3) {
            // 边框样式
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
        }

        style.setFont(font);
        return style;
    }

    /**
     *
     * 合并单元格
     *
     * @param sheetIx
     *            指定 Sheet 页，从 0 开始
     * @param firstRow
     *            开始行
     * @param lastRow
     *            结束行
     * @param firstCol
     *            开始列
     * @param lastCol
     *            结束列
     */
    public void region(Workbook workbook, int sheetIx, int firstRow, int lastRow, int firstCol, int lastCol) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    /**
     *
     * 指定行是否为空
     *
     * @param sheetIx
     *            指定 Sheet 页，从 0 开始
     * @param rowIndex
     *            指定开始行，从 0 开始
     * @return true 不为空，false 不行为空
     * @throws IOException
     */
    public boolean isRowNull(Workbook workbook, int sheetIx, int rowIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        return sheet.getRow(rowIndex) == null;
    }

    /**
     *
     * 创建行，若行存在，则清空
     *
     * @param sheetIx
     *            指定 sheet 页，从 0 开始
     * @param rowIndex
     *            指定创建行，从 0 开始
     * @return
     * @throws IOException
     */
    public boolean createRow(Workbook workbook, int sheetIx, int rowIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        sheet.createRow(rowIndex);
        return true;
    }

    /**
     *
     * 指定单元格是否为空
     *
     * @param sheetIx
     *            指定 Sheet 页，从 0 开始
     * @param rowIndex
     *            指定开始行，从 0 开始
     * @param colIndex
     *            指定开始列，从 0 开始
     * @return true 行不为空，false 行为空
     * @throws IOException
     */
    public boolean isCellNull(Workbook workbook, int sheetIx, int rowIndex, int colIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        if (!isRowNull(workbook, sheetIx, rowIndex)) {
            return false;
        }
        Row row = sheet.getRow(rowIndex);
        return row.getCell(colIndex) == null;
    }

    /**
     *
     * 创建单元格
     *
     * @param sheetIx
     *            指定 sheet 页，从 0 开始
     * @param rowIndex
     *            指定行，从 0 开始
     * @param colIndex
     *            指定创建列，从 0 开始
     * @return true 列为空，false 行不为空
     * @throws IOException
     */
    public boolean createCell(Workbook workbook, int sheetIx, int rowIndex, int colIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        Row row = sheet.getRow(rowIndex);
        row.createCell(colIndex);
        return true;
    }

    /**
     * 返回sheet 中的行数
     *
     *
     * @param sheetIx
     *            指定 Sheet 页，从 0 开始
     * @return
     */
    public int getRowCount(Workbook workbook, int sheetIx) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        if (sheet.getPhysicalNumberOfRows() == 0) {
            return 0;
        }
        return sheet.getLastRowNum() + 1;

    }

    /**
     *
     * 返回所在行的列数
     *
     * @param sheetIx
     *            指定 Sheet 页，从 0 开始
     * @param rowIndex
     *            指定行，从0开始
     * @return 返回-1 表示所在行为空
     */
    public int getColumnCount(Workbook workbook, int sheetIx, int rowIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        Row row = sheet.getRow(rowIndex);
        return row == null ? -1 : row.getLastCellNum();

    }

    /**
     * @Author Ray
     * @param workbook: Excel
     * @param sheetIx:  指定 Sheet 页，从 0 开始
     * @param rowIndex: 指定行，从0开始
     * @param colIndex: 指定列，从0开始
     * @param value: 值
     * @Return boolean
     * @Description 插入字符类型值
     * @Date 2018/10/11 19:26
     */
    public void setValueAt(Workbook workbook, int sheetIx, int rowIndex, int colIndex, String value) throws IOException {
        setValueAt(workbook, sheetIx, rowIndex, colIndex, value, false);
    }

    public void setValueAt(Workbook workbook, int sheetIx, int rowIndex, int colIndex, String value, boolean isSetStyle) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        
        // 将Excel内的行高设置为15
        sheet.setDefaultRowHeight((short)(20*15));
        
        Row row = sheet.getRow(rowIndex);
        Cell cell = null;
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        cell = row.createCell(colIndex);
        if (cell == null) {
            cell = row.getCell(colIndex);
        }

        if (isSetStyle) {
            setStyle(workbook, sheetIx, rowIndex, colIndex, makeStyle(workbook, 3));
        }
        cell.setCellValue(value);
        if(value.length()>50){
            CellStyle style = cell.getCellStyle();
            if(null == style) {
                style = workbook.createCellStyle();
                style.setWrapText(true);
                cell.setCellStyle(style);
            }else{
                style.setWrapText(true);
            }
        }


    }
    /**
     * @Author Ray
     * @param workbook: Excel
     * @param sheetIx:  指定 Sheet 页，从 0 开始
     * @param rowIndex: 指定行，从0开始
     * @param colIndex: 指定列，从0开始
     * @param value: 值
     * @Return boolean
     * @Description 插入字符类型值
     * @Date 2018/10/11 19:26
     */
    public void setValueAt(Workbook workbook, int sheetIx, int rowIndex, int colIndex, double value) throws IOException {

        Sheet sheet = workbook.getSheetAt(sheetIx);
        Row row = sheet.getRow(rowIndex);
        Cell cell = null;
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        cell = row.createCell(colIndex);
        if (cell == null) {
            cell = row.getCell(colIndex);
        }
        cell.setCellValue(value);
    }
    /**
     * @Author Ray
     * @param workbook: Excel
     * @param sheetIx:  指定 Sheet 页，从 0 开始
     * @param rowIndex: 指定行，从0开始
     * @param colIndex: 指定列，从0开始
     * @param value: 值
     * @Return boolean
     * @Description 插入数字类型值
     * @Date 2018/10/11 19:26
     */
    public void setValueAt(Workbook workbook, int sheetIx, int rowIndex, int colIndex, int value) throws IOException {

        Sheet sheet = workbook.getSheetAt(sheetIx);
        Row row = sheet.getRow(rowIndex);
        Cell cell = null;
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        cell = row.createCell(colIndex);
        if (cell == null) {
            cell = row.getCell(colIndex);
        }
        cell.setCellValue(value);
    }

    /**
     * @Author Ray
     * @param sheetIx: 指定sheet
     * @param insertRowNum: 插入行的行号
     * @param rows: 要插入的行数
     * @Return void
     * @Description 要插入的行数
     * @Date 2018/10/15 17:47
     */
    public void insertRow(Workbook workbook, int sheetIx, int insertRowNum, int rows) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        sheet.shiftRows(insertRowNum, sheet.getLastRowNum(), rows,true,false);
        sheet.createRow(insertRowNum);
    }

    /**
     *
     * 返回 row 和 column 位置的单元格值
     *
     * @param sheetIx
     *            指定 Sheet 页，从 0 开始
     * @param rowIndex
     *            指定行，从0开始
     * @param colIndex
     *            指定列，从0开始
     * @return
     *
     */
    public String getValueAt(Workbook workbook, int sheetIx, int rowIndex, int colIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        return getCellValueToString(sheet.getRow(rowIndex).getCell(colIndex));
    }

    /**
     *
     * 重置指定行的值
     *
     * @param rowData
     *            数据
     * @param sheetIx
     *            指定 Sheet 页，从 0 开始
     * @param rowIndex
     *            指定行，从0开始
     * @return
     * @throws IOException
     */
    public boolean setRowValue(Workbook workbook, int sheetIx, List<String> rowData, int rowIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        Row row = sheet.getRow(rowIndex);
        for (int i = 0; i < rowData.size(); i++) {
            row.getCell(i).setCellValue(rowData.get(i));
        }
        return true;
    }

    /**
     *
     * 返回指定行的值的集合
     *
     * @param sheetIx
     *            指定 Sheet 页，从 0 开始
     * @param rowIndex
     *            指定行，从0开始
     * @return
     */
    public List<String> getRowValue(Workbook workbook, int sheetIx, int rowIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        Row row = sheet.getRow(rowIndex);
        List<String> list = new ArrayList<String>();
        if (row == null) {
            list.add(null);
        } else {
            for (int i = 0; i < row.getLastCellNum(); i++) {
                list.add(getCellValueToString(row.getCell(i)));
            }
        }
        return list;
    }

    /**
     *
     * 返回列的值的集合
     *
     * @param sheetIx
     *            指定 Sheet 页，从 0 开始
     * @param rowIndex
     *            指定行，从0开始
     * @param colIndex
     *            指定列，从0开始
     * @return
     */
    public List<String> getColumnValue(Workbook workbook, int sheetIx, int rowIndex, int colIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        List<String> list = new ArrayList<String>();
        for (int i = rowIndex; i < getRowCount(workbook, sheetIx); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                list.add(null);
                continue;
            }
            list.add(getCellValueToString(sheet.getRow(i).getCell(colIndex)));
        }
        return list;
    }

    /**
     *
     * 获取excel 中sheet 总页数
     *
     * @return
     */
    public int getSheetCount(Workbook workbook) {
        return workbook.getNumberOfSheets();
    }

    public void createSheet(Workbook workbook) {
        workbook.createSheet();
    }

    /**
     *
     * 设置sheet名称，长度为1-31，不能包含后面任一字符: ：\ / ? * [ ]
     *
     * @param sheetIx
     *            指定 Sheet 页，从 0 开始，//
     * @param name
     * @return
     * @throws IOException
     */
    public boolean setSheetName(Workbook workbook, int sheetIx, String name) throws IOException {
        workbook.setSheetName(sheetIx, name);
        return true;
    }

    /**
     *
     * 获取 sheet名称
     *
     * @param sheetIx
     *            指定 Sheet 页，从 0 开始
     * @return
     * @throws IOException
     */
    public String getSheetName(Workbook workbook, int sheetIx) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        return sheet.getSheetName();
    }

    /**
     * 获取sheet的索引，从0开始
     *
     * @param name
     *            sheet 名称
     * @return -1表示该未找到名称对应的sheet
     */
    public int getSheetIndex(Workbook workbook, String name) {
        return workbook.getSheetIndex(name);
    }

    /**
     *
     * 删除指定sheet
     *
     * @param sheetIx
     *            指定 Sheet 页，从 0 开始
     * @return
     * @throws IOException
     */
    public boolean removeSheetAt(Workbook workbook, int sheetIx) throws IOException {
        workbook.removeSheetAt(sheetIx);
        return true;
    }

    /**
     *
     * 删除指定sheet中行，改变该行之后行的索引
     *
     * @param sheetIx
     *            指定 Sheet 页，从 0 开始
     * @param rowIndex
     *            指定行，从0开始
     * @return
     * @throws IOException
     */
    public boolean removeRow(Workbook workbook, int sheetIx, int rowIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        sheet.shiftRows(rowIndex + 1, getRowCount(workbook, sheetIx), -1);
        Row row = sheet.getRow(getRowCount(workbook, sheetIx) - 1);
        sheet.removeRow(row);
        return true;
    }

    /**
     *
     * 设置sheet 页的索引
     *
     * @param sheetname
     *            Sheet 名称
     * @param sheetIx
     *            Sheet 索引，从0开始
     */
    public void setSheetOrder(Workbook workbook, String sheetname, int sheetIx) {
        workbook.setSheetOrder(sheetname, sheetIx);
    }

    /**
     *
     * 清空指定sheet页（先删除后添加并指定sheetIx）
     *
     * @param sheetIx
     *            指定 Sheet 页，从 0 开始
     * @return
     * @throws IOException
     */
    public boolean clearSheet(Workbook workbook, int sheetIx) throws IOException {
        String sheetname = getSheetName(workbook, sheetIx);
        removeSheetAt(workbook, sheetIx);
        workbook.createSheet(sheetname);
        setSheetOrder(workbook, sheetname, sheetIx);
        return true;
    }

    public Workbook getWorkbook(Workbook workbook) {
        return workbook;
    }

    //public void writeExcel(Workbook workbook, String fileDir) throws IOException {
    //    FileOutputStream os = null;
    //    try {
    //        os = new FileOutputStream(fileDir);
    //        workbook.write(os);
    //        os.flush();
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    } finally {
    //        if (os != null) {
    //            os.close();
    //        }
    //        workbook.close();
    //    }
    //}

    /**
     * @Author Ray
     * @param workbook: Excel
     * @param filePath: 文件输出目录
     * @Return java.lang.String
     * @Description
     * @Date 2018/10/12 14:33
     */
    public String writeExcel(Workbook workbook, String filePath) throws IOException {
        String fileDir = "";
        String fileName = UUID.randomUUID() + ".xlsx";
        FileOutputStream os = null;
        try {
            fileDir = filePath + fileName;
            os = new FileOutputStream(fileDir);
            workbook.write(os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
            workbook.close();
        }
        return fileName;
    }


    /**
     * @Author Ray
     * @param workbook: Excel
     * @param filePath: 文件输出目录
     * @Return java.lang.String
     * @Description
     * @Date 2018/10/12 14:33
     */
    public String writeExcel2(Workbook workbook, String filePath,String fileName) throws IOException {
        String fileDir = "";
        FileOutputStream os = null;
        try {
            fileDir = filePath + fileName;
            os = new FileOutputStream(fileDir);
            workbook.write(os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
            workbook.close();
        }
        return fileName;
    }

    /**
     *
     * 转换单元格的类型为String 默认的 <br>
     * 默认的数据类型：CELL_TYPE_BLANK(3), CELL_TYPE_BOOLEAN(4),
     * CELL_TYPE_ERROR(5),CELL_TYPE_FORMULA(2), CELL_TYPE_NUMERIC(0),
     * CELL_TYPE_STRING(1)
     *
     * @param cell
     * @return
     *
     */
    private String getCellValueToString(Cell cell) {
        String pattern = "yyyy-MM-dd";
        String strCell = "";
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    if (pattern != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        strCell = sdf.format(date);
                    } else {
                        strCell = date.toString();
                    }
                    break;
                }
//                // 不是日期格式，则防止当数字过长时以科学计数法显示
//                HSSFCellStyle cellStyle = new CellStyle();
//                cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00E+00"));
//                cell.setCellStyle(cellStyle);
                strCell = cell.toString();
                break;
            case STRING:
                strCell = cell.getStringCellValue();
                break;
            default:
                break;
        }
        return strCell;
    }

    /**
     * @Author Ray
     * @param response: HttpServletResponse
     * @param filePath: 原始文件路径
     * @param fileName: 目标文件名称
     * @Return void
     * @Description 下载Excel文件
     * @Date 2018/10/12 14:50
     */
    public void downlaod(HttpServletResponse response, String filePath, String fileName) {
        BufferedInputStream br = null;
        OutputStream out = null;
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                response.sendError(404, "File not found!");
                return;
            }
            br = new BufferedInputStream(new FileInputStream(f));
            byte[] buf = new byte[1024];
            int len = 0;
            response.reset();
            response.setContentType("application/x-msdownload");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            String filename = fileName + "_" + dateFormat.format(new Date()) + ".xlsx";
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename,"UTF-8"));
            out = response.getOutputStream();
            while ((len = br.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将查询的数据集转换成Excel文件并通过浏览器下载
     * @param columnNames  表头  key 表头名称 value 对应的数据字段
     * @param rowsObject 数据集
     * @param response 响应对象
     */
    public static void write(Map<String,String> columnNames, List<?> rowsObject, HttpServletResponse response, String fileName) throws IOException {
        //使用xlsx格式的文件
        Workbook workbook = new XSSFWorkbook();
        workbook.createSheet(fileName);
        List<List<String>> rows = new ArrayList<>();
        //初始化表头数据
        List<String> titles = new ArrayList<>(columnNames.keySet());
        //添加表头
        rows.add(titles);
        //转换数据
        List<List<String>> contents = rowsObject.stream().map(o->{
            List<String> row = new ArrayList<>();
            //如果是map的话，通过key取值
            if (o instanceof Map){
                Map m = (Map) o;
                titles.forEach(t->{
                    String key = columnNames.get(t);
                    Object value = m.get(key);
                    String v = value==null?""
                            :value instanceof Date
                            ?DateFormatUtils.format((Date) value,"yyyy-MM-dd HH:mm:ss")
                            :String.valueOf(value);
                    row.add(v);
                });
            }else{
                Class<?> c = o.getClass();
                titles.forEach(t->{
                    try {
                        Field field = c.getDeclaredField(columnNames.get(t));
                        field.setAccessible(true);
                        Object value = field.get(o);
                        String v = value==null?""
                                :value instanceof Date
                                ?DateFormatUtils.format((Date) value,"yyyy-MM-dd HH:mm:ss")
                                :String.valueOf(value);
                        row.add(v);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            }

            return row;
        }).collect(Collectors.toList());

        rows.addAll(contents);
        //如果写入成功了，就下载
        ExcelUtil excelUtil = new ExcelUtil();
        if (excelUtil.write(workbook,rows)){
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-msdownload");
            String filename = fileName + ".xlsx";
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename,"UTF-8"));
            workbook.write(response.getOutputStream());
        }
    }


    /**
     * 将查询的数据集转换成Excel文件并通过浏览器下载
     * @param columnNames  表头  key 表头名称 value 对应的数据字段
     * @param rowsObject 数据集
     * @param response 响应对象
     */
    public static String writeToStatiscs(Map<String,String> columnNames, List<?> rowsObject, HttpServletResponse response, String fileName, String path) throws IOException {
        //使用xlsx格式的文件
        Workbook workbook = new XSSFWorkbook();
        workbook.createSheet(fileName);
        List<List<String>> rows = new ArrayList<>();
        //初始化表头数据
        List<String> titles = new ArrayList<>(columnNames.keySet());
        //添加表头
        rows.add(titles);
        //转换数据
        List<List<String>> contents = rowsObject.stream().map(o->{
            List<String> row = new ArrayList<>();
            //如果是map的话，通过key取值
            if (o instanceof Map){
                Map m = (Map) o;
                titles.forEach(t->{
                    String key = columnNames.get(t);
                    Object value = m.get(key);
                    value=value==null?0:value;
                    String v = value==null?""
                            :value instanceof Date
                            ?DateFormatUtils.format((Date) value,"yyyy-MM-dd HH:mm:ss")
                            :String.valueOf(value);
                    row.add(v);
                });
            }else{
                Class<?> c = o.getClass();
                titles.forEach(t->{
                    try {
                        Field field = c.getDeclaredField(columnNames.get(t));
                        field.setAccessible(true);
                        Object value = field.get(o);
                        String v = value==null?""
                                :value instanceof Date
                                ?DateFormatUtils.format((Date) value,"yyyy-MM-dd HH:mm:ss")
                                :String.valueOf(value);
                        row.add(v);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            }

            return row;
        }).collect(Collectors.toList());

        rows.addAll(contents);
//        //如果写入成功了，就下载
        ExcelUtil excelUtil = new ExcelUtil();





        //根据路径创建输出流

        ExcelUtil excelUtil1=new ExcelUtil();
        for (int i = 0; i <rows.size() ; i++) {
            List<String> list = rows.get(i);
            int j=0;
            for (int x = 0; x < list.size(); x++) {
                excelUtil.setValueAt(workbook,0,i,j++,list.get(x));

            }

        }
        return excelUtil.ToStatiscs(workbook,path+fileName);


    }

    /**
     * @Author Ray
     * @param workbook: Excel
     * @param filePath: 文件输出目录
     * @Return java.lang.String
     * @Description
     * @Date 2018/10/12 14:33
     */
    public String ToStatiscs(Workbook workbook, String filePath) throws IOException {
        String fileDir = "";

        String fileName  = RedisUtil.createId("statiscs") + ".xlsx";
        FileOutputStream os = null;
        try {
            fileDir = filePath + fileName;
            os = new FileOutputStream(fileDir);
            workbook.write(os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
            workbook.close();
        }
        return fileName;
    }
}
