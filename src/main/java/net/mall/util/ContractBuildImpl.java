package net.mall.util;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import net.mall.Setting;
import net.mall.entity.Order;
import net.mall.model.ContractModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

/***采购合同
 */
public class ContractBuildImpl  extends ContractModel {


    private  final String ORDER_NO_FORMAT = "PO＃%s";
    private  final String MONTH_FMT = "yyyyMM";
    private  final String STR_FORMAT = "%s%s";

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
        PdfPCell cell = new PdfPCell(new Phrase(companyTtile, titleFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(9);
        cell.setBorder(0);
        table.addCell(cell);
        /****采购合同****/
        PdfPCell twoCell = new PdfPCell(new Phrase("采购合同", titleFont));
        twoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        twoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        twoCell.setColspan(9);
        twoCell.setBorder(0);
        table.addCell(twoCell);
        document.add(table);
    }


    /***
     * 创建主题
     * @param document
     * @throws DocumentException
     * @throws IOException
     */
    private  void createSubjectTable(Document document)
            throws DocumentException, IOException {
        PdfPTable tableBaseInfo = new PdfPTable(9);
        tableBaseInfo.setWidthPercentage(100); // 宽度100%填充
        tableBaseInfo.setSpacingBefore(10f); // 前间距
        /****字体****/
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font contentFont = new Font(baseFont, 12f);
        /***************1行*******************************/
        /****买方
         * **/
        PdfPCell sellerCellLable = new PdfPCell(new Phrase("甲方（卖方）：", contentFont));
        sellerCellLable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        sellerCellLable.setVerticalAlignment(Element.ALIGN_MIDDLE);
        sellerCellLable.setUseAscender(true);
        sellerCellLable.setUseDescender(true);
        sellerCellLable.setBorder(0);
        sellerCellLable.setBorderWidthTop( 0.0f);
        sellerCellLable.setBorderWidthLeft(0.0f);
        sellerCellLable.setColspan(2);
        sellerCellLable.setPaddingTop(10f);
        tableBaseInfo.addCell(sellerCellLable);
        PdfPCell sellerCell = new PdfPCell();
        sellerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        sellerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        sellerCell.setUseAscender(true);
        sellerCell.setUseDescender(true);
        sellerCell.setBorder(0);
        sellerCell.setBorderWidthTop( 0.0f);
        sellerCell.setBorderWidthLeft(0.0f);
        sellerCell.setColspan(3);
        sellerCell.setPaddingTop(10f);
        sellerCell.addElement(new Paragraph(sellerName, contentFont));
        tableBaseInfo.addCell(sellerCell);
        /******合同编号***/
        PdfPCell contractCellLable = new PdfPCell(new Phrase("合同编号：", contentFont));
        contractCellLable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        contractCellLable.setVerticalAlignment(Element.ALIGN_MIDDLE);
        contractCellLable.setUseAscender(true);
        contractCellLable.setUseDescender(true);
        contractCellLable.setBorder(0);
        contractCellLable.setBorderWidthTop( 0.0f);
        contractCellLable.setBorderWidthLeft(0.0f);
        contractCellLable.setColspan(2);
        contractCellLable.setPaddingTop(10f);
        tableBaseInfo.addCell(contractCellLable);
        PdfPCell contractCell = new PdfPCell();
        contractCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        contractCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        contractCell.setUseAscender(true);
        contractCell.setUseDescender(true);
        contractCell.setBorder(0);
        contractCell.setBorderWidthTop( 0.0f);
        contractCell.setBorderWidthLeft(0.0f);
        contractCell.setColspan(2);
        contractCell.addElement(new Paragraph(String.format(ORDER_NO_FORMAT,orderNo), contentFont));
        contractCell.setPaddingTop(10f);
        tableBaseInfo.addCell(contractCell);
        /***************2行*******************************/
        /****买方
         * **/
        PdfPCell buyCellLable = new PdfPCell(new Phrase("乙方（买方）：", contentFont));
        buyCellLable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        buyCellLable.setVerticalAlignment(Element.ALIGN_MIDDLE);
        buyCellLable.setUseAscender(true);
        buyCellLable.setUseDescender(true);
        buyCellLable.setBorder(0);
        buyCellLable.setBorderWidthTop( 0.0f);
        buyCellLable.setBorderWidthLeft(0.0f);
        buyCellLable.setColspan(2);
        buyCellLable.setPaddingTop(10f);
        tableBaseInfo.addCell(buyCellLable);
        PdfPCell buyCell = new PdfPCell();
        buyCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        buyCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        buyCell.setUseAscender(true);
        buyCell.setUseDescender(true);
        buyCell.setBorder(0);
        buyCell.setBorderWidthTop( 0.0f);
        buyCell.setBorderWidthLeft(0.0f);
        buyCell.setColspan(3);
        buyCell.addElement(new Paragraph(buyName, contentFont));
        buyCell.setPaddingTop(10f);
        tableBaseInfo.addCell(buyCell);
        /******合同编号***/
        PdfPCell signedCellLable = new PdfPCell(new Phrase("签订地点：", contentFont));
        signedCellLable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        signedCellLable.setVerticalAlignment(Element.ALIGN_MIDDLE);
        signedCellLable.setUseAscender(true);
        signedCellLable.setUseDescender(true);
        signedCellLable.setBorder(0);
        signedCellLable.setBorderWidthTop( 0.0f);
        signedCellLable.setBorderWidthLeft(0.0f);
        signedCellLable.setColspan(2);
        signedCellLable.setPaddingTop(10f);
        tableBaseInfo.addCell(signedCellLable);
        PdfPCell signedCell = new PdfPCell();
        signedCell.setUseAscender(true);
        signedCell.setUseDescender(true);
        signedCell.setBorder(0);
        signedCell.setBorderWidthTop( 0.0f);
        signedCell.setBorderWidthLeft(0.0f);
        signedCell.setColspan(2);
        signedCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        signedCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        signedCell.addElement(new Paragraph("上海", contentFont));
        signedCell.setPaddingTop(10f);
        tableBaseInfo.addCell(signedCell);
        document.add(tableBaseInfo);
    }


    /***
     * 产品明细
     * @param document
     * @throws DocumentException
     * @throws IOException
     */
    private  void createProductDetailsTable(Document document)
            throws DocumentException, IOException {
        PdfPTable tableBaseInfo = new PdfPTable(9);
        tableBaseInfo.setWidthPercentage(100); // 宽度100%填充
        tableBaseInfo.setSpacingBefore(10f); // 前间距
        int rowItemSize = items.size();
        /****字体****/
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font contentFont = new Font(baseFont, 12f);
        /***************1行*******************************/
        PdfPCell titleCellLable = new PdfPCell(new Phrase("一、产品明细", contentFont));
        titleCellLable.setUseAscender(true);
        titleCellLable.setUseDescender(true);
        titleCellLable.setBorder(0);
        titleCellLable.setBorderWidthTop( 0.0f);
        titleCellLable.setBorderWidthLeft(0.0f);
        titleCellLable.setColspan(9);
        tableBaseInfo.addCell(titleCellLable);
        /***************2行*******************************/
        PdfPCell proNameCellLable = new PdfPCell(new Phrase("品名", contentFont));
        proNameCellLable.setUseAscender(true);
        proNameCellLable.setUseDescender(true);
        proNameCellLable.setHorizontalAlignment(Element.ALIGN_LEFT);
        proNameCellLable.setVerticalAlignment(Element.ALIGN_CENTER);
        proNameCellLable.setBorderWidth(0.1f);
        proNameCellLable.setColspan(2);
        tableBaseInfo.addCell(proNameCellLable);
        PdfPCell quantityCellLable = new PdfPCell(new Phrase("数量", contentFont));
        quantityCellLable.setUseAscender(true);
        quantityCellLable.setUseDescender(true);
        quantityCellLable.setHorizontalAlignment(Element.ALIGN_CENTER);
        quantityCellLable.setVerticalAlignment(Element.ALIGN_CENTER);
        quantityCellLable.setBorderWidth(0.1f);
        tableBaseInfo.addCell(quantityCellLable);
        PdfPCell unitPriceCellLable = new PdfPCell(new Phrase("单价（含税）", contentFont));
        unitPriceCellLable.setUseAscender(true);
        unitPriceCellLable.setUseDescender(true);
        unitPriceCellLable.setHorizontalAlignment(Element.ALIGN_CENTER);
        unitPriceCellLable.setVerticalAlignment(Element.ALIGN_CENTER);
        unitPriceCellLable.setBorderWidth(0.1f);
        unitPriceCellLable.setColspan(2);
        tableBaseInfo.addCell(unitPriceCellLable);
        PdfPCell totalAmountCellLable = new PdfPCell(new Phrase("合计金额", contentFont));
        totalAmountCellLable.setUseAscender(true);
        totalAmountCellLable.setUseDescender(true);
        totalAmountCellLable.setHorizontalAlignment(Element.ALIGN_CENTER);
        totalAmountCellLable.setVerticalAlignment(Element.ALIGN_CENTER);
        totalAmountCellLable.setBorderWidth(0.1f);
        totalAmountCellLable.setColspan(2);
        tableBaseInfo.addCell(totalAmountCellLable);
        PdfPCell noteCellLable = new PdfPCell(new Phrase("备注", contentFont));
        noteCellLable.setUseAscender(true);
        noteCellLable.setUseDescender(true);
        noteCellLable.setHorizontalAlignment(Element.ALIGN_CENTER);
        noteCellLable.setVerticalAlignment(Element.ALIGN_CENTER);
        noteCellLable.setBorderWidth(0.1f);
        noteCellLable.setColspan(2);
        tableBaseInfo.addCell(noteCellLable);
        Map<String,Boolean> isAddMap = new HashMap<>();
        items.stream().forEach(tMap -> {
            PdfPCell proNameCel = new PdfPCell(new Phrase(tMap.get("proName").toString(), contentFont));
            proNameCel.setUseAscender(true);
            proNameCel.setUseDescender(true);
            proNameCel.setHorizontalAlignment(Element.ALIGN_CENTER);
            proNameCel.setVerticalAlignment(Element.ALIGN_CENTER);
            proNameCel.setBorderWidth(0.1f);
            proNameCel.setColspan(2);
            tableBaseInfo.addCell(proNameCel);
            PdfPCell quantityCell = new PdfPCell(new Phrase(tMap.get("quantity").toString(), contentFont));
            quantityCell.setUseAscender(true);
            quantityCell.setUseDescender(true);
            quantityCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            quantityCell.setVerticalAlignment(Element.ALIGN_CENTER);
            quantityCell.setBorderWidth(0.1f);
            tableBaseInfo.addCell(quantityCell);
            PdfPCell unitPriceCell = new PdfPCell(new Phrase(numberToStr(tMap.get("unitPrice")), contentFont));
            unitPriceCell.setUseAscender(true);
            unitPriceCell.setUseDescender(true);
            unitPriceCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            unitPriceCell.setVerticalAlignment(Element.ALIGN_CENTER);
            unitPriceCell.setBorderWidth(0.1f);
            unitPriceCell.setColspan(2);
            tableBaseInfo.addCell(unitPriceCell);
            PdfPCell totalAmountCell = new PdfPCell(new Phrase(numberToStr(tMap.get("totalAmount")), contentFont));
            totalAmountCell.setUseAscender(true);
            totalAmountCell.setUseDescender(true);
            totalAmountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            totalAmountCell.setVerticalAlignment(Element.ALIGN_CENTER);
            totalAmountCell.setBorderWidth(0.1f);
            totalAmountCell.setColspan(2);
            tableBaseInfo.addCell(totalAmountCell);
            //
            if(!isAddMap.containsKey("isAdd")
                    || !isAddMap.get("isAdd")){
                PdfPCell noteCell = new PdfPCell(new Phrase((ConvertUtils.isNotEmpty(tMap.get("note"))?tMap.get("note").toString():""), contentFont));
                noteCell.setUseAscender(true);
                noteCell.setUseDescender(true);
                noteCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                noteCell.setVerticalAlignment(Element.ALIGN_CENTER);
                noteCell.setBorderWidth(0.1f);
                noteCell.setColspan(2);
                noteCell.setRowspan((rowItemSize+3));
                tableBaseInfo.addCell(noteCell);
                isAddMap.put("isAdd",true);
            }
        });
        /****合计金额**/
        PdfPCell totalCellLable = new PdfPCell(new Phrase("合计金额", contentFont));
        totalCellLable.setUseAscender(true);
        totalCellLable.setUseDescender(true);
        totalCellLable.setHorizontalAlignment(Element.ALIGN_LEFT);
        totalCellLable.setVerticalAlignment(Element.ALIGN_CENTER);
        totalCellLable.setBorderWidth(0.1f);
        totalCellLable.setColspan(2);
        tableBaseInfo.addCell(totalCellLable);
        PdfPCell totalCell = new PdfPCell(new Phrase(numberToStr(orderTotalAmount), contentFont));
        totalCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        totalCell.setVerticalAlignment(Element.ALIGN_CENTER);
        totalCell.setUseAscender(true);
        totalCell.setUseDescender(true);
        totalCell.setBorderWidth(0.1f);
        totalCell.setColspan(5);
        tableBaseInfo.addCell(totalCell);
        /****备注**/
        PdfPCell remarkCellLable = new PdfPCell(new Phrase("备注", contentFont));
        remarkCellLable.setUseAscender(true);
        remarkCellLable.setUseDescender(true);
        remarkCellLable.setHorizontalAlignment(Element.ALIGN_LEFT);
        remarkCellLable.setVerticalAlignment(Element.ALIGN_CENTER);
        remarkCellLable.setBorderWidth(0.1f);
        remarkCellLable.setColspan(2);
        tableBaseInfo.addCell(remarkCellLable);
        PdfPCell remarkCell = new PdfPCell(new Phrase("增值税发票价格", contentFont));
        remarkCell.setUseAscender(true);
        remarkCell.setUseDescender(true);
        remarkCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        remarkCell.setVerticalAlignment(Element.ALIGN_CENTER);
        remarkCell.setBorderWidth(0.1f);
        remarkCell.setColspan(5);
        tableBaseInfo.addCell(remarkCell);
        /****总金额（大写）**/
        PdfPCell totalCapitalCellLable = new PdfPCell(new Phrase("总金额（大写）：", contentFont));
        totalCapitalCellLable.setUseAscender(true);
        totalCapitalCellLable.setUseDescender(true);
        totalCapitalCellLable.setHorizontalAlignment(Element.ALIGN_LEFT);
        totalCapitalCellLable.setVerticalAlignment(Element.ALIGN_CENTER);
        totalCapitalCellLable.setBorderWidth(0.1f);
        totalCapitalCellLable.setColspan(2);
        tableBaseInfo.addCell(totalCapitalCellLable);
        PdfPCell totalCapitalCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"人民币：",ChineseNumber.getChineseNumber(orderTotalAmount.toString())), contentFont));
        totalCapitalCell.setUseAscender(true);
        totalCapitalCell.setUseDescender(true);
        totalCapitalCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        totalCapitalCell.setVerticalAlignment(Element.ALIGN_CENTER);
        totalCapitalCell.setBorderWidth(0.1f);
        totalCapitalCell.setColspan(5);
        tableBaseInfo.addCell(totalCapitalCell);
        document.add(tableBaseInfo);
    }


    /***
     * 协议
     * @param document
     * @throws DocumentException
     * @throws IOException
     */
    private  void createAgreementTable(Document document)
            throws DocumentException, IOException {
        PdfPTable tableBaseInfo = new PdfPTable(9);
        tableBaseInfo.setWidthPercentage(100); // 宽度100%填充
        tableBaseInfo.setSpacingBefore(10f); // 前间距
        int rowItemSize = items.size();
        /****字体****/
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font contentFont = new Font(baseFont, 12f);
        /***************1行*******************************/
        PdfPCell oneCellLable = new PdfPCell(new Phrase("二、质量标准：按照甲方提供的资料为准，乙方确认后发货。", contentFont));
        oneCellLable.setUseAscender(true);
        oneCellLable.setUseDescender(true);
        oneCellLable.setBorder(0);
        oneCellLable.setBorderWidthTop( 0.0f);
        oneCellLable.setBorderWidthLeft(0.0f);
        oneCellLable.setColspan(9);
        tableBaseInfo.addCell(oneCellLable);
        PdfPCell twoCellLable = new PdfPCell(new Phrase("三、包装形式：按照甲方出厂的包装形式。", contentFont));
        twoCellLable.setPaddingTop(10f);
        twoCellLable.setUseAscender(true);
        twoCellLable.setUseDescender(true);
        twoCellLable.setBorder(0);
        twoCellLable.setBorderWidthTop( 0.0f);
        twoCellLable.setBorderWidthLeft(0.0f);
        twoCellLable.setColspan(9);
        tableBaseInfo.addCell(twoCellLable);
        PdfPCell threeCellLable = new PdfPCell(new Phrase("四、交货时间：7个工作日内发货。", contentFont));
        threeCellLable.setUseAscender(true);
        threeCellLable.setUseDescender(true);
        threeCellLable.setBorder(0);
        threeCellLable.setBorderWidthTop( 0.0f);
        threeCellLable.setBorderWidthLeft(0.0f);
        threeCellLable.setColspan(9);
        threeCellLable.setPaddingTop(10f);
        tableBaseInfo.addCell(threeCellLable);
        PdfPCell fourCellLable = new PdfPCell(new Phrase(String.format(STR_FORMAT,"五、交货地点：",orderAddress), contentFont));
        fourCellLable.setUseAscender(true);
        fourCellLable.setUseDescender(true);
        fourCellLable.setBorder(0);
        fourCellLable.setBorderWidthTop( 0.0f);
        fourCellLable.setBorderWidthLeft(0.0f);
        fourCellLable.setColspan(9);
        fourCellLable.setPaddingTop(10f);
        tableBaseInfo.addCell(fourCellLable);
        PdfPCell sixCellLable = new PdfPCell(new Phrase("六、运输方式以及费用承担：乙方。", contentFont));
        sixCellLable.setUseAscender(true);
        sixCellLable.setUseDescender(true);
        sixCellLable.setBorder(0);
        sixCellLable.setBorderWidthTop( 0.0f);
        sixCellLable.setBorderWidthLeft(0.0f);
        sixCellLable.setColspan(9);
        sixCellLable.setPaddingTop(10f);
        tableBaseInfo.addCell(sixCellLable);
        PdfPCell sevenCellLable = new PdfPCell(new Phrase("七、产品验收：乙方对合同有异议的，须在收到货物的7个工作日内向甲方书面提出。", contentFont));
        sevenCellLable.setUseAscender(true);
        sevenCellLable.setUseDescender(true);
        sevenCellLable.setBorder(0);
        sevenCellLable.setBorderWidthTop( 0.0f);
        sevenCellLable.setBorderWidthLeft(0.0f);
        sevenCellLable.setColspan(9);
        sevenCellLable.setPaddingTop(10f);
        tableBaseInfo.addCell(sevenCellLable);
        PdfPCell eightCellLable = new PdfPCell(new Phrase("八、付款方式：100%款到发货。", contentFont));
        eightCellLable.setUseAscender(true);
        eightCellLable.setUseDescender(true);
        eightCellLable.setBorder(0);
        eightCellLable.setBorderWidthTop( 0.0f);
        eightCellLable.setBorderWidthLeft(0.0f);
        eightCellLable.setColspan(9);
        eightCellLable.setPaddingTop(10f);
        tableBaseInfo.addCell(eightCellLable);
        PdfPCell nineCellLable = new PdfPCell(new Phrase("九、合同争议的解决：本合同履行过程中如发生争议，由双方协商解决，协商不成向合同签订地人民法院起诉解决。", contentFont));
        nineCellLable.setUseAscender(true);
        nineCellLable.setUseDescender(true);
        nineCellLable.setBorder(0);
        nineCellLable.setBorderWidthTop( 0.0f);
        nineCellLable.setBorderWidthLeft(0.0f);
        nineCellLable.setColspan(9);
        nineCellLable.setPaddingTop(10f);
        tableBaseInfo.addCell(nineCellLable);
        PdfPCell tenCellLable = new PdfPCell(new Phrase("十、本合同字双方法定代表人或授权代表人签字并加盖双方合同章之日起生效。", contentFont));
        tenCellLable.setUseAscender(true);
        tenCellLable.setUseDescender(true);
        tenCellLable.setBorder(0);
        tenCellLable.setBorderWidthTop( 0.0f);
        tenCellLable.setBorderWidthLeft(0.0f);
        tenCellLable.setColspan(9);
        tenCellLable.setPaddingTop(10f);
        tableBaseInfo.addCell(tenCellLable);
        PdfPCell elevenCellLable = new PdfPCell(new Phrase("十一、本合同一式两份，双方各执一份。", contentFont));
        elevenCellLable.setUseAscender(true);
        elevenCellLable.setUseDescender(true);
        elevenCellLable.setBorder(0);
        elevenCellLable.setBorderWidthTop( 0.0f);
        elevenCellLable.setBorderWidthLeft(0.0f);
        elevenCellLable.setColspan(9);
        elevenCellLable.setPaddingTop(10f);
        tableBaseInfo.addCell(elevenCellLable);
        PdfPCell twelveCellLable = new PdfPCell(new Phrase("十二、乙方应提供合法的委印手续（未提供的视为甲方原创设计），确保不侵犯第三方知识产权，如有版权问题引起法律纠纷及经济损失由乙方承担。", contentFont));
        twelveCellLable.setUseAscender(true);
        twelveCellLable.setUseDescender(true);
        twelveCellLable.setBorder(0);
        twelveCellLable.setBorderWidthTop( 0.0f);
        twelveCellLable.setBorderWidthLeft(0.0f);
        twelveCellLable.setColspan(9);
        twelveCellLable.setPaddingTop(10f);
        tableBaseInfo.addCell(twelveCellLable);
        PdfPCell thirteenCellLable = new PdfPCell(new Phrase("十三、其他约定事宜：", contentFont));
        thirteenCellLable.setUseAscender(true);
        thirteenCellLable.setUseDescender(true);
        thirteenCellLable.setBorder(0);
        thirteenCellLable.setBorderWidthTop( 0.0f);
        thirteenCellLable.setBorderWidthLeft(0.0f);
        thirteenCellLable.setColspan(9);
        twelveCellLable.setPaddingTop(10f);
        tableBaseInfo.addCell(thirteenCellLable);
        /********其他事项约定******/
        PdfPCell ownerCellLable = new PdfPCell(new Phrase("甲方（卖方）", contentFont));
        ownerCellLable.setUseAscender(true);
        ownerCellLable.setUseDescender(true);
        ownerCellLable.setHorizontalAlignment(Element.ALIGN_CENTER);
        ownerCellLable.setVerticalAlignment(Element.ALIGN_CENTER);
        ownerCellLable.setBorderWidth(0.1f);
        ownerCellLable.setColspan(5);
        tableBaseInfo.addCell(ownerCellLable);
        PdfPCell secondCellLable = new PdfPCell(new Phrase("乙方（买方）", contentFont));
        secondCellLable.setUseAscender(true);
        secondCellLable.setUseDescender(true);
        secondCellLable.setHorizontalAlignment(Element.ALIGN_CENTER);
        secondCellLable.setVerticalAlignment(Element.ALIGN_CENTER);
        secondCellLable.setBorderWidth(0.1f);
        secondCellLable.setColspan(4);
        tableBaseInfo.addCell(secondCellLable);
        this.createOtherInfo(tableBaseInfo,contentFont);
        document.add(tableBaseInfo);
    }

    /***
     *
     * @param
     * @return
     */
    private String numberToStr(Object obj){
        java.text.DecimalFormat  df = new DecimalFormat("￥#,##0.000");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(obj);
    }




    /***
     * 设置详情
     * @param tableBaseInfo
     * @param contentFont
     */
    private void createOtherInfo(PdfPTable tableBaseInfo,Font contentFont){
        /*****甲方***/
        PdfPCell nameCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"甲方：",storeName), contentFont));
        nameCell.setUseAscender(true);
        nameCell.setUseDescender(true);
        nameCell.setBorderWidth(0.0f);
        nameCell.setBorderWidthLeft(0.1f);
        nameCell.setColspan(5);
        tableBaseInfo.addCell(nameCell);
        PdfPCell nameaCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"乙方：",memberName), contentFont));
        nameaCell.setUseAscender(true);
        nameaCell.setUseDescender(true);
        nameaCell.setBorderWidth(0.0f);
        nameaCell.setBorderWidthLeft(0.1f);
        nameaCell.setBorderWidthRight(0.1f);
        nameaCell.setColspan(4);
        tableBaseInfo.addCell(nameaCell);
        /****地址：******/
        PdfPCell addressCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"地址：",storeAdress), contentFont));
        addressCell.setPaddingTop(10f);
        addressCell.setUseAscender(true);
        addressCell.setUseDescender(true);
        addressCell.setBorderWidth(0.0f);
        addressCell.setBorderWidthLeft(0.1f);
        addressCell.setColspan(5);
        tableBaseInfo.addCell(addressCell);
        PdfPCell addressaCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"地址：",memberAddress), contentFont));
        addressaCell.setPaddingTop(10f);
        addressaCell.setUseAscender(true);
        addressaCell.setUseDescender(true);
        addressaCell.setBorderWidth(0.0f);
        addressaCell.setBorderWidthLeft(0.1f);
        addressaCell.setBorderWidthRight(0.1f);
        addressaCell.setColspan(4);
        tableBaseInfo.addCell(addressaCell);
        /****电话：******/
        PdfPCell phoneCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"电话：",storePhone), contentFont));
        phoneCell.setPaddingTop(10f);
        phoneCell.setUseAscender(true);
        phoneCell.setUseDescender(true);
        phoneCell.setBorderWidth(0.0f);
        phoneCell.setBorderWidthLeft(0.1f);
        phoneCell.setColspan(5);
        tableBaseInfo.addCell(phoneCell);
        PdfPCell phoneaCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"电话：",memberPhone), contentFont));
        phoneaCell.setPaddingTop(10f);
        phoneaCell.setUseAscender(true);
        phoneaCell.setUseDescender(true);
        phoneaCell.setBorderWidth(0.0f);
        phoneaCell.setBorderWidthLeft(0.1f);
        phoneaCell.setBorderWidthRight(0.1f);
        phoneaCell.setColspan(4);
        tableBaseInfo.addCell(phoneaCell);
        /****法定代表人：******/
        PdfPCell legalCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"法定代表人：",storeLegalPerson), contentFont));
        legalCell.setPaddingTop(10f);
        legalCell.setUseAscender(true);
        legalCell.setUseDescender(true);
        legalCell.setBorderWidth(0.0f);
        legalCell.setBorderWidthLeft(0.1f);
        legalCell.setColspan(5);
        tableBaseInfo.addCell(legalCell);
        PdfPCell legalaCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"法定代表人：",memberLegalPerson), contentFont));
        legalaCell.setPaddingTop(10f);
        legalaCell.setUseAscender(true);
        legalaCell.setUseDescender(true);
        legalaCell.setBorderWidth(0.0f);
        legalaCell.setBorderWidthLeft(0.1f);
        legalaCell.setBorderWidthRight(0.1f);
        legalaCell.setColspan(4);
        tableBaseInfo.addCell(legalaCell);
        /****日期******/
        PdfPCell dateCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"日期：",storeDate), contentFont));
        dateCell.setPaddingTop(10f);
        dateCell.setUseAscender(true);
        dateCell.setUseDescender(true);
        dateCell.setBorderWidth(0.0f);
        dateCell.setBorderWidthLeft(0.1f);
        dateCell.setColspan(5);
        tableBaseInfo.addCell(dateCell);
        PdfPCell dateaCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"日期：",memberDate), contentFont));
        dateaCell.setPaddingTop(10f);
        dateaCell.setUseAscender(true);
        dateaCell.setUseDescender(true);
        dateaCell.setBorderWidth(0.0f);
        dateaCell.setBorderWidthLeft(0.1f);
        dateaCell.setBorderWidthRight(0.1f);
        dateaCell.setColspan(4);
        tableBaseInfo.addCell(dateaCell);
        /****税号：******/
        PdfPCell einCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"税号：",storeTaxNo), contentFont));
        einCell.setPaddingTop(10f);
        einCell.setUseAscender(true);
        einCell.setUseDescender(true);
        einCell.setBorderWidth(0.0f);
        einCell.setBorderWidthLeft(0.1f);
        einCell.setColspan(5);
        tableBaseInfo.addCell(einCell);
        PdfPCell einaCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"税号：",memberTaxNo), contentFont));
        einaCell.setPaddingTop(10f);
        einaCell.setUseAscender(true);
        einaCell.setUseDescender(true);
        einaCell.setBorderWidth(0.0f);
        einaCell.setBorderWidthLeft(0.1f);
        einaCell.setBorderWidthRight(0.1f);
        einaCell.setColspan(4);
        tableBaseInfo.addCell(einaCell);
        /****开户行：******/
        PdfPCell openingBankCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"开户行：",storeBankName), contentFont));
        openingBankCell.setPaddingTop(10f);
        openingBankCell.setUseAscender(true);
        openingBankCell.setUseDescender(true);
        openingBankCell.setBorderWidth(0.0f);
        openingBankCell.setBorderWidthLeft(0.1f);
        openingBankCell.setColspan(5);
        tableBaseInfo.addCell(openingBankCell);
        PdfPCell openingBankaCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"开户行：",memberBankName), contentFont));
        openingBankaCell.setPaddingTop(10f);
        openingBankaCell.setUseAscender(true);
        openingBankaCell.setUseDescender(true);
        openingBankaCell.setBorderWidth(0.0f);
        openingBankaCell.setBorderWidthLeft(0.1f);
        openingBankaCell.setBorderWidthRight(0.1f);
        openingBankaCell.setColspan(4);
        tableBaseInfo.addCell(openingBankaCell);
        /****开户行地址：******/
        PdfPCell bankAddressCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"开户行地址：",storeBankAddress), contentFont));
        bankAddressCell.setPaddingTop(10f);
        bankAddressCell.setUseAscender(true);
        bankAddressCell.setUseDescender(true);
        bankAddressCell.setBorderWidth(0.0f);
        bankAddressCell.setBorderWidthLeft(0.1f);
        bankAddressCell.setColspan(5);
        tableBaseInfo.addCell(bankAddressCell);
        PdfPCell bankAddressaCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"开户行地址：",memberBankAddress), contentFont));
        bankAddressaCell.setPaddingTop(10f);
        bankAddressaCell.setUseAscender(true);
        bankAddressaCell.setUseDescender(true);
        bankAddressaCell.setBorderWidth(0.0f);
        bankAddressaCell.setBorderWidthLeft(0.1f);
        bankAddressaCell.setBorderWidthRight(0.1f);
        bankAddressaCell.setColspan(4);
        tableBaseInfo.addCell(bankAddressaCell);
        /***账号：**/
        PdfPCell bankAccounCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"账号：",storeBankAccount), contentFont));
        bankAccounCell.setPaddingTop(10f);
        bankAccounCell.setUseAscender(true);
        bankAccounCell.setUseDescender(true);
        bankAccounCell.setBorderWidth(0.0f);
        bankAccounCell.setBorderWidthLeft(0.1f);
        bankAccounCell.setBorderWidthBottom(0.1f);
        bankAccounCell.setColspan(5);
        tableBaseInfo.addCell(bankAccounCell);
        PdfPCell bankAccounaCell = new PdfPCell(new Phrase(String.format(STR_FORMAT,"账号：",memberBankAccount), contentFont));
        bankAccounaCell.setPaddingTop(10f);
        bankAccounaCell.setUseAscender(true);
        bankAccounaCell.setUseDescender(true);
        bankAccounaCell.setBorderWidth(0.0f);
        bankAccounaCell.setBorderWidthLeft(0.1f);
        bankAccounaCell.setBorderWidthRight(0.1f);
        bankAccounaCell.setBorderWidthBottom(0.1f);
        bankAccounaCell.setColspan(4);
        tableBaseInfo.addCell(bankAccounaCell);
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
        /**头部表格*/
        createSubjectTable(document);
        /**订单信息
         * **/
        createProductDetailsTable(document);
        createAgreementTable(document);
        //信息核验
        document.close();
        return SETTING.getFileUrl() + url;
    }


    public static void main(String[] args)
            throws IOException, DocumentException {
        List<Map<String,Object>> items = new ArrayList<>();
        Map<String,Object> itemMap = new HashMap<>();
        itemMap.put("proName","一次性民用口罩");
        itemMap.put("quantity","10000");
        itemMap.put("unitPrice",new BigDecimal("2.3"));
        itemMap.put("totalAmount",new BigDecimal("23000"));
        items.add(itemMap);
        Map<String,Object> itemaMap = new HashMap<>();
        itemaMap.put("proName","一次性民用口罩");
        itemaMap.put("quantity","10000");
        itemaMap.put("unitPrice",new BigDecimal("2.3"));
        itemaMap.put("totalAmount",new BigDecimal("23000"));
        items.add(itemaMap);
        Map<String,Object> itembMap = new HashMap<>();
        itembMap.put("proName","一次性民用口罩");
        itembMap.put("quantity","10000");
        itembMap.put("unitPrice",new BigDecimal("2.3"));
        itembMap.put("totalAmount",new BigDecimal("23000"));
        items.add(itembMap);
        ContractBuildImpl contractBuildImpl = new ContractBuildImpl();
        contractBuildImpl
                .setCompanyTtile("济南德宝瑞新材料有限公司")
                .setSellerName( "张家港佰陆纺织有限公司")
                .setBuyName("济南德宝瑞新材料有限公司")
                .setOrderNo( "2019008")
                .setItems(items)
                .setOrderTotalAmount(new BigDecimal("23000.00"))
                .setMemberName("张家港佰陆纺织有限公司")
                .setMemberAddress("张家港市凤凰镇镇北路43号")
                .setMemberPhone(" 0512-58625117")
                .setMemberLegalPerson(" 陆伟 13862200524")
                .setMemberDate("2020年3月17日")
                .setMemberTaxNo("91320 582MA 1PDAQ 489")
                .setMemberBankName("中国建设银行股份有限公司张家港万红支行")
                .setMemberBankAddress("张家港万红")
                .setMemberBankAccount("32250198628900000128")
                .setStoreName("济南德宝瑞新材料有限公司")
                .setStoreAdress("济南市长清区万德镇四区五零四号")
                .setStorePhone("0531-87463705/021-61833518")
                .setStoreLegalPerson("张丽霞")
                .setStoreDate("2020年3月17日")
                .setStoreTaxNo("91370 113MA 3NNC0 A3P")
                .setStoreBankName("中国建设银行股份有限公司济南长清支行")
                .setStoreBankAddress("山东省济南市长清区经十西路1454号")
                .setStoreBankAccount("37050161720800000665");
        contractBuildImpl.generateContract();
    }
}
