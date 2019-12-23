/*
 *
 * 
 *
 * 
 */
package net.mall;

/**
 * 公共参数
 * 
 * @author huanghy
 * @version 6.1
 */
public final class CommonAttributes {

	/**
	 * 日期格式配比
	 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	/**
	 * mall.xml文件路径
	 */
	public static final String MALL_XML_PATH = "classpath:mall.xml";

	/**
	 * mall.properties文件路径
	 */
	public static final String MALL_PROPERTIES_PATH = "classpath:mall.properties";

	/**
	 * 不可实例化
	 */
	private CommonAttributes() {
	}

}