package net.mall.util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import net.mall.Setting;
import net.mall.entity.Business;
import net.mall.entity.Member;
import net.mall.entity.Order;
import net.mall.entity.OrderItem;
import net.mall.entity.PaymentMethod;
import net.mall.entity.Store;
/***
 * PdfUtil概要说明：创建合同
 * @author Huanghy
 */
public class PdfUtil {
	
	/***
	 * 边框大小
	 */
	 private static final float BODER_SIZE = 0.1f;
	 private static final float TITILE_FONT_SIZE = 14f;
	 private static final float TABLE_HEAD_FONT_SIZE = 12f;
	 private static  Font  TITLE_FONT;
	 private static  Font  TABLE_HEAD_FONT;
	 private static  Font  CLAUSE_FONT;
	 private static  Font  CONTENT_FONT;
	 private static Setting  SETTING;
	 private static final String MONTH_FMT = "yyyyMM";
	 private static final String DATE_TIME_FMT = "yyyy-MM-dd HH:mm:ss";
	 
	 /***初始化字体
	  */
	 static {
		try {
			BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			TITLE_FONT = new Font(baseFont,TITILE_FONT_SIZE,Font.BOLD);
			TABLE_HEAD_FONT = new Font(baseFont,TABLE_HEAD_FONT_SIZE,Font.BOLD);
			CLAUSE_FONT = new Font(baseFont,TITILE_FONT_SIZE);
			CONTENT_FONT =  new Font(baseFont,TABLE_HEAD_FONT_SIZE);
			SETTING = SystemUtils.getSetting();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		 
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
        table.setSpacingBefore(5f); // 前间距
        table.setSpacingAfter(0.5f); // 后间距
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell cell = new PdfPCell(new Phrase("化纤产品采购合同",TITLE_FONT));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(11);
        cell.setBorder(0);
        table.addCell(cell);
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
    private static void createHeadTable(Document document,Order order)
    		throws DocumentException{
    	PdfPTable tableBaseInfo = new PdfPTable(11);
    	tableBaseInfo.setWidthPercentage(100); // 宽度100%填充
    	tableBaseInfo.setSpacingBefore(10f); // 前间距
    	/***供应商**/
    	Store store = order.getStore();
    	Business business = store.getBusiness();
    	String storeName = (null != store.getName()?store.getName():business.getName());
    	String storeZipCode = (null !=store.getZipCode()?store.getZipCode():"");
    	String storeAddress = (null != store.getAddress()?store.getAddress():"");
    	String storePhone = (null != store.getPhone()?store.getPhone():business.getPhone());
    	String storeBankName = (null != business.getBankName()?business.getBankName():"");
    	String storeBankAccount = (null != business.getBankAccount()?business.getBankAccount():"");
    	/***采购商*/
    	Member member = order.getMember();
    	String memberName = (null != member.getName()?member.getName():"");
    	String memberZipCode = (null != member.getZipCode()?member.getZipCode():order.getZipCode());
    	String memberAddress = (null != member.getAddress()?member.getAddress():order.getAddress());
    	String memberPhone = (null != member.getPhone()?member.getPhone():order.getPhone());
    	String memberBankName = (null != member.getBankName()?member.getBankName():"");
    	String memberBankAccount = (null != member.getBankAccount()?member.getBankAccount():"");
    	/***************1行*******************************/
        PdfPCell cellBaseInfo  = new PdfPCell(new Phrase("供方:",CONTENT_FONT));
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setBorderWidthTop(BODER_SIZE);
        cellBaseInfo.setBorderWidthLeft(BODER_SIZE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(2);
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setBorderWidthTop(BODER_SIZE);
        cellBaseInfo.addElement(new Paragraph(storeName,CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("邮编:",CONTENT_FONT));
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setBorderWidthTop(BODER_SIZE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(2);
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setBorderWidthTop(BODER_SIZE);
        cellBaseInfo.setBorderWidthRight(BODER_SIZE);
        cellBaseInfo.addElement(new Paragraph(storeZipCode,CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("需方:",CONTENT_FONT));
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setBorderWidthTop(BODER_SIZE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(2);
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setBorderWidthTop(BODER_SIZE);
        cellBaseInfo.addElement(new Paragraph(memberName,CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("邮编:",CONTENT_FONT));
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setBorderWidthTop(BODER_SIZE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setBorderWidthTop(BODER_SIZE);
        cellBaseInfo.setBorderWidthRight(BODER_SIZE);
        cellBaseInfo.addElement(new Paragraph(memberZipCode,CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        /***************2行*******************************/
        cellBaseInfo = new PdfPCell(new Phrase("地址:",CONTENT_FONT));
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setBorderWidthLeft(BODER_SIZE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(2);
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.addElement(new Paragraph(storeAddress,CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("电话:",CONTENT_FONT));
        createAscDesc(cellBaseInfo,true);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(2);
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setBorderWidthRight(BODER_SIZE);
        cellBaseInfo.addElement(new Paragraph(storePhone,CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("地址:",CONTENT_FONT));
        createAscDesc(cellBaseInfo,true);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(2);
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.addElement(new Paragraph(memberAddress,CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("电话:",CONTENT_FONT));
        createAscDesc(cellBaseInfo,true);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setBorderWidthRight(BODER_SIZE);
        cellBaseInfo.addElement(new Paragraph(memberPhone,CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        /***************3行*******************************/
        cellBaseInfo = new PdfPCell(new Phrase("银行:",CONTENT_FONT));
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setBorderWidthLeft(BODER_SIZE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(2);
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.addElement(new Paragraph(storeBankName,CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("传真:",CONTENT_FONT));
        createAscDesc(cellBaseInfo,true);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(2);
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setBorderWidthRight(BODER_SIZE);
        cellBaseInfo.addElement(new Paragraph(storePhone,CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("银行:",CONTENT_FONT));
        createAscDesc(cellBaseInfo,true);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(2);
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.addElement(new Paragraph(memberBankName,CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("传真:",CONTENT_FONT));
        createAscDesc(cellBaseInfo,true);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setBorderWidthRight(BODER_SIZE);
        cellBaseInfo.addElement(new Paragraph(memberPhone,CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        /***************4行*******************************/
        cellBaseInfo = new PdfPCell(new Phrase("账号:",CONTENT_FONT));
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setBorderWidthLeft(BODER_SIZE);
        cellBaseInfo.setBorderWidthBottom(BODER_SIZE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(5);
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setBorderWidthBottom(BODER_SIZE);
        cellBaseInfo.setBorderWidthRight(BODER_SIZE);
        cellBaseInfo.addElement(new Paragraph(storeBankAccount,CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("账号:",CONTENT_FONT));
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setBorderWidthBottom(BODER_SIZE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(4);
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setBorderWidthBottom(BODER_SIZE);
        cellBaseInfo.setBorderWidthRight(BODER_SIZE);
        cellBaseInfo.addElement(new Paragraph(memberBankAccount,CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        document.add(tableBaseInfo);
    }
    
    
    /***
     * createAscDesc方法慨述:设置排序
     * @param cellBaseInfo void
     * @创建人 huanghy
     * @创建时间 2019年12月30日 上午8:54:31
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    private static void createAscDesc(PdfPCell cellBaseInfo,boolean hiddenBorder){
    	cellBaseInfo.setUseAscender(true);
        cellBaseInfo.setUseDescender(true);
        if(hiddenBorder){
        	cellBaseInfo.setBorder(0);
        }
    }
    
    
    /***
     * calculateWeight方法慨述:计算重量
     * @param order
     * @return String
     * @创建人 huanghy
     * @创建时间 2019年12月30日 下午12:00:48
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    private static String calculateWeight(Order order){
    	DecimalFormat df = new DecimalFormat("#,##0.000");
    	df.setRoundingMode(RoundingMode.HALF_UP); 
    	BigDecimal divideScl = new BigDecimal(1000);
    	BigDecimal totalWeight = order.getOrderItems().stream().map(orderItem -> {
    		
    		return orderItem.getSku().getTotalUnit()
    				.multiply(new BigDecimal(orderItem.getQuantity())
    						.divide(divideScl));
    	}).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    	return df.format(totalWeight);
    }
    
    
    /***
     * calculateWeight方法慨述:计算总量
     * @param orderItem
     * @return String
     * @创建人 huanghy
     * @创建时间 2019年12月30日 下午2:14:40
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    private static String calculateWeight(OrderItem orderItem){
    	DecimalFormat df = new DecimalFormat("#,##0.000");
    	df.setRoundingMode(RoundingMode.HALF_UP); 
    	BigDecimal divideScl = new BigDecimal(1000);
    	BigDecimal totalWeight = orderItem.getSku().getTotalUnit()
				.multiply(new BigDecimal(orderItem.getQuantity())
						.divide(divideScl));
	    	return df.format(totalWeight);
    }
    
    /***
     * calculateSpec方法慨述:获取规格
     * @param specs
     * @return String
     * @创建人 huanghy
     * @创建时间 2019年12月30日 下午2:10:00
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    private static String calculateSpec(List<String> specs){
    	StringBuilder sb = new StringBuilder(100);
    	specs.stream().forEach(spc -> {
    		sb.append(spc);
    		sb.append("/");
    	});
    	return sb.toString().substring(0,sb.lastIndexOf("/"));
    }
    
    
    /***
     * calculateAmount方法慨述:计算金额
     * @param amount
     * @return String
     * @创建人 huanghy
     * @创建时间 2019年12月30日 下午1:17:13
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    private static String calculateAmount(BigDecimal amount,boolean hiddenYuan){
    	DecimalFormat df = new DecimalFormat("#,##0.000");
    	df.setRoundingMode(RoundingMode.HALF_UP); 
    	if(hiddenYuan){
    		return df.format(amount);
    	} else {
    		return df.format(amount) + "元";
    	}
    	
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
    private static void createOrderTable(Document document,Order order) 
    		throws DocumentException{
    	PdfPTable tableBaseInfo = new PdfPTable(11);
    	tableBaseInfo.setWidthPercentage(100); // 宽度100%填充
        /***************1行*******************************/
        PdfPCell cellBaseInfo = new PdfPCell(new Phrase("订单号",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("签约地点",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("生成日期",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("生效日期",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("结算方式",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("合约总重量（吨）",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("不含税金额（元）",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("增值税（元）",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("合约总金额（元）",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("定金金额（元）",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("价格类型",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        /********实际值******/
        cellBaseInfo = new PdfPCell(new Phrase(order.getSn(),CONTENT_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
    	if(order.getPaymentMethod().equals(PaymentMethod.Method.ONLINE)){
    		cellBaseInfo.addElement(new Phrase("线上",CONTENT_FONT));
    	}
    	if(order.getPaymentMethod().equals(PaymentMethod.Method.OFFLINE)){
    		cellBaseInfo.addElement(new Phrase("线下",CONTENT_FONT));
    	}
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("生成日期",CONTENT_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        java.text.SimpleDateFormat dateTime = new java.text.SimpleDateFormat(DATE_TIME_FMT);
        
        cellBaseInfo.addElement(new Phrase(dateTime.format(new Date()),CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellBaseInfo.addElement(new Phrase("全额支付",CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellBaseInfo.addElement(new Phrase(calculateWeight(order),CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellBaseInfo.addElement(new Phrase(calculateAmount(order.getAmount(),true),CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("增值税（元）",CONTENT_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        if(null != order.getTax()){
        	 cellBaseInfo.addElement(new Phrase(calculateAmount(order.getTax(),true),CONTENT_FONT));
        }
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellBaseInfo.addElement(new Phrase(calculateAmount(order.getAmount(),true),CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellBaseInfo.addElement(new Phrase(calculateAmount(BigDecimal.ZERO,true),CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
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
        /***************1行*******************************/
    	String strContent = "合同条款：\n" +
				"1.需方注册并登录福布云商平台阅读并同意《用户注册协议》、《卖家交易规则》、《买家交易规则》。\n" + 
				"2.本合同为线上生成的电子合同，合同生成时即生效。\n" + 
				"3.质量异议解决办法具体以工厂为准，签订理赔协议的时限一般为立项后45个工作日。";
        PdfPCell cellBaseInfo = new PdfPCell(new Phrase(strContent,CLAUSE_FONT));
        cellBaseInfo.setColspan(11);
        cellBaseInfo.setNoWrap(false);
        createAscDesc(cellBaseInfo,false);
        tableBaseInfo.addCell(cellBaseInfo);
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
    private static void createOrderItemTable(Document document,Order order)
    		throws DocumentException{
    	PdfPTable tableBaseInfo = new PdfPTable(11);
    	tableBaseInfo.setWidthPercentage(100); // 宽度100%填充
        /***************1行*******************************/
        PdfPCell cellBaseInfo  = new PdfPCell(new Phrase("序号",TABLE_HEAD_FONT));
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellBaseInfo.setRowspan(2);
        createAscDesc(cellBaseInfo,false);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("品种",TABLE_HEAD_FONT));
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellBaseInfo.setRowspan(2);
        createAscDesc(cellBaseInfo,false);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("牌号",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("规格说明",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("产地",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("存放地",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("运输方式",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("规格详情",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("重量规格",TABLE_HEAD_FONT));
        cellBaseInfo.setColspan(3);
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        /*************二行****************/
        cellBaseInfo = new PdfPCell(new Phrase("总重量",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("挂牌价格（含税）",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("平台成交价（含税）",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("代理费（含税）",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("合同价格（含税）",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("合同金额（含税）",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("仓库",TABLE_HEAD_FONT));
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("特殊说明",TABLE_HEAD_FONT));
        cellBaseInfo.setColspan(2);
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        /********循环表格*********/
        List<OrderItem> orderItems = order.getOrderItems();
        BigDecimal totalAmount = orderItems.stream().map(OrderItem::getSubtotal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        if(null != orderItems){        	
        	orderItems.stream().forEach(orderItem -> {
        		int arrayIndex = order.getArrayIndex();
        		arrayIndex++;
        		PdfPCell cellSubBaseInfo  = new PdfPCell(new Phrase(String.valueOf(arrayIndex),TABLE_HEAD_FONT));
                cellSubBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSubBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellSubBaseInfo.setRowspan(2);
                createAscDesc(cellSubBaseInfo,false);
                tableBaseInfo.addCell(cellSubBaseInfo);
                cellSubBaseInfo = new PdfPCell(new Phrase(orderItem.getProduct().getName(),TABLE_HEAD_FONT));
                cellSubBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSubBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellSubBaseInfo.setRowspan(2);
                createAscDesc(cellSubBaseInfo,false);
                tableBaseInfo.addCell(cellSubBaseInfo);
                cellSubBaseInfo = new PdfPCell(new Phrase(orderItem.getSn(),TABLE_HEAD_FONT));
                createAscDesc(cellSubBaseInfo,false);
                cellSubBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSubBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableBaseInfo.addCell(cellSubBaseInfo);
                cellSubBaseInfo = new PdfPCell(new Phrase(calculateSpec(orderItem.getSpecifications()),TABLE_HEAD_FONT));
                createAscDesc(cellSubBaseInfo,false);
                cellSubBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSubBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableBaseInfo.addCell(cellSubBaseInfo);
                cellSubBaseInfo = new PdfPCell();
                createAscDesc(cellSubBaseInfo,false);
                cellSubBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSubBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableBaseInfo.addCell(cellSubBaseInfo);
                cellSubBaseInfo = new PdfPCell();
                createAscDesc(cellSubBaseInfo,false);
                cellSubBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSubBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableBaseInfo.addCell(cellSubBaseInfo);
                cellSubBaseInfo = new PdfPCell(new Phrase("供方提供运输服务",TABLE_HEAD_FONT));
                createAscDesc(cellSubBaseInfo,false);
                cellSubBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSubBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableBaseInfo.addCell(cellSubBaseInfo);
                cellSubBaseInfo = new PdfPCell();
                createAscDesc(cellSubBaseInfo,false);
                cellSubBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSubBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableBaseInfo.addCell(cellSubBaseInfo);
                cellSubBaseInfo = new PdfPCell();
                cellSubBaseInfo.setColspan(3);
                createAscDesc(cellSubBaseInfo,false);
                cellSubBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSubBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableBaseInfo.addCell(cellSubBaseInfo);
                /*************二行****************/
                cellSubBaseInfo = new PdfPCell(new Phrase(calculateWeight(orderItem),TABLE_HEAD_FONT));
                createAscDesc(cellSubBaseInfo,false);
                cellSubBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSubBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableBaseInfo.addCell(cellSubBaseInfo);
                cellSubBaseInfo = new PdfPCell(new Phrase(calculateAmount(orderItem.getPrice(),true),TABLE_HEAD_FONT));
                createAscDesc(cellSubBaseInfo,false);
                cellSubBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSubBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableBaseInfo.addCell(cellSubBaseInfo);
                cellSubBaseInfo = new PdfPCell(new Phrase(calculateAmount(orderItem.getPrice(),true),TABLE_HEAD_FONT));
                createAscDesc(cellSubBaseInfo,false);
                cellSubBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSubBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableBaseInfo.addCell(cellSubBaseInfo);
                cellSubBaseInfo = new PdfPCell(new Phrase(calculateAmount(BigDecimal.ZERO,true),TABLE_HEAD_FONT));
                createAscDesc(cellSubBaseInfo,false);
                cellSubBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSubBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableBaseInfo.addCell(cellSubBaseInfo);
                cellSubBaseInfo = new PdfPCell(new Phrase(calculateAmount(orderItem.getPrice(),true),TABLE_HEAD_FONT));
                createAscDesc(cellSubBaseInfo,false);
                cellSubBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSubBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableBaseInfo.addCell(cellSubBaseInfo);
                cellSubBaseInfo = new PdfPCell(new Phrase(calculateAmount(orderItem.getPrice(),true),TABLE_HEAD_FONT));
                createAscDesc(cellSubBaseInfo,false);
                cellSubBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSubBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableBaseInfo.addCell(cellSubBaseInfo);
                cellSubBaseInfo = new PdfPCell();
                createAscDesc(cellSubBaseInfo,false);
                cellSubBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSubBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableBaseInfo.addCell(cellSubBaseInfo);
                cellSubBaseInfo = new PdfPCell(new Phrase());
                cellSubBaseInfo.setColspan(2);
                createAscDesc(cellSubBaseInfo,false);
                cellSubBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellSubBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableBaseInfo.addCell(cellSubBaseInfo);
                order.setArrayIndex(arrayIndex);
        	});
        }
        /*************三行****************/
        cellBaseInfo = new PdfPCell(new Phrase("合计",CONTENT_FONT));
        cellBaseInfo.setColspan(2);
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellBaseInfo.addElement(new Phrase(calculateAmount(totalAmount,false),CONTENT_FONT));
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(4);
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell();
        cellBaseInfo.setColspan(3);
        createAscDesc(cellBaseInfo,false);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellBaseInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableBaseInfo.addCell(cellBaseInfo);
        document.add(tableBaseInfo);
    }
    
    
    
    /***
     * generateContract方法慨述:
     * @param order
     * @return
     * @throws FileNotFoundException
     * @throws DocumentException String
     * @创建人 huanghy
     * @创建时间 2019年12月30日 上午10:52:59
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    public static String generateContract(Order order) 
    		throws FileNotFoundException, DocumentException{
    	String uploadDir = SETTING.getUploadDir();
    	String  contractPath = SETTING.getTempleatePath();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(MONTH_FMT);
        String monthDir = sdf.format(new Date());
        String tFilePath = uploadDir + contractPath + File.separator + monthDir;
        File  monthFile = new File(tFilePath);
        if(!monthFile.exists()){
        	monthFile.mkdir();
        }
        String fileName = UUID.randomUUID() + ".pdf";
        String filePath = tFilePath + File.separator + fileName;
    	File file = new File(filePath);
    	String url = contractPath + File.separator + monthDir + File.separator + fileName;
	    //生成PDF文档
	    Document document = new Document(PageSize.A4);
	    PdfWriter.getInstance(document,new FileOutputStream(file));
	    document.open();
	    // 标题
	    createTitle(document);
	    /**头部表格*/
	    createHeadTable(document,order);
	    /**订单信息
	     * **/
	    createOrderTable(document,order);
	    /**条款
	     * */
	    createClauseTable(document);
	    /***商品详情
	     */
	    createOrderItemTable(document,order);
	    /**盖章地方
	     * **/
	    createSealTable(document);
	    //信息核验
	    document.close();
	    return SETTING.getFileUrl() + url;
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
    	tableBaseInfo.setSpacingBefore(25f); // 前间距
    	tableBaseInfo.setSpacingAfter(10f); // 后间距
        /***************1行*******************************/
    	PdfPCell cellBaseInfo  = new PdfPCell();
        cellBaseInfo.setColspan(3);
        createAscDesc(cellBaseInfo,true);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("采购方签字盖章",CONTENT_FONT));
        cellBaseInfo.setColspan(4);
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_LEFT);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("供应商签字盖章",CONTENT_FONT));
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellBaseInfo.setColspan(5);
        createAscDesc(cellBaseInfo,true);
        tableBaseInfo.addCell(cellBaseInfo);
        /*************二行****************/
        cellBaseInfo  = new PdfPCell();
        cellBaseInfo.setColspan(3);
        createAscDesc(cellBaseInfo,true);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("2019-10-11",CONTENT_FONT));
        cellBaseInfo.setColspan(4);
        createAscDesc(cellBaseInfo,true);
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_LEFT);
        tableBaseInfo.addCell(cellBaseInfo);
        cellBaseInfo = new PdfPCell(new Phrase("2019-12-12",CONTENT_FONT));
        cellBaseInfo.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellBaseInfo.setColspan(5);
        createAscDesc(cellBaseInfo,true);
        tableBaseInfo.addCell(cellBaseInfo);
        document.add(tableBaseInfo);
    }
}
