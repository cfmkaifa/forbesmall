package net.mall.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import net.mall.Setting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

/***
 *
 */
public class ReconContractBuildImpl extends ReconContractModel {


    private  final String ORDER_NO_FORMAT = "PO＃%s";
    private  final String MONTH_FMT = "yyyyMM";
    private  final String STR_FORMAT = "%s，%s";

    private static Setting SETTING;

    /***初始化字体
     */
    static {
        SETTING = SystemUtils.getSetting();
    }


    /****
     * 创建合同标题
     * @param document
     * @throws DocumentException
     * @throws IOException
     */
    private  void createTitle(Document document)
            throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100); // 宽度100%填充
        table.setSpacingBefore(5f); // 前间距
        table.setSpacingAfter(0.5f); // 后间距
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font titleFont = new Font(baseFont, 14f, Font.BOLD);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell cell = new PdfPCell(new Phrase("福布云商", titleFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(2);
        cell.setBorder(0);
        table.addCell(cell);
        /****左标题****/
        PdfPCell rightCell = new PdfPCell(new Phrase(title, titleFont));
        rightCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        rightCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        rightCell.setColspan(7);
        rightCell.setBorder(0);
        table.addCell(rightCell);
        rightCell = new PdfPCell(new Phrase("", titleFont));
        rightCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        rightCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        rightCell.setColspan(9);
        rightCell.setBorder(0);
        table.addCell(rightCell);
        PdfPCell twoCell = new PdfPCell(new Phrase("上海让雷智能科技有限公司对账单信息", titleFont));
        twoCell.setPaddingTop(10.0f);
        twoCell.setPaddingBottom(10.0f);
        twoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        twoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        twoCell.setColspan(9);
        twoCell.setBorder(0);
        table.addCell(twoCell);
        document.add(table);
    }

    /***
     * 主题
     * @param document
     * @throws DocumentException
     * @throws IOException
     */
    private  void createDetailsTable(Document document)
            throws DocumentException, IOException {
        PdfPTable tableBaseInfo = new PdfPTable(9);
        tableBaseInfo.setWidthPercentage(100); // 宽度100%填充
        tableBaseInfo.setSpacingBefore(10f); // 前间距
        /****字体****/
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font contentFont = new Font(baseFont, 12f);
        /***************1行*******************************/
        PdfPCell titleCellLable = new PdfPCell(new Phrase("合同订单日期：", contentFont));
        titleCellLable.setUseAscender(true);
        titleCellLable.setUseDescender(true);
        titleCellLable.setBorderWidth(0.1f);
        titleCellLable.setColspan(2);
        tableBaseInfo.addCell(titleCellLable);
        PdfPCell valueCellLable = new PdfPCell(new Phrase(orderDate, contentFont));
        valueCellLable.setUseAscender(true);
        valueCellLable.setUseDescender(true);
        valueCellLable.setHorizontalAlignment(Element.ALIGN_LEFT);
        titleCellLable.setBorderWidth(0.1f);
        valueCellLable.setColspan(7);
        tableBaseInfo.addCell(valueCellLable);
        /***************1行*******************************/
        PdfPCell otitleCellLable = new PdfPCell(new Phrase("合同订单号：", contentFont));
        otitleCellLable.setUseAscender(true);
        otitleCellLable.setUseDescender(true);
        otitleCellLable.setBorderWidth(0.1f);
        otitleCellLable.setColspan(2);
        tableBaseInfo.addCell(titleCellLable);
        PdfPCell ovalueCellLable = new PdfPCell(new Phrase(String.format(ORDER_NO_FORMAT,orderSn), contentFont));
        ovalueCellLable.setUseAscender(true);
        ovalueCellLable.setUseDescender(true);
        valueCellLable.setHorizontalAlignment(Element.ALIGN_LEFT);
        ovalueCellLable.setBorderWidth(0.1f);
        ovalueCellLable.setColspan(7);
        tableBaseInfo.addCell(ovalueCellLable);
        /***货物明细**/
        if(ConvertUtils.isNotEmpty(reconContractProducts)){
            reconContractProducts.forEach(reconContractProduct -> {
                String proName = String.format(STR_FORMAT,reconContractProduct.getProName(),reconContractProduct.getSpecs());
                PdfPCell ttitleCellLable = new PdfPCell(new Phrase("产品名称：", contentFont));
                ttitleCellLable.setUseAscender(true);
                ttitleCellLable.setUseDescender(true);
                ttitleCellLable.setBorderWidth(0.1f);
                ttitleCellLable.setColspan(2);
                tableBaseInfo.addCell(ttitleCellLable);
                PdfPCell tvalueCellLable = new PdfPCell(new Phrase(proName, contentFont));
                tvalueCellLable.setUseAscender(true);
                tvalueCellLable.setUseDescender(true);
                tvalueCellLable.setBorderWidth(0.1f);
                tvalueCellLable.setColspan(7);
                tableBaseInfo.addCell(tvalueCellLable);
                PdfPCell tttitleCellLable = new PdfPCell(new Phrase("商品单价：", contentFont));
                tttitleCellLable.setUseAscender(true);
                tttitleCellLable.setUseDescender(true);
                tttitleCellLable.setBorderWidth(0.1f);
                tttitleCellLable.setColspan(2);
                tableBaseInfo.addCell(tttitleCellLable);
                PdfPCell ttvalueCellLable = new PdfPCell(new Phrase(numberToStr(reconContractProduct.getPrice()), contentFont));
                ttvalueCellLable.setUseAscender(true);
                ttvalueCellLable.setUseDescender(true);
                ttvalueCellLable.setBorderWidth(0.1f);
                ttvalueCellLable.setColspan(7);
                tableBaseInfo.addCell(ttvalueCellLable);
            });
            }
            /***************1行*******************************/
            PdfPCell ttitleCellLable = new PdfPCell(new Phrase("生产单位：", contentFont));
            ttitleCellLable.setUseAscender(true);
            ttitleCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            ttitleCellLable.setColspan(2);
            tableBaseInfo.addCell(ttitleCellLable);
            PdfPCell tvalueCellLable = new PdfPCell(new Phrase(sellerName, contentFont));
            tvalueCellLable.setUseAscender(true);
            tvalueCellLable.setUseDescender(true);
            tvalueCellLable.setBorderWidth(0.1f);
            tvalueCellLable.setColspan(7);
            tableBaseInfo.addCell(tvalueCellLable);
            /*******************/
            ttitleCellLable = new PdfPCell(new Phrase("收货方单位名称：", contentFont));
            ttitleCellLable.setUseAscender(true);
            ttitleCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            ttitleCellLable.setColspan(2);
            tableBaseInfo.addCell(ttitleCellLable);
            tvalueCellLable = new PdfPCell(new Phrase(buyerName, contentFont));
            tvalueCellLable.setUseAscender(true);
            tvalueCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            tvalueCellLable.setColspan(7);
            tableBaseInfo.addCell(tvalueCellLable);
            /*******************/
            ttitleCellLable = new PdfPCell(new Phrase("收货方地址：", contentFont));
            ttitleCellLable.setUseAscender(true);
            ttitleCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            ttitleCellLable.setColspan(2);
            tableBaseInfo.addCell(ttitleCellLable);
            tvalueCellLable = new PdfPCell(new Phrase(receAddress, contentFont));
            tvalueCellLable.setUseAscender(true);
            tvalueCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            tvalueCellLable.setColspan(7);
            tableBaseInfo.addCell(tvalueCellLable);
            ttitleCellLable = new PdfPCell(new Phrase("收货方联系方式：", contentFont));
            ttitleCellLable.setUseAscender(true);
            ttitleCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            ttitleCellLable.setColspan(2);
            tableBaseInfo.addCell(ttitleCellLable);
            tvalueCellLable = new PdfPCell(new Phrase(String.format(STR_FORMAT,receHuman,recePhone), contentFont));
            tvalueCellLable.setUseAscender(true);
            tvalueCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            tvalueCellLable.setColspan(7);
            tableBaseInfo.addCell(tvalueCellLable);
            /*******************/
            ttitleCellLable = new PdfPCell(new Phrase("福布客服：", contentFont));
            ttitleCellLable.setUseAscender(true);
            ttitleCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            ttitleCellLable.setColspan(2);
            tableBaseInfo.addCell(ttitleCellLable);
            tvalueCellLable = new PdfPCell(new Phrase("", contentFont));
            tvalueCellLable.setUseAscender(true);
            tvalueCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            tvalueCellLable.setColspan(7);
            tableBaseInfo.addCell(tvalueCellLable);
            /*******************/
            ttitleCellLable = new PdfPCell(new Phrase("福布客服联系电话：", contentFont));
            ttitleCellLable.setUseAscender(true);
            ttitleCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            ttitleCellLable.setColspan(2);
            tableBaseInfo.addCell(ttitleCellLable);
            tvalueCellLable = new PdfPCell(new Phrase("", contentFont));
            tvalueCellLable.setUseAscender(true);
            tvalueCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            tvalueCellLable.setColspan(7);
            tableBaseInfo.addCell(tvalueCellLable);
            /*******************/
            ttitleCellLable = new PdfPCell(new Phrase("合同签订重量("+unitName+")：", contentFont));
            ttitleCellLable.setUseAscender(true);
            ttitleCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            ttitleCellLable.setColspan(2);
            tableBaseInfo.addCell(ttitleCellLable);
            tvalueCellLable = new PdfPCell(new Phrase(totalWeight, contentFont));
            tvalueCellLable.setUseAscender(true);
            tvalueCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            tvalueCellLable.setColspan(7);
            tableBaseInfo.addCell(tvalueCellLable);
            /*******************/
            ttitleCellLable = new PdfPCell(new Phrase("合同签订总金额：", contentFont));
            ttitleCellLable.setUseAscender(true);
            ttitleCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            ttitleCellLable.setColspan(2);
            tableBaseInfo.addCell(ttitleCellLable);
            tvalueCellLable = new PdfPCell(new Phrase(numberToStr(orderTotalAmount), contentFont));
            tvalueCellLable.setUseAscender(true);
            tvalueCellLable.setUseDescender(true);
            tvalueCellLable.setBorderWidth(0.1f);
            tvalueCellLable.setColspan(7);
            tableBaseInfo.addCell(tvalueCellLable);
            /*******************/
            ttitleCellLable = new PdfPCell(new Phrase("合同首付金额：", contentFont));
            ttitleCellLable.setUseAscender(true);
            ttitleCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            ttitleCellLable.setColspan(2);
            tableBaseInfo.addCell(ttitleCellLable);
            tvalueCellLable = new PdfPCell(new Phrase(numberToStr(orderAdownPayment), contentFont));
            tvalueCellLable.setUseAscender(true);
            tvalueCellLable.setUseDescender(true);
            tvalueCellLable.setBorderWidth(0.1f);
            tvalueCellLable.setColspan(7);
            tableBaseInfo.addCell(tvalueCellLable);
            /*******************/
            ttitleCellLable = new PdfPCell(new Phrase("合同尾款金额：", contentFont));
            ttitleCellLable.setUseAscender(true);
            ttitleCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            ttitleCellLable.setColspan(2);
            tableBaseInfo.addCell(ttitleCellLable);
            tvalueCellLable = new PdfPCell(new Phrase(numberToStr(orderFinalAmount), contentFont));
            tvalueCellLable.setUseAscender(true);
            tvalueCellLable.setUseDescender(true);
            tvalueCellLable.setBorderWidth(0.1f);
            tvalueCellLable.setColspan(7);
            tableBaseInfo.addCell(tvalueCellLable);
            /*******************/
            ttitleCellLable = new PdfPCell(new Phrase(" ", contentFont));
            ttitleCellLable.setUseAscender(true);
            ttitleCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            ttitleCellLable.setColspan(2);
            tableBaseInfo.addCell(ttitleCellLable);
            tvalueCellLable = new PdfPCell(new Phrase(" ", contentFont));
            tvalueCellLable.setUseAscender(true);
            tvalueCellLable.setUseDescender(true);
            tvalueCellLable.setBorderWidth(0.1f);
            tvalueCellLable.setColspan(7);
            tableBaseInfo.addCell(tvalueCellLable);
            /*******************/
            ttitleCellLable = new PdfPCell(new Phrase("发货日期", contentFont));
            ttitleCellLable.setUseAscender(true);
            ttitleCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            ttitleCellLable.setColspan(2);
            tableBaseInfo.addCell(ttitleCellLable);
            tvalueCellLable = new PdfPCell(new Phrase(deliveryDate, contentFont));
            tvalueCellLable.setUseAscender(true);
            tvalueCellLable.setUseDescender(true);
            tvalueCellLable.setBorderWidth(0.1f);
            tvalueCellLable.setColspan(7);
            tableBaseInfo.addCell(tvalueCellLable);
            /*******************/
            ttitleCellLable = new PdfPCell(new Phrase("合同号", contentFont));
            ttitleCellLable.setUseAscender(true);
            ttitleCellLable.setUseDescender(true);
            ttitleCellLable.setBorderWidth(0.1f);
            ttitleCellLable.setColspan(2);
            tableBaseInfo.addCell(ttitleCellLable);
            tvalueCellLable = new PdfPCell(new Phrase(String.format(ORDER_NO_FORMAT,orderSn), contentFont));
            tvalueCellLable.setUseAscender(true);
            tvalueCellLable.setUseDescender(true);
            tvalueCellLable.setBorderWidth(0.1f);
            tvalueCellLable.setColspan(7);
            tableBaseInfo.addCell(tvalueCellLable);
            /***发货明细**/
            if(ConvertUtils.isNotEmpty(deliveryPros)){
                deliveryPros.forEach(deliveryPro -> {
                    String proName = String.format(STR_FORMAT,deliveryPro.getProName(),deliveryPro.getSpcecs());
                    PdfPCell fitleCellLable = new PdfPCell(new Phrase("货物明细：", contentFont));
                    fitleCellLable.setUseAscender(true);
                    fitleCellLable.setUseDescender(true);
                    fitleCellLable.setBorderWidth(0.1f);
                    fitleCellLable.setColspan(2);
                    tableBaseInfo.addCell(fitleCellLable);
                    PdfPCell fvalueCellLable = new PdfPCell(new Phrase(proName, contentFont));
                    fvalueCellLable.setUseAscender(true);
                    fvalueCellLable.setUseDescender(true);
                    fvalueCellLable.setBorderWidth(0.1f);
                    fvalueCellLable.setColspan(7);
                    tableBaseInfo.addCell(fvalueCellLable);
                    PdfPCell tttitleCellLable = new PdfPCell(new Phrase("实际发货数量("+unitName+")：", contentFont));
                    tttitleCellLable.setUseAscender(true);
                    tttitleCellLable.setUseDescender(true);
                    tttitleCellLable.setBorderWidth(0.1f);
                    tttitleCellLable.setColspan(2);
                    tableBaseInfo.addCell(tttitleCellLable);
                    PdfPCell ttvalueCellLable = new PdfPCell(new Phrase(deliveryPro.getDeliveryTotalWeight(), contentFont));
                    ttvalueCellLable.setUseAscender(true);
                    ttvalueCellLable.setUseDescender(true);
                    ttvalueCellLable.setBorderWidth(0.1f);
                    ttvalueCellLable.setColspan(7);
                    tableBaseInfo.addCell(ttvalueCellLable);
                    tttitleCellLable = new PdfPCell(new Phrase("单价：", contentFont));
                    tttitleCellLable.setUseAscender(true);
                    tttitleCellLable.setUseDescender(true);
                    tttitleCellLable.setBorderWidth(0.1f);
                    tttitleCellLable.setColspan(2);
                    tableBaseInfo.addCell(tttitleCellLable);
                    ttvalueCellLable = new PdfPCell(new Phrase(numberToStr(deliveryPro.getPrice()), contentFont));
                    ttvalueCellLable.setUseAscender(true);
                    ttvalueCellLable.setUseDescender(true);
                    ttvalueCellLable.setBorderWidth(0.1f);
                    ttvalueCellLable.setColspan(7);
                    tableBaseInfo.addCell(ttvalueCellLable);
                });
                /*******************/
                ttitleCellLable = new PdfPCell(new Phrase("运费：", contentFont));
                ttitleCellLable.setUseAscender(true);
                ttitleCellLable.setUseDescender(true);
                ttitleCellLable.setBorderWidth(0.1f);
                ttitleCellLable.setColspan(2);
                tableBaseInfo.addCell(ttitleCellLable);
                if(ConvertUtils.isEmpty(freight)){
                    freight = BigDecimal.ZERO;
                }
                tvalueCellLable = new PdfPCell(new Phrase(numberToStr(freight), contentFont));
                tvalueCellLable.setUseAscender(true);
                tvalueCellLable.setUseDescender(true);
                tvalueCellLable.setBorderWidth(0.1f);
                tvalueCellLable.setColspan(7);
                tableBaseInfo.addCell(tvalueCellLable);
                /*******************/
                ttitleCellLable = new PdfPCell(new Phrase("发货应收金额：", contentFont));
                ttitleCellLable.setUseAscender(true);
                ttitleCellLable.setUseDescender(true);
                ttitleCellLable.setBorderWidth(0.1f);
                ttitleCellLable.setColspan(2);
                tableBaseInfo.addCell(ttitleCellLable);
                tvalueCellLable = new PdfPCell(new Phrase(numberToStr(deliveryTotalAmount), contentFont));
                tvalueCellLable.setUseAscender(true);
                tvalueCellLable.setUseDescender(true);
                tvalueCellLable.setBorderWidth(0.1f);
                tvalueCellLable.setColspan(7);
                tableBaseInfo.addCell(tvalueCellLable);
                /*******************/
                ttitleCellLable = new PdfPCell(new Phrase(" ", contentFont));
                ttitleCellLable.setUseAscender(true);
                ttitleCellLable.setUseDescender(true);
                ttitleCellLable.setBorderWidth(0.1f);
                ttitleCellLable.setColspan(2);
                tableBaseInfo.addCell(ttitleCellLable);
                tvalueCellLable = new PdfPCell(new Phrase(" ", contentFont));
                tvalueCellLable.setUseAscender(true);
                tvalueCellLable.setUseDescender(true);
                tvalueCellLable.setBorderWidth(0.1f);
                tvalueCellLable.setColspan(7);
                tableBaseInfo.addCell(tvalueCellLable);
                /*******************/
                ttitleCellLable = new PdfPCell(new Phrase("实收首付款：", contentFont));
                ttitleCellLable.setUseAscender(true);
                ttitleCellLable.setUseDescender(true);
                ttitleCellLable.setBorderWidth(0.1f);
                ttitleCellLable.setColspan(2);
                tableBaseInfo.addCell(ttitleCellLable);
                tvalueCellLable = new PdfPCell(new Phrase(numberToStr(paidInAmount), contentFont));
                tvalueCellLable.setUseAscender(true);
                tvalueCellLable.setUseDescender(true);
                tvalueCellLable.setBorderWidth(0.1f);
                tvalueCellLable.setColspan(7);
                tableBaseInfo.addCell(tvalueCellLable);
                /*******************/
                ttitleCellLable = new PdfPCell(new Phrase("实发货款金额：", contentFont));
                ttitleCellLable.setUseAscender(true);
                ttitleCellLable.setUseDescender(true);
                ttitleCellLable.setBorderWidth(0.1f);
                ttitleCellLable.setColspan(2);
                tableBaseInfo.addCell(ttitleCellLable);
                tvalueCellLable = new PdfPCell(new Phrase(numberToStr(paidDeliveryAmount), contentFont));
                tvalueCellLable.setUseAscender(true);
                tvalueCellLable.setUseDescender(true);
                tvalueCellLable.setBorderWidth(0.1f);
                tvalueCellLable.setColspan(7);
                tableBaseInfo.addCell(tvalueCellLable);
                /*******************/
                ttitleCellLable = new PdfPCell(new Phrase("差额：", contentFont));
                ttitleCellLable.setUseAscender(true);
                ttitleCellLable.setUseDescender(true);
                ttitleCellLable.setBorderWidth(0.1f);
                ttitleCellLable.setColspan(2);
                tableBaseInfo.addCell(ttitleCellLable);
                tvalueCellLable = new PdfPCell(new Phrase(numberToStr(difAmount), contentFont));
                tvalueCellLable.setUseAscender(true);
                tvalueCellLable.setUseDescender(true);
                tvalueCellLable.setBorderWidth(0.1f);
                tvalueCellLable.setColspan(7);
                tableBaseInfo.addCell(tvalueCellLable);
                /*******************/
                ttitleCellLable = new PdfPCell(new Phrase(" ", contentFont));
                ttitleCellLable.setUseAscender(true);
                ttitleCellLable.setUseDescender(true);
                ttitleCellLable.setBorderWidth(0.1f);
                ttitleCellLable.setColspan(2);
                tableBaseInfo.addCell(ttitleCellLable);
                tvalueCellLable = new PdfPCell(new Phrase(" ", contentFont));
                tvalueCellLable.setUseAscender(true);
                tvalueCellLable.setUseDescender(true);
                tvalueCellLable.setBorderWidth(0.1f);
                tvalueCellLable.setColspan(7);
                tableBaseInfo.addCell(tvalueCellLable);
                /*******************/
                ttitleCellLable = new PdfPCell(new Phrase(" ", contentFont));
                ttitleCellLable.setUseAscender(true);
                ttitleCellLable.setUseDescender(true);
                ttitleCellLable.setBorderWidth(0.1f);
                ttitleCellLable.setColspan(2);
                tableBaseInfo.addCell(ttitleCellLable);
                tvalueCellLable = new PdfPCell(new Phrase(" ", contentFont));
                tvalueCellLable.setUseAscender(true);
                tvalueCellLable.setUseDescender(true);
                tvalueCellLable.setBorderWidth(0.1f);
                tvalueCellLable.setColspan(7);
                tableBaseInfo.addCell(tvalueCellLable);
                /*******************/
                ttitleCellLable = new PdfPCell(new Phrase(" ", contentFont));
                ttitleCellLable.setUseAscender(true);
                ttitleCellLable.setUseDescender(true);
                ttitleCellLable.setBorderWidth(0.1f);
                ttitleCellLable.setColspan(2);
                tableBaseInfo.addCell(ttitleCellLable);
                tvalueCellLable = new PdfPCell(new Phrase(" ", contentFont));
                tvalueCellLable.setUseAscender(true);
                tvalueCellLable.setUseDescender(true);
                tvalueCellLable.setBorderWidth(0.1f);
                tvalueCellLable.setColspan(7);
                tableBaseInfo.addCell(tvalueCellLable);
                /*******************/
                ttitleCellLable = new PdfPCell(new Phrase("备注：", contentFont));
                ttitleCellLable.setUseAscender(true);
                ttitleCellLable.setUseDescender(true);
                ttitleCellLable.setBorderWidth(0.1f);
                ttitleCellLable.setColspan(2);
                tableBaseInfo.addCell(ttitleCellLable);
                tvalueCellLable = new PdfPCell(new Phrase(orderStasuts, contentFont));
                tvalueCellLable.setUseAscender(true);
                tvalueCellLable.setUseDescender(true);
                tvalueCellLable.setBorderWidth(0.1f);
                tvalueCellLable.setColspan(7);
                tableBaseInfo.addCell(tvalueCellLable);
                /*******************/
                ttitleCellLable = new PdfPCell(new Phrase("", contentFont));
                ttitleCellLable.setUseAscender(true);
                ttitleCellLable.setUseDescender(true);
                ttitleCellLable.setBorder(0);
                ttitleCellLable.setBorderWidthTop( 0.0f);
                ttitleCellLable.setBorderWidthLeft(0.0f);
                ttitleCellLable.setColspan(2);
                tableBaseInfo.addCell(ttitleCellLable);
                tvalueCellLable = new PdfPCell(new Phrase("", contentFont));
                tvalueCellLable.setUseAscender(true);
                tvalueCellLable.setUseDescender(true);
                tvalueCellLable.setBorder(0);
                tvalueCellLable.setBorderWidthTop( 0.0f);
                tvalueCellLable.setBorderWidthLeft(0.0f);
                tvalueCellLable.setColspan(7);
                tableBaseInfo.addCell(tvalueCellLable);
                /*******************/
                ttitleCellLable = new PdfPCell(new Phrase("复核:", contentFont));
                ttitleCellLable.setPaddingTop(10.0f);
                ttitleCellLable.setUseAscender(true);
                ttitleCellLable.setUseDescender(true);
                ttitleCellLable.setBorder(0);
                ttitleCellLable.setBorderWidthTop( 0.0f);
                ttitleCellLable.setBorderWidthLeft(0.0f);
                ttitleCellLable.setColspan(1);
                tableBaseInfo.addCell(ttitleCellLable);
                tvalueCellLable = new PdfPCell(new Phrase("", contentFont));
                tvalueCellLable.setPaddingTop(10.0f);
                tvalueCellLable.setUseAscender(true);
                tvalueCellLable.setUseDescender(true);
                tvalueCellLable.setBorder(0);
                tvalueCellLable.setBorderWidthTop( 0.0f);
                tvalueCellLable.setBorderWidthLeft(0.0f);
                tvalueCellLable.setColspan(2);
                tableBaseInfo.addCell(tvalueCellLable);
                ttitleCellLable = new PdfPCell(new Phrase("审核:", contentFont));
                ttitleCellLable.setPaddingTop(10.0f);
                ttitleCellLable.setUseAscender(true);
                ttitleCellLable.setUseDescender(true);
                ttitleCellLable.setBorder(0);
                ttitleCellLable.setBorderWidthTop( 0.0f);
                ttitleCellLable.setBorderWidthLeft(0.0f);
                ttitleCellLable.setColspan(1);
                tableBaseInfo.addCell(ttitleCellLable);
                tvalueCellLable = new PdfPCell(new Phrase("", contentFont));
                tvalueCellLable.setPaddingTop(10.0f);
                tvalueCellLable.setUseAscender(true);
                tvalueCellLable.setUseDescender(true);
                tvalueCellLable.setBorder(0);
                tvalueCellLable.setBorderWidthTop( 0.0f);
                tvalueCellLable.setBorderWidthLeft(0.0f);
                tvalueCellLable.setColspan(2);
                tableBaseInfo.addCell(tvalueCellLable);
                ttitleCellLable = new PdfPCell(new Phrase("制表:", contentFont));
                ttitleCellLable.setPaddingTop(10.0f);
                ttitleCellLable.setUseAscender(true);
                ttitleCellLable.setUseDescender(true);
                ttitleCellLable.setBorder(0);
                ttitleCellLable.setBorderWidthTop( 0.0f);
                ttitleCellLable.setBorderWidthLeft(0.0f);
                ttitleCellLable.setColspan(1);
                tableBaseInfo.addCell(ttitleCellLable);
                tvalueCellLable = new PdfPCell(new Phrase("", contentFont));
                tvalueCellLable.setPaddingTop(10.0f);
                tvalueCellLable.setUseAscender(true);
                tvalueCellLable.setUseDescender(true);
                tvalueCellLable.setBorder(0);
                tvalueCellLable.setBorderWidthTop( 0.0f);
                tvalueCellLable.setBorderWidthLeft(0.0f);
                tvalueCellLable.setColspan(2);
                tableBaseInfo.addCell(tvalueCellLable);
        }
        document.add(tableBaseInfo);
    }

    /***
     *
     * @param
     * @return
     */
    private String numberToStr(BigDecimal obj){
        java.text.DecimalFormat  df = new DecimalFormat("￥#,##0.000");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(obj);
    }







    /****
     * 生成合同
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public  String generateContract()
            throws IOException, DocumentException {
        String uploadDir = SETTING.getUploadDir();
        String contractPath = SETTING.getTempleatePath();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(MONTH_FMT);
        String monthDir = sdf.format(new Date());
        String tFilePath = uploadDir + contractPath + File.separator + monthDir;
        File monthFile = new File(tFilePath);
        if (!monthFile.exists()) {
            monthFile.mkdir();
        }
        String fileName = UUID.randomUUID() + ".pdf";
        String filePath = tFilePath + File.separator + fileName;
        File file = new File(filePath);
        String url = contractPath + File.separator + monthDir + File.separator + fileName;
        //生成PDF文档
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        // 标题
        createTitle(document);
        /***主题信息
         * ***/
        createDetailsTable(document);
        //信息核验
        document.close();
        return SETTING.getFileUrl() + url;
    }


    public static void main(String[] args)
            throws IOException, DocumentException {
        List<ReconContractProduct> reconContractProducts = new ArrayList<>();
        ReconContractProduct reconContractProduct = new ReconContractProduct();
        reconContractProduct.setProName("普白亲水ES短纤");
        reconContractProduct.setSpecs("成分PP/PE,2D*38mm");
        reconContractProduct.setPrice(new BigDecimal("26.7"));
        reconContractProducts.add(reconContractProduct);
        /***发货数量*/
        List<DeliveryPro> deliveryPros = new ArrayList<>();
        DeliveryPro deliveryPro = new DeliveryPro();
        deliveryPro.setProName("普白亲水ES短纤");
        deliveryPro.setSpcecs("成分PP/PE,2D*38mm");
        deliveryPro.setPrice(new BigDecimal("26.7"));
        deliveryPro.setDeliveryTotalWeight("838.2");
        deliveryPros.add(deliveryPro);
        ReconContractBuildImpl reconContractBuildImpl = new ReconContractBuildImpl();
        reconContractBuildImpl
                .setTitle("化纤平台")
                .setSellerName( "张家港佰陆纺织有限公司")
                .setBuyerName("济南德宝瑞新材料有限公司")
                .setOrderSn( "2019008")
                .setTotalWeight("838.2")
                .setUnitName("kg")
                .setReconContractProducts(reconContractProducts)
                .setDeliveryPros(deliveryPros)
                .setOrderDate("2020年4月9日")
                .setDeliveryDate("2020年4月10日")
                 .setDifAmount(BigDecimal.ZERO)
                .setFreight(BigDecimal.ZERO)
                .setDeliveryTotalAmount(new BigDecimal("22217.60"))
                .setOrderAdownPayment(new BigDecimal("22217.60"))
                .setOrderFinalAmount(new BigDecimal("22217.60"))
                .setPaidInAmount(new BigDecimal("22217.60"))
                .setPaidDeliveryAmount(new BigDecimal("22217.60"))
                .setReceHuman("沈总")
                .setRecePhone("13585914169")
                .setReceAddress("上海市金山区枫泾镇工业区霆锋公路")
                .setOrderStasuts("补款单未付款")
                .setOrderTotalAmount(new BigDecimal("23000.00"));
        reconContractBuildImpl.generateContract();
    }
}
