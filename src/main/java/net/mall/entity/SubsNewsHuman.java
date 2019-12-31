package net.mall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
/***
 * SubsNewsHuman概要说明：订阅记录
 * @author Huanghy
 */
@Entity
public class SubsNewsHuman extends BaseEntity<Long>{

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */ 
	private static final long serialVersionUID = 7799050859874485576L;

	
	
	@Column(nullable = false,name="human_id")
	private Long humanId;
	
	@Column(nullable = false,name="data_id")
	private Long dataId;
	
	@Column(nullable = false,name="sn")
	private String sn;
	
	
	/***
	 * 
	 */
	@Column(nullable = false,name="expd")
	private Date expd;
}
