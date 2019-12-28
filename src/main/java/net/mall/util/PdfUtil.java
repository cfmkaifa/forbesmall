package net.mall.util;
import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfUtil {
	 /**
     * @param args
     */
    public static void main(String[] args) throws Exception {	
		    //测试pdf保存路径
		    String filePath = "C:/opt/pdfPath/demoxs.pdf";
		//    Image image = Image.getInstance(IMG);
		    File file = new File(filePath);
		    //生成PDF文档
		    Document document = new Document(PageSize.A4);
		    PdfWriter.getInstance(document,new FileOutputStream(file));
		    document.open();
		    // 标题
		    createTitle(document);
		    /**头部表格*/
		    createHeadTable(document);
		    /**订单信息
		     * **/
		    createOrderTable(document);
		    /**条款
		     * */
		    createClauseTable(document);
		    /***商品详情
		     */
		    createOrderItemTable(document);
		    /**盖章地方
		     * **/
		    createSealTable(document);
		    //信息核验
		    document.close();
    }
    
    
    /***
     * createTitle方法慨述:标题
     * @param document
     * @throws DocumentException void
     * @创建人 huanghy
     * @创建时间 2019年12月28日 下午1:30:46
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    private static void createTitle(Document document)
    		throws DocumentException{
    	PdfPTable table = new PdfPTable(11);
        table.setWidthPercentage(100); // 宽度100%填充
        table.setSpacingBefore(10f); // 前间距
        table.setSpacingAfter(10f); // 后间距
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell cell = new PdfPCell(new Phrase("化纤产品采购合同",iTextPDFUtil.getColorFont()));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(11);
        table.addCell(iTextPDFUtil.addBaseCell(cell));
        document.add(table);
    }
    
    
    /***
     * createHeadTable方法慨述:采购商/供应商信息
     * @param document
     * @throws DocumentException void
     * @创建人 huanghy
     * @创建时间 2019年12月28日 下午1:34:13
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    private static void createHeadTable(Document document)
    		throws DocumentException{
    	PdfPTable tableBaseInfo = new PdfPTable(11);
    	tableBaseInfo.setWidthPercentage(100); // 宽度100%填充
    	tableBaseInfo.setSpacingBefore(10f); // 前间距
    	tableBaseInfo.setSpacingAfter(10f); // 后间距
        /***************1行*******************************/
        //供方
        PdfPCell cellBaseInfo  = new PdfPCell(new Phrase("供方:",iTextPDFUtil.getColorFont()));
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(2);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        //邮编
        cellBaseInfo = new PdfPCell(new Phrase("邮编:",iTextPDFUtil.getColorFont()));
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(2);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        //需方
        cellBaseInfo = new PdfPCell(new Phrase("需方:",iTextPDFUtil.getColorFont()));
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(2);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        //邮编
        cellBaseInfo = new PdfPCell(new Phrase("邮编:",iTextPDFUtil.getColorFont()));
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell();
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        /***************2行*******************************/
        //供方
        cellBaseInfo = new PdfPCell(new Phrase("地址:",iTextPDFUtil.getColorFont()));
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(2);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        //邮编
        cellBaseInfo = new PdfPCell(new Phrase("电话:",iTextPDFUtil.getColorFont()));
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(2);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        //需方
        cellBaseInfo = new PdfPCell(new Phrase("地址:",iTextPDFUtil.getColorFont()));
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(2);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        //邮编
        cellBaseInfo = new PdfPCell(new Phrase("电话:",iTextPDFUtil.getColorFont()));
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell();
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        /***************3行*******************************/
        //供方
        cellBaseInfo = new PdfPCell(new Phrase("银行:",iTextPDFUtil.getColorFont()));
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(2);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        //邮编
        cellBaseInfo = new PdfPCell(new Phrase("传真:",iTextPDFUtil.getColorFont()));
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(2);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        //需方
        cellBaseInfo = new PdfPCell(new Phrase("银行:",iTextPDFUtil.getColorFont()));
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(2);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        //邮编
        cellBaseInfo = new PdfPCell(new Phrase("传真:",iTextPDFUtil.getColorFont()));
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell();
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        /***************4行*******************************/
        //供方
        cellBaseInfo = new PdfPCell(new Phrase("账号:",iTextPDFUtil.getColorFont()));
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(5);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        //邮编
        cellBaseInfo = new PdfPCell(new Phrase("账号:",iTextPDFUtil.getColorFont()));
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(4);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        document.add(tableBaseInfo);
    }
    
    
    /***
     * createBorder方法慨述:设置边框线
     * @param cellBaseInfo void
     * @创建人 huanghy
     * @创建时间 2019年12月28日 下午1:57:10
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    private static void createBorder(PdfPCell cellBaseInfo){
    	cellBaseInfo.setBorderWidthTop(1);
        cellBaseInfo.setBorderWidthLeft(1);
        cellBaseInfo.setBorderWidthRight(1);
        cellBaseInfo.setBorderWidthBottom(1);
        cellBaseInfo.setUseAscender(true);
        cellBaseInfo.setUseDescender(true);
    }
    
    /***
     * createOrderTable方法慨述:订单
     * @param document
     * @throws DocumentException void
     * @创建人 huanghy
     * @创建时间 2019年12月28日 下午1:41:59
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    private static void createOrderTable(Document document) 
    		throws DocumentException{
    	PdfPTable tableBaseInfo = new PdfPTable(11);
    	tableBaseInfo.setWidthPercentage(100); // 宽度100%填充
    	tableBaseInfo.setSpacingBefore(10f); // 前间距
    	tableBaseInfo.setSpacingAfter(10f); // 后间距
        /***************1行*******************************/
        PdfPCell cellBaseInfo = new PdfPCell(new Phrase("订单号",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell(new Phrase("签约地点",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell(new Phrase("生成日期",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell(new Phrase("生效日期",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell(new Phrase("结算方式",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell(new Phrase("合约总重量（吨）",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell(new Phrase("不含税金额（元）",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell(new Phrase("增值税（元）",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell(new Phrase("合约总金额（元）",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell(new Phrase("定金金额（元）",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        cellBaseInfo = new PdfPCell(new Phrase("价格类型",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        document.add(tableBaseInfo);
    }
    
    /***
     * createClauseTable方法慨述:设置条款信息
     * @param document
     * @throws DocumentException void
     * @创建人 huanghy
     * @创建时间 2019年12月28日 下午2:08:10
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    private static void createClauseTable(Document document) 
    		throws DocumentException{
    	PdfPTable tableBaseInfo = new PdfPTable(11);
    	tableBaseInfo.setWidthPercentage(100); // 宽度100%填充
    	tableBaseInfo.setSpacingBefore(10f); // 前间距
    	tableBaseInfo.setSpacingAfter(10f); // 后间距
        /***************1行*******************************/
    	String strContent = "合同条款：\n" +
				"1.需方注册并登录福布云商平台阅读并同意《用户注册协议》、《卖家交易规则》、《买家交易规则》。\n" + 
				"2.本合同为线上生成的电子合同，合同生成时即生效。\n" + 
				"3.质量异议解决办法具体以工厂为准，签订理赔协议的时限一般为立项后45个工作日。";
        PdfPCell cellBaseInfo = new PdfPCell(new Phrase(strContent,iTextPDFUtil.getColorFont()));
        cellBaseInfo.setColspan(11);
        cellBaseInfo.setNoWrap(false);
        tableBaseInfo.addCell(iTextPDFUtil.addBaseCell(cellBaseInfo));
        document.add(tableBaseInfo);
    }
    
    /***
     * createOrderItemTable方法慨述:商品详情
     * @param document
     * @throws DocumentException void
     * @创建人 huanghy
     * @创建时间 2019年12月28日 下午2:37:31
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    private static void createOrderItemTable(Document document)
    		throws DocumentException{
    	PdfPTable tableBaseInfo = new PdfPTable(11);
    	tableBaseInfo.setWidthPercentage(100); // 宽度100%填充
    	tableBaseInfo.setSpacingBefore(10f); // 前间距
    	tableBaseInfo.setSpacingAfter(10f); // 后间距
        /***************1行*******************************/
        PdfPCell cellBaseInfo  = new PdfPCell(new Phrase("序号",iTextPDFUtil.getColorFont()));
        cellBaseInfo.setRowspan(2);
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("品种",iTextPDFUtil.getColorFont()));
        cellBaseInfo.setRowspan(2);
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("牌号",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("规格说明",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("产地",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("存放地",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("运输方式",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("规格详情",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("重量规格",iTextPDFUtil.getColorFont()));
        cellBaseInfo.setColspan(3);
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        /*************二行****************/
        cellBaseInfo = new PdfPCell(new Phrase("总重量",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("挂牌价格（含税）",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("平台成交价（含税）",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("代理费（含税）",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("合同价格（含税）",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("合同金额（含税）",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("仓库",iTextPDFUtil.getColorFont()));
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("特殊说明",iTextPDFUtil.getColorFont()));
        cellBaseInfo.setColspan(2);
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        /*************三行****************/
        cellBaseInfo = new PdfPCell(new Phrase("合计",iTextPDFUtil.getColorFont()));
        cellBaseInfo.setColspan(2);
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(4);
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(3);
        createBorder(cellBaseInfo);
        tableBaseInfo.addCell(cellBaseInfo);
        document.add(tableBaseInfo);
    }
    
    
    /***
     * createSealTable方法慨述:设置签章
     * @param document
     * @throws DocumentException void
     * @创建人 huanghy
     * @创建时间 2019年12月28日 下午3:23:00
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    public static void createSealTable(Document document) 
    		throws DocumentException{
    	PdfPTable tableBaseInfo = new PdfPTable(11);
    	tableBaseInfo.setWidthPercentage(100); // 宽度100%填充
    	tableBaseInfo.setSpacingBefore(10f); // 前间距
    	tableBaseInfo.setSpacingAfter(10f); // 后间距
        /***************1行*******************************/
    	PdfPCell cellBaseInfo  = new PdfPCell();
        cellBaseInfo.setColspan(3);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("采购方签字盖章",iTextPDFUtil.getColorFont()));
        cellBaseInfo.setColspan(4);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("供应商签字",iTextPDFUtil.getColorFont()));
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellBaseInfo.setColspan(5);
        tableBaseInfo.addCell(cellBaseInfo);
        /*************二行****************/
        cellBaseInfo  = new PdfPCell();
        cellBaseInfo.setColspan(3);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("2019-10-11",iTextPDFUtil.getColorFont()));
        cellBaseInfo.setColspan(4);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("2019-12-12",iTextPDFUtil.getColorFont()));
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellBaseInfo.setColspan(5);
        tableBaseInfo.addCell(cellBaseInfo);
        document.add(tableBaseInfo);
    }
}
